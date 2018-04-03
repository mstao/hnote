<template>
  <div id="app">
  
    <el-container>
      <el-header>Header</el-header>
      <el-container class="content">
        <el-aside width="200px">Aside</el-aside>
        <el-aside width="200px">Aside</el-aside>
        <el-main>Main</el-main>
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
  background-color: #B3C0D1;
  color: #333;
  text-align: center;
  line-height: 60px;
}

.el-aside {
  background-color: #D3DCE6;
  color: #333;
  text-align: center;
  height: 100%;
}

.el-main {
  background-color: #E9EEF3;
  color: #333;
  text-align: center;
  width: 100%
}

.content {
     position: absolute;
    top: 60px;
    bottom: 0px;
    left: 0px;
} 
</style>
