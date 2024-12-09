<template>
  <div class="user-management">
    <div v-permission="['system::users::list']" class="search-bar">
      <div class="grid-item">
        <span>
          用户名:
        </span>
        <el-input v-model="searchForm.username" placeholder="请输入用户名" style="width: 200px;" />
      </div>
      <div class="grid-item">
        <span>
          邮箱:
        </span>
        <el-input v-model="searchForm.email" placeholder="请输入邮箱" style="width: 200px;" />
      </div>
      <div class="grid-item">
        <el-button type="primary" icon="el-icon-search" @click="handleSearch">搜索</el-button>
      </div>
    </div>
    <div v-permission="['system::users::insert','system::users::delete','system::users::import','system::users::export','system::users::list']" class="button-bar">
      <el-button v-permission="['system::users::insert']" :type="buttonBar.insertType" :size="buttonBar.size" :plain="buttonBar.plain" @click="handleAdd">新增项目</el-button>
      <el-button v-permission="['system::users::delete']" :type="buttonBar.deleteType" :size="buttonBar.size" :plain="buttonBar.plain" @click="handleBatchDelete">批量删除</el-button>
      <el-button v-permission="['system::users::import']" :type="buttonBar.importType" :size="buttonBar.size" :plain="buttonBar.plain" @click="handleImport">导入</el-button>
      <el-button v-permission="['system::users::export']" :type="buttonBar.exportType" :size="buttonBar.size" :plain="buttonBar.plain" @click="handleExport">导出</el-button>
      <el-button v-permission="['system::users::list']" :type="buttonBar.reFreshType" :size="buttonBar.size" :plain="buttonBar.plain" @click="handleRefresh">刷新</el-button>
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
      <!-- <el-table-column prop="id" label="用户ID" v-bind="columnProps" /> -->
      <el-table-column prop="username" label="用户名" v-bind="columnProps" />
      <el-table-column prop="password" label="密码" v-bind="columnProps" />
      <el-table-column prop="email" label="邮箱" v-bind="columnProps" />
      <el-table-column prop="createTime" label="创建时间" v-bind="columnProps" />
      <el-table-column prop="createBy" label="创建人" v-bind="columnProps" />
      <el-table-column prop="updateTime" label="更新时间" v-bind="columnProps" />
      <el-table-column prop="updateBy" label="更新人" v-bind="columnProps" />
      <el-table-column
        v-if="checkPermission(['system::users::grant', 'system::users::update', 'system::users::delete'])"
        label="操作"
        width="200"
        fixed="right"
        :header-align="tableConfig.headerAlign"
        align="center"
      >
        <template slot-scope="scope">
          <el-button v-permission="['system::users::grant']" :size="toolBar.size" :type="toolBar.updateType" icon="el-icon-user" @click="handleAssRole(scope.row)">分配角色</el-button>
          <el-button v-permission="['system::users::update']" :size="toolBar.size" :type="toolBar.updateType" :icon="toolBar.updateIcon" @click="handleEdit(scope.row)">编辑</el-button>
          <el-button v-permission="['system::users::delete']" :size="toolBar.size" :type="toolBar.deleteType" :icon="toolBar.deleteIcon" @click="handleDelete(scope.row.id)">删除</el-button>
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
    <!-- 新增/编辑用户对话框 -->
    <el-dialog :title="dialogTitle" :visible.sync="dialogVisible" width="500px" :close-on-click-modal="false" center>
      <el-form ref="form" :model="form" :rules="rules">
        <el-form-item label="用户名" prop="username">
          <el-input v-model="form.username" autocomplete="off" />
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input v-model="form.password" autocomplete="off" />
        </el-form-item>
        <el-form-item label="邮箱" prop="email">
          <el-input v-model="form.email" autocomplete="off" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="dialogVisible = false">取 消</el-button>
      </div>
    </el-dialog>
    <!-- 分配角色对话框 -->
    <el-dialog
      :title="dialogByUserTitle"
      :visible.sync="dialogVisibleByUser"
      width="800px"
      :close-on-click-modal="false"
      center
    >
      <el-form ref="form" :model="formByUser" style="text-align: center;">
        <el-transfer
          v-model="transferValue"
          style="text-align: left; display: inline-block"
          filterable
          :filter-method="transferFilter"
          filter-placeholder="请输入用户名"
          :data="transferData"
          :button-texts="['取消', '添加']"
          :titles="['未拥有角色', '已拥有角色']"
          :props="{
            key: 'id',
            label: 'name'
          }"
        />

      </el-form>

      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitFormByUser">确 定</el-button>
        <el-button @click="dialogVisibleByUser = false">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { getGrantByUserId, saveGrantByUser } from '@/api/system/roles'
import { deleteByIds, pageByParams, updateSpecifyById, save } from '@/api/system/users'

export default {
  data() {
    return {
      //
      // buttonBar:this.buttonBar,
      tableLoading: false,
      // 表格样式设置
      tableConfig: {
        tooltip: true, // 表格内容长度过长时鼠标悬浮显示
        columnWidth: '180px', // 表格宽度
        align: 'center', // 内容对齐方式
        headerAlign: 'center' // 标题对齐方式
      },
      transferData: [],
      // 分配角色选择数据
      transferValue: [],
      // 分配角色弹窗开关
      dialogVisibleByUser: false,
      // 分配角色弹窗标题
      dialogByUserTitle: '',
      formByUser: {
        userId: null,
        roleIds: []
      },
      // 新增/修改弹窗开关
      dialogVisible: false,
      // 新增/修改弹窗标题
      dialogTitle: '',
      // 新增按钮被点击时为 insert 修改按钮被点击时值为 update
      dialogType: '',
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
      // 新增修改表单
      form: {
        username: null, // 用户名
        password: null, // 密码
        email: null // 邮箱
      },
      // 表单前端校验是否输入
      rules: {
        username: [
          { required: true, message: '请输入用户名', trigger: 'blur' }
        ],
        password: [
          { required: true, message: '请输入密码', trigger: 'blur' }
        ],
        email: [
          { required: true, message: '请输入邮箱', trigger: 'blur' }
        ]
      }
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
    // 分配角色表单提交
    submitFormByUser() {
      this.formByUser.roleIds = this.transferValue
      saveGrantByUser(this.formByUser).then(() => {
        this.$message({
          type: 'success',
          message: '分配成功!'
        })
      }).catch(() => {

      })
    },
    // 分配角色按钮被点击
    handleAssRole(row) {
      this.dialogVisibleByUser = true
      this.dialogByUserTitle = '用户：' + row.username + ' 分配角色'
      this.formByUser.userId = row.id
      getGrantByUserId(row.id).then(res => {
        const { data } = res
        console.log(data)
        this.transferData = data.roles
        this.transferValue = data.userIds
      })
    },
    // 分配角色搜索框输入过滤
    transferFilter(query, item) {
      return item.name.indexOf(query) > -1
    },
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
        // console.log(error)
      })
    },
    // 新增按钮被点击
    handleAdd() {
      this.dialogTitle = '新增'
      this.dialogVisible = true
      this.dialogType = 'insert'
      this.resetForm()
    },
    // 编辑按钮被点击
    handleEdit(row) {
      this.dialogTitle = '编辑'
      this.dialogType = 'update'
      this.form = { ...row }
      this.dialogVisible = true
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
      // console.log(selection);
      // console.log(this.selectedIds);
    },
    // 导入按钮被点击
    handleImport() {
      // console.log('数据导入');
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
    },
    // 重置表单
    resetForm() {
      const resetForm = {
        username: null, // 用户名
        password: null, // 密码
        email: null // 邮箱
      }
      this.form = resetForm
      // console.log(this.form);
    },
    // 新增/修改表单提交
    submitForm() {
      this.$refs.form.validate((valid) => {
        if (valid) {
          if (this.dialogType === 'insert') {
            // 新增
            save(this.form).then(() => {
              this.$message({
                type: 'success',
                message: '新增成功!'
              })
              this.handlePagination()
            }).catch(error => {
              this.$message.error(error)
              // console.log(error)
            })
          } else if (this.dialogType === 'update') {
            // 修改
            updateSpecifyById(this.form).then(() => {
              this.$message({
                type: 'success',
                message: '修改成功!'
              })
              this.handlePagination()
            }).catch(error => {
              this.$message.error(error)
              // console.log(error)
            })
          } else {
            this.$message.$error('请选择要操作的数据')
            return false
          }
          this.dialogVisible = false
        } else {
          // console.log('表单验证失败');
        }
      })
    }
  }
}
</script>

<style scoped>

</style>
