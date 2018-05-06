<template>
  <div class="content-container">
    <div class="top-navi-container">
      <div class="top-navi">
        <span class="title">{{title}}</span>
        <div class="operation">
            <img :src="editPic" class="eidt" @click='changeEditStatus' @mouseover="editPic = editPicBlue" @mouseout="editPic = editPic1" />
            <img :src="sharePic" class="share" @mouseover="sharePic = sharePicBlue" @mouseout="sharePic = sharePic1" />
            <el-dropdown @command="handleCommand">
              <span class="el-dropdown-link">
                <img :src="morePic" class="more" @mouseover="morePic = morePicBlue" @mouseout="morePic = morePic1" />
              </span>
              <el-dropdown-menu slot="dropdown">
                <el-dropdown-item command='move'>移动到</el-dropdown-item>
                <el-dropdown-item command='download'>下载</el-dropdown-item>
                <el-dropdown-item command='delete'>删除</el-dropdown-item>
              </el-dropdown-menu>
            </el-dropdown>
            <img :src="infoPic" class="info"  v-popover:popoverInfo @mouseover="infoPic = infoPicBlue" @mouseout="infoPic = infoPic1" />
            <el-popover
              ref="popoverInfo"
              placement="top-start"
              width="200"
              trigger="hover">
              <div>

              </div>
            </el-popover>
        </div>
      </div>
    </div>
    <br/>

    <div v-if="showMarkdownEditor" class="edit-content">
      <mavon-editor style="height: 100%"  v-model="mdContent"></mavon-editor>
    </div>
    <div v-else class="html-content markdown-body" v-html="htmlContent">
    </div>
  </div>
</template>

<script>
  import { mapGetters } from 'vuex'
  import { mavonEditor } from 'mavon-editor'
  import 'mavon-editor/dist/css/index.css'
  const marked = require('marked');

  export default {
    name: 'editor',
    data() {
      return {
        editPic: "/static/img/edit.png",
        editPic1: "/static/img/edit.png",
        editPicBlue: "/static/img/edit-blue.png",
        sharePic: "/static/img/share-gray.png",
        sharePic1: "/static/img/share-gray.png",
        sharePicBlue: "/static/img/share-blue.png",
        morePic: "/static/img/more.png",
        morePic1: "/static/img/more.png",
        morePicBlue: "/static/img/more-blue.png",
        infoPic: "/static/img/info.png",
        infoPic1: "/static/img/info.png",
        infoPicBlue: "/static/img/info-blue.png",
        showMarkdownEditor: false,
        mdContent: '',
        title: ''
      }
    },
    computed: {
      ...mapGetters([
        'note',
      ]),
      htmlContent() {
        if (this.note.content !== undefined) {
          this.mdContent = this.note.content
          this.title = this.note.title
          return marked(this.note.content) 
        } else {
          return marked('## SO GAD')
        }
      }
    },
    methods: {
      handleCommand(command) {
        if (command == 'move') {
          this.$store.dispatch('ChangeFileDialogVisible', true)
        }
      },
      changeEditStatus() {
        this.showMarkdownEditor == true ? this.showMarkdownEditor = false : this.showMarkdownEditor = true
      }
    },
    components: {
        mavonEditor
        // or 'mavon-editor': mavonEditor
    }
  };
</script>

<style lang="css" scoped>
 

.top-navi-container {
  border-bottom: 1px solid #EBEBEB;
  position: fixed;
  height: 72px;
  width: 60%;
  overflow-y: hidden;
}

.top-navi {
  line-height: 72px;
}

.top-navi .title {
  font-size: 16px;
  color: #333333;
  margin-left: 20px;
}

.top-navi .operation {
  float: right;
  margin-right: 20px;
}
.top-navi .operation img {
  margin-left: 20px;
  cursor: pointer;
}

.edit-content {
  margin-top: 54px;
  position: fixed;
  height: 87%;
  width: 60%;
}

.html-content {
  margin-top: 54px;
  padding-left: 20px;
  padding-top: 20px;
  height: 800px;
  overflow: auto;
}
</style>
