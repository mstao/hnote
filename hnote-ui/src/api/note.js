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

export function getLastestNotes(pageNumber, pageSize) {
    return request({
        url: '/notes/lastest',
        method: 'get',
        params: {
            pageNumber,
            pageSize,
        }
    })
}

export function getNoteById(noteId) {
    return request({
        url: '/notes/' + noteId,
        method: 'get'
    })
}

export function updateNote(note) {
    return request({
        url: '/notes',
        method: 'put',
        data: note
    })
}

export function createNote(note) {
    return request({
        url: '/notes',
        method: 'post',
        data: note
    })
}

export function deleteTagByNidTid(nid, tid) {
    return request({
        url: '/notes/' + nid + '/tags/' + tid,
        method: 'delete'
    })
}

