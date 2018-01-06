<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: dongmengyuan
  Date: 18-1-3
  Time: 下午6:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>linux图书管理</title>
    <meta name="viewport" content="width=device-width,inital-scale=1,maxmum-scale=1,user-scalable=no">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="HandleFriendly" content="true">
    <!--font-awesome矢量图标-->
    <link href="/font-awesome-4.7.0/css/font-awesome.min.css" rel="stylesheet">
    <link href="/css/bootstrap.min.css" rel="stylesheet">
    <link href="/css/index.css" rel="stylesheet">
    <link href="/css/index1.css" rel="stylesheet">
    <link href="/css/mybooks.css" rel="stylesheet">
</head>

<body>
<header>
    <div id="hea">
        <img id="index_head" src="/img/index_head.png" />
        <a id="head" href="main.jsp">XiyouLinux Group 图书借阅</a>
        <div id="index1_input">
            <input type="text" placeholder="搜索书名/作者/归属者">
            <button class="btn btn-link">提交</button>
            <a href="/mybook/getBook" id="head_in"><i class="fa fa-file-text fa-fw"></i>我的书籍</a>
            <a href="pushbook.html"><i class="fa fa-tags fa-fw"></i>上传数据</a>
        </div>
        <a id="index1_sign" href="index.jsp">退出登录</a>
    </div>
</header>
<div id="main">
    <div id="head2">
        <a>所上传的书</a>
        <a>所借阅的书</a>
    </div>
    <div id="push">
        <c:forEach items="${MyReturnBook}" var="returnbook">
            <div class="rows books_push">
                <div class="col-xs-12 col-md-2 book_img">
                    <img src="/img/book0.jpeg">
                </div>
                <div class="book_info col-xs-12 col-md-8">
                    <p>《${returnbook.key.ugkName}》-----${returnbook.key.author}</p>
                    <p>${returnbook.key.describ}</p>
                    <p><span><i class="fa fa-user"></i>${returnbook.value}</span>
                        <span><i class="fa fa-book"></i>被借4次</span>
                    </p>
                </div>
                <div class="col-xs-12 col-md-2">
                    <button class="btn" onclick="deleteBookByUserAndPk_id(${uid},${returnbook.key.pkId})">下架图书</button>
                    <button class="btn modify">修改信息</button>
                </div>
                <div style="clear:both"></div>
            </div>
        </c:forEach>
    </div>
    <div id="borrow">
        <c:forEach items="${MyBorrowBook}" var="book">
            <div class="rows">
                <div class="col-xs-12 col-md-2 book_img">
                    <img src="/img/book0.jpeg">
                </div>
                <div class="book_info col-xs-12 col-md-8">
                    <p>《${book.key.ugkName}》-----${book.key.author}</p>
                    <p>${book.key.describ}</p>
                    <p><span><i class="fa fa-user"></i>${book.value}</span>
                        <span><i class="fa fa-book"></i>被借4次</span>
                    </p>
                </div>
                <div class="col-xs-12 col-md-2">
                    <button class="btn" onclick="ReturnBookByUserAndPkid(${uid},${book.key.pkId})">归还图书</button>
                </div>
                <div style="clear:both"></div>
            </div>
        </c:forEach>
    </div>
</div>
<div id="mask"></div>
<form id="box">
    <h3>修改图书信息<img src="/img/close_def.png" id="close"></h3>
    <p>书名：<input type="text"></p>
    <p>作者：<input type="text"></p>
    <p>数量：<input type="text"></p>
    <p>分类：<input type="text"><input type="text"></p>
    <p>描述：<textarea cols="30" rows="2"></textarea></p>
    <button>登录</button>
</form>
<footer>
    <div class="rows">
        <div class="col-xs-6 col-md-3">
            <p class="footer_head">友情链接</p>
            <p><a>西邮Linux兴趣小组</a></p>
            <p><a>西安邮电大学</a></p>
            <p><a>西安邮电大学计算机学院</a></p>
            <p><a>linux内核之旅</a></p>
            <p><a>The Linux Kernel Archives</a></p>
            <p><a>The Linux Foundation</a></p>
        </div>
        <div class="col-xs-6 col-md-3">
            <p class="footer_head">社区</p>
            <p>邮件列表：<a>xiyoulinux</a></p>
            <p>新浪微博：<a>@西邮Linux兴趣小组</a></p>
            <p>GUN：<a>GUN's Not Unix</a></p>
            <p>LWN：<a>Linux Weekly News</a></p>
            <p>Linux Story：<a>Linux Story</a></p>
        </div>
        <div class="col-xs-6 col-md-3">
            <p class="footer_head">联系我们</p>
            <p><span><i class="fa fa-map-marker"></i>地址：陕西省 西安市 长安区 西安邮电大学长安校区 东区 教学实验楼 FZ118</span></p>
            <p><span><i class="fa fa-envelope"></i>邮编：710121</span></p>
        </div>
        <div class="col-xs-6 col-md-3">
            <p class="footer_head">关注我们</p>
            <img src="/img/weixin.jpg">
        </div>
        <div style="clear: both;height:0;"></div>
    </div>
    <div id="foot">
        <p>Copyright @ 2006-2017 西邮Linux兴趣小组 </p>
        <p>All Rights Reserved</p>
    </div>
</footer>

</body>
<script type="text/javascript" src="/js/jquery.min.js"></script>
<script type="text/javascript" src="/js/bootstrap.min.js"></script>
<script type="text/javascript" src="/js/canvas1.js"></script>
<script type="text/javascript" src="/js/index.js"></script>
<script type="text/javascript" src="/js/mybooks.js"></script>
<script type="text/javascript">
    function ReturnBookByUserAndPkid(uid,pk_id) {
        $.post("/mybook/returnBook",{"csUserId":uid,"bookInfoPkId":pk_id},function(data){
            window.location.reload();
        });
    }
    
    function deleteBookByUserAndPk_id(uid,pk_id) {
        $.post("/mybook/deleteBook",{"ugkUid":uid,"pkId":pk_id},function(data){
            window.location.reload();
        });
    }
</script>
</html>
