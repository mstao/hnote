import request from '@/utils/request'

export function loginByUsername(username, password) {

  return request({
    url: '/tokens',
    method: 'post',
    data: {
      'userName': username,
      'password': password
    }
  })
}

export function logout() {
  return request({
    url: '/tokens',
    method: 'delete'
  })
}

export function getUserInfo(token) {
  return request({
    url: '/user/info',
    method: 'get',
    params: { token }
  })
}
