import request from '@/utils/request'

export function getAllTrashsByPage(pageNumber, pageSize) {
    return request({
        url: '/trashs/filters',
        method: 'get',
        params: {
            pageNumber,
            pageSize
        }
    })
}

export function recover(nid){
    return request({
        url: '/trashs/recover/' + nid,
        method: 'put'
    })
}