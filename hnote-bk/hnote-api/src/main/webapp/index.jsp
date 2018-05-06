<%--
  Created by IntelliJ IDEA.
  User: mingshan
  Date: 2017/10/3
  Time: 10:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Aplication started!</title>
    <style>
        html, body {width:100%; height:100%; padding:0; margin:0;}
        /* 白色背景太单调了，我们来点背景 */
        html {
            color: rgb(222,222,222); 
            background: rgb(0,43,54);
        }
        center {
            position: relative;
            top: 100px;
        }
        center h2 {
            display: inline-block;

        }
    </style>
</head>
<body>
    <canvas id="canvas" style="position:absolute;top:0px;left:0px;z-index:1;"></canvas>
    <center>
    <pre>
        -------------------------------------------
        <h2>Application started!</h2>
        -------------------------------------------


        |||||||||||||||||||||||||||||||||||||||||||
        |||||||||||||||||||||||||-|||-|||||||||||||
        ||||||||||||--|||||||||||-|-|||||||||||||||
        |||||||||--||||--||||||||-|||||||||||||||||
        ||||||||||||--|||||||||||-|-|||||||||||||||
        |||||||||||||||||||||||||-|||-|||||||||||||
        |||||||||||||||||||||||||||||||||||||||||||
    </pre>
    </center>
    <script type="text/javascript">
     var canvas = document.getElementById('canvas'); 
     var ctx = canvas.getContext('2d'); 
     canvas.width = canvas.parentNode.offsetWidth; 
     canvas.height = canvas.parentNode.offsetHeight;
     //如果浏览器支持requestAnimFrame则使用requestAnimFrame否则使用setTimeout 
     window.requestAnimFrame = (function(){ 
     return window.requestAnimationFrame  || 
       window.webkitRequestAnimationFrame || 
       window.mozRequestAnimationFrame || 
       function( callback ){ 
        window.setTimeout(callback, 1000 / 60); 
       }; 
     })(); 
     // 波浪大小
     var boHeight = canvas.height / 10;
     var posHeight = canvas.height / 1.2;
     //初始角度为0 
     var step = 0; 
     //定义三条不同波浪的颜色 
     var lines = ["rgba(0,222,255, 0.2)", 
         "rgba(157,192,249, 0.2)", 
         "rgba(0,168,255, 0.2)"]; 
     function loop(){ 
      ctx.clearRect(0,0,canvas.width,canvas.height); 
      step++; 
      //画3个不同颜色的矩形 
      for(var j = lines.length - 1; j >= 0; j--) { 
       ctx.fillStyle = lines[j]; 
       //每个矩形的角度都不同，每个之间相差45度 
       var angle = (step+j*50)*Math.PI/180; 
       var deltaHeight = Math.sin(angle) * boHeight;
       var deltaHeightRight = Math.cos(angle) * boHeight; 
       ctx.beginPath();
       ctx.moveTo(0, posHeight+deltaHeight); 
       ctx.bezierCurveTo(canvas.width/2, posHeight+deltaHeight-boHeight, canvas.width / 2, posHeight+deltaHeightRight-boHeight, canvas.width, posHeight+deltaHeightRight); 
       ctx.lineTo(canvas.width, canvas.height); 
       ctx.lineTo(0, canvas.height); 
       ctx.lineTo(0, posHeight+deltaHeight); 
       ctx.closePath(); 
       ctx.fill(); 
      }
      requestAnimFrame(loop);
     } 
     loop(); 
    </script>
</body>
</html>
