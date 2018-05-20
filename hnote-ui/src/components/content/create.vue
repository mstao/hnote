<template>
  <div class="content-container">
    <div class="top-navi-container">
      <div class="top-navi">
        <input class="title" v-model="title" /> 
        <div class="operation">
            <span class="loading-span" v-show="isShowLoading">
              <svg-icon icon-class="loading" />
            </span>
            <img :src="savePic" v-show="isShowSaveBtn" class="save" @click='updateContent' @mouseover="savePic = savePicBlue" @mouseout="savePic = savePic1" />
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
                  <li><span>来源：</span> <span>111</span></li>
                  <li><span>作者：</span> <span>111</span></li>
                  <li><span>创建时间：</span> <span>111</span></li>
                  <li><span>修改时间：</span> <span>111</span></li>
                  <li><span>文件夹：</span> <span>111</span></li>
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
  </div>
</template>

<script>
  import { mapGetters } from 'vuex'
  import { mavonEditor } from 'mavon-editor'
  import 'mavon-editor/dist/css/index.css'
  import { getTagsByNid, saveTag } from '@/api/tag'
  import { createNote } from '@/api/note'

  const marked = require('marked');

  export default {
    name: 'editor',
    data() {
      return {
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
        showMarkdownEditor: true,
        isShowTagDiv: false,
        isShowSaveBtn: true,
        isShowLoading: false,
        inputVisible: false,
        inputValue: '',
        dynamicTags: [],
        sourceTags:  []
      }
    },
    watch:{
     
    },
    methods: {
        handleCommand(command) {
        },
        handleTagClose(tag) {
        },
        saveTagInput() {
        },
        handleInputConfirm() {

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
