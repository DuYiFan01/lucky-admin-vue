import request from '@/utils/request'
const apiPath = '/system/roles'; // api Path url

// 查询所有
export function list(data) {
    return request({
        url: apiPath + '/list',
        method: 'post',
        data
    })
}

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


// 查询当前角色的授权用户列表
export function grant(roleId) {
    return request({
        url: apiPath + '/grant/' + roleId,
        method: 'get'
    })
}
