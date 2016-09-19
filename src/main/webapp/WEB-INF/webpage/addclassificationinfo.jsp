<%@ page import="net.blf2.model.entity.ClassificationInfo" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Iterator" %>
<%--
  Created by IntelliJ IDEA.
  User: t_mengxh
  Date: 2016/9/10
  Time: 14:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>分类</title>
  <!-- Bootstrap core CSS -->
  <!-- 新 Bootstrap 核心 CSS 文件 -->
  <link rel="stylesheet" href="http://cdn.bootcss.com/bootstrap/3.3.0/css/bootstrap.min.css">

  <!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
  <script src="http://cdn.bootcss.com/jquery/1.11.1/jquery.min.js"></script>

  <!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
  <script src="http://cdn.bootcss.com/bootstrap/3.3.0/js/bootstrap.min.js"></script>
</head>
<body>
<form action="/op/addClassificationInfo" method="post">
  <table class="table table-bordered">
    <thead>当前已有分类</thead>
    <tr>
      <td>分类ID<td>
      <td>分类名</td>
    </tr>
    <%
      HttpSession httpSession = request.getSession();
      List<ClassificationInfo>classificationInfoAll = (List<ClassificationInfo>) httpSession.getAttribute("classificationInfoAll");
      if(classificationInfoAll == null){%>
        <h2>无数据！！！</h2>
      <%}else{
        httpSession.removeAttribute("classificationInfoAll");
        Iterator<ClassificationInfo>iter = classificationInfoAll.iterator();
        while(iter.hasNext()){
          ClassificationInfo classificationInfo = iter.next();%>
    <tr>
      <td><%=classificationInfo.getClassifocationId()%></td>
      <td><%=classificationInfo.getClassficationName()%></td>
    </tr>
        <%}
      }
    %>

  <tr>
    <td colspan="1">添加分类</td>
  </tr>
  <tr>
    <td>请输入名称：</td>
    <td><input type="text" name="classificationName"/></td>
  </tr>
  <tr>
    <td><input type="submit"/></td>
    <td><input type="reset" /></td>
  </tr>
  </table>
  </form>
<a href="/op/toLogin"><button class="btn-primary" type="button">返回登录</button></a>
</body>
</html>
