<template>
  <div id="app">
  
    <el-container>
      <el-header>
        <div class="logo">
          <span>H-Note</span>

        </div>
        <div class="refresh">
          <i class="el-icon-refresh"></i> 
          <span>同步</span>
        </div>
        <div class="profile">
          <el-dropdown>
            <div class="avatar el-dropdown-link">
              <img src="./assets/avatar.jpg" class="avatar-img" />
            </div>
            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item>个人信息</el-dropdown-item>
              <el-dropdown-item>帐户设置</el-dropdown-item>
              <el-dropdown-item>导入笔记</el-dropdown-item>
              <el-dropdown-item>帮助</el-dropdown-item>
              <el-dropdown-item divided>注销登录</el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>
        </div>
        
        <div class="operation">
          <span class="write-mode">写作模式</span>
          <span class="write-mode">探索</span>
        </div>
      </el-header>
      <el-container id="content">
        <el-aside width="20%" class="aside-operation">
            <div class="add-new-doc">
              <el-dropdown class="add-new-doc-dropdown">
                <span class="el-dropdown-link">
                  <img src="./assets/add.png" class="add-image">新文档<i class="el-icon-arrow-down el-icon--right"></i>
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
              
            </div>

        </el-aside>
        <el-aside width="20%" class="aside-list">Aside</el-aside>
        <el-main><router-view class="view"></router-view></el-main>
      </el-container>
    </el-container>
  </div>
</template>

<script>

export default {
  name: 'app'
}
</script>

<style>
body{margin: 0;}
#app {
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
}

.aside-operation .add-new-doc {
  width: 100px;
  margin: 20px auto;
}

.el-dropdown-link .add-image {
  position: relative;
  top: 8px;
  margin-right: 5px;
}

.aside-list {
  background: #FAFAFA;
}

.el-main {
  background-color: #E9EEF3;
  color: #333;
  text-align: center;
}

#content {
    background-color: #cc85d9;
    width: 100%;
    position: absolute;
    top: 50px;
    bottom: 0px;
    left: 0px;
}

</style>
