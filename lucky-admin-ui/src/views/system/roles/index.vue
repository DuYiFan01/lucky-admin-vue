<template>
  <div class="user-management">
    <div v-permission="['system::role::list']" class="search-bar">
      <div class="grid-item">
        <span>
          角色名称:
        </span>
        <el-input v-model="searchForm.name" placeholder="请输入角色名称" style="width: 200px;" />
      </div>
      <div class="grid-item">
        <span>
          角色描述:
        </span>
        <el-input v-model="searchForm.description" placeholder="请输入角色描述" style="width: 200px;" />
      </div>
      <div class="grid-item">
        <el-button type="primary" icon="el-icon-search" @click="handleSearch">搜索</el-button>
      </div>
    </div>
    <div v-permission="['system::role::insert','system::role::delete','system::role::import','system::role::export','system::role::list']" class="button-bar">
      <el-button v-permission="['system::role::insert']" :type="buttonBar.insertType" :size="buttonBar.size" :plain="buttonBar.plain" @click="handleAdd">新增项目</el-button>
      <el-button v-permission="['system::role::delete']" :type="buttonBar.deleteType" :size="buttonBar.size" :plain="buttonBar.plain" @click="handleBatchDelete">批量删除</el-button>
      <el-button v-permission="['system::role::import']" :type="buttonBar.importType" :size="buttonBar.size" :plain="buttonBar.plain" @click="handleImport">导入</el-button>
      <el-button v-permission="['system::role::export']" :type="buttonBar.exportType" :size="buttonBar.size" :plain="buttonBar.plain" @click="handleExport">导出</el-button>
      <el-button v-permission="['system::role::list']" :type="buttonBar.reFreshType" :size="buttonBar.size" :plain="buttonBar.plain" @click="handleRefresh">刷新</el-button>
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
      <!-- <el-table-column prop="id" label="角色ID" v-bind="columnProps" /> -->
      <el-table-column prop="name" label="角色名称" v-bind="columnProps" />
      <el-table-column prop="description" label="角色描述" v-bind="columnProps" />
      <el-table-column prop="createTime" label="创建时间" v-bind="columnProps" />
      <el-table-column prop="createBy" label="创建人" v-bind="columnProps" />
      <el-table-column prop="updateTime" label="更新时间" v-bind="columnProps" />
      <el-table-column prop="updateBy" label="更新人" v-bind="columnProps" />
      <el-table-column
        v-if="checkPermission(['system::role::grant','system::role::update','system::role::delete'])"
        label="操作"
        width="200"
        fixed="right"
        :header-align="tableConfig.headerAlign"
        align="center"
      >
        <template slot-scope="scope">
          <el-button v-permission="['system::role::grant']" :size="toolBar.size" :type="toolBar.updateType" icon="el-icon-user" @click="handleAssUser(scope.row)">分配用户</el-button>
          <el-button v-permission="['system::role::update']" :size="toolBar.size" :type="toolBar.updateType" :icon="toolBar.updateIcon" @click="handleEdit(scope.row)">编辑</el-button>
          <el-button v-permission="['system::role::delete']" :size="toolBar.size" :type="toolBar.deleteType" :icon="toolBar.deleteIcon" @click="handleDelete(scope.row.id)">删除</el-button>
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
    <el-dialog
      :title="dialogTitle"
      :visible.sync="dialogVisible"
      width="500px"
      :close-on-click-modal="false"
      center
      @opened="dialogOpened(form)"
    >
      <el-form ref="form" :model="form" :rules="rules">
        <el-form-item label="角色名称" prop="name">
          <el-input v-model="form.name" autocomplete="off" />
        </el-form-item>
        <el-form-item label="角色描述" prop="description">
          <el-input v-model="form.description" autocomplete="off" />
        </el-form-item>
        <el-form-item v-show="menuShow" label="分配权限" prop="menus">
          <el-input v-model="filterText" placeholder="请输入权限名称" />
          <el-button size="mini" type="primary" @click="handleCheckAll">全选/全不选</el-button>
          <el-button size="mini" type="primary" @click="handleExpand(form.menus)">展开/折叠</el-button>
          <el-button size="mini" type="danger" @click="handleResetRole(form.menus)">重置</el-button>
          <el-button size="mini" type="info" @click="handleRefresh">刷新</el-button>
          <el-tree
            ref="menuTree"
            class="filter-tree"
            style="height: 200px; overflow: auto;"
            :data="menusTree"
            node-key="id"
            show-checkbox
            :check-strictly="true"
            :props="defaultProps"
            :default-expand-all="isExpand"
            :filter-node-method="filterMenusTree"
            @check="handleCheck"
          />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="dialogVisible = false">取 消</el-button>
      </div>
    </el-dialog>
    <!-- 角色分配用户对话框 -->
    <el-dialog
      :title="dialogByRoleTitle"
      :visible.sync="dialogVisibleByRole"
      width="800px"
      :close-on-click-modal="false"
      center
    >

      <el-form ref="form" :model="formByRole" style="text-align: center;">
        <el-transfer
          v-model="transferValue"
          style="text-align: left; display: inline-block"
          filterable
          :filter-method="transferFilter"
          filter-placeholder="请输入用户名"
          :data="transferData"
          :button-texts="['取消', '授权']"
          :titles="['未授权用户', '已授权用户']"
          :props="{
            key: 'id',
            label: 'username'
          }"
        />

      </el-form>

      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitFormByRole">确 定</el-button>
        <el-button @click="dialogVisibleByRole = false">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { deleteByIds, pageByParams, updateAllById, save, getGrantByRoleId, saveGrant } from '@/api/system/roles'
import { getMenusTree } from '@/api/system/menus'

export default {
  data() {
    return {
      // 分配权限的显示与不显示 与展开和收起互动
      menuShow: true,
      // 全部展开/收起
      isExpand: true,
      // 分配用户数据
      transferData: [],
      // 分配用户选择数据
      transferValue: [],
      // 分配权限搜索条件
      filterText: '',
      // 权限树渲染格式
      defaultProps: {
        id: 'id',
        children: 'children',
        label: 'title'
      },
      // 当前角色拥有的菜单权限ID
      treeIds: [],
      // 全局加载中开启/ 关闭
      tableLoading: false,
      // 表格样式设置
      tableConfig: {
        tooltip: true, // 表格内容长度过长时鼠标悬浮显示
        columnWidth: '180px', // 表格宽度
        align: 'center', // 内容对齐方式
        headerAlign: 'center' // 标题对齐方式
      },
      // 分配用户弹窗开关
      dialogVisibleByRole: false,
      // 分配用户弹窗标题
      dialogByRoleTitle: '',
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
      // 菜单权限树
      menusTree: [],
      // 搜索条件
      searchForm: {
        name: null,
        description: null
      },
      // 主数据复选框选择的主键
      selectedIds: [],
      // 新增修改表单
      form: {
        name: null, // 角色名称
        description: null, // 角色描述
        menus: [] // 菜单权限ID
      },
      //
      formByRole: {
        roleId: null,
        userIds: []
      },
      // 表单前端校验是否输入
      rules: {
        name: [
          { required: true, message: '请输入角色名称', trigger: 'blur' }
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
  watch: {
    filterText(val) {
      this.$refs.tree.filter(val)
    }
  },
  created() {
    this.getTableData({}, this.currentPage, this.pageSize)
    this.getTreeData()
  },
  methods: {
    // 分配角色搜索框输入过滤
    transferFilter(query, item) {
      return item.username.indexOf(query) > -1
    },
    // 菜单权限树复选框选中事件
    handleCheck(data, node) {
      console.log('node', node)
      const ids = node.checkedKeys
      for (let i = 0; i < node.halfCheckedKeys.length; i++) {
        const id = node.halfCheckedKeys[i]
        ids.push(id)
      }
      this.form.menus = ids
    },
    // 分配用户按钮被点击
    handleAssUser(row) {
      this.dialogVisibleByRole = true
      this.dialogByRoleTitle = '角色：' + row.name + ' 分配用户'
      this.formByRole.roleId = row.id
      getGrantByRoleId(row.id).then(res => {
        const { data } = res
        this.transferData = data.userList
        this.transferValue = data.authUserIds
      })
    },
    // 新增/修改弹窗打开渲染DOM后事件
    dialogOpened(row) {
      console.log('row', row)
      if (row.name !== null) {
        const ids = row.menus
        this.selectedMenus(ids)
      } else {
        this.selectedMenus([])
      }
    },
    // 菜单树选中
    selectedMenus(ids) {
      // 选中菜单树
      this.$refs.menuTree.setCheckedKeys(ids)
      // 全选和全不选不会触发 tree的 check 事件这里手动处理解决form表单中没有菜单数据的问题
      this.form.menus = ids
    },
    // 全选/全不选
    handleCheckAll() {
      const value = this.$refs.menuTree.getCheckedKeys().length > 0 ? [] : this.treeIds
      this.selectedMenus(value)
    },
    // 树展开/收缩
    handleExpand(row) {
      this.isExpand = !this.isExpand
      this.menuShow = false
      this.$nextTick(() => {
        this.menuShow = true
      })
    },
    // 重置选中的菜单项目，恢复到此次修改之前
    handleResetRole(row) {
      const treeIds = row
      this.selectedMenus(treeIds)
    },
    // 菜单过滤数据
    filterMenusTree(value, data) {
      if (!value) return true
      return data.title.indexOf(value) !== -1
    },
    // 初始化菜单权限树数据
    getTreeData() {
      this.tableLoading = true
      getMenusTree({}).then(res => {
        const { data } = res
        // 权限的ids
        this.treeIds = data.map(item => item.id)
        // 菜单权限树插入数据
        this.menusTree = this.handleTree(data)
        this.tableLoading = false
      }).catch(() => {
        this.tableLoading = false
      })
    },
    // 表格主数据获取数据
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
    // 新增按钮被点击
    handleAdd() {
      this.getTreeData()
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
      this.getTreeData()
    },
    // 重置表单
    resetForm() {
      const resetForm = {
        name: null, // 角色名称
        description: null, // 角色描述
        menus: []
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
            updateAllById(this.form).then(() => {
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
    },
    // 分配用户表单提交
    submitFormByRole() {
      console.log('transferValue', this.transferValue)
      this.formByRole.userIds = this.transferValue
      console.log('formByRole', this.formByRole)
      saveGrant(this.formByRole).then(() => {
        this.$message({
          type: 'success',
          message: '分配成功!'
        })
      }).catch(() => {

      })
    }
  }

}
</script>
<style scoped>

</style>
