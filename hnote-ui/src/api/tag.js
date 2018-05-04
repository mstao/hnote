import request from '@/utils/request'

export function getTagsByNid(nid) {
    return request({
        url: '/tags/filters',
        method: 'get',
        params: {nid}
    })
}

export function saveTag(tag) {
    return request({
        url: '/tags',
        method: 'post',
        data: tag
    })
}

export function getTagById(tid) {
    return request({
        url: '/tags/' + tid,
        method: 'get'
    })
}