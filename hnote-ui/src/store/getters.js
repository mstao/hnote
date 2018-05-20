const getters = {
  token: state => state.user.token,
  avatar: state => state.user.avatar,
  name: state => state.user.name,
  errorLogs: state => state.errorLog.logs,
  fileDialogVisible: state => state.dialog.fileDialogVisible,
  note: state => state.note.note,
  currentSelectedNote: state => state.note.currentSelectedNote,
  selectedFolder: state => state.folder.selectedFolder
}
export default getters
