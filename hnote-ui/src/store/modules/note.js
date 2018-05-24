import { getNoteById, getLastestNotes } from '@/api/note'

const note = {
    state: {
        note: {},
        currentSelectedNote: {}
    },
    mutations: {
        SET_NOTE: (state, note) => {
          state.note = note
        },
        SET_CURRENT_SELECTED_NOTE: (state, note) => {
          state.currentSelectedNote = note
        } 
    },
    actions: {
        GetNoteInfoById({ commit }, noteId) {
            return new Promise((resolve, reject) => {
                getNoteById(noteId).then(response => {
                  if (!response.status == 200) {
                    reject('error')
                  } else if (response.status == 200) {
                    commit('SET_NOTE', response.data)
                  } else if (response.status == 204) {
                    commit('SET_NOTE', {})
                  }

                  resolve(response)
                }).catch(error => {
                  reject(error)
                })
            })
        },
        SetNote({ commit }, note) {
          commit('SET_NOTE', note)
        },
        ClearNoteInfo({ commit }) {
          commit('SET_NOTE', {})
        },
        UpdateNote({ commit }, note) {
          commit('SET_NOTE', note)
        },
        SetCurrentSelectedNote({ commit }, note) {
          commit('SET_CURRENT_SELECTED_NOTE', note)
        }
    }
}

export default note