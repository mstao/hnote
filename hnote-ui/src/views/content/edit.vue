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
            <img :src="sharePic" class="share" @mouseover="sharePic = sharePicBlue" @mouseout="sharePic = sharePic1" @click="shareNote"/>
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
      <mavon-editor style="height: 100%" ref="md" @imgAdd="$imgAdd" v-model="content"></mavon-editor>
    </div>

    <div v-else class="html-content markdown-body" v-html="htmlContent">
    </div>
    
    <!-- share dialog -->
    <share-dialog :noteId="note.id == undefined ? 0 : note.id" />

    <el-dialog
      title="删除提示"
      :visible.sync="deleteNoteDialogVisible"
      width="30%">
      <span>确认删除以下笔记：</span>
      <div class="delete-dialog-content">
        <img src="/static/img/word.png" class="delete-type-img" v-if="note.noteType && note.noteType.name == 'word'" />
        <img src="/static/img/markdown.png" class="delete-type-img" v-else-if="note.noteType && note.noteType.name == 'md'" />
        <span>{{note.title}}</span>
      </div>
      <span slot="footer" class="dialog-footer">
        <el-button @click="deleteNoteDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="handDeleteNote" v-loading.fullscreen.lock="fullscreenLoading">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
  import { mapGetters } from 'vuex'
  import { mavonEditor } from 'mavon-editor'
  import 'mavon-editor/dist/css/index.css'
  import { getTagsByNid, saveTag } from '@/api/tag'
  import { deleteTagByNidTid, updateNote, createNote, deleteNote } from '@/api/note'
  import { uploadImage } from '@/api/file'
  import sensitiveWordChecker from '@/utils/sensitiveWordChecker'
  import share_dialog from '../dialog/shareDialog.vue'

  const marked = require('marked');

  export default {
    name: 'edit',
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
        sourceTags:  [],
        deleteNoteDialogVisible: false,
        fullscreenLoading: false,
        //nid: this.$route.params.id
      }
    },
    // create() {
    //   this.$store.dispatch('GetNoteInfoById', this.nid)
    // },
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
    components: {
      'mavon-editor': mavonEditor,
      'share-dialog': share_dialog
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
        } else if(command == 'delete') {
          this.deleteNoteDialogVisible = true
        } else if (command == 'download') {
          this.downloadNote()
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
            name: inputValue,
            uid: localStorage.getItem('userId')
          }
    
          new Promise((resolve, reject) => {
            saveTag(tag, this.note.id).then(response => {
              if (response.status == 201) {
                this.fetchTagsByNoteId(this.note.id)
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
              this.fetchTagsByNoteId(this.note.id)
            })
          }

          this.isShowTagDiv = true
        }
      },
      fetchTagsByNoteId(noteId) {
        new Promise((resolve, reject) => {
          getTagsByNid(noteId).then(response => {
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
            // 检测敏感词
            if (!this.checkSensitiveWord(title)) {
              return
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

        // 检测敏感词
        if (!(this.checkSensitiveWord(this.title) || 
            this.checkSensitiveWord(this.content))) {
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
      handDeleteNote() {
        // close dialog
        this.deleteNoteDialogVisible = false
        // loading
        this.openFullScreen() 
        
        var note = this.note
        if (note.id != undefined) {
          this.deleteNote(note.id)
        } else {
          this.$message({
            message: '无效的删除操作！',
            type: 'warning'
          });
        }
      },
      deleteNote(id) {
        new Promise((resolve, reject) => {
          deleteNote(id).then(response => {
            if (response.status == 204) {
              this.$message({
                message: '删除成功！',
                type: 'success'
              });

              // Remove note from vuex
              this.$store.dispatch('ClearNoteInfo')
            }
          })
        })
      },
      shareNote() {
        if (this.note.id != undefined) {
          this.$store.dispatch('ChangeShareDialogVisible', true)
        }
      },
      openFullScreen() {
        this.fullscreenLoading = true;
        setTimeout(() => {
          this.fullscreenLoading = false;
        }, 1500);
      },
      $imgAdd(pos, $file){
          // 第一步.将图片上传到服务器.
          var formdata = new FormData();
          formdata.append('file', $file);
          new Promise((resolve, reject) => {
            uploadImage(formdata).then((response) => {
                // 第二步.将返回的url替换到文本原位置![...](0) -> ![...](url)
                // $vm.$img2Url 详情见本页末尾
                this.$refs.md.$img2Url(pos, response.data.url);
            })
          })
      },
      downloadNote() {
        if (this.note.id != undefined) {
          window.location.href = process.env.BASE_API + "/files/download?bid=" + this.note.id
        }
      },
      checkSensitiveWord(content) {
          if (sensitiveWordChecker(content)) {
            this.$message({
              message: '包含敏感词汇，发送失败：）',
              type: 'warning'
            });
            return false;
          } else {
            return true;
          }
      },
      test(item) {
        console.log("item = " + JSON.stringify(item))
      }
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
  height: 83%;
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

.delete-dialog-content {
  margin-top: 20px;
}
.delete-dialog-content .delete-type-img { 
  position: relative;
  top: 5px;
}
</style>
