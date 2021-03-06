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

export function logout(userId) {
  return request({
    url: '/tokens',
    method: 'delete',
    data: {
      'userId': userId
    }
  })
}

export function getUserInfo(userId) {
  return request({
    url: '/users/' + userId,
    method: 'get'
  })
}

export function checkUser(userName) {
  return request({
    url: '/users/check',
    method: 'get',
    params: { userName }
  })
}

export function createUser(user) {
  return request({
    url: '/users',
    method: 'post',
    data: user
  })
} 