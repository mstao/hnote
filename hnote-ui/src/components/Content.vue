<template>
  <div>
    <div class="top-navi-container">
      <div class="top-navi">
        <span class="title">Git 提交的正确姿势</span>
        <div class="operation">
            <img :src="editPic" class="eidt" @mouseover="editPic = editPicBlue" @mouseout="editPic = editPic1" />
            <img :src="sharePic" class="share" @mouseover="sharePic = sharePicBlue" @mouseout="sharePic = sharePic1" />
            <el-dropdown>
              <span class="el-dropdown-link">
                <img :src="morePic" class="more" @mouseover="morePic = morePicBlue" @mouseout="morePic = morePic1" />
              </span>
              <el-dropdown-menu slot="dropdown">
                <el-dropdown-item>移动到</el-dropdown-item>
                <el-dropdown-item>下载</el-dropdown-item>
                <el-dropdown-item>删除</el-dropdown-item>
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
    <div class="edit-content">
        <mavon-editor style="height: 100%"></mavon-editor>
    </div>
  </div>
</template>

<style>
.top-navi-container {
  border-bottom: 1px solid #EBEBEB;
  z-index: 100;
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

</style>

<script>
  // Local Registration
  import { mavonEditor } from 'mavon-editor'
  import 'mavon-editor/dist/css/index.css'

  export default {
    name: 'editor',
    data() {
      return {
        editPic: "../static/img/edit.png",
        editPic1: "../static/img/edit.png",
        editPicBlue: "../static/img/edit-blue.png",
        sharePic: "../static/img/share-gray.png",
        sharePic1: "../static/img/share-gray.png",
        sharePicBlue: "../static/img/share-blue.png",
        morePic: "../static/img/more.png",
        morePic1: "../static/img/more.png",
        morePicBlue: "../static/img/more-blue.png",
        infoPic: "../static/img/info.png",
        infoPic1: "../static/img/info.png",
        infoPicBlue: "../static/img/info-blue.png",
        value: ''
      }
    },
    methods: {
    },
    components: {
        mavonEditor
        // or 'mavon-editor': mavonEditor
    }
  };

</script>