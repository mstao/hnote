import request from '@/utils/request'

export function getNotesByPage(pageNumber, pageSize, fid) {
    return request({
        url: '/notes/filters',
        method: 'get',
        params: {
            pageNumber,
            pageSize,
            fid
        }
    })
}

export function getNoteById(noteId) {
    return request({
        url: '/notes/' + noteId,
        method: 'get'
    })
}