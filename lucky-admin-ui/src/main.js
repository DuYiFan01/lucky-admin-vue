import Vue from 'vue'

import 'normalize.css/normalize.css' // A modern alternative to CSS resets

import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'

import '@/styles/index.scss' // global css

import '@/assets/css/index.css'

import App from './App'
import store from './store'
import router from './router'

import '@/icons' // icon
import '@/permission' // permission control
import permission from './directive/permission'
import { handleTree, checkPermission } from './utils/lucky'

/**
 * If you don't want to use mock-server
 * you want to use MockJs for mock api
 * you can execute: mockXHR()
 *
 * Currently MockJs will be used in the production environment,
 * please remove it before going online ! ! !
 */
if (process.env.NODE_ENV === 'production') {
  const { mockXHR } = require('../mock')
  mockXHR()
}

// set ElementUI lang to EN
// Vue.use(ElementUI, { locale })
// 如果想要中文版 element-ui，按如下方式声明
Vue.use(ElementUI)
// 自定义指令
Vue.use(permission)

// 自定义函数
Vue.prototype.handleTree = handleTree
Vue.prototype.checkPermission = checkPermission

// 全局按钮样式 操作栏按钮
Vue.prototype.buttonBar = {
  size: 'medium',
  plain: true,
  insertType: 'primary',
  deleteType: 'danger',
  importType: 'success',
  exportType: 'warning',
  reFreshType: 'info'
}
// 全局按钮样式 工具栏按钮
Vue.prototype.toolBar = {
  size: 'mini',
  updateType: 'text',
  deleteType: 'text',
  updateIcon: 'el-icon-edit',
  deleteIcon: 'el-icon-delete'
}

Vue.config.productionTip = false

new Vue({
  el: '#app',
  router,
  store,
  render: h => h(App)
})
