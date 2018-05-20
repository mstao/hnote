<template>
  <div class="wrapper">
    <el-container>
      <!-- S header -->
      <h-header/>
      <!-- E header -->
      <!-- content -->
      <router-view></router-view>
    </el-container>
  </div>
</template>

<script>
  import h_header from './header.vue'

  export default {
    name: 'home',
    data() {
      return {
      };
    },
    components: {
      'h-header': h_header,
    },
    methods: {
    },
  };
</script>

<style>
body{margin: 0;}
.wapper {
  min-width: 1200px;
  margin: 0 auto;
  font-family: "Helvetica Neue","PingFang SC",Arial,sans-serif;
}
</style>
