import request from '@/utils/request'

export function getTagsByNid(nid) {
    return request({
        url: '/tags/filters',
        method: 'get',
        params: {nid}
    })
}

export function saveTag(tag, nid) {
    return request({
        url: '/tags',
        method: 'post',
        data: {name: tag.name},
        params: {nid}
    })
}

export function getTagById(tid) {
    return request({
        url: '/tags/' + tid,
        method: 'get'
    })
}
