import request from '@/utils/request'
const apiPath = '/system/menus' // api Path url

// id查询一个
export function getById(id) {
  return request({
    url: apiPath + '/get/' + id,
    method: 'get'
  })
}

// 新增
export function save(data) {
  return request({
    url: apiPath + '/save',
    method: 'post',
    data
  })
}

// 修改所有字段为新值
export function updateAllById(data) {
  return request({
    url: apiPath + '/updateAllById',
    method: 'post',
    data
  })
}

// 修改指定字段为新值
export function updateSpecifyById(data) {
  return request({
    url: apiPath + '/updateSpecifyById',
    method: 'post',
    data
  })
}

// 删除和批量删除 (传id数组)
export function deleteByIds(ids) {
  return request({
    url: apiPath + '/delete/' + ids,
    method: 'get'
  })
}

// 获取菜单树
export function getMenusTree(data) {
  return request({
    url: apiPath + '/getMenusTree',
    method: 'post',
    data
  })
}
