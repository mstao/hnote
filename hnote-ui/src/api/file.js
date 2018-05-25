import request from '@/utils/request'

export function uploadImage(formdata) {
    return request({
        url: '/files/image',
        method: 'post',
        data: formdata,
        headers: { 'Content-Type': 'multipart/form-data' },
    })
}