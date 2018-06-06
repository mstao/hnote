<template>
  <el-container id="content">
    <el-aside width="20%" class="aside-operation">
      <div class="add-new-doc">
        <el-dropdown class="add-new-doc-dropdown" @command="handleCreateCommand">
          <span class="el-dropdown-link">
            <img src="/static/img/add.png" class="add-image">新文档<i class="el-icon-arrow-down el-icon--right"></i>
          </span>
          <el-dropdown-menu slot="dropdown">
            <el-dropdown-item>新建笔记</el-dropdown-item>
            <el-dropdown-item command="create-md">新建Markdown</el-dropdown-item>
            <el-dropdown-item command="create-folder">新建文件夹</el-dropdown-item>
            <el-dropdown-item divided>导入笔记</el-dropdown-item>
          </el-dropdown-menu>
        </el-dropdown>
      </div>

      <div class="operation-list">
        <ul class="accordion-ul" @mouseover="cancelFirstLiStyle">
          <li @click="fetchLastestNotes"><div :class="{'li-background-hover': isSelectFirstLi }"><img src="/static/img/new-doc.png" /><span>最新文档</span></div></li>
          <li>
            <div class="link" v-on:click="dropdown($event)"><img src="/static/img/folder.png"><span>我的文件夹</span></div>
            <div class="submenu">
              <el-tree 
                ref="folderTree" 
                node-key="id" 
                accordion 
                :data="folders" 
                @node-click="handleNodeClick" 
                @node-contextmenu="handNodeContextmenu"
                @node-expand="handleNodeExpandCollapse" 
                @node-collapse="handleNodeExpandCollapse">
              </el-tree>
              <div class="folder-item-operation-box item-operation-box">
                <ul>
                  <li  @mouseover="showExpandNewDiv = true"  @mouseout="showExpandNewDiv = false">新建<img src="/static/img/right-expand.png" />
                    <div v-if="showExpandNewDiv" class="expand-new-div">
                      <ul>
                        <li @click="handNodeClickForAddFolder">文件夾</li>
                        <li @click="handNodeClickForAddMdClick">Markdown</li>
                      </ul>
                    </div>
                  </li>
                  <li>重命名</li>
                  <li>删除</li>
                </ul>
              </div>
            </div>
          </li>
          <li>
            <div class="link" v-on:click="dropdown($event)"><img src="/static/img/tag.png" /><span>我的标签</span></div>
            <div class="submenu tag-menu">
              <span class="tag-span" @click.stop="fetchNotesByTid(tag)" :title="tag" v-bind:key="tag" v-for="tag in dynamicTags">{{tag}}</span>
            </div>
          </li>
          <li><div><img src="/static/img/share.png" /><span>我的分享</span></div></li>
          <li @click="fetchTrashsInfo"><div><img src="/static/img/garbage.png" /><span>废纸篓</span></div></li>
        </ul>
      </div>
    </el-aside>
    <el-aside width="20%" class="aside-list">
      <div class="navi-list-container">
        <div class="navi-list">
          <img src="/static/img/back.png" @click="fetchNotesByParentFolder" class="back" />
          <el-input  placeholder="搜索内容" v-model="token" v-on:keyup.enter.native="search"></el-input>
          <el-dropdown @command="handleSortCommand">
            <span class="el-dropdown-link">
              <img src="/static/img/sort-option.png" class="sort-option-img"><i class="el-icon-arrow-down el-icon--right"></i>
            </span>
            <el-dropdown-menu slot="dropdown" class="navi-list-dropdown-menu">
              <el-dropdown-item command="createTime"><span>创建时间</span>
                <img src="/static/img/asc.png" v-if="sortItem.sort == 'gmt_create' ? ( sortItem.sortType == 'asc' ? true : false) : false" />
                <img src="/static/img/desc.png" v-if="sortItem.sort == 'gmt_create' ? ( sortItem.sortType == 'desc' ? true : false) : false" />
              </el-dropdown-item>
              <el-dropdown-item command="modifiedTime"><span>修改时间</span>
                <img src="/static/img/asc.png" v-if="sortItem.sort == 'gmt_modified' ? ( sortItem.sortType == 'asc' ? true : false) : false" />
                <img src="/static/img/desc.png" v-if="sortItem.sort == 'gmt_modified' ? ( sortItem.sortType == 'desc' ? true : false) : false" />
              </el-dropdown-item>
              <el-dropdown-item command="fileName"><span>文件名称</span>
                <img src="/static/img/asc.png" v-if="sortItem.sort == 'title' ? ( sortItem.sortType == 'asc' ? true : false) : false" />
                <img src="/static/img/desc.png" v-if="sortItem.sort == 'title' ? ( sortItem.sortType == 'desc' ? true : false) : false" />
              </el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>
        </div>
      </div>
      <div class="list-content-container" v-if="noteList.length != 0">
        <div class="list-content" @mouseover="handleSetCurrentNote(item)" @click="goNoteDetailPage(item.id)" :data-nid="item.id" :key="item.id" v-for="item in noteList">
          <div>
            <img src="/static/img/word.png" class="type-img" v-if="item.noteType.name == 'word'" />
            <img src="/static/img/markdown.png" class="type-img" v-else-if="item.noteType.name == 'md'" />

            <span class="title">{{item.title.substring(0, 18)}}</span>
            <span class="date">{{item.date.substring(5, 10)}}</span>
            <span class="operation">
              <img src="/static/img/download.png" title="下载" @click.stop="downloadNote" />
              <img src="/static/img/share_16.png" title="分享" @click.stop="shareNote" />
              <img src="/static/img/delete.png" title="删除" @click.stop="deleteNoteDialogVisible = true" />
            </span>
          </div>
          <div class="rename-input">
            <el-input></el-input>
          </div>
        </div>

        <div class="list-item-operation-box item-operation-box">
          <ul>
            <li @click="changeFileDialogVisible">移动到</li>
            <li @click="deleteNoteDialogVisible = true" v-if="!currentSelectedNote.deleted">删除</li>
            <li @click="recover" v-else>恢复</li>
            <li @click="downloadNote">下载</li>
            <li @click="shareNote">分享</li>
          </ul>
        </div>
      </div>
      
      <div class="list-content-container" v-else>
        <center>
          <img src="http://p8rape2j2.bkt.clouddn.com/Blank-page_No-content.png" class="default-no-list-image"/>
        </center>
      </div>
    </el-aside>
    <el-main width="60%">
      <router-view class="view"></router-view>
    </el-main>
    <!-- move to folder -->
    <file-dailog :folders="folders"/>

    <!-- 刪除提示 -->
    <el-dialog
      title="删除提示"
      :visible.sync="deleteNoteDialogVisible"
      width="30%">
      <span>确认删除以下笔记：</span>
      <div class="delete-dialog-content">
        <img src="/static/img/word.png" class="delete-type-img" v-if="currentSelectedNote.noteType && currentSelectedNote.noteType.name == 'word'" />
        <img src="/static/img/markdown.png" class="delete-type-img" v-else-if="currentSelectedNote.noteType && currentSelectedNote.noteType.name == 'md'" />
        <span>{{currentSelectedNote.title}}</span>
      </div>
      <span slot="footer" class="dialog-footer">
        <el-button @click="deleteNoteDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="handDeleteNote" v-loading.fullscreen.lock="fullscreenLoading">确 定</el-button>
      </span>
    </el-dialog>

    <!-- 新增dialog -->
    <el-dialog
      title="新增文件夹"
      :visible.sync="saveFolder.addFolderDialogVisible"
      width="30%">
      <span>请填写文件夹名称：</span>
      <el-input v-model="saveFolder.inputFolderName" clearable v-on:blur="checkFolder" placeholder="请输入内容"></el-input>
      <span class="loading-span" v-show="saveFolder.isShowCheckFolderLoading">
        <svg-icon icon-class="loading" />
      </span>
      <span :class="{'folder-valid-true': saveFolder.inputFolderNameValid, 'folder-valid-false': !saveFolder.inputFolderNameValid}">
        {{saveFolder.folderNameValidTips}}
      </span>
      <br/><br/>
      <span>请选择父文件夹(不选择即为根文件夹)：</span>
      <div class="submenu mf-folder">
        <el-tree :data="folders" accordion @node-click="handleSelectParentFolderClick"></el-tree>
      </div><br>
      <span v-if="saveFolder.currentSelectParentFolder.label">已选择父文件夹：<b>{{saveFolder.currentSelectParentFolder.label}}</b> &nbsp;&nbsp;
        <el-button type="primary" size="mini" icon="el-icon-delete" @click="clearSelectParentFolder" title="清除"></el-button>
      </span>
      <span slot="footer" class="dialog-footer">
        <el-button @click="saveFolder.addFolderDialogVisible = false">取 消</el-button>
        <el-button type="primary"  ref="saveFolderBtn" @click="handAddFolderClick" v-loading.fullscreen.lock="fullscreenLoading">确 定</el-button>
      </span>
    </el-dialog>
  </el-container>
</template>

<script>
  import NProgress from 'nprogress' // progress bar
  import 'nprogress/nprogress.css'// progress bar style
  import file_dialog from '../dialog/fileDialog.vue'
  import { toJsonTree, getNode } from '@/utils/json'
  import { folderFilter, saveFolder } from '@/api/folder'
  import { getNotesByPage, getLastestNotes, deleteNote, getNoteByToken, getNoteByTid, updateNoteFolder } from '@/api/note'
  import { getAllTrashsByPage, recover } from '@/api/trash'
  import { getTagsByUid } from '@/api/tag'
  import { getNowFormatDate } from '@/utils/date'
  import { mapGetters } from 'vuex'

  NProgress.configure({ showSpinner: false })// NProgress Configuration

  export default {
    name: 'note-container',
    data() {
      return {
        folders: [],
        sourceTags: [],
        dynamicTags: [],
        inputVisible: false,
        inputValue: '',
        noteList: [],
        dialogVisible: false,
        showExpandNewDiv: false,
        userId: '',
        isSelectFirstLi: true,
        deleteNoteDialogVisible: false,
        fullscreenLoading: false,
        token: '',
        sortItem: { sort: 'gmt_create', sortType: 'desc' },
        saveFolder: {
          addFolderDialogVisible: false, 
          currentSelectParentFolder: {},
          inputFolderName: '',
          inputFolderNameValid: false,
          isShowCheckFolderLoading: false,
          folderNameValidTips: ''
        }
      };
    },
    components: {
      'file-dailog': file_dialog
    },
    computed: {
      ...mapGetters([
        'selectedFolder',
        'name',
        'currentSelectedNote'
      ]),
    },
    created() {
      this.init()
    },
    watch: {
      '$route': 'init'
    },
    methods: {
      init() {
        this.fetchBasicInfo();

        $(document).on("contextmenu",".list-content", function(e) {
              var clientHeight = window.innerHeight;

              if (clientHeight - e.pageY > 250) {
                $(".list-item-operation-box")
                  .css("left", e.pageX)
                  .css("top", e.pageY - 40)
                  .show();
              } else {
                $(".list-item-operation-box")
                  .css("left", e.pageX)
                  .css("top", e.pageY - 240)
                  .show();
              }
            
              e.preventDefault();  // return false; also works
            }
          );

          $(document).on("mouseenter", ".list-content", function() {
            $(this).find(".date").hide();
            $(this).find(".operation").show();
          });

          $(document).on("mouseleave", ".list-content", function() {
            $(this).find(".date").show();
            $(this).find(".operation").hide();
          });
      },
      fetchBasicInfo() {
        // start loading
        const loading = this.$loading({
          lock: true,
          text: 'Loading',
          spinner: 'el-icon-loading'
        });
        setTimeout(() => {
          loading.close();
        }, 2000);

        // start progress bar
        NProgress.start()

        this.fetchFolders()

        this.fetchLastestNotes()

        this.fetchTags()

        NProgress.done()
        loading.close()
      },
      fetchTags() {
        const uid = localStorage.getItem("userId");
        new Promise((resolve, reject) => {
          getTagsByUid(uid).then(response => {
            if (response.status == 200) {
              var tempTags = [];
              var x;
              this.sourceTags = response.data;
              for (x in this.sourceTags) {
                tempTags.push(this.sourceTags[x].name)
              }
              this.dynamicTags = tempTags;
            }
            resolve()
          }).catch(error => {
            reject(error)
          })
        })
      },
      fetchFolders() {
        const uid = localStorage.getItem("userId");
        new Promise((resolve, reject) => {
          folderFilter({uid:uid}).then(response => {
            const data = response.data
            console.log(data)
            this.folders = toJsonTree(data, 'id', 'pid', 'children')
            
            this.$store.dispatch('SetSelectedFolder', this.findDefaultFolder(data))
            resolve()
          }).catch(error => {
            reject(error)
          })
        })
      },
      findDefaultFolder(data) {
        var x;
        for (x in data) {
          if (data[x].pid == 0 && data[x].level == 0) {
            return data[x];
            break;
          } 
        }
      },
      fetchLastestNotes() {
        var pageNumber = 1;
        var pageSize = 20;
        new Promise((resolve, reject) => {
          getLastestNotes(pageNumber, pageSize, this.sortItem.sort, this.sortItem.sortType).then(response => {
            this.handleFetchNotes(response)  
          })
        })
      },
      handleNodeClick(data) {
        this.fetchNotesByFolderId(data)
      },
      handNodeContextmenu(e, data) {
        // pop up right menu.
        var clientHeight = window.innerHeight;
        if (clientHeight - e.pageY > 250) {
          $(".folder-item-operation-box")
            .css("left", e.pageX)
            .css("top", e.pageY - 40)
            .show();
        } else {
          $(".folder-item-operation-box")
            .css("left", e.pageX)
            .css("top", e.pageY - 240)
            .show();
        }
      
        e.preventDefault();  // return false; also works

        this.$store.dispatch('SetSelectedFolder', data)
      },
      fetchNotesByFolderId(folder) {
        var fid = folder.id
        var pageNumber = 1;
        var pageSize = 20;
        new Promise((resolve, reject) => {
          getNotesByPage(pageNumber, pageSize, fid, this.sortItem.sort, this.sortItem.sortType).then(response => {
              this.handleFetchNotes(response)
          })
        })
        
        this.$store.dispatch('SetSelectedFolder', folder)
      },
      handleFetchNotes(response) {
        const tempList = [];
        if (response.status == 200) {
          const items = response.data.items;
          for (var i = 0; i < items.length; i++) {
            var temp = {
              id: items[i].id,
              title: items[i].title,
              date: items[i].gmtCreate,
              deleted: items[i].deleted,
              noteType: items[i].noteType,
              folder: items[i].folder
            }
            tempList.unshift(temp);
          }
          this.noteList = tempList
          // load note info by first item
          this.goNoteDetailPage(items[0].id);
          // Update the current note
        } else if (response.status == 204) {
          this.noteList = tempList
          this.$store.dispatch('ClearNoteInfo')
        } 
      },
      goNoteDetailPage(id) {
        this.$store.dispatch('GetNoteInfoById', id)
      },
      handleNodeExpandCollapse() {
        $(".item-operation-box").hide();
      },
      dropdown(event) {
        var el = event.currentTarget;
        var $this = $(el);
        var $next = $this.next();
        $next.slideToggle();
      },
      handleClose(tag) {
        this.dynamicTags.splice(this.dynamicTags.indexOf(tag), 1);
      },
      showInput() {
        this.inputVisible = true;
        this.$nextTick(_ => {
          this.$refs.saveTagInput.$refs.input.focus();
        });
      },
      handleInputConfirm() {
        let inputValue = this.inputValue;
        if (inputValue) {
          this.dynamicTags.push(inputValue);
        }
        this.inputVisible = false;
        this.inputValue = '';
      },
      changeFileDialogVisible() {
        this.$store.dispatch('ChangeFileDialogVisible', true)
      },
      handDeleteNote() {
        // close dialog
        this.deleteNoteDialogVisible = false
        // loading
        this.openFullScreen() 
        
        var note = this.currentSelectedNote
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
              // Remove note from list
              var data = this.noteList
              var x
              for (x in data) {
                if (data[x].id == id) {
                  data.splice(x, 1);
                  break;
                } 
              }

              // Remove note from vuex
              this.$store.dispatch('ClearNoteInfo')
            }
          })
        })
      },
      cancelFirstLiStyle() {
        this.isSelectFirstLi = false
      },
      handleSetCurrentNote(item) {
        if (item !== undefined) {
          this.$store.dispatch('SetCurrentSelectedNote', item)
        }
      },
      handleCreateCommand(command) {
        if (command == 'create-md') {
          this.createMd(this.selectedFolder)
        } else if (command == 'create-folder') {
          this.saveFolder.addFolderDialogVisible = true
        }
      },
      createMd(folder) {
        var data = {
          title: '新的markdown',
          content: '',
          noteType: { name: 'md' },
          folder: folder,
          date: getNowFormatDate(),
          author: this.name,
          source: ''
        }
        this.noteList.push(data)
        this.$store.dispatch('SetNote', data)
      },
      search() {
        if (this.token.replace(/(^s*)|(s*$)/g, "").length == 0) {
          this.$message({
              message: '请输入要搜索的内容！',
              type: 'warning'
          });
        } else {
          var pageNumber = 1
          var pageSize = 20
          
          new Promise((resolve, reject) => {
            getNoteByToken(this.token, pageNumber, pageSize, this.sortItem.sort, this.sortItem.sortType).then(response => {
              this.handleFetchNotes(response)
              resolve()
            }).catch(error => {
              reject(error)
            })
          })
        }
      },
      fetchTrashsInfo() {
        var pageNumber = 1;
        var pageSize = 20;
        new Promise((resolve, reject) => {
          getAllTrashsByPage(pageNumber, pageSize, this.sortItem.sort, this.sortItem.sortType).then(response => {
            this.handleFetchNotes(response)
            resolve()
          }).catch(error => {
            reject(error)
          })
        })
      },
      shareNote() {
        if (this.currentSelectedNote.id != undefined) {
          this.$router.push('/share/index/' + this.currentSelectedNote.id)
        }
      },
      downloadNote() {
        if (this.currentSelectedNote.id != undefined) {
          window.location.href = process.env.BASE_API + "/files/download?bid=" + this.currentSelectedNote.id
        }
      },
      recover() {
        // loading
        this.openFullScreen() 

        var id = this.currentSelectedNote.id;
        new Promise((resolve, reject) => {
          recover(id).then(response => {
            if (response.status == 204) {
              this.$message({
                message: '恢复成功！',
                type: 'success'
              });
              // Remove note from list
              var data = this.noteList
              var x
              for (x in data) {
                if (data[x].id == id) {
                  data.splice(x, 1);
                  break;
                } 
              }

              // Remove note from vuex
              this.$store.dispatch('ClearNoteInfo')
            }
          })
        })
      },
      fetchNotesByTid(tag) {
        // Get tag id
        var x;
        var tagId;
        for (x in this.sourceTags) {
          if (tag == this.sourceTags[x].name) {
            tagId = this.sourceTags[x].id;
            break;
          }
        }

        var pageNumber = 1;
        var pageSize = 20;
        // Delete data from database
        new Promise((resolve, reject) => {
          getNoteByTid(tagId, pageNumber, pageSize, this.sortItem.sort, this.sortItem.sortType).then(response => {
            if (response.status == 200) {
              this.handleFetchNotes(response)
            }
          })
        })
      },
      fetchNotesByParentFolder() {
        var curr = this.selectedFolder
    
        if (curr) {
          var obj = this.$refs.folderTree.getNode(curr.id).parent.data
          
          if (obj !== this.folders) {
            // fetch note data.
            this.fetchNotesByFolderId(obj)
          }
        }
      },
      handleSortCommand(command) {
        if (command == 'createTime') {
          this.changeSortStatus('gmt_create')
        } else if (command == 'modifiedTime') {
          this.changeSortStatus('gmt_modified')
        } else if (command == 'fileName') {
          this.changeSortStatus('title')
        }
      },
      changeSortStatus(sort) {
          var sortType = this.sortItem.sortType;
          if (sortType == 'desc') {
            this.sortItem.sortType = 'asc'
          } else if (sortType == 'asc') {
            this.sortItem.sortType = 'desc'
          }
          this.sortItem.sort = sort
      },
      updatedNoteFolder(folderId, noteId) {
        new Promise((resolve, reject) => {
          updateNoteFolder(folderId, noteId).then(response => {
            if (response.status == 204) {
              this.$message({
                  message: '移动文件夹成功！',
                  type: 'success'
              });
            }
          })
        })
      },
      handleSelectParentFolderClick(data) {
        this.saveFolder.currentSelectParentFolder = data
      },
      handAddFolderClick() {
        if (this.saveFolder.inputFolderNameValid) {
          var curr = this.saveFolder.currentSelectParentFolder;
          if (curr.level == 0) {
            this.$message({
                message: '默认文件夹下不允许新建文件夹!',
                type: 'warning'
            });
            return
          }

          this.openFullScreen()

          new Promise((resolve, reject) => {
            var label = this.saveFolder.inputFolderName
            var pid = curr.id ? curr.id : 0
            var uid = localStorage.getItem('userId')
            var level = curr.id ? curr.level + 1 : 1
            var data = {
              label : label,
              pid: pid,
              uid: uid,
              level: level
            }
            saveFolder(data).then(response => {
              if (response.status == 201) {
                this.$message({
                  message: '创建文件夹成功！',
                  type: 'success'
                });

                this.saveFolder.currentSelectParentFolder = {}
                this.saveFolder.inputFolderNameValid = false
                this.saveFolder.inputFolderName = ''
                this.saveFolder.addFolderDialogVisible = false

                this.fetchFolders();
              }
            })
          })
        } else {
          this.$message({
              message: '文件夹名称检测不通过!',
              type: 'warning'
          });
        }
      },
      checkFolder() {
        if (this.saveFolder.inputFolderName) {
          this.saveFolder.isShowCheckFolderLoading = true
          var uid = localStorage.getItem('userId') 
          new Promise((resolve, reject) => {
            folderFilter({label: this.saveFolder.inputFolderName, uid: uid}).then(response => {
              if (response.status == 200) {
                var data = response.data
                if (data.length == 0) {
                  this.saveFolder.inputFolderNameValid = true
                  this.saveFolder.folderNameValidTips = '文件夹名称可用'
                } else {
                  this.saveFolder.inputFolderNameValid = false
                  this.saveFolder.folderNameValidTips = '文件夹名称已被占用'
                }
                this.saveFolder.isShowCheckFolderLoading = false
              }
              resolve()
            }).catch(error => {
              this.saveFolder.isShowCheckFolderLoading = false
              reject(error)
            }) 
          })
        }
      },
      clearSelectParentFolder() {
        this.saveFolder.currentSelectParentFolder = {}
      },
      handNodeClickForAddFolder() {
        this.saveFolder.addFolderDialogVisible = true
      },
      handNodeClickForAddMdClick() {
        var curr = this.selectedFolder
        this.createMd(curr)
      },
      openFullScreen() {
       const loading = this.$loading({
          lock: true,
          text: 'Loading',
          spinner: 'el-icon-loading',
          background: 'rgba(0, 0, 0, 0.7)'
        });
        setTimeout(() => {
          loading.close();
        }, 2000);
      },
      test(item) {
        console.log("item = " + JSON.stringify(item))
      }
    }
  };

  $.fn.contextmenu = function(func) {
    return this.bind("contextmenu", func);
  }

  $(document).click(function(){
    $(".item-operation-box").hide();
  });
  
</script>

<style>
.el-aside {
  height: 100%;
  border-right: 1px solid #F0F0F0;
}
.aside-operation {
  background: #ffffff;
  overflow: hidden;
}
.aside-operation .add-new-doc {
  width: 100px;
  margin: 20px auto;
}
.el-dropdown-link {
  cursor: pointer;
}
.el-dropdown-link .add-image {
  position: relative;
  top: 8px;
  margin-right: 5px;
}
.operation-list {
  border-top:1px solid #EBEBEB; 
  height: 92%;
  overflow: auto;
}
.operation-list ul li {
  list-style-type: none;
  cursor: pointer;
}
.li-background-hover {
  background: #D2E2FF;
  border-left: 4px solid #5576BD;
}
.operation-list ul li:last-child {
  margin-bottom: 60px;
}
.operation-list ul li > div:first-child {
  margin-left: -40px;
  padding-left: 40px;
  padding-bottom: 10px;
}
.operation-list ul li > div:first-child:hover {
  background: #D2E2FF;
  border-left: 4px solid #5576BD;
  padding-left: 36.5px;
}
.list-backgroud-hover {
  background: #D2E2FF;
  border-left: 4px solid #5576BD;
  padding-left: 36px;
}
.operation-list ul li > .submenu {
  display: none;
}
.operation-list .folder-item-operation-box {
  display: none;
}
.operation-list .folder-item-operation-box img {
  position: relative;
  top: 3px;
  margin-left: 15px;
}
.operation-list .folder-item-operation-box .expand-new-div {
  position: absolute;
  top: 0px;
  left: 110px;
  z-index: 200;
  background-color: #fff;
  border: 1px solid #ebeef5;
  border-radius: 4px;
  -webkit-box-shadow: 0 2px 12px 0 rgba(0,0,0,.1);
  box-shadow: 0 2px 12px 0 rgba(0,0,0,.1);
}
.operation-list ul li img {
  position: relative;
  top: 7px;
  margin-right: 10px;
}
.operation-list ul li .tag-menu {
  margin-left: 5px;
  margin-top: 10px;
  margin-bottom: 10px;
  width: 250px;
}
.operation-list ul li .tag-menu .tag-span {
  background-color: rgba(64,158,255,.1);
  display: inline-block;
  padding: 0 10px;
  height: 32px;
  line-height: 30px;
  font-size: 12px;
  color: #409eff;
  border-radius: 4px;
  box-sizing: border-box;
  border: 1px solid rgba(64,158,255,.2);
  white-space: nowrap;
  margin-left: 10px;
  margin-top: 5px;
  cursor: pointer;
}

.aside-list {
  background: #FAFAFA;
  overflow: hidden;
}
.aside-list .navi-list-container {
  border-bottom: 1px solid #EBEBEB;
  position: fixed;
  height: 72px;
  width: 20%;
  overflow-y: hidden;
}
.aside-list .navi-list {
  width: 350px;
  margin: 0 auto;
  line-height: 72px;
}
.aside-list .navi-list .back, .el-input, .el-dropdown {
  display: inline;
}
.aside-list .navi-list .back {
  position: relative;
  top: 6px;
  margin-left: 10px;
  cursor: pointer;
}
.aside-list .navi-list .el-input {
  margin-left: 20px;
}
.aside-list .navi-list .el-dropdown {
  margin-left: 30px;
}
.aside-list .navi-list .el-dropdown .sort-option-img {
  position: relative;
  top: 6px;
}
.aside-list .navi-list .el-input__inner {
  width: 200px;
  height: 30px;
}
.aside-list .list-content-container {
  margin-top: 73px;
  overflow: auto;
  height: 92%;
}
.aside-list .list-content {
  height: 50px;
  line-height: 50px;
  color: #8590A6;
}
.aside-list .list-content:last-child {
  margin-bottom: 60px;
}
.aside-list .list-content:hover {
  background: #F2F2F2;
}
.aside-list .list-content .type-img {
  margin-left: 20px;
  position: relative;
  top: 4px;
}
.aside-list .list-content .date {
  color: #B2B2CB;
  float: right;
  margin-right: 20px;
}
.aside-list .list-content .operation {
  float: right;
  margin-right: 20px;
  display: none;
}
.aside-list .list-content .operation img {
  margin-left: 8px;
}
.aside-list .list-content .operation img:hover {
  cursor: pointer;
}
.aside-list .list-content .rename-input {
  width: 200px;
  margin-left: 20px;
  display: none;
}
.item-operation-box {
  position: absolute;
  display: none;
  z-index: 200;
  background-color: #fff;
  border: 1px solid #ebeef5;
  border-radius: 4px;
  -webkit-box-shadow: 0 2px 12px 0 rgba(0,0,0,.1);
  box-shadow: 0 2px 12px 0 rgba(0,0,0,.1);
}
.item-operation-box ul {
  display: block;
  list-style-type: disc;
  -webkit-margin-before: 1em;
  -webkit-margin-after: 1em;
  -webkit-margin-start: 0px;
  -webkit-margin-end: 0px;
  -webkit-padding-start: 40px;
  margin-left: -40px;
}
.item-operation-box ul li {
  list-style: none;
  line-height: 36px;
  padding: 0 20px;
  margin: 0;
  font-size: 14px;
  color: #606266;
  cursor: pointer;
  outline: 0;
  display: list-item;
  text-align: -webkit-match-parent;
}
.item-operation-box ul li:hover {
  background-color: #ecf5ff;
  color: #66b1ff;
}
.el-main {
  background-color: #ffffff;
  color: #333;
  padding: 0;
  overflow: hidden;
}
#content {
  background-color: #cc85d9;
  width: 100%;
  position: absolute;
  top: 50px;
  bottom: 0px;
  left: 0px;
}
.default-no-list-image {
  margin-top: 20px;
}

.delete-dialog-content {
  margin-top: 20px;
}
.delete-dialog-content .delete-type-img { 
  position: relative;
  top: 5px;
}
.folder-valid-false {
  color: #F56C6C;
}
.folder-valid-true {
  color: #67C23A;
}
</style>

