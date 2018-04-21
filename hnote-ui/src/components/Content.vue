<template>
  <div class="content-container">
    <div class="top-navi-container">
      <div class="top-navi">
        <span class="title">Git 提交的正确姿势</span>
        <div class="operation">
            <img :src="editPic" class="eidt" @mouseover="editPic = editPicBlue" @mouseout="editPic = editPic1" />
            <img :src="sharePic" class="share" @mouseover="sharePic = sharePicBlue" @mouseout="sharePic = sharePic1" />
            <el-dropdown>
              <span class="el-dropdown-link">
                <img :src="morePic" class="more" @mouseover="morePic = morePicBlue" @mouseout="morePic = morePic1" />
              </span>
              <el-dropdown-menu slot="dropdown">
                <el-dropdown-item>移动到</el-dropdown-item>
                <el-dropdown-item>下载</el-dropdown-item>
                <el-dropdown-item>删除</el-dropdown-item>
              </el-dropdown-menu>
            </el-dropdown>
            <img :src="infoPic" class="info"  v-popover:popoverInfo @mouseover="infoPic = infoPicBlue" @mouseout="infoPic = infoPic1" />
            <el-popover
              ref="popoverInfo"
              placement="top-start"
              width="200"
              trigger="hover">
              <div>

              </div>
            </el-popover>
        </div>
      </div>
    </div>
    <br/>

    <div v-if="showMarkdownEditor" class="edit-content">
        <mavon-editor style="height: 100%"></mavon-editor>
    </div>
    <div v-else class="html-content markdown-body">
      <h1>Markdown 语法简介</h1> <blockquote> <p>
        <a href="http://commonmark.org/help/" target="_blank">语法详解</a></p>
         </blockquote> <h2><strong>粗体</strong></h2> <pre><code class="lang-">**粗体** __粗体__ </code></pre>
         <h2><em>斜体</em></h2> <pre><code class="lang-">*斜体* _斜体_ </code></pre> <h2>标题</h2>
          <pre><code class="lang-"># 一级标题 # 一级标题 ==== ## 二级标题 ## 二级标题 ---- ### 三级标题 ### #### 四级标题 #### ##### 五级标题 ##### ###### 六级标题 ###### </code></pre> <h2>分割线</h2> <pre><code class="lang-">*** --- </code></pre> <hr /> <h2><sup>上</sup>角<sub>下</sub>标</h2> <pre><code class="lang-">上角标 x^2^ 下角标 H~2~0 </code></pre> <h2><ins>下划线</ins> <s>中划线</s></h2> <pre><code class="lang-">++下划线++ ~~中划线~~ </code></pre> <h2><mark>标记</mark></h2> <pre><code class="lang-">==标记== </code></pre> <h2>段落引用</h2> <pre><code class="lang-">&gt; 一级 &gt;&gt; 二级 &gt;&gt;&gt; 三级 ... </code></pre> <h2>列表</h2> <pre><code class="lang-">有序列表 1. 2. 3. ... 无序列表 - - ... </code></pre> <h2>链接</h2> <pre><code class="lang-">[链接](www.baidu.com) ![图片描述](http://www.image.com) </code></pre> <h2>代码段落</h2> <p>``` type</p> <p>代码段落</p> <p>```</p> <p>` 代码块 `</p> <pre><div class="hljs"><code class="lang-c++"><span class="hljs-function"><span class="hljs-keyword">int</span> <span class="hljs-title">main</span><span class="hljs-params">()</span> </span>{ <span class="hljs-built_in">printf</span>(<span class="hljs-string">"hello world!"</span>); } </code></div></pre> <p><code>code</code></p> <h2>表格(table)</h2> <pre><code class="lang-">| 标题1 | 标题2 | 标题3 | | :-- | :--: | ----: | | 左对齐 | 居中 | 右对齐 | | ---------------------- | ------------- | ----------------- | </code></pre> <table> <thead> <tr> <th style="text-align:left">标题1</th> <th style="text-align:center">标题2</th> <th style="text-align:right">标题3</th> </tr> </thead> <tbody> <tr> <td style="text-align:left">左对齐</td> <td style="text-align:center">居中</td> <td style="text-align:right">右对齐</td> </tr> <tr> <td style="text-align:left">----------------------</td> <td style="text-align:center">-------------</td> <td style="text-align:right">-----------------</td> </tr> </tbody> </table> <h2>脚注(footnote)</h2> <pre><code class="lang-">hello[^hello] </code></pre> <p>见底部脚注<sup class="footnote-ref"><a href="#fn1" id="fnref1">[1]</a></sup></p> <h2>表情(emoji)</h2> <p><a href="https://www.webpagefx.com/tools/emoji-cheat-sheet/" target="_blank">参考网站: https://www.webpagefx.com/tools/emoji-cheat-sheet/</a></p> <pre><code class="lang-">:laughing: :blush: :smiley: :) ... </code></pre> <p>😆😊😃😃</p> <h2><span class="katex"><span class="katex-mathml"><math><semantics><mrow><mtext>KaTeX</mtext></mrow><annotation encoding="application/x-tex">\KaTeX</annotation></semantics></math></span><span class="katex-html" aria-hidden="true"><span class="strut" style="height:0.68333em;"></span><span class="strut bottom" style="height:1.0302031249999999em;vertical-align:-0.34687312499999995em;"></span><span class="base"><span class="mord katex-logo"><span class="k">K</span><span class="a">A</span><span class="t">T</span><span class="e">E</span><span class="x">X</span></span></span></span></span>公式</h2> <p>我们可以渲染公式例如：<span class="katex"><span class="katex-mathml"><math><semantics><mrow><msub><mi>x</mi><mi>i</mi></msub><mo>+</mo><msub><mi>y</mi><mi>i</mi></msub><mo>=</mo><msub><mi>z</mi><mi>i</mi></msub></mrow><annotation encoding="application/x-tex">x_i + y_i = z_i</annotation></semantics></math></span><span class="katex-html" aria-hidden="true"><span class="strut" style="height:0.58333em;"></span><span class="strut bottom" style="height:0.7777700000000001em;vertical-align:-0.19444em;"></span><span class="base"><span class="mord"><span class="mord mathit">x</span><span class="msupsub"><span class="vlist-t vlist-t2"><span class="vlist-r"><span class="vlist" style="height:0.31166399999999994em;"><span style="top:-2.5500000000000003em;margin-left:0em;margin-right:0.05em;"><span class="pstrut" style="height:2.7em;"></span><span class="sizing reset-size6 size3 mtight"><span class="mord mathit mtight">i</span></span></span></span><span class="vlist-s">​</span></span><span class="vlist-r"><span class="vlist" style="height:0.15em;"></span></span></span></span></span><span class="mbin">+</span><span class="mord"><span class="mord mathit" style="margin-right:0.03588em;">y</span><span class="msupsub"><span class="vlist-t vlist-t2"><span class="vlist-r"><span class="vlist" style="height:0.31166399999999994em;"><span style="top:-2.5500000000000003em;margin-left:-0.03588em;margin-right:0.05em;"><span class="pstrut" style="height:2.7em;"></span><span class="sizing reset-size6 size3 mtight"><span class="mord mathit mtight">i</span></span></span></span><span class="vlist-s">​</span></span><span class="vlist-r"><span class="vlist" style="height:0.15em;"></span></span></span></span></span><span class="mrel">=</span><span class="mord"><span class="mord mathit" style="margin-right:0.04398em;">z</span><span class="msupsub"><span class="vlist-t vlist-t2"><span class="vlist-r"><span class="vlist" style="height:0.31166399999999994em;"><span style="top:-2.5500000000000003em;margin-left:-0.04398em;margin-right:0.05em;"><span class="pstrut" style="height:2.7em;"></span><span class="sizing reset-size6 size3 mtight"><span class="mord mathit mtight">i</span></span></span></span><span class="vlist-s">​</span></span><span class="vlist-r"><span class="vlist" style="height:0.15em;"></span></span></span></span></span></span></span></span>和<span class="katex"><span class="katex-mathml"><math><semantics><mrow><msubsup><mo>∑</mo><mrow><mi>i</mi><mo>=</mo><mn>1</mn></mrow><mi>n</mi></msubsup><msub><mi>a</mi><mi>i</mi></msub><mo>=</mo><mn>0</mn></mrow><annotation encoding="application/x-tex">\sum_{i=1}^n a_i=0</annotation></semantics></math></span><span class="katex-html" aria-hidden="true"><span class="strut" style="height:0.804292em;"></span><span class="strut bottom" style="height:1.104002em;vertical-align:-0.29971000000000003em;"></span><span class="base"><span class="mop"><span class="mop op-symbol small-op" style="position:relative;top:-0.0000050000000000050004em;">∑</span><span class="msupsub"><span class="vlist-t vlist-t2"><span class="vlist-r"><span class="vlist" style="height:0.804292em;"><span style="top:-2.40029em;margin-left:0em;margin-right:0.05em;"><span class="pstrut" style="height:2.7em;"></span><span class="sizing reset-size6 size3 mtight"><span class="mord mtight"><span class="mord mathit mtight">i</span><span class="mrel mtight">=</span><span class="mord mathrm mtight">1</span></span></span></span><span style="top:-3.2029em;margin-right:0.05em;"><span class="pstrut" style="height:2.7em;"></span><span class="sizing reset-size6 size3 mtight"><span class="mord mathit mtight">n</span></span></span></span><span class="vlist-s">​</span></span><span class="vlist-r"><span class="vlist" style="height:0.29971000000000003em;"></span></span></span></span></span><span class="mord"><span class="mord mathit">a</span><span class="msupsub"><span class="vlist-t vlist-t2"><span class="vlist-r"><span class="vlist" style="height:0.31166399999999994em;"><span style="top:-2.5500000000000003em;margin-left:0em;margin-right:0.05em;"><span class="pstrut" style="height:2.7em;"></span><span class="sizing reset-size6 size3 mtight"><span class="mord mathit mtight">i</span></span></span></span><span class="vlist-s">​</span></span><span class="vlist-r"><span class="vlist" style="height:0.15em;"></span></span></span></span></span>
          <span class="mrel">=</span><span class="mord mathrm">0</span></span></span></span><br /> 我们也可以单行渲染</p> <p><span class="katex-display"><span class="katex"><span class="katex-mathml"><math><semantics><mrow><munderover><mo>∑</mo><mrow><mi>i</mi><mo>=</mo><mn>1</mn></mrow><mi>n</mi></munderover><msub><mi>a</mi><mi>i</mi></msub><mo>=</mo><mn>0</mn></mrow><annotation encoding="application/x-tex">\sum_{i=1}^n a_i=0 </annotation></semantics></math></span><span class="katex-html" aria-hidden="true"><span class="strut" style="height:1.6513970000000002em;"></span><span class="strut bottom" style="height:2.929066em;vertical-align:-1.277669em;"></span><span class="base"><span class="mop op-limits"><span class="vlist-t vlist-t2"><span class="vlist-r"><span class="vlist" style="height:1.6513970000000002em;"><span style="top:-1.872331em;margin-left:0em;"><span class="pstrut" style="height:3.05em;"></span><span class="sizing reset-size6 size3 mtight"><span class="mord mtight"><span class="mord mathit mtight">i</span><span class="mrel mtight">=</span><span class="mord mathrm mtight">1</span></span></span></span><span style="top:-3.050005em;"><span class="pstrut" style="height:3.05em;"></span><span><span class="mop op-symbol large-op">∑</span></span></span><span style="top:-4.3000050000000005em;margin-left:0em;"><span class="pstrut" style="height:3.05em;"></span><span class="sizing reset-size6 size3 mtight"><span class="mord mathit mtight">n</span></span></span></span><span class="vlist-s">​</span></span><span class="vlist-r"><span class="vlist" style="height:1.277669em;"></span></span></span></span><span class="mord"><span class="mord mathit">a</span><span class="msupsub"><span class="vlist-t vlist-t2"><span class="vlist-r"><span class="vlist" style="height:0.31166399999999994em;"><span style="top:-2.5500000000000003em;margin-left:0em;margin-right:0.05em;"><span class="pstrut" style="height:2.7em;"></span><span class="sizing reset-size6 size3 mtight"><span class="mord mathit mtight">i</span></span></span></span><span class="vlist-s">​</span></span><span class="vlist-r"><span class="vlist" style="height:0.15em;"></span></span></span></span></span><span class="mrel">=</span><span class="mord mathrm">0</span></span></span></span></span></p> <p>具体可参照<a href="http://www.intmath.com/cg5/katex-mathjax-comparison.php" target="_blank">katex文档</a>和<a href="https://github.com/Khan/KaTeX/wiki/Function-Support-in-KaTeX" target="_blank">katex支持的函数</a>以及<a href="https://math.meta.stackexchange.com/questions/5020/mathjax-basic-tutorial-and-quick-reference" target="_blank">latex文档</a></p> <h2>布局</h2> <div class="hljs-left"> <p><code>::: hljs-left</code><br /> <code>居左</code><br /> <code>:::</code></p> </div> <div class="hljs-center"> <p><code>::: hljs-center</code><br /> <code>居中</code><br /> <code>:::</code></p> </div> <div class="hljs-right"> <p><code>::: hljs-right</code><br /> <code>居右</code><br /> <code>:::</code></p> </div> <h2>定义</h2> <dl> <dt>术语一</dt> <dd> <p>定义一</p> </dd> <dt>包含有<em>行内标记</em>的术语二</dt> <dd> <p>定义二</p> <pre><code> {一些定义二的文字或代码} </code></pre> <p>定义二的第三段</p> </dd> </dl> <pre><code class="lang-">术语一 : 定义一 包含有*行内标记*的术语二 : 定义二 {一些定义二的文字或代码} 定义二的第三段 </code></pre> <h2>abbr</h2> <p><abbr title="Hyper Text Markup Language">HTML</abbr> 规范由 <abbr title="World Wide Web Consortium">W3C</abbr> 维护</p> <pre><code class="lang-">*[HTML]: Hyper Text Markup Language *[W3C]: World Wide Web Consortium HTML 规范由 W3C 维护 </code></pre> <hr class="footnotes-sep" /> <section class="footnotes"> <ol class="footnotes-list"> <li id="fn1" class="footnote-item"><p>一个注脚 <a href="#fnref1" class="footnote-backref">↩︎</a></p> </li> </ol> </section> 
    </div>
  </div>
</template>


<style lang="css" scoped>
.top-navi-container {
  border-bottom: 1px solid #EBEBEB;
  position: fixed;
  height: 72px;
  width: 60%;
  overflow-y: hidden;
}

.top-navi {
  line-height: 72px;
}

.top-navi .title {
  font-size: 16px;
  color: #333333;
  margin-left: 20px;
}

.top-navi .operation {
  float: right;
  margin-right: 20px;
}
.top-navi .operation img {
  margin-left: 20px;
  cursor: pointer;
}

.edit-content {
  margin-top: 54px;
  position: fixed;
  height: 87%;
  width: 60%;
}

.html-content {
  margin-top: 54px;
  padding-left: 20px;
  padding-top: 20px;
  height: 800px;
  overflow: auto;
}
</style>

<script>
  // Local Registration
  import { mavonEditor } from 'mavon-editor'
  import 'mavon-editor/dist/css/index.css'
  const marked = require('marked');

  export default {
    name: 'editor',
    data() {
      return {
        editPic: "/static/img/edit.png",
        editPic1: "/static/img/edit.png",
        editPicBlue: "/static/img/edit-blue.png",
        sharePic: "/static/img/share-gray.png",
        sharePic1: "/static/img/share-gray.png",
        sharePicBlue: "/static/img/share-blue.png",
        morePic: "/static/img/more.png",
        morePic1: "/static/img/more.png",
        morePicBlue: "/static/img/more-blue.png",
        infoPic: "/static/img/info.png",
        infoPic1: "/static/img/info.png",
        infoPicBlue: "/static/img/info-blue.png",
        htmlContent: '',
        showMarkdownEditor: true
      }
    },
    mounted() {
       this.init()
    },
    methods: {
      init() {
        this.markdownToHtml();
      },
      markdownToHtml() {
        this.htmlContent =  marked('# Marked in the browser\n\nRendered by **marked**.');
      }
    },
    components: {
        mavonEditor
        // or 'mavon-editor': mavonEditor
    }
  };

</script>