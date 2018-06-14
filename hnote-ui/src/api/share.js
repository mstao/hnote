import request from '@/utils/request'

export function createShare(share) {
    return request({
        url: '/shares/',
        method: 'post',
        data: share
    })
}

export function getShareInfoByCode(code) {
    return request({
        url: '/shares/' + code,
        method: 'get'
    })
}