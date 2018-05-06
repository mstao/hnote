import { getNoteById } from '@/api/note'

const note = {
    state: {
        note: ''
    },
    mutations: {
        SET_NOTE: (state, note) => {
          state.note = note
        }
    },
    actions: {
        GetNoteInfoById({ commit }, noteId) {
            return new Promise((resolve, reject) => {
                getNoteById(noteId).then(response => {
                  if (!response.status == 200) { // 由于mockjs 不支持自定义状态码只能这样hack
                    reject('error')
                  }
    
                  commit('SET_NOTE', response.data)
                  resolve(response)
                }).catch(error => {
                  reject(error)
                })
            })
        }
    }
}

export default note