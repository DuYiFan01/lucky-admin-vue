<template>
  <div class="user-management">
    <div class="search-bar">
      <div class="grid-item">
        <span>
          模块标题:
        </span>
        <el-input
          v-model="searchForm.title"
          placeholder="请输入模块标题"
          style="width: 200px; margin-right: 10px;"
        />
      </div>
      <div class="grid-item">
        <span>
          业务类型（0其它 1新增 2修改 3删除）:
        </span>
      </div>
      <div class="grid-item">
        <span>
          方法名称:
        </span>
        <el-input
          v-model="searchForm.methodName"
          placeholder="请输入方法名称"
          style="width: 200px; margin-right: 10px;"
        />
      </div>
      <div class="grid-item">
        <span>
          请求方式:
        </span>
        <el-input
          v-model="searchForm.requestMethod"
          placeholder="请输入请求方式"
          style="width: 200px; margin-right: 10px;"
        />
      </div>
      <div class="grid-item">
        <span>
          操作人员:
        </span>
        <el-input
          v-model="searchForm.username"
          placeholder="请输入操作人员"
          style="width: 200px; margin-right: 10px;"
        />
      </div>
      <div class="grid-item">
        <span>
          请求URL:
        </span>
        <el-input
          v-model="searchForm.url"
          placeholder="请输入请求URL"
          style="width: 200px; margin-right: 10px;"
        />
      </div>
      <div class="grid-item">
        <span>
          主机地址:
        </span>
        <el-input
          v-model="searchForm.ip"
          placeholder="请输入主机地址"
          style="width: 200px; margin-right: 10px;"
        />
      </div>
      <div class="grid-item">
        <span>
          请求参数:
        </span>
        <el-input
          v-model="searchForm.param"
          placeholder="请输入请求参数"
          style="width: 200px; margin-right: 10px;"
        />
      </div>
      <div class="grid-item">
        <span>
          返回参数:
        </span>
        <el-input
          v-model="searchForm.result"
          placeholder="请输入返回参数"
          style="width: 200px; margin-right: 10px;"
        />
      </div>
      <div class="grid-item">
        <span>
          操作状态（0正常 1异常）:
        </span>
      </div>
      <div class="grid-item">
        <span>
          错误消息:
        </span>
        <el-input
          v-model="searchForm.errorMsg"
          placeholder="请输入错误消息"
          style="width: 200px; margin-right: 10px;"
        />
      </div>
      <div class="grid-item">
        <span>
          操作时间:
        </span>
      </div>
      <div class="grid-item">
        <span>
          消耗时间:
        </span>
      </div>
      <div class="grid-item">
        <el-button type="primary" @click="handleSearch">搜索</el-button>
      </div>
    </div>
    <div class="button-bar">
      <el-button type="primary" @click="handleAdd">新增用户</el-button>
      <el-button type="danger" @click="handleBatchDelete">批量删除</el-button>
      <el-button type="success" @click="handleImport">导入</el-button>
      <el-button type="warning" @click="handleExport">导出</el-button>
      <el-button type="info" @click="handleRefresh">刷新</el-button>
    </div>
    <el-table
      v-loading.fullscreen.lock="tableLoading"
      :data="tableData"
      style="width: 100%"
      border
      max-height="560"
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" width="55" />
      <el-table-column prop="id" label="日志主键" v-bind="columnProps" />
      <el-table-column prop="title" label="模块标题" v-bind="columnProps" />
      <el-table-column prop="businessType" label="业务类型（0其它 1新增 2修改 3删除）" v-bind="columnProps" />
      <el-table-column prop="methodName" label="方法名称" v-bind="columnProps" />
      <el-table-column prop="requestMethod" label="请求方式" v-bind="columnProps" />
      <el-table-column prop="username" label="操作人员" v-bind="columnProps" />
      <el-table-column prop="url" label="请求URL" v-bind="columnProps" />
      <el-table-column prop="ip" label="主机地址" v-bind="columnProps" />
      <el-table-column prop="param" label="请求参数" v-bind="columnProps" />
      <el-table-column prop="result" label="返回参数" v-bind="columnProps" />
      <el-table-column prop="status" label="操作状态（0正常 1异常）" v-bind="columnProps" />
      <el-table-column prop="errorMsg" label="错误消息" v-bind="columnProps" />
      <el-table-column prop="startTime" label="操作时间" v-bind="columnProps" />
      <el-table-column prop="costTime" label="消耗时间" v-bind="columnProps" />
      <el-table-column label="操作" width="200" fixed="right" :header-align="tableConfig.headerAlign">
        <template slot-scope="scope">
          <el-button size="mini" @click="handleEdit(scope.row)">编辑</el-button>
          <el-button size="mini" type="danger" @click="handleDelete(scope.row.id)">删除</el-button>
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
    >
      <el-form ref="form" :model="form" :rules="rules">
        <el-form-item label="模块标题" prop="title">
          <el-input v-model="form.title" autocomplete="off" />
        </el-form-item>
        <el-form-item label="业务类型（0其它 1新增 2修改 3删除）" prop="businessType">
          <el-input v-model="form.businessType" autocomplete="off" />
        </el-form-item>
        <el-form-item label="方法名称" prop="methodName">
          <el-input v-model="form.methodName" autocomplete="off" />
        </el-form-item>
        <el-form-item label="请求方式" prop="requestMethod">
          <el-input v-model="form.requestMethod" autocomplete="off" />
        </el-form-item>
        <el-form-item label="操作人员" prop="username">
          <el-input v-model="form.username" autocomplete="off" />
        </el-form-item>
        <el-form-item label="请求URL" prop="url">
          <el-input v-model="form.url" autocomplete="off" />
        </el-form-item>
        <el-form-item label="主机地址" prop="ip">
          <el-input v-model="form.ip" autocomplete="off" />
        </el-form-item>
        <el-form-item label="请求参数" prop="param">
          <el-input v-model="form.param" autocomplete="off" />
        </el-form-item>
        <el-form-item label="返回参数" prop="result">
          <el-input v-model="form.result" autocomplete="off" />
        </el-form-item>
        <el-form-item label="操作状态（0正常 1异常）" prop="status">
          <el-input v-model="form.status" autocomplete="off" />
        </el-form-item>
        <el-form-item label="错误消息" prop="errorMsg">
          <el-input v-model="form.errorMsg" autocomplete="off" />
        </el-form-item>
        <el-form-item label="操作时间" prop="startTime">
          <el-input v-model="form.startTime" autocomplete="off" />
        </el-form-item>
        <el-form-item label="消耗时间" prop="costTime">
          <el-input v-model="form.costTime" autocomplete="off" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="dialogVisible = false">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { deleteByIds, pageByParams, updateSpecifyById, save } from '@/api/system/logs'

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
        title: null, // 模块标题
        businessType: null, // 业务类型（0其它 1新增 2修改 3删除）
        methodName: null, // 方法名称
        requestMethod: null, // 请求方式
        username: null, // 操作人员
        url: null, // 请求URL
        ip: null, // 主机地址
        param: null, // 请求参数
        result: null, // 返回参数
        status: null, // 操作状态（0正常 1异常）
        errorMsg: null, // 错误消息
        startTime: null, // 操作时间
        costTime: null // 消耗时间
      },
      // 表单前端校验是否输入
      rules: {
        title: [
          { required: true, message: '请输入模块标题', trigger: 'blur' }
        ],
        businessType: [
          { required: true, message: '请输入业务类型（0其它 1新增 2修改 3删除）', trigger: 'blur' }
        ],
        methodName: [
          { required: true, message: '请输入方法名称', trigger: 'blur' }
        ],
        requestMethod: [
          { required: true, message: '请输入请求方式', trigger: 'blur' }
        ],
        username: [
          { required: true, message: '请输入操作人员', trigger: 'blur' }
        ],
        url: [
          { required: true, message: '请输入请求URL', trigger: 'blur' }
        ],
        ip: [
          { required: true, message: '请输入主机地址', trigger: 'blur' }
        ],
        param: [
          { required: true, message: '请输入请求参数', trigger: 'blur' }
        ],
        result: [
          { required: true, message: '请输入返回参数', trigger: 'blur' }
        ],
        status: [
          { required: true, message: '请输入操作状态（0正常 1异常）', trigger: 'blur' }
        ],
        errorMsg: [
          { required: true, message: '请输入错误消息', trigger: 'blur' }
        ],
        startTime: [
          { required: true, message: '请输入操作时间', trigger: 'blur' }
        ],
        costTime: [
          { required: true, message: '请输入消耗时间', trigger: 'blur' }
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
          this.$message.error(error)
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
          this.$message.error(error)
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
    },
    // 重置表单
    resetForm() {
      const resetForm = {
        title: null, // 模块标题
        businessType: null, // 业务类型（0其它 1新增 2修改 3删除）
        methodName: null, // 方法名称
        requestMethod: null, // 请求方式
        username: null, // 操作人员
        url: null, // 请求URL
        ip: null, // 主机地址
        param: null, // 请求参数
        result: null, // 返回参数
        status: null, // 操作状态（0正常 1异常）
        errorMsg: null, // 错误消息
        startTime: null, // 操作时间
        costTime: null // 消耗时间
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
