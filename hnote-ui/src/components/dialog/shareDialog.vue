<template>
    <el-dialog      
      title="分享文档"
      :visible.sync="shareDialogVisible"
      :before-close="handleClose"
      width="22%">
        <div style="margin-top: 20px;">
            <div class="share-link">
                <p class="title">链接分享</p>
                <div class="content">
                    <input class="share-link-input" v-model="shareUrl" readonly="" type="text">
                    <button class="link-copy" @click='handleCopy(shareUrl, $event)'>复制链接</button>
                </div>
            </div>
            <div class="share-menu">
                <p class="title">分享到</p>
                <div class="icons">
                    <div class="auth-icon">
                        <div class="tim auth-icon-title">
                            <span class="name">TIM</span>
                        </div>
                    </div>
                    <div class="auth-icon">
                        <div class="qq auth-icon-title">
                            <span class="name">QQ</span>
                        </div>
                    </div>
                    <div class="auth-icon">
                        <div class="wechat auth-icon-title">
                            <span class="name">微信</span>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </el-dialog>
</template>

<script>
    import { mapGetters } from 'vuex'
    import { createShare } from '@/api/share'
    import clip from '@/utils/clipboard' // use clipboard directly

    export default {
        name: 'share-dialog',
        data() {
            return {
                shareUrl: ''
            }
        },
        computed: {
            ...mapGetters([
                'shareDialogVisible'
            ])
        },
        props: {
            noteId: {
                type: Number,
                required: true
            }
        },
        watch: {
            'shareDialogVisible': function(visible) {
                if (visible && this.noteId > 0) {
                    new Promise((resolve, reject) => {
                        var share = {
                            noteId: this.noteId,
                            viewPassword: ''
                        } 
                        createShare(share).then(response => {
                            if (response.status == 201) {
                                var data = response.data
                                this.shareUrl = "localhost:8088" + '/share/doc/' + data.code
                            }
                        })
                    })
                }
            }
        },
        methods: {
            handleNodeClick(data) {
                this.currentSelectedFolder = data;
            },
            changeDialogVisible() {
                this.$store.dispatch('ChangeShareDialogVisible', false)
            },
            handleClose() {
                this.$store.dispatch('ChangeShareDialogVisible', false)
            },
            handleCopy(text, event) {
                clip(text, event)
            }
        }
    }
</script>

<style scope>
.share-link {
    margin-bottom: 36px;
}

.share-link .title {
    font-size: 14px;
    line-height: 14px;
    margin: 0 0 14px;
}

.share-link .content .share-link-input {
    font-size: 16px;
    height: 44px;
    width: 252px;
    line-height: 26px;
    padding: 8px;
    -webkit-box-sizing: border-box;
    box-sizing: border-box;
    border: 1px solid #f0f0f4;
    text-overflow: ellipsis;
}

input::-moz-placeholder, textarea::-moz-placeholder {
    color: #999;
}

.share-link .content .share-link-input::-moz-selection {
    background-color: #d9e9ff;
    height: 26px;
}
::-moz-selection {
    background: rgba(1,136,251,.25);
}

.share-menu {
    margin-top: 30px;
}

.share-menu .title {
    margin: 0 0 12px;
    font-size: 14px;
}

.share-menu .icons .auth-icon {
    height: 112px;
    width: 112px;
    -webkit-box-sizing: border-box;
    box-sizing: border-box;
    -webkit-box-pack: center;
    -ms-flex-pack: center;
    justify-content: center;
    -webkit-box-align: end;
    -ms-flex-align: end;
    align-items: flex-end;
    cursor: pointer;
    border: 1px solid transparent;
}

.share-menu .icons, .share-menu .icons .auth-icon {
    display: -webkit-box;
    display: -ms-flexbox;
    display: flex;
}

.share-menu .icons .auth-icon {
    cursor: pointer;
}

.share-menu .icons .auth-icon:hover {
    border: 1px solid #d4d4d4;
}

.share-menu .icons .auth-icon .auth-icon-title {
    text-align: center;
}

.share-menu .icons .auth-icon .tim::before {
    content: "";
    display: block;
    margin-bottom: 6px;
    background-image: url(//s1.url.cn/tim/docs/components/img/css/sprites//icons_retina-6ab041.png);
    background-position: -210px -190px;
    width: 42px;
    height: 42px;
}

.share-menu .icons .auth-icon .qq::before {
    content: "";
    display: block;
    margin-bottom: 6px;
    background-image: url(//s1.url.cn/tim/docs/components/img/css/sprites//icons_retina-6ab041.png);
    background-position: -158px -190px;
    width: 42px;
    height: 42px;
}

.share-menu .icons .auth-icon .wechat::before {
    content: "";
    display: block;
    margin-bottom: 6px;
    background-image: url(//s1.url.cn/tim/docs/components/img/css/sprites//icons_retina-6ab041.png);
    background-position: -250px -103px;
    width: 42px;
    height: 42px;
}



button::-moz-focus-inner {

    padding: 0;
    border: 0;

}
::-moz-selection {

    background: rgba(1,136,251,.25);

}

.share-link .content .link-copy:hover {
    background-color: #45a5ff;
}
.share-link .content .link-copy {

    height: 44px;
    width: 80px;
    -webkit-box-sizing: border-box;
    box-sizing: border-box;
    vertical-align: top;
    cursor: pointer;
    color: #fff;
    background-color: #388cf5;
    border: none;
    font-size: 14px;

}

</style>
