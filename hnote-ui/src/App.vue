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
              <ul class="accordion-ul">
                <li><div><img src="./assets/new-doc.png" /><span>最新文档</span></div></li>
                <li>
                  <div class="link" v-on:click="dropdown($event)"><img src="./assets/folder.png"><span>我的文件夹</span></div>
                  <div class="submenu">
                    <el-tree :data="folder" :props="defaultProps" @node-click="handleNodeClick"></el-tree>
                  </div>
                </li>
                <li>
                  <div class="link" v-on:click="dropdown($event)"><img src="./assets/tag.png" /><span>我的标签</span></div>
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
                <li><div><img src="./assets/share.png" /><span>我的分享</span></div></li>
                <li><div><img src="./assets/garbage.png" /><span>废纸篓</span></div></li>
              </ul>
            </div>

        </el-aside>
        <el-aside width="20%" class="aside-list">
          <div class="navi-list-container">
            <div class="navi-list">
              <img src="./assets/back.png" class="back" />
              <el-input v-model="input" placeholder="搜索内容"></el-input>
              <el-dropdown>
                <span class="el-dropdown-link">
                  <img src="./assets/sort-option.png" class="sort-option-img"><i class="el-icon-arrow-down el-icon--right"></i>
                </span>
                <el-dropdown-menu slot="dropdown" class="navi-list-dropdown-menu">
                  <el-dropdown-item><span>摘要</span><img src="./assets/selected.png" /></el-dropdown-item>
                  <el-dropdown-item><span>列表</span></el-dropdown-item>
                  <el-dropdown-item divided><span>创建时间</span><img src="./assets/asc.png" /></el-dropdown-item>
                  <el-dropdown-item><span>修改时间</span></el-dropdown-item>
                  <el-dropdown-item><span>文件名称</span></el-dropdown-item>
                </el-dropdown-menu>
              </el-dropdown>
            </div>
          </div>
          <router-view class="view"></router-view>
        </el-aside>
        <el-main></el-main>
      </el-container>
    </el-container>
  </div>
</template>

<script>
  export default {
    name: 'app',
    data() {
      return {
        folder: [{
          label: '一级 1',
          children: [{
            label: '二级 1-1',
            children: [{
              label: '三级 1-1-1'
            }]
          }]
        }, {
          label: '一级 2',
          children: [{
            label: '二级 2-1',
            children: [{
              label: '三级 2-1-1'
            }]
          }, {
            label: '二级 2-2',
            children: [{
              label: '三级 2-2-1'
            }]
          }]
        }, {
          label: '一级 3',
          children: [{
            label: '二级 3-1',
            children: [{
              label: '三级 3-1-1'
            }]
          }, {
            label: '二级 3-2',
            children: [{
              label: '三级 3-2-1'
            }]
          }]
        }],
        defaultProps: {
          children: 'children',
          label: 'label'
        },
        dynamicTags: ['标签一', '标签二', '标签三'],
        inputVisible: false,
        inputValue: ''
      };
    },
    methods: {
      handleNodeClick(data) {
        console.log(data);
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
      }
    }
  };

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
}

.operation-list ul li {
  list-style-type: none;
  cursor: pointer;
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
}

.aside-list .navi-list-container {
  border-bottom: 1px solid #EBEBEB;
  z-index: 100;
  position: static;
}

.aside-list .navi-list {
  width: 350px;
  margin: 17.5px auto;
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
