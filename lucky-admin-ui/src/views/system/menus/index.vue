<template>
  <div class="user-management">
    <div v-permission="['system::menus::list']" class="search-bar">
      <div class="grid-item">
        <span>
          菜单名称:
        </span>
        <el-input v-model="searchForm.title" placeholder="请输入菜单名称" style="width: 200px;" />
      </div>
      <div class="grid-item">
        <el-button type="primary" icon="el-icon-search" @click="handleSearch">搜索</el-button>
      </div>
    </div>
    <div v-permission="['system::menus::insert','system::menus::list']" class="button-bar">
      <el-button v-permission="['system::menus::insert']" :type="buttonBar.insertType" :size="buttonBar.size" :plain="buttonBar.plain" @click="handleAdd">新增菜单</el-button>
      <el-button type="danger" :size="buttonBar.size" :plain="buttonBar.plain" @click="handleExpand">收起/展开</el-button>
      <el-button v-permission="['system::menus::list']" :type="buttonBar.reFreshType" :size="buttonBar.size" :plain="buttonBar.plain" @click="handleRefresh">刷新</el-button>
    </div>
    <el-table
      v-if="tableShow"
      v-loading.fullscreen.lock="tableLoading"
      row-key="id"
      :default-expand-all="isExpand"
      :data="treeData"
      style="width: 100%"
      border
      max-height="560"
      :tree-props="{ children: 'children', hasChildren: 'hasChildren' }"
    >
      <el-table-column prop="title" label="菜单名称" v-bind="columnProps" />
      <el-table-column prop="icon" label="菜单图标" v-bind="columnProps">
        <template slot-scope="scope">
          <svg-icon :icon-class="scope.row.icon" />
        </template>
      </el-table-column>
      <!-- <el-table-column prop="id" label="菜单ID" v-bind="columnProps" /> -->
      <el-table-column prop="orderNum" label="显示顺序" v-bind="columnProps" />
      <el-table-column prop="roles" label="权限标识" v-bind="columnProps" />
      <el-table-column prop="component" label="组件路径" v-bind="columnProps" />
      <el-table-column prop="path" label="路由地址" v-bind="columnProps" />
      <el-table-column prop="visible" label="是否显示" v-bind="columnProps">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.visible === 1">显示</el-tag>
          <el-tag v-if="scope.row.visible === 0" type="danger">隐藏</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="createTime" label="创建时间" v-bind="columnProps" />
      <el-table-column
        v-if="checkPermission(['system::menus::update','system::menus::delete'])"
        label="操作"
        width="150"
        fixed="right"
        :header-align="tableConfig.headerAlign"
        align="center"
      >
        <template slot-scope="scope">
          <el-button v-permission="['system::menus::update']" :size="toolBar.size" :type="toolBar.updateType" :icon="toolBar.updateIcon" @click="handleEdit(scope.row)">编辑</el-button>
          <el-button v-permission="['system::menus::delete']" :size="toolBar.size" :type="toolBar.deleteType" :icon="toolBar.deleteIcon" @click="handleDelete(scope.row.id)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <!-- 添加或修改菜单对话框 -->
    <el-dialog
      :title="dialogTitle"
      :visible.sync="dialogVisible"
      width="680px"
      :close-on-click-modal="false"
      center
      append-to-body
    >
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-row>
          <el-col :span="24">
            <el-form-item label="上级菜单" prop="parentId">
              <treeselect
                v-model="form.parentId"
                :options="menuOptions"
                :normalizer="normalizer"
                :show-count="true"
                placeholder="选择上级菜单"
              />
            </el-form-item>
          </el-col>

          <el-col :span="24">
            <el-form-item label="菜单类型" prop="menuType">
              <el-radio-group v-model="form.menuType">
                <el-radio label="M">目录</el-radio>
                <el-radio label="C">菜单</el-radio>
                <el-radio label="F">按钮</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>

          <el-col v-if="form.menuType != 'F'" :span="24">
            <el-form-item label="菜单图标" prop="icon">
              <el-popover placement="bottom-start" width="460" trigger="click" @show="$refs['iconSelect'].reset()">
                <IconSelect ref="iconSelect" :active-icon="form.icon" @selected="selectedIcon" />
                <el-input slot="reference" v-model="form.icon" placeholder="点击选择图标" readonly>
                  <svg-icon v-if="form.icon" slot="prefix" :icon-class="form.icon" style="width: 25px;" />
                  <i v-else slot="prefix" class="el-icon-search el-input__icon" />
                </el-input>
              </el-popover>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="菜单名称" prop="title">
              <el-input v-model="form.title" placeholder="请输入菜单中文名称" />
            </el-form-item>
          </el-col>
          <el-col v-if="form.menuType != 'F'" :span="12">
            <el-form-item prop="name">
              <span slot="label">
                <el-tooltip content="菜单别名,菜单英文名称，如路由为/system/users的菜单别名一般为users,保证唯一标识即可，填写英文" placement="top">
                  <i class="el-icon-question" />
                </el-tooltip>
                菜单别名
              </span>
              <el-input v-model="form.name" placeholder="请输入菜单别名" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="显示排序" prop="orderNum">
              <el-input-number v-model="form.orderNum" controls-position="right" :min="0" />
            </el-form-item>
          </el-col>
          <el-col v-if="form.menuType != 'F' && form.menuType != 'C'" :span="12">
            <el-form-item prop="isFrame">
              <span slot="label">
                <el-tooltip content="选择是外链则路由地址需要以`http(s)://`开头" placement="top">
                  <i class="el-icon-question" />
                </el-tooltip>
                是否外链
              </span>
              <el-radio-group v-model="form.isFrame">
                <el-radio v-for="v in isFrameTyps" :key="v.value" :label="v.value">{{ v.label
                }}</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
          <el-col v-if="form.menuType != 'F'" :span="12">
            <el-form-item prop="path">
              <span slot="label">
                <el-tooltip content="访问的路由地址，如：`user`，如外网地址需内链访问则以`http(s)://`开头" placement="top">
                  <i class="el-icon-question" />
                </el-tooltip>
                路由地址
              </span>
              <el-input v-model="form.path" placeholder="请输入路由地址" />
            </el-form-item>
          </el-col>
          <el-col v-if="form.menuType == 'C'" :span="12">
            <el-form-item prop="component">
              <span slot="label">
                <el-tooltip content="访问的组件路径，如：`system/user/index`，默认在`views`目录下" placement="top">
                  <i class="el-icon-question" />
                </el-tooltip>
                组件路径
              </span>
              <el-input v-model="form.component" placeholder="请输入组件路径" />
            </el-form-item>
          </el-col>
          <el-col v-if="form.menuType == 'F'" :span="12">
            <el-form-item prop="perms">
              <el-input v-model="form.roles" placeholder="请输入权限标识" maxlength="100" />
              <span slot="label">
                <el-tooltip content="控制器中定义的权限字符，如：@PreAuthorize(`@ss.hasPermi('system:user:list')`)" placement="top">
                  <i class="el-icon-question" />
                </el-tooltip>
                权限字符
              </span>
            </el-form-item>
          </el-col>
          <el-col v-if="form.menuType == 'C'" :span="12">
            <el-form-item prop="query">
              <el-input v-model="form.query" placeholder="请输入路由参数" maxlength="255" />
              <span slot="label">
                <el-tooltip
                  content="访问路由的默认传递参数，如：`{&quot;id&quot;: 1, &quot;name&quot;: &quot;ry&quot;}`"
                  placement="top"
                >
                  <i class="el-icon-question" />
                </el-tooltip>
                路由参数
              </span>
            </el-form-item>
          </el-col>
          <el-col v-if="form.menuType == 'C'" :span="12">
            <el-form-item prop="isCache">
              <span slot="label">
                <el-tooltip content="选择是则会被`keep-alive`缓存，需要匹配组件的`name`和地址保持一致" placement="top">
                  <i class="el-icon-question" />
                </el-tooltip>
                是否缓存
              </span>
              <el-radio-group v-model="form.isCache">
                <el-radio v-for="v in isCacheTyps" :key="v.value" :label="v.value">{{ v.label
                }}</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item prop="visible">
              <span slot="label">
                <el-tooltip content="选择隐藏则路由将不会出现在侧边栏，但仍然可以访问" placement="top">
                  <i class="el-icon-question" />
                </el-tooltip>
                显示状态
              </span>
              <el-radio-group v-model="form.visible">
                <el-radio v-for="v in visibleTyps" :key="v.value" :label="v.value">{{ v.label
                }}</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="dialogVisible = false">取 消</el-button>
      </div>
    </el-dialog>

  </div>
</template>

<script>
import { deleteByIds, save, getMenusTree, updateAllById } from '@/api/system/menus'
import IconSelect from '@/components/IconSelect/index'
import Treeselect from '@riophae/vue-treeselect'
import '@riophae/vue-treeselect/dist/vue-treeselect.css'

export default {
  components: { Treeselect, IconSelect },
  data() {
    return {
      // 表格显示与不显示 与展开和收起互动
      tableShow: true,
      // 全部展开/收起
      isExpand: false,
      // 全局加载中开启/ 关闭
      tableLoading: false,
      // 表格样式设置
      tableConfig: {
        tooltip: true, // 表格内容长度过长时鼠标悬浮显示
        columnWidth: '180px', // 表格宽度
        align: 'center', // 内容对齐方式
        headerAlign: 'center' // 标题对齐方式
      },
      // 菜单状态 0 显示 1隐藏
      visibleTyps: [
        { value: 1, label: '显示' },
        { value: 0, label: '隐藏' }
      ],
      // 是否缓存 1 缓存 0不缓存
      isCacheTyps: [
        { value: 1, label: '缓存' },
        { value: 0, label: '不缓存' }
      ],
      // 是否外链 1是 0否
      isFrameTyps: [
        { value: 1, label: '是' },
        { value: 0, label: '否' }
      ],
      // dialog表单中可选择上级菜单项目
      menuOptions: [],
      // 新增/修改弹窗开关
      dialogVisible: false,
      // 新增/修改弹窗标题
      dialogTitle: '',
      // 新增按钮被点击时为 insert 修改按钮被点击时值为 update
      dialogType: '',
      // 菜单树主数据
      treeData: [],
      // 搜索条件
      searchForm: {
        title: null // 菜单名称
      },
      // 新增修改表单
      form: {
        name: null, // 菜单别名
        parentId: 0, // 父菜单ID
        orderNum: 1, // 显示顺序
        path: null, // 路由地址
        component: null, // 组件路径
        query: null, // 路由参数
        title: null, // 菜单名称
        isFrame: 0, // 是否为外链（1是 0否）
        isCache: 1, // 是否缓存（1缓存 0不缓存）
        menuType: 'M', // 菜单类型（M目录 C菜单 F按钮）
        visible: 1, // 菜单状态（1显示 0隐藏）
        roles: null, // 权限标识
        icon: null, // 菜单图标
        remark: null // 备注
      },
      // 表单前端校验是否输入
      rules: {
        title: [
          { required: true, message: '请输入菜单名称', trigger: 'blur' }
        ],
        parentId: [
          { required: true, message: '请输入父菜单', trigger: 'blur' }
        ],
        orderNum: [
          { required: true, message: '请输入显示顺序', trigger: 'blur' }
        ],
        path: [
          { required: true, message: '请输入路由地址', trigger: 'blur' }
        ],
        component: [
          { required: true, message: '请输入组件路径', trigger: 'blur' }
        ],
        name: [
          { required: true, message: '请输入菜单别名', trigger: 'blur' }
        ],
        isFrame: [
          { required: true, message: '请输入是否为外链', trigger: 'blur' }
        ],
        menuType: [
          { required: true, message: '请输入菜单类型', trigger: 'blur' }
        ],
        visible: [
          { required: true, message: '请输入菜单状态', trigger: 'blur' }
        ],
        roles: [
          { required: true, message: '请输入权限标识', trigger: 'blur' }
        ]
      }
    }
  },
  computed: {
    // 计算属性 表格样式
    columnProps() {
      return {
        showOverflowTooltip: this.tableConfig.tooltip,
        headerAlign: this.tableConfig.headerAlign,
        align: this.tableConfig.align,
        width: this.tableConfig.width
      }
    }
  },
  // 挂在完DOM之后的生命周期
  created() {
    this.handlePagination()
  },
  // 函数
  methods: {
    // 初始化菜单树主数据
    getTreeData() {
      this.tableLoading = true
      getMenusTree(this.searchForm).then(res => {
        const { data } = res
        // 初始化菜单主数据
        this.treeData = this.handleTree(data)
        this.tableLoading = false
      }).catch(() => {
        this.tableLoading = false
      })
    },
    // 新增时获取上级菜单
    getMouldsTree() {
      getMenusTree({}).then(res => {
        const { data } = res
        this.menuOptions = []
        const menu = { id: 0, title: '主类目', children: [] }
        menu.children = this.handleTree(data)
        this.menuOptions.push(menu)
      }).catch(() => {
        this.tableLoading = false
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
    // dialog表单上级目录结构格式化
    normalizer(node) {
      if (node.children && !node.children.length) {
        delete node.childrens
      }
      return {
        id: node.id,
        label: node.title,
        children: node.children
      }
    },
    // 表单中选择图标
    selectedIcon(name) {
      this.form.icon = name
    },
    // 菜单主数据收缩展开按钮被点击
    handleExpand() {
      this.isExpand = !this.isExpand
      this.tableShow = false
      this.$nextTick(() => {
        this.tableShow = true
      })
      // console.log("收起/展开：",this.isExpand);
    },
    // 搜索按钮被点击
    handleSearch() {
      this.handlePagination()
    },
    // 刷新按钮被点击
    handleRefresh() {
      this.handlePagination()
    },
    // 获取菜单主数据和表单中上级目录数据
    handlePagination() {
      // 初始化菜单树主数据
      this.getTreeData()
      // 初始化新增表单中上级目录数据
      this.getMouldsTree()
    },
    // 重置表单
    resetForm() {
      const resetForm = {
        name: null, // 菜单别名
        parentId: 0, // 父菜单ID
        orderNum: 1, // 显示顺序
        path: null, // 路由地址
        component: null, // 组件路径
        query: null, // 路由参数
        title: null, // 菜单名称
        isFrame: 0, // 是否为外链（1是 0否）
        isCache: 1, // 是否缓存（1缓存 0不缓存）
        menuType: 'M', // 菜单类型（M目录 C菜单 F按钮）
        visible: 1, // 菜单状态（1显示 0隐藏）
        roles: null, // 权限标识
        icon: null, // 菜单图标
        remark: null // 备注
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
            }).catch(() => {
            })
          } else if (this.dialogType === 'update') {
            // 修改
            updateAllById(this.form).then(() => {
              this.$message({
                type: 'success',
                message: '修改成功!'
              })
              this.handlePagination()
            }).catch(() => {

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
