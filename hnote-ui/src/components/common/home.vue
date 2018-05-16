<template>
  <div class="wrapper">
    <el-container>
      <!-- S header -->
      <h-header/>
      <!-- E header -->
      <el-container id="content">
        <el-aside width="20%" class="aside-operation">
            <div class="add-new-doc">
              <el-dropdown class="add-new-doc-dropdown">
                <span class="el-dropdown-link">
                  <img src="/static/img/add.png" class="add-image">新文档<i class="el-icon-arrow-down el-icon--right"></i>
                </span>
                <el-dropdown-menu slot="dropdown">
                  <el-dropdown-item>新建笔记</el-dropdown-item>
                  <el-dropdown-item>新建Markdown</el-dropdown-item>
                  <el-dropdown-item>新建文件夹</el-dropdown-item>
                  <el-dropdown-item divided>导入笔记</el-dropdown-item>
                </el-dropdown-menu>
              </el-dropdown>
            </div>

            <div class="operation-list">
              <ul class="accordion-ul">
                <li><div><img src="/static/img/new-doc.png" /><span>最新文档</span></div></li>
                <li>
                  <div class="link" v-on:click="dropdown($event)"><img src="/static/img/folder.png"><span>我的文件夹</span></div>
                  <div class="submenu">
                    <el-tree :data="folders" @node-click="handleNodeClick" @node-expand="handleNodeExpandCollapse" @node-collapse="handleNodeExpandCollapse"></el-tree>
                    <div class="folder-item-operation-box item-operation-box">
                      <ul>
                        <li  @mouseover="showExpandNewDiv = true"  @mouseout="showExpandNewDiv = false">新建<img src="/static/img/right-expand.png" />
                          <div v-if="showExpandNewDiv" class="expand-new-div">
                            <ul>
                              <li>文件夾</li>
                              <li>Markdown</li>
                            </ul>
                          </div>
                        </li>
                        <li>重命名</li>
                        <li @click="changeFileDialogVisible">移动到</li>
                        <li>删除</li>
                        <li>分享</li>
                      </ul>
                    </div>
                  </div>
                </li>
                <li>
                  <div class="link" v-on:click="dropdown($event)"><img src="/static/img/tag.png" /><span>我的标签</span></div>
                  <div class="submenu tag-menu">
                    <el-tag
                      :key="tag"
                      v-for="tag in dynamicTags"
                      closable
                      :disable-transitions="false"
                      @close="handleClose(tag)">
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
                </li>
                <li><div><img src="/static/img/share.png" /><span>我的分享</span></div></li>
                <li><div><img src="/static/img/garbage.png" /><span>废纸篓</span></div></li>
              </ul>
            </div>

        </el-aside>
        <el-aside width="20%" class="aside-list">
          <div class="navi-list-container">
            <div class="navi-list">
              <img src="/static/img/back.png" class="back" />
              <el-input  placeholder="搜索内容"></el-input>
              <el-dropdown>
                <span class="el-dropdown-link">
                  <img src="/static/img/sort-option.png" class="sort-option-img"><i class="el-icon-arrow-down el-icon--right"></i>
                </span>
                <el-dropdown-menu slot="dropdown" class="navi-list-dropdown-menu">
                  <el-dropdown-item><span>摘要</span><img src="/static/img/selected.png" /></el-dropdown-item>
                  <el-dropdown-item><span>列表</span></el-dropdown-item>
                  <el-dropdown-item divided><span>创建时间</span><img src="/static/img/asc.png" /></el-dropdown-item>
                  <el-dropdown-item><span>修改时间</span></el-dropdown-item>
                  <el-dropdown-item><span>文件名称</span></el-dropdown-item>
                </el-dropdown-menu>
              </el-dropdown>
            </div>
          </div>
          <div class="list-content-container" v-if="noteList.length != 0">
            <div class="list-content" @click="goNoteDetailPage(item.id)" :data-nid="item.id" :key="item.id" v-for="item in noteList">
              <div>
                <img src="/static/img/word.png" class="type-img" />
                <span class="title">{{item.title.substring(0, 20)}}</span>
                <span class="date">{{item.date.substring(5, 10)}}</span>
                <span class="operation">
                  <img src="/static/img/download.png" />
                  <img src="/static/img/share_16.png" />
                  <img src="/static/img/delete.png" />
                </span>
              </div>
              <!-- <div class="rename-input">
                <el-input></el-input>
              </div> -->
            </div>

            <div class="list-item-operation-box item-operation-box">
              <ul>
                <li @click="rename">重命名</li>
                <li @click="changeFileDialogVisible">移动到</li>
                <li>删除</li>
                <li>下载</li>
                <li>分享</li>
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
      </el-container>
    </el-container>

    <!-- 移动到文件夹dialog -->
    <file-dailog :folders="folders"/>
  </div>
</template>

<script>
  import NProgress from 'nprogress' // progress bar
  import 'nprogress/nprogress.css'// progress bar style
  import h_header from './header.vue'
  import file_dialog from '../dialog/fileDialog.vue'
  import toJsonTree from '@/utils/toJsonTree'
  import { getFoldersByUid } from '@/api/folder'
  import { getNotesByPage } from '@/api/note'

  NProgress.configure({ showSpinner: false })// NProgress Configuration

  export default {
    name: 'home',
    data() {
      return {
        folders: [],
        dynamicTags: [],
        inputVisible: false,
        inputValue: '',
        noteList: [],
        dialogVisible: false,
        showExpandNewDiv: false,
        userName: '',
        avator: '',
        userId: '',
        currentNoteId: ''
      };
    },
    components: {
      'h-header': h_header,
      'file-dailog': file_dialog
    },
    mounted() {
      this.init()
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

          $(document).on("contextmenu", ".el-tree-node__content", function(e) {
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
          });
      },
      fetchBasicInfo() {
        // start progress bar
        NProgress.start()

        this.fetchFolders();

        NProgress.done()
      },
      fetchFolders() {
        const uid = localStorage.getItem("userId");
        new Promise((resolve, reject) => {
          getFoldersByUid(uid).then(response => {
            const data = response.data
            const param = {parent:'pid' };
            this.folders = toJsonTree(data, 'id', 'pid', 'children');
            resolve()
          }).catch(error => {
            reject(error)
          })
        })
      },
      handleNodeClick(data) {
        var fid = data.id;
        var pageNumber = 1;
        var pageSize = 20;
        new Promise((resolve, reject) => {
          getNotesByPage(pageNumber, pageSize, fid).then(response => {
            const tempList = [];
            if (response.status == 200) {
              const items = response.data.items;
              for (var i = 0; i < items.length; i++) {
                var temp = {
                  id: items[i].id,
                  title: items[i].title,
                  date: items[i].gmtCreate
                }
                tempList.push(temp);
              }
              this.noteList = tempList
              // load note info by first item
              this.goNoteDetailPage(items[0].id);
            } else if (response.status == 204) {
              this.noteList = tempList
              this.$store.dispatch('clearNoteInfo')
            }   
          })
        })
      },
      goNoteDetailPage(id) {
        console.log("id = " + id)
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
      rename() {

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
body{margin: 0;}
.wapper {
  min-width: 1200px;
  margin: 0 auto;
  font-family: "Helvetica Neue","PingFang SC",Arial,sans-serif;
}

.el-header {
  background-color: #3091F2;
  color: #ffffff;
  line-height: 60px;
  height: 60px;
}

.el-header .logo {
  float: left;
  line-height: 50px;
}

.el-header .logo > span {
  font-size: 16px;
}

.el-header .refresh {
  float: left;
  line-height: 50px;
  margin-left: 50px;
}

.el-header .operation {
  float: right;
  line-height: 50px;
  margin-right: 50px;
  font-size: 14px;
}

.el-header .operation > span {
  cursor: pointer;
  padding-left: 20px;
}

.el-header .profile {
  float: right;
  width: 40px;
  height: 40px;
  margin-top: 5px;
  margin-right: 30px;
}

.el-header .profile .avatar {
  width: 40px;
  height: 40px;
  border-radius:40px;
  -moz-border-radius: 40px;
  -webkit-border-radius: 40px;
  cursor: pointer;
  display: block;
}

.el-header .profile .avatar .avatar-img {
  width: 40px;
  height: 40px;
  line-height: 0;		/* remove line-height */
  display: inline;	/* circle wraps image */
  border-radius: 50%;	/* relative value */
  -moz-border-radius: 50%;
  -webkit-border-radius: 50%;
  transition: linear 0.25s;
}

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
}

.operation-list ul li .tag-menu .el-tag {
  margin-left: 10px;
  margin-top: 5px;
}

.operation-list ul li .tag-menu .button-new-tag {
  margin-top: 5px;
}

.operation-list ul li .tag-menu .button-new-tag:hover {
  margin-top: 5px;
}

.el-tag + .el-tag {
  margin-left: 10px;
}

.button-new-tag {
  margin-left: 10px;
  height: 32px;
  line-height: 30px;
  padding-top: 0;
  padding-bottom: 0;
}

.input-new-tag {
  width: 90px;
  margin-left: 10px;
  vertical-align: bottom;
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
  top: 3px;
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
</style>
