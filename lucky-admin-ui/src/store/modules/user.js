import { login, logout, getInfo, updatePassword } from '@/api/user'
import { getToken, setToken, removeToken } from '@/utils/auth'
import { resetRouter } from '@/router'
import Layout from '@/layout'
import ParentView from '@/components/ParentView/index.vue'

const getDefaultState = () => {
  return {
    id: '',
    token: getToken(),
    username: '',
    roles: [],
    routersTree: []
  }
}

const state = getDefaultState()

const mutations = {
  RESET_STATE: (state) => {
    Object.assign(state, getDefaultState())
  },
  SET_TOKEN: (state, token) => {
    state.token = token
  },
  SET_USER_NAME: (state, username) => {
    state.username = username
  },
  SET_ID: (state, id) => {
    state.id = id
  },
  SET_ROLES: (state, roles) => {
    state.roles = roles
  },
  SET_PERMISSIONS: (state, permissions) => {
    state.permissions = permissions
  },
  SET_ROUTER_TREE: (state, routersTree) => {
    state.routersTree = routersTree
  }
}

/**
 * 格式化路由菜单，转换router Tree
 */
function formartRoutesRecursive(routers) {
  const routersTree = []
  for (let i = 0; i < routers.length; i++) {
    const e = routers[i]
    if (e.component) {
      if (e.component === 'Layout') {
        e.component = Layout
      } else if (e.component === 'ParentView') {
        e.component = ParentView
      } else {
        const component = e.component
        e.component = (re) => require([`@/views${component}`], re)
      }
    }
    if (e.children && e.children.length > 0) {
      e.children = formartRoutesRecursive(e.children)
    }
    routersTree.push(e)
  }
  return routersTree
}

const actions = {
  // 调用登录API
  login({ commit }, userInfo) {
    const { username, password } = userInfo
    return new Promise((resolve, reject) => {
      login({ username: username.trim(), password: password }).then(response => {
        const { data } = response
        commit('SET_TOKEN', data.token)
        setToken(data.token)
        resolve()
      }).catch(error => {
        reject(error)
      })
    })
  },

  // 获取用户信息
  getInfo({ commit, state }) {
    return new Promise((resolve, reject) => {
      getInfo(state.token).then(response => {
        const { data } = response
        if (!data) {
          reject('验证失败,请重新登录')
        }

        const { roles, username, id, routers, permissions } = data

        // roles must be a non-empty array
        if (!roles || roles.length <= 0) {
          reject('getInfo: 角色必须是非空数组!')
        }
        const routerTree = formartRoutesRecursive(routers)
        // 将404页面添加到最后的路由中去
        routerTree.push({ path: '*', redirect: '/404', hidden: true })
        commit('SET_ROLES', roles)
        commit('SET_USER_NAME', username)
        commit('SET_PERMISSIONS', permissions)
        commit('SET_ROUTER_TREE', routerTree)
        commit('SET_ID', id)
        resolve(data)
      }).catch(error => {
        reject(error)
      })
    })
  },

  // user 退出登录
  logout({ commit, state }) {
    return new Promise((resolve, reject) => {
      logout(state.token).then(() => {
        removeToken() // must remove  token  first
        resetRouter()
        commit('RESET_STATE')
        resolve()
      }).catch(error => {
        reject(error)
      })
    })
  },

  // 修改密码
  updatePassword({ commit }, updatePasswordVo) {
    return new Promise((resolve, reject) => {
      updatePassword(updatePasswordVo).then(response => {
        resolve()
      }).catch(error => {
        reject(error)
      })
    })
  },

  // remove token
  resetToken({ commit }) {
    return new Promise(resolve => {
      removeToken() // must remove  token  first
      commit('RESET_STATE')
      resolve()
    })
  }
}

export default {
  namespaced: true,
  state,
  mutations,
  actions
}

