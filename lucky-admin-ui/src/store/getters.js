
const getters = {
  sidebar: state => state.app.sidebar,
  device: state => state.app.device,
  token: state => state.user.token,
  username: state => state.user.username,
  roles: state => state.user.roles,
  routersTree: state => state.user.routersTree,
  permissions: state => state.user.permissions,
  permission_routes: state => state.permission.routes
}
export default getters
