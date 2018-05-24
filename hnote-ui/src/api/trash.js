import request from '@/utils/request'

export function getAllTrashsByPage(pageNumber, pageSize, sort, sortType) {
    return request({
        url: '/trashs/filters',
        method: 'get',
        params: {
            pageNumber,
            pageSize,
            sort,
            sortType
        }
    })
}

export function recover(nid){
    return request({
        url: '/trashs/recover/' + nid,
        method: 'put'
    })
}