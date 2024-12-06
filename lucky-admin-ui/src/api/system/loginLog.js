import request from '@/utils/request'
const apiPath = '/system/loginLog' // api Path url

// 条件分页查询
export function pageByParams(data, currentPage, pageSize) {
  return request({
    url: apiPath + '/pageByParams?&currentPage=' + currentPage + '&pageSize=' + pageSize,
    method: 'post',
    data
  })
}

// id查询一个
export function getById(id) {
  return request({
    url: apiPath + '/get/' + id,
    method: 'get'
  })
}

// 删除和批量删除 (传id数组)
export function deleteByIds(ids) {
  return request({
    url: apiPath + '/delete/' + ids,
    method: 'get'
  })
}
