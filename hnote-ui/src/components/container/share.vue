<template>
    <el-container id="share-content">
        <el-main width="100%">
            <router-view class="view"></router-view>
        </el-main>
    </el-container>
</template>
<script>
export default {
    name: 'share',
    data() {
        return {

        }
    }
}
</script>

<style scoped>
#share-content {
  background-color: #F8F8F8;
  width: 100%;
  position: absolute;
  top: 50px;
  bottom: 0px;
  left: 0px;
}
</style>
