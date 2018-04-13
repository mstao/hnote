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
                <el-dropdown-item>黄金糕</el-dropdown-item>
                <el-dropdown-item>狮子头</el-dropdown-item>
                <el-dropdown-item>螺蛳粉</el-dropdown-item>
                <el-dropdown-item disabled>双皮奶</el-dropdown-item>
                <el-dropdown-item divided>蚵仔煎</el-dropdown-item>
              </el-dropdown-menu>
            </el-dropdown>
            <img :src="infoPic" class="info" @mouseover="infoPic = infoPicBlue" @mouseout="infoPic = infoPic1" />
        </div>
      </div>
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

</style>

<script>
  export default {
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
        infoPicBlue: "../static/img/info-blue.png"
      }
    },
    methods: {
    }
  };

</script>