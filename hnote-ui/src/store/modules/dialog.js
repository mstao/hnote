const dialog = {
    state: {
        fileDialogVisible: false,
        shareDialogVisible: false
    },
    mutations: {
        SET_FILE_DIALOG_VISIBLE: (state, fileDialogVisible) => {
          state.fileDialogVisible = fileDialogVisible
        },
        SET_SHARE_DIALOG_VISIBLE: (state, shareDialogVisible) => {
            state.shareDialogVisible = shareDialogVisible
        }
    },
    actions: {
        ChangeFileDialogVisible({ commit }, visible) {
            commit('SET_FILE_DIALOG_VISIBLE', visible)
        },

        ChangeShareDialogVisible({ commit }, visible) {
            commit('SET_SHARE_DIALOG_VISIBLE', visible)
        }
    }
}

export default dialog