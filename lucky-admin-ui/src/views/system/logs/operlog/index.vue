<template>
  <div class="user-management">
    <div v-permission="['system::logs::oprLogs::list']" class="search-bar">
      <div class="grid-item">
        <span>
          模块标题:
        </span>
        <el-input v-model="searchForm.title" placeholder="请输入模块标题" style="width: 200px; " />
      </div>
      <div class="grid-item">
        <span>
          业务类型:
        </span>
        <el-select v-model="searchForm.businessType" placeholder="请选择业务类型" style="width: 200px; ">
          <el-option v-for="item in businessTypes" :key="item.value" :label="item.label" :value="item.value" />
        </el-select>
      </div>
      <div class="grid-item">
        <span>
          方法名称:
        </span>
        <el-input v-model="searchForm.methodName" placeholder="请输入方法名称" style="width: 200px; " />
      </div>
      <div class="grid-item">
        <span>
          请求方式:
        </span>
        <el-select v-model="searchForm.requestMethod" placeholder="请选择请求方式" style="width: 200px; ">
          <el-option v-for="item in requestMenthods" :key="item.value" :label="item.label" :value="item.value" />
        </el-select>
      </div>
      <div class="grid-item">
        <span>
          操作人员:
        </span>
        <el-input v-model="searchForm.username" placeholder="请输入操作人员" style="width: 200px; " />
      </div>
      <div class="grid-item">
        <span>
          请求URL:
        </span>
        <el-input v-model="searchForm.url" placeholder="请输入请求URL" style="width: 200px;" />
      </div>
      <div class="grid-item">
        <span>
          操作状态:
        </span>
        <el-select v-model="searchForm.status" placeholder="操作状态" style="width: 200px; ">
          <el-option v-for="item in statusItem" :key="item.value" :label="item.label" :value="item.value" />
        </el-select>
      </div>
      <div class="grid-item">
        <span>
          <el-button type="primary" icon="el-icon-search" @click="handleSearch">搜索</el-button>
        </span>

      </div>
    </div>
    <div
      v-permission="['system::logs::oprLogs::delete', 'system::logs::oprLogs::export', 'system::logs::oprLogs::list']"
      class="button-bar"
    >
      <el-button
        v-permission="['system::logs::oprLogs::delete']"
        :type="buttonBar.deleteType"
        :size="buttonBar.size"
        :plain="buttonBar.plain"
        @click="handleBatchDelete"
      >批量删除</el-button>
      <el-button
        v-permission="['system::logs::oprLogs::export']"
        :type="buttonBar.exportType"
        :size="buttonBar.size"
        :plain="buttonBar.plain"
        @click="handleExport"
      >导出</el-button>
      <el-button
        v-permission="['system::logs::oprLogs::list']"
        :type="buttonBar.reFreshType"
        :size="buttonBar.size"
        :plain="buttonBar.plain"
        @click="handleRefresh"
      >刷新</el-button>
    </div>
    <el-table
      v-loading.fullscreen.lock="tableLoading"
      :data="tableData"
      style="width: 100%"
      border
      max-height="560"
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" width="55" v-bind="columnProps" />
      <el-table-column type="index" width="50" label="序号" v-bind="columnProps" />
      <!-- <el-table-column prop="id" label="日志主键" v-bind="columnProps" /> -->
      <el-table-column prop="title" label="模块标题" v-bind="columnProps" />
      <el-table-column prop="businessType" label="业务类型" v-bind="columnProps">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.businessType === 1">新增</el-tag>
          <el-tag v-if="scope.row.businessType === 2" type="warning">修改</el-tag>
          <el-tag v-if="scope.row.businessType === 3" type="danger">删除</el-tag>
          <el-tag v-if="scope.row.businessType === 8">代码生成</el-tag>
          <el-tag v-if="scope.row.businessType === 0" type="info">其他</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="methodName" label="方法名称" v-bind="columnProps" />
      <el-table-column prop="requestMethod" label="请求方式" v-bind="columnProps" />
      <el-table-column prop="username" label="操作人员" v-bind="columnProps" />
      <el-table-column prop="url" label="请求URL" v-bind="columnProps" />
      <el-table-column prop="ip" label="主机地址" v-bind="columnProps" />
      <el-table-column prop="param" label="请求参数" v-bind="columnProps" />
      <el-table-column prop="result" label="返回参数" v-bind="columnProps" />
      <el-table-column prop="status" label="操作状态" v-bind="columnProps">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.status === 1" type="danger">异常</el-tag>
          <el-tag v-if="scope.row.status === 0" type="success">正常</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="errorMsg" label="错误消息" v-bind="columnProps" />
      <el-table-column prop="startTime" label="操作时间" v-bind="columnProps" />
      <el-table-column prop="costTime" label="消耗时间" v-bind="columnProps" />
      <el-table-column
        v-if="checkPermission(['system::logs::oprLogs::update', 'system::logs::oprLogs::delete'])"
        label="操作"
        width="100"
        fixed="right"
        :header-align="tableConfig.headerAlign"
        align="center"
      >
        <template slot-scope="scope">
          <el-button
            v-permission="['system::logs::oprLogs::delete']"
            :size="toolBar.size"
            :type="toolBar.deleteType"
            :icon="toolBar.deleteIcon"
            @click="handleDelete(scope.row.id)"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <div class="pagination">
      <el-pagination
        :current-page="currentPage"
        :page-sizes="[1, 10, 20, 50, 100]"
        :page-size="pageSize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total"
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
      />
    </div>
  </div>
</template>

<script>
import { deleteByIds, pageByParams } from '@/api/system/oprLogs'

export default {
  data() {
    return {
      tableLoading: false,
      // 表格样式设置
      tableConfig: {
        tooltip: true, // 表格内容长度过长时鼠标悬浮显示
        columnWidth: '180px', // 表格宽度
        align: 'center', // 内容对齐方式
        headerAlign: 'center' // 标题对齐方式
      },
      // 当前页码
      currentPage: 1,
      // 页码显示条数
      pageSize: 10,
      // 数据总条数
      total: 0,
      // 表格数据
      tableData: [],
      // 搜索条件
      searchForm: {
      },
      // 复选框选择的主键
      selectedIds: [],
      // 请求方式
      requestMenthods: [
        { value: null, label: '全部' },
        { value: 'GET', label: 'GET' },
        { value: 'POST', label: 'POST' },
        { value: 'PUT', label: 'PUT' },
        { value: 'DELETE', label: 'DELETE' }
      ],
      // 业务类型
      businessTypes: [
        { value: null, label: '全部' },
        { value: 1, label: '新增' },
        { value: 2, label: '修改' },
        { value: 3, label: '删除' },
        { value: 0, label: '其他' }
      ],
      statusItem: [
        { value: null, label: '全部' },
        { value: 1, label: '异常' },
        { value: 0, label: '正常' }
      ]
    }
  },
  computed: {
    // 计算属性
    columnProps() {
      return {
        showOverflowTooltip: this.tableConfig.tooltip,
        headerAlign: this.tableConfig.headerAlign,
        align: this.tableConfig.align,
        width: this.tableConfig.width
      }
    }
  },
  created() {
    this.getTableData({}, this.currentPage, this.pageSize)
  },
  methods: {
    // 表格初始化获取数据
    getTableData(data, currentPage, pageSize) {
      this.tableLoading = true
      pageByParams(data, currentPage, pageSize).then(res => {
        const { data } = res
        // console.log(data);
        this.currentPage = data.currentPage
        this.pageSize = data.pageSize
        this.total = data.total
        this.tableData = data.data
        this.tableLoading = false
      }).catch(() => {
        this.tableLoading = false
      })
    },
    // 删除按钮被点击
    handleDelete(id) {
      this.$confirm('此操作将删除此条数据, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        // console.log(id)
        deleteByIds(id).then(() => {
          this.$message({
            type: 'success',
            message: '删除成功!'
          })
          this.handlePagination()
        }).catch(() => {

        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        })
      })
    },
    // 批量删除按钮被点击
    handleBatchDelete() {
      if (this.selectedIds.length <= 0) {
        this.$message({
          type: 'warning',
          message: '请选择要删除的数据'
        })
        return
      }
      this.$confirm('此操作将删除此条数据, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        deleteByIds(this.selectedIds).then(() => {
          this.$message({
            type: 'success',
            message: '删除成功!'
          })
          this.handlePagination()
        }).catch(() => {
          // console.log(error)
        })
      })
    },
    // 搜索按钮被点击
    handleSearch() {
      this.handlePagination()
    },
    // 行数据复选框被选中
    handleSelectionChange(selection) {
      this.selectedUsers = selection
      this.selectedIds = selection.map(obj => obj.id)
    },
    // 导出按钮被点击
    handleExport() {
      // 假设这里有一个API调用导出用户
      // console.log('数据导出');
    },
    // 刷新按钮被点击
    handleRefresh() {
      this.handlePagination()
    },
    handleSizeChange(newSize) {
      this.pageSize = newSize
      // console.log("页面显示条数被改变：" + newSize + "条");
      this.handlePagination()
    },
    // 页码被改变
    handleCurrentChange(newPage) {
      this.currentPage = newPage
      // console.log("页码被改变：" + newPage);
      this.handlePagination()
    },
    // 页码改变/页面显示条数改变
    handlePagination() {
      this.getTableData(this.searchForm, this.currentPage, this.pageSize)
    }
  }
}
</script>

<style scoped></style>
