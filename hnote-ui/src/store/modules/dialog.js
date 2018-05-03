const dialog = {
    state: {
        fileDialogVisible: false
    },
    mutations: {
        SET_FILE_DIALOG_VISIBLE: (state, fileDialogVisible) => {
          state.fileDialogVisible = fileDialogVisible
        }
    },
    actions: {
        ChangeFileDialogVisible({ commit }, visible) {
            return new Promise((resolve, reject) => {
                commit('SET_FILE_DIALOG_VISIBLE', visible)
            })
        }
    }
}

export default dialog