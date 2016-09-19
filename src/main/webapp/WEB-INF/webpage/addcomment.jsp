<%--
  Created by IntelliJ IDEA.
  User: t_mengxh
  Date: 2016/9/19
  Time: 20:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>添加评论</title>
  <link rel="stylesheet" href="http://cdn.bootcss.com/bootstrap/3.3.0/css/bootstrap.min.css">

  <!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
  <script src="http://cdn.bootcss.com/jquery/1.11.1/jquery.min.js"></script>

  <!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
  <script src="http://cdn.bootcss.com/bootstrap/3.3.0/js/bootstrap.min.js"></script>
</head>
<body>
<%
  HttpSession httpSession = request.getSession();
  String repairId = (String) httpSession.getAttribute("repairId");
  if(repairId == null){%>
    <h2>不可评论！！！</h2>
  <%}else{
%>
<form method="post" action="/op/addComment">
<table class="table table-bordered">
  <thead>添加评论</thead>
  <tr>
    <td>维修单ID</td>
    <td><input type="text" name="repairId" value="<%=repairId%>"  /></td>
  </tr>
  <tr>
    <td>评论内容</td>
    <td><textarea rows="3" cols="20" name="commentText"></textarea></td>
  </tr>
  <tr>
    <td><button type="submit">提交</button></td>
    <td><a href="/op/toLoginMainPage"><button type="button">返回</button></a></td>
  </tr>
  </table>
  </form>
<%}%>
</body>
</html>
