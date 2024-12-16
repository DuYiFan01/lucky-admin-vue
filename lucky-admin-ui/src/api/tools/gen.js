import request from '@/utils/request'
const apiPath = '/tools/gen' // api Path url


// 查询列表信息
export function tableList(data, currentPage, pageSize) {
    return request({
        url: apiPath + '/tableList?&currentPage=' + currentPage + '&pageSize=' + pageSize,
        method: 'post',
        data
    })
}

// 生成代码预览
export function previewCode(tableName) {
    return request({
      url: apiPath + '/previewCode/' + tableName,
      method: 'get'
    })
  }
