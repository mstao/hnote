import request from '@/utils/request'

export function getNotesByPage(pageNumber, pageSize, fid, sort, sortType) {
    return request({
        url: '/notes/filters',
        method: 'get',
        params: {
            pageNumber,
            pageSize,
            fid,
            sort,
            sortType
        }
    })
}

export function getLastestNotes(pageNumber, pageSize, sort, sortType) {
    return request({
        url: '/notes/lastest',
        method: 'get',
        params: {
            pageNumber,
            pageSize,
            sort,
            sortType
        }
    })
}

export function getNoteById(noteId) {
    return request({
        url: '/notes/' + noteId,
        method: 'get'
    })
}

export function getNoteByToken(token, pageNumber, pageSize,  sort, sortType) {
    return request({
        url: '/notes/search',
        method: 'get',
        params: {
            token,
            pageNumber,
            pageSize,
            sort,
            sortType
        }
    })
}

export function getNoteByTid(tid, pageNumber, pageSize, sort, sortType) {
    return request({
        url: '/notes/tags/' + tid,
        method: 'get',
        params: {
            pageNumber,
            pageSize,
            sort,
            sortType
        }
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

export function updateNoteFolder(folderId, noteId) {
    return request({
        url: '/notes/folder',
        method: 'put',
        data: { folderId: folderId, id: noteId }
    })
}

export function deleteNote(id) {
    return request({
        url: '/notes/' + id,
        method: 'delete'
    })
}

export function deleteTagByNidTid(nid, tid) {
    return request({
        url: '/notes/' + nid + '/tags/' + tid,
        method: 'delete'
    })
}

