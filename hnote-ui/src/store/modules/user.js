import { loginByUsername, logout, getUserInfo } from '@/api/user'
import { getToken, setToken, removeToken } from '@/utils/auth'
import { Message } from 'element-ui'

const user = {
  state: {
    user: '',
    status: '',
    token: getToken(),
    name: '',
    avatar: ''
  },

  mutations: {
    SET_TOKEN: (state, token) => {
      state.token = token
    },
    SET_NAME: (state, name) => {
      state.name = name
    },
    SET_AVATAR: (state, avatar) => {
      state.avatar = avatar
    }
  },

  actions: {
    SetToken({ commit, state }, token) {
      commit('SET_TOKEN', token)
      setToken(token)
    },
    // 获取用户信息
    GetUserInfo({ commit, state }) {
      return new Promise((resolve, reject) => {
        const userId = localStorage.getItem('userId')
        getUserInfo(userId).then(response => {
          if (!response.status == 200) { // 由于mockjs 不支持自定义状态码只能这样hack
            reject('error')
          } 
          const data = response.data

          commit('SET_NAME', data.name)     
          commit('SET_AVATAR', data.avatarUrl)
          resolve(response)
        }).catch(error => {
          reject(error)
        })
      })
    },

    // 第三方验证登录
    // LoginByThirdparty({ commit, state }, code) {
    //   return new Promise((resolve, reject) => {
    //     commit('SET_CODE', code)
    //     loginByThirdparty(state.status, state.email, state.code).then(response => {
    //       commit('SET_TOKEN', response.data.token)
    //       setToken(response.data.token)
    //       resolve()
    //     }).catch(error => {
    //       reject(error)
    //     })
    //   })
    // },

    // 登出
    LogOut({ commit, state }) {
      return new Promise((resolve, reject) => {
        logout(state.userId).then(() => {
          commit('SET_TOKEN', '')
          commit('SET_USER_ID', '')
          removeToken()
          resolve()
        }).catch(error => {
          reject(error)
        })
      })
    },

    // 前端 登出
    FedLogOut({ commit }) {
      return new Promise(resolve => {
        commit('SET_TOKEN', '')
        commit('SET_USER_ID', '')
        removeToken()
        resolve()
      })
    },

  }
}

export default user
