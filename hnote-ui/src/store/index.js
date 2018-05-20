import Vue from 'vue'
import Vuex from 'vuex'
import user from './modules/user'
import errorLog from './modules/errorLog'
import dialog from './modules/dialog'
import note from './modules/note'
import folder from './modules/folder'
import getters from './getters'

Vue.use(Vuex)

const store = new Vuex.Store({
  modules: {
    user,
    errorLog,
    dialog,
    note,
    folder
  },
  getters
})

export default store
