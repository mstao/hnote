import { getNoteById, getLastestNotes } from '@/api/note'

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
                  } else if (response.status == 200) {
                    commit('SET_NOTE', response.data)
                  } else if (response.status == 204) {
                    commit('SET_NOTE', '')
                  }

                  resolve(response)
                }).catch(error => {
                  reject(error)
                })
            })
        },
        ClearNoteInfo({ commit }) {
          commit('SET_NOTE', '')
        }
    }
}

export default note