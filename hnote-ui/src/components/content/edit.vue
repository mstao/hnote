<template>
  <div class="content-container">
    <div class="top-navi-container">
      <div class="top-navi">
        <input class="title" v-model="title" /> 
        <div class="operation">
            <span class="loading-span" v-show="isShowLoading">
              <svg-icon icon-class="loading" />
            </span>
            <img :src="editPic" v-show="isShowEditBtn" class="eidt" @click='changeEditStatus' @mouseover="editPic = editPicBlue" @mouseout="editPic = editPic1" />
            <img :src="savePic" v-show="isShowSaveBtn" class="save" @click='updateOrSaveContent' @mouseover="savePic = savePicBlue" @mouseout="savePic = savePic1" />
            <img :src="tagPic" class="tag" @click="toggleTagDiv" @mouseover="tagPic = tagPicBlue" @mouseout="tagPic = tagPic1" />
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
              <div class="note-info-tips">
                <ul>
                  <li><span>来源：</span> <span>{{note.source}}</span></li>
                  <li><span>作者：</span> <span>{{note.author}}</span></li>
                  <li><span>创建时间：</span> <span>{{note.gmtCreate != undefined ? note.gmtCreate.substring(0,10) : ""}}</span></li>
                  <li><span>修改时间：</span> <span>{{note.gmtModified != undefined ? note.gmtModified.substring(0,10) : ""}}</span></li>
                  <li><span>文件夹：</span> <span>{{label}}</span></li>
                </ul>
              </div>
            </el-popover>
        </div>
      </div>
    </div>
    <br/>
    <div></div>
    <transition name="slide-fade">
    <div class="tag-container" v-show="isShowTagDiv">
      <div class="tag-menu">
        <el-tag
          :key="tag"
          v-for="tag in dynamicTags"
          closable
          :disable-transitions="false"
          @close="handleTagClose(tag)">
          {{tag}}
        </el-tag>
        <el-input
          class="input-new-tag"
          v-if="inputVisible"
          v-model="inputValue"
          ref="saveTagInput"
          size="small"
          @keyup.enter.native="handleInputConfirm"
          @blur="handleInputConfirm"
        >
        </el-input>
        <el-button v-else class="button-new-tag" size="small" @click="showInput">+ New Tag</el-button>
      </div>
    </div>
    </transition>

    <div v-if="showMarkdownEditor" class="edit-content">
      <mavon-editor style="height: 100%"  v-model="content"></mavon-editor>
    </div>

    <div v-else class="html-content markdown-body" v-html="htmlContent">
    </div>
  </div>
</template>

<script>
  import { mapGetters } from 'vuex'
  import { mavonEditor } from 'mavon-editor'
  import 'mavon-editor/dist/css/index.css'
  import { getTagsByNid, saveTag } from '@/api/tag'
  import { deleteTagByNidTid, updateNote, createNote } from '@/api/note'

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
        savePic: "/static/img/save.png",
        savePic1: "/static/img/save.png",
        savePicBlue: "/static/img/save-blue.png",
        tagPic: "/static/img/tag-gray.png",
        tagPic1: "/static/img/tag-gray.png",
        tagPicBlue: "/static/img/tag-blue.png",
        showMarkdownEditor: false,
        isShowEditBtn: true,
        isShowSaveBtn: false,
        isShowTagDiv: false,
        isShowLoading: false,
        inputVisible: false,
        inputValue: '',
        dynamicTags: [],
        sourceTags:  []
      }
    },
    watch:{
      note(newNote, oldNote) {
        if (newNote.id === oldNote.id){  
          return
        } else {
          this.showMarkdownEditor = false;
          this.isShowEditBtn = true;
          this.isShowSaveBtn = false;
        } 
      }
    },
    computed: {
      ...mapGetters([
        'note',
      ]),
      htmlContent() {
        if (this.note.content == undefined) {
          return marked('<center>![image](http://p8rape2j2.bkt.clouddn.com/no-content.png)</center>')
        } else { 
          if (this.note.id == undefined) {
            this.isShowEditBtn = false;
            this.isShowSaveBtn = true;
            this.showMarkdownEditor = true
          }
          return marked(this.note.content) 
        }
      },
      label() {
        if (this.note.content !== undefined) {
          return this.note.folder.label
        } else {
          return ''
        }
      },
      title: {
        get () {
          return this.note.title
        },
        set (value) {
          if (value != this.note.title) {
            var note = this.note
            note.title = value;
            this.$store.dispatch('UpdateNote', note)
            this.updateTitle()
          }
        }
      },
      content: {
        get () {
          return this.note.content
        },
        set (value) {
          if (value != this.note.content) {
            var note = this.note
            note.content = value;
            this.$store.dispatch('UpdateNote', note)
          }
        }
      }
    },
    mounted() {
      this.init()
    },
    methods: {
      init() {
        if (this.note.id == undefined) {
          this.showMarkdownEditor = true;
        }
      },
      handleCommand(command) {
        if (command == 'move') {
          this.$store.dispatch('SetCurrentSelectedNote', this.note)
          this.$store.dispatch('ChangeFileDialogVisible', true)
        }
      },
      handleTagClose(tag) {
        // Get tag id
        var x;
        var tagId;
        for (x in this.sourceTags) {
          if (tag == this.sourceTags[x].name) {
            tagId = this.sourceTags[x].id;
            break;
          }
        }
        // Delete data from database
        new Promise((resolve, reject) => {
          deleteTagByNidTid(this.note.id, tagId).then(response => {
            if (response.status == 204) {
              this.dynamicTags.splice(this.dynamicTags.indexOf(tag), 1);
            }
          })
        })
      },
      showInput() {
        this.inputVisible = true;
        this.$nextTick(_ => {
          this.$refs.saveTagInput.$refs.input.focus();
        });
      },
      handleInputConfirm() {
        var inputValue = this.inputValue;
        if (inputValue) {
          var tag = {
            name: inputValue
          }
    
          new Promise((resolve, reject) => {
            saveTag(tag, this.note.id).then(response => {
              if (response.status == 201) {
                this.dynamicTags.push(inputValue);
              }
            })
          }) 
        }

        this.inputVisible = false;
        this.inputValue = '';
      },
      changeEditStatus() {
        if (this.showMarkdownEditor) {
          this.isShowEditBtn = true;
          this.isShowSaveBtn = false;
          this.showMarkdownEditor = false
        } else {
          this.isShowEditBtn = false;
          this.isShowSaveBtn = true;
          this.showMarkdownEditor = true
        }
      },
      toggleTagDiv() {
        if (this.isShowTagDiv) {
          this.isShowTagDiv = false
        } else {
          if (this.note.id == undefined) {

          } else {
            new Promise((resolve, reject) => {
              getTagsByNid(this.note.id).then(response => {
                if (response.status == 200) {
                  var tempTags = [];
                  var x;
                  this.sourceTags = response.data;
                  for (x in this.sourceTags) {
                    tempTags.push(this.sourceTags[x].name)
                  }
                  this.dynamicTags = tempTags;
                }
              })
            })
          }

          this.isShowTagDiv = true
        }
      },
      updateTitle() {
        if (this.note.id != undefined) {
          var id = this.note.id;
          var title = this.title;

          if (title != '') {
            this.isShowLoading = true;
            var data = {
              id: id,
              title: title
            }
            new Promise((resolve, reject) => {
              updateNote(data).then(response => {
                if (response.status == 204) {
                  this.$message({
                    message: '修改标题成功',
                    type: 'success'
                  });
                }

                this.isShowLoading = false
              })
            })
          } else {
            this.$message({
              message: '标题不能为空！',
              type: 'warning'
            });
          }
        }
      },
      updateOrSaveContent() {
        if (this.note.id == undefined) {
          this.saveNote()
        } else {
          this.updateContent()
        }
      },
      saveNote() {
        if (this.title == '新的markdown' || this.title.replace(/(^s*)|(s*$)/g, "").length == 0) {
          this.$message({
              message: '请输入标题',
              type: 'warning'
          });
          return
        }
        
        if (this.content.replace(/(^s*)|(s*$)/g, "").length == 0) {
          this.$message({
              message: '请输入正文内容',
              type: 'warning'
          });
          return
        }

        var data = {
          title: this.title,
          content: this.content,
          folderId: this.note.folder.id,
          author: this.note.author,
          source: '',
          typeId: 2,
          uid: localStorage.getItem('userId')
        }

        new Promise((resolve, reject) => {
          createNote(data).then(response => {
            if (response.status == 201) {
              var id = response.data.id
              this.$store.dispatch('GetNoteInfoById', id)
              this.$message({
                message: '新增markdown成功',
                type: 'success'
              });
            } else {
              this.$message({
                message: '新增markdown失败，请检查网络',
                type: 'success'
              });
            }
          })
        })
      },
      updateContent() {
        this.isShowLoading = true;
        var id = this.note.id;
        var content = this.content;
        var data = {
          id: id,
          content: content
        }
        new Promise((resolve, reject) => {
          updateNote(data).then(response => {
            if (response.status == 204) {
              this.$message({
                message: '修改内容成功',
                type: 'success'
              });
            }

            this.isShowLoading = false
          })
        })
      },
      test(item) {
        console.log("item = " + JSON.stringify(item))
      }
    },
    components: {
      'mavon-editor': mavonEditor
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
  display: inline-block;
  min-width: 73%;
  height: 72px;
  border: 0px;
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
  height: 86%;
  width: 60%;
}

.html-content {
  margin-top: 54px;
  position: fixed;
  padding-left: 20px;
  padding-top: 20px;
  height: 84%;
  width: 59%;
  overflow: auto;
}

.tag-container {
  margin-top: 54px;
  min-height: 30px;
  border-bottom: 1px solid #EBEBEB;
  margin-bottom: -54px;
}

.tag-container .tag-menu {
  margin-left: 5px;
  margin-top: 10px;
  margin-bottom: 10px;
}
.tag-container .tag-menu .el-tag {
  margin-left: 10px;
  margin-top: 5px;
}

.tag-container .tag-menu .button-new-tag {
  margin-top: 5px;
}

.el-tag + .el-tag {
  margin-left: 10px;
}
.button-new-tag {
  margin-left: 10px;
  height: 32px;
  line-height: 32px;
  padding-top: 0;
  padding-bottom: 0;
}
.input-new-tag {
  margin-top: 5px;
  margin-left: 10px;
  vertical-align: bottom;
}

.note-info-tips {
  color: #666666;
}

.note-info-tips ul {
  margin-left: -20px;
}

.note-info-tips ul li {
  list-style-type: none;
} 

.slide-fade-enter-active {
  transition: all .3s ease;
}
.slide-fade-leave-active {
  transition: all .8s cubic-bezier(1.0, 0.5, 0.8, 1.0);
}
.slide-fade-enter, .slide-fade-leave-to
/* .slide-fade-leave-active for below version 2.1.8 */ {
  transform: translateX(10px);
  opacity: 0;
}
</style>
