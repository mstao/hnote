import request from '@/utils/request'

export function getFoldersByUid(uid) {
    return request({
        url: '/folders/filters',
        method: 'get',
        params: {uid: uid}
    })
}

export function folderFilter(data) {
    return request({
        url: '/folders/filters',
        method: 'get',
        params: data
    })
}

export function saveFolder(folder) {
    return request({
        url: '/folders',
        method: 'post',
        data: folder
    })
}