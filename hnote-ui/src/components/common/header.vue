<template>
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
                <img src="/static/img/avatar.jpg" class="avatar-img" />
            </div>
            <el-dropdown-menu slot="dropdown">
                <el-dropdown-item>个人信息</el-dropdown-item>
                <el-dropdown-item>帐户设置</el-dropdown-item>
                <el-dropdown-item>导入笔记</el-dropdown-item>
                <el-dropdown-item>帮助</el-dropdown-item>
                <el-dropdown-item @click="logout" divided>注销登录</el-dropdown-item>
            </el-dropdown-menu>
            </el-dropdown>
        </div>

        <div class="operation">
            <span class="write-mode">写作模式</span>
            <span class="write-mode">探索</span>
        </div>
    </el-header>
</template>

<script>
export default {
  name: 'h-header',
  methods: {
    logout() {
      this.$store.dispatch('LogOut').then(() => {
        location.reload()// In order to re-instantiate the vue-router object to avoid bugs
      })
    }
  }
}
</script>

<style>
.el-header {
  background-color: #3091F2;
  color: #ffffff;
  line-height: 60px;
  height: 60px;
  width: 100%;
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
</style>


