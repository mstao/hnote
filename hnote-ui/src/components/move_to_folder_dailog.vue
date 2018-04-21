<template>
    <!-- 移动到文件夹dialog -->
    <el-dialog
      title="移动到"
      :visible.sync="dialogVisible"
      width="30%">
      <div class="mf-title">
        <img src="/static/img/word.png" />
        <span class="title">Git 提交的正确姿势</span>
      </div>
      <div class="submenu mf-folder">
        <el-tree :data="folder" :props="defaultProps" @node-click="handleNodeClick"></el-tree>
      </div>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="dialogVisible = false">确 定</el-button>
      </span>
    </el-dialog>
</template>

<script>
export default {
  name: "move-to-folder-dialog"
}
</script>
