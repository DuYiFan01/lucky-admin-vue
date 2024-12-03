<template>
  <div class="user-management">
    <div class="search-bar">
      <div class="grid-item">
        <span>
          角色名称:
        </span>
        <el-input v-model="searchForm.name" placeholder="请输入角色名称" style="width: 200px; margin-right: 10px;" />
      </div>
      <div class="grid-item">
        <span>
          角色描述:
        </span>
        <el-input v-model="searchForm.description" placeholder="请输入角色描述" style="width: 200px; margin-right: 10px;" />
      </div>
      <div class="grid-item">
        <el-button type="primary" @click="handleSearch">搜索</el-button>
      </div>
    </div>
    <div class="button-bar">
      <el-button type="primary" @click="handleAdd">新增项目</el-button>
      <el-button type="danger" @click="handleBatchDelete">批量删除</el-button>
      <el-button type="success" @click="handleImport">导入</el-button>
      <el-button type="warning" @click="handleExport">导出</el-button>
      <el-button type="info" @click="handleRefresh">刷新</el-button>
    </div>
    <el-table v-loading.fullscreen.lock="tableLoading" :data="tableData" style="width: 100%" border max-height="560"
      @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" />
      <el-table-column prop="id" label="角色ID" v-bind="columnProps" />
      <el-table-column prop="name" label="角色名称" v-bind="columnProps" />
      <el-table-column prop="description" label="角色描述" v-bind="columnProps" />
      <el-table-column prop="createTime" label="创建时间" v-bind="columnProps" />
      <el-table-column prop="createBy" label="创建人" v-bind="columnProps" />
      <el-table-column prop="updateTime" label="更新时间" v-bind="columnProps" />
      <el-table-column prop="updateBy" label="更新人" v-bind="columnProps" />
      <el-table-column prop="delFlag" label="逻辑删除标志，0-未删除，1-已删除" v-bind="columnProps" />
      <el-table-column label="操作" width="300" fixed="right" :header-align="tableConfig.headerAlign" align="center">
        <template slot-scope="scope">
          <el-button size="mini" type="primary" @click="handleAssUser(scope.row)">分配用户</el-button>
          <el-button size="mini" @click="handleEdit(scope.row)">编辑</el-button>
          <el-button size="mini" type="danger" @click="handleDelete(scope.row.id)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <div class="pagination">
      <el-pagination :current-page="currentPage" :page-sizes="[1, 10, 20, 50, 100]" :page-size="pageSize"
        layout="total, sizes, prev, pager, next, jumper" :total="total" @size-change="handleSizeChange"
        @current-change="handleCurrentChange" />
    </div>
    <!-- 新增/编辑用户对话框 -->
    <el-dialog :title="dialogTitle" :visible.sync="dialogVisible" width="500px" :close-on-click-modal="false" center
      @opened="dialogOpened(form)">
      <el-form ref="form" :model="form" :rules="rules">
        <el-form-item label="角色名称" prop="name">
          <el-input v-model="form.name" autocomplete="off" />
        </el-form-item>
        <el-form-item label="角色描述" prop="description">
          <el-input v-model="form.description" autocomplete="off" />
        </el-form-item>
        <el-form-item v-show="menuShow" label="分配权限" prop="menus">
          <el-input placeholder="请输入权限名称" v-model="filterText">
          </el-input>
          <el-button size="mini" type="primary" @click="handleCheckAll">全选/全不选</el-button>
          <el-button size="mini" type="danger" @click="handleResetRole(form.menus)">重置</el-button>
          <el-button size="mini" type="info" @click="handleRefresh">刷新</el-button>
          <el-tree class="filter-tree" ref="menuTree" :data="menusTree" node-key="id" show-checkbox :check-strictly=true
            :props="defaultProps" :default-expand-all=isExpand :filter-node-method="filterMenusTree"
            @check="handleCheck">
          </el-tree>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="dialogVisible = false">取 消</el-button>
      </div>
    </el-dialog>
    <!-- 角色分配用户对话框 -->
    <el-dialog :title="dialogByRoleTitle" :visible.sync="dialogVisibleByRole" width="800px"
      :close-on-click-modal="false" center >
      <div style="text-align: center;">
        <el-transfer style="text-align: left; display: inline-block" filterable :filter-method="transferFilter"
          filter-placeholder="请输入用户名" v-model="transferValue" :data="transferData" :button-texts="['取消', '授权']"
          :titles="['未授权用户', '已授权用户']" :props="{
            key: 'id',
            label: 'username'
          }">
        </el-transfer>
      </div>

      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitFormByRole">确 定</el-button>
        <el-button @click="dialogVisibleByRole = false">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { deleteByIds, pageByParams, updateAllById, save, grant } from '@/api/system/roles'
import { getMenusTree } from '@/api/system/menus'

export default {
  data() {
    return {
      menuShow: true,
      isExpand: true,
      // 分配用户数据
      transferData: [{ key: 1, label: '上海', disabled: false }, { key: 2, label: '北京', disabled: false }],
      // 分配用户选择数据
      transferValue: [],
      // 菜单树过滤条件
      filterText: '',
      defaultProps: {
        id: 'id',
        children: 'children',
        label: 'title',
      },
      treeIds: [],
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
      //菜单权限树
      menusTree: [],
      // 搜索条件
      searchForm: {
      },
      // 复选框选择的主键
      selectedIds: [],
      // 新增修改表单
      form: {
        name: null, // 角色名称
        description: null, // 角色描述
        menus: [], // 菜单权限ID
      },
      // 表单前端校验是否输入
      rules: {
        name: [
          { required: true, message: '请输入角色名称', trigger: 'blur' }
        ],
        description: [
          { required: true, message: '请输入角色描述', trigger: 'blur' }
        ],
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
    this.getTreeData()
  },
  methods: {
    // 用户角色过滤
    transferFilter(query, item) {
      return item.username.indexOf(query) > -1;
    },
    // tree中复选框发生改变触发事件
    handleCheck(data, node) {
      console.log("node", node);
      const ids = node.checkedKeys
      for (let i = 0; i < node.halfCheckedKeys.length; i++) {
        const id = node.halfCheckedKeys[i];
        ids.push(id)
      }
      this.form.menus = ids
    },
    // 分配用户按钮被点击
    handleAssUser(row) {
      this.dialogVisibleByRole = true
      this.dialogByRoleTitle = '角色：' + row.name + ' 分配用户'
      grant(row.id).then(res => {
       const { data } = res
       console.log("data",data);
       
       this.transferData = data.userList
       this.transferValue = data.authUserIds
      })
    },
    // 新增/修改弹窗打开后菜单树自动选中
    dialogOpened(row) {
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
      this.$refs.menuTree.setCheckedKeys(ids);
      // 全选和全不选不会触发 tree的 check 事件这里手动处理解决form表单中没有菜单数据的问题
      this.form.menus = ids
    },
    handleTree(data, id, parentId, children) {
      let config = {
        id: id || 'id',
        parentId: parentId || 'parentId',
        childrenList: children || 'children'
      };

      var childrenListMap = {};
      var nodeIds = {};
      var tree = [];

      for (let d of data) {
        let parentId = d[config.parentId];
        if (childrenListMap[parentId] == null) {
          childrenListMap[parentId] = [];
        }
        nodeIds[d[config.id]] = d;
        childrenListMap[parentId].push(d);
      }

      for (let d of data) {
        let parentId = d[config.parentId];
        if (nodeIds[parentId] == null) {
          tree.push(d);
        }
      }

      for (let t of tree) {
        adaptToChildrenList(t);
      }

      function adaptToChildrenList(o) {
        if (childrenListMap[o[config.id]] !== null) {
          o[config.childrenList] = childrenListMap[o[config.id]];
        }
        if (o[config.childrenList]) {
          for (let c of o[config.childrenList]) {
            adaptToChildrenList(c);
          }
        }
      }
      return tree;
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
      if (!value) return true;
      return data.title.indexOf(value) !== -1;
    },
    // 初始化Tree数据
    getTreeData() {
      this.tableLoading = true
      getMenusTree().then(res => {
        const { data } = res
        // 主数据
        this.treeIds = data.map(item => item.id)
        this.menusTree = this.handleTree(data)
        this.tableLoading = false
      }).catch(error => {
        this.$message.error(error)
        this.tableLoading = false
      })
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
      }).catch(error => {
        this.$message.error(error)
        this.tableLoading = false
        // console.log(error)
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
        }).catch(error => {
          // this.$message.error(error)
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
        }).catch(error => {
          // this.$message.error(error)
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

    }
  },
  watch: {
    filterText(val) {
      this.$refs.tree.filter(val);
    }
  }

}
</script>

<style scoped>
.user-management {
  padding: 20px;
}

.search-bar {
  margin-bottom: 20px;
  padding: 20px 40px;
  box-shadow: 1px 1px 3px rgba(0, 0, 0, .2);
  border-radius: 10px;
  background-color: white;

  display: flex;
  flex-wrap: wrap;
  gap: 10px;
  align-items: center;

}

.grid-item {
  width: 300px;
  display: flex;
  align-items: center;
}

.button-bar {
  margin-bottom: 20px;
  padding: 20px 20px;
  box-shadow: 1px 1px 3px rgba(0, 0, 0, .2);
  border-radius: 10px;
  background-color: white;

  white-space: nowrap;
}

.el-table {
  margin-bottom: 20px;
  border-radius: 10px;
  box-shadow: 1px 1px 3px rgba(0, 0, 0, .2);
}

.pagination {
  display: flex;
  justify-content: center;
  align-items: center;
  margin-top: 20px;
}

@media (max-width: 768px) {
  .search-bar {
    flex-direction: column;
  }

  .button-bar {
    flex-direction: column;
    align-items: center;
  }

  .el-table {
    font-size: 14px;
  }

  .el-pagination {
    font-size: 14px;
  }
}
</style>
