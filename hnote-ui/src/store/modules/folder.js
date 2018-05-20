const folder = {
    state: {
        selectedFolder: ''
    },
    mutations: {
        SET_SELECTED_FOLDER: (state, folder) => {
          state.selectedFolder = folder
        }
    },
    actions: {
        SetSelectedFolder({ commit }, folder) {
            return new Promise((resolve, reject) => {
                commit('SET_SELECTED_FOLDER', folder)
            })
        }
    }
}

export default folder