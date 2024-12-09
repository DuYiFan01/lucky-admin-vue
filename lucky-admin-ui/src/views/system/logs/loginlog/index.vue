<template>
  <div class="user-management">
    <div v-permission="['system::logs::loginLog::list']" class="search-bar">
      <div class="grid-item">
        <span>
          登录IP地址:
        </span>
        <el-input v-model="searchForm.ip" placeholder="请输入登录IP地址" style="width: 200px;" />
      </div>
      <div class="grid-item">
        <span>
          登录地点:
        </span>
        <el-input v-model="searchForm.ipAddr" placeholder="请输入登录地点" style="width: 200px;" />
      </div>
      <div class="grid-item">
        <span>
          浏览器类型:
        </span>
        <el-input v-model="searchForm.browser" placeholder="请输入浏览器类型" style="width: 200px;" />
      </div>
      <div class="grid-item">
        <span>
          操作系统:
        </span>
        <el-input v-model="searchForm.os" placeholder="请输入操作系统" style="width: 200px;" />
      </div>
      <div class="grid-item">
        <el-button type="primary" icon="el-icon-search" @click="handleSearch">搜索</el-button>
      </div>
    </div>
    <div
      v-permission="['system::logs::loginLog::delete', 'system::logs::loginLog::export', 'system::logs::loginLog::list']"
      class="button-bar"
    >
      <el-button
        v-permission="['system::logs::loginLog::delete']"
        :type="buttonBar.deleteType"
        :size="buttonBar.size"
        :plain="buttonBar.plain"
        @click="handleBatchDelete"
      >批量删除</el-button>
      <el-button
        v-permission="['system::logs::loginLog::export']"
        :type="buttonBar.exportType"
        :size="buttonBar.size"
        :plain="buttonBar.plain"
        @click="handleExport"
      >导出</el-button>
      <el-button
        v-permission="['system::logs::loginLog::list']"
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
      <!-- <el-table-column prop="id" label="id" v-bind="columnProps" /> -->
      <el-table-column prop="username" label="登录账号" v-bind="columnProps" />
      <el-table-column prop="ip" label="登录IP地址" v-bind="columnProps" />
      <el-table-column prop="ipAddr" label="登录地点" v-bind="columnProps" />
      <el-table-column prop="browser" label="浏览器类型" v-bind="columnProps" />
      <el-table-column prop="os" label="操作系统" v-bind="columnProps" />
      <el-table-column prop="createTime" label="访问时间" v-bind="columnProps" />
      <el-table-column
        v-if="checkPermission(['system::logs::loginLog::update', 'system::logs::loginLog::delete'])"
        label="操作"
        width="100"
        fixed="right"
        :header-align="tableConfig.headerAlign"
        align="center"
      >
        <template slot-scope="scope">
          <el-button
            v-permission="['system::logs::loginLog::delete']"
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
import { deleteByIds, pageByParams } from '@/api/system/loginLog'

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
      selectedIds: []
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
          // console.log(error)
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

<style scoped>
</style>
