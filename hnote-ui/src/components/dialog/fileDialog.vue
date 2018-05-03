<template>
      <!-- 移动到文件夹dialog -->
    <el-dialog
      title="移动到"
      :visible.sync="fileDialogVisible"
      width="30%">
      <div class="mf-title">
        <img src="/static/img/word.png" />
        <span class="title">Git 提交的正确姿势</span>
      </div>
      <div class="submenu mf-folder">
        <el-tree :data="folders" @node-click="handleNodeClick"></el-tree>
      </div>
      <span slot="footer" class="dialog-footer">
        <el-button @click="changeDialogVisible">取 消</el-button>
        <el-button type="primary" @click="changeDialogVisible">确 定</el-button>
      </span>
    </el-dialog>
</template>

<script>
    import { mapGetters } from 'vuex'

    export default {
        name: 'file-dialog',
        computed: {
            ...mapGetters([
                'fileDialogVisible'
            ])
        },
        props: {
            folders: {
                type: Array,
                required: true
            }
        },
        methods: {
            changeDialogVisible() {
                this.$store.dispatch('ChangeFileDialogVisible', false)
            },
            handleNodeClick(data) {
                console.log(data);
            },
        }
    }
</script>

<style>
    .mf-title {
      font-size: 16px;
      margin-top: -10px;
    }

    .mf-title img {
      position: relative;
      top: 4px;
    }

    .mf-folder {
      border: 1px solid #EBEBEB;
      padding: 5px;
      margin-top: 10px;
    }
</style>
