import request from '@/utils/request'

export function getFoldersByUid(uid) {
    return request({
        url: '/folders/filters',
        method: 'get',
        params: {uid}
    })
}