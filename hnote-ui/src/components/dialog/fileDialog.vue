<template>
    <!-- 移动到文件夹dialog -->
    <el-dialog
      title="移动到"
      :visible.sync="fileDialogVisible"
      :before-close="handleClose"
      width="30%">
      <div class="mf-title">
        <img src="/static/img/word.png" class="type-img" v-if="currentSelectedNote.noteType && currentSelectedNote.noteType.name == 'word'" />
        <img src="/static/img/markdown.png" class="type-img" v-else-if="currentSelectedNote.noteType && currentSelectedNote.noteType.name == 'md'" />
        <span class="title">{{ currentSelectedNote.title }}</span>
      </div>
      <div class="submenu mf-folder">
        <el-tree :data="folders" @node-click="handleNodeClick"></el-tree>
      </div>
      <span slot="footer" class="dialog-footer">
        <el-button @click="changeDialogVisible">取 消</el-button>
        <el-button type="primary" @click="moveNoteFolder">确 定</el-button>
      </span>
    </el-dialog>
</template>

<script>
    import { mapGetters } from 'vuex'
    import { updateNoteFolder } from '@/api/note'

    export default {
        name: 'file-dialog',
        data() {
            return {
                currentSelectedFolder: {}
            }
        },
        computed: {
            ...mapGetters([
                'fileDialogVisible',
                'currentSelectedNote'
            ])
        },
        props: {
            folders: {
                type: Array,
                required: true
            }
        },
        methods: {
            handleNodeClick(data) {
                this.currentSelectedFolder = data;
            },
            changeDialogVisible() {
                this.$store.dispatch('ChangeFileDialogVisible', false)
            },
            handleClose() {
                this.$store.dispatch('ChangeFileDialogVisible', false)
            },
            moveNoteFolder() {
                var noteId = this.currentSelectedNote.id
                var folderId = this.currentSelectedFolder.id
    
                if (!folderId || folderId == this.currentSelectedNote.folder.id) {
                   this.$message({
                        message: '请选择文件夹',
                        type: 'warning'
                    });

                    return
                } 
                new Promise((resolve, reject) => {
                    updateNoteFolder(folderId, noteId).then(response => {
                        if (response.status == 204) {
                            this.$message({
                                message: '移动文件夹成功！',
                                type: 'success'
                            });
                            this.$store.dispatch('ChangeFileDialogVisible', false)
                        }
                    })
                })
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
