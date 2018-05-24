const folder = {
    state: {
        selectedFolder: {}
    },
    mutations: {
        SET_SELECTED_FOLDER: (state, folder) => {
          state.selectedFolder = folder
        }
    },
    actions: {
        SetSelectedFolder({ commit }, folder) {
            commit('SET_SELECTED_FOLDER', folder)
        }
    }
}

export default folder