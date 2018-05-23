<template>
    <el-header>
        <div class="logo" title="首页" @click="goHome">
            <span>H-Note</span>
        </div>
        <div class="refresh">
            <i class="el-icon-loading"></i>
            <span>同步</span>
        </div>
        <div class="profile">
            <el-dropdown @command="handleCommand">
              <div class="avatar el-dropdown-link">
                  <img :src="avatar" class="avatar-img" />
              </div>
              <el-dropdown-menu slot="dropdown">
                  <el-dropdown-item command='userInfo'>个人信息</el-dropdown-item>
                  <el-dropdown-item>帐户设置</el-dropdown-item>
                  <el-dropdown-item>导入笔记</el-dropdown-item>
                  <el-dropdown-item>帮助</el-dropdown-item>
                  <el-dropdown-item command='logout' divided>注销登录</el-dropdown-item>
              </el-dropdown-menu>
            </el-dropdown>
        </div>

        <div class="operation">
            <span class="write-mode">写作模式</span>
            <span class="write-mode">探索</span>
            <span class="write-mode">
              <el-popover
                placement="top"
                width="160"
                v-model="isShowNotification">
                <p>这是一段内容 通知？</p>
    
                <svg-icon icon-class="notification" slot="reference" />
                
              </el-popover>
              <span id="circle"></span>
            </span>
            <span class="write-mode" @click="userDialogVisible = true">{{name}}</span>
        </div>

        <!-- user info -->
        <el-dialog
            title="我的信息"
            :visible.sync="userDialogVisible"
            width="30%"
            :before-close="handleClose">
            <user-info/>
            <span slot="footer" class="dialog-footer">
              <el-button type="primary" @click="userDialogVisible = false">确 定</el-button>
            </span>
          </el-dialog>

    </el-header>
</template>

<script>
import store from '../../store'
import { mapGetters } from 'vuex'
import user_info from '../user/userInfo'

export default {  
  name: 'h-header',
  data() {
      return {
        userName: '',
        avatarUrl: '',
        userDialogVisible: false,
        isShowNotification: false
      }
  },
  computed: {
    ...mapGetters([
      'name',
      'avatar'
    ])
  },
  components: { 
    'user-info': user_info
  },
  methods: {
    goHome() {
      this.$router.push('/')
    },
    handleCommand(command) {
      if (command == 'logout') {
        this.$store.dispatch('LogOut').then(() => {
          location.reload()// In order to re-instantiate the vue-router object to avoid bugs
        })
      } else if (command == 'userInfo') {
        this.userDialogVisible = true
      }
    },
    handleClose() {
      this.userDialogVisible = false
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
  cursor: pointer;
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

#circle {
  position: absolute;
  top: 10px;
  width: 0.65rem;
  height: 0.65rem;
  border-radius: 100%;
  background: #f07379;
}

</style>


