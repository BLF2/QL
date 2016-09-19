<%@ page import="net.blf2.model.entity.ClassificationInfo" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Iterator" %>
<%--
  Created by IntelliJ IDEA.
  User: t_mengxh
  Date: 2016/9/13
  Time: 19:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>提交维修表</title>
  <!-- Bootstrap core CSS -->
  <!-- 新 Bootstrap 核心 CSS 文件 -->
  <link rel="stylesheet" href="http://cdn.bootcss.com/bootstrap/3.3.0/css/bootstrap.min.css">

  <!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
  <script src="http://cdn.bootcss.com/jquery/1.11.1/jquery.min.js"></script>

  <!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
  <script src="http://cdn.bootcss.com/bootstrap/3.3.0/js/bootstrap.min.js"></script>
</head>
<body>
<%
  HttpSession httpSession = request.getSession();
  List<ClassificationInfo> classificationInfoAll = (List<ClassificationInfo>) httpSession.getAttribute("classificationInfoAll");
%>
<form action="/op/addRepairForm" method="post">
<table class="table table-bordered" id="table1">
  <thead align="centre">提交维修信息</thead>
  <tr>
    <td>具体地点：</td>
    <td><input type="text" name="descriptionTitle" /></td>
  </tr>
  <tr>
    <td>描述：</td>
    <td><textarea rows="10" cols="30" name="descriptionText"></textarea></td>
  </tr>
  <tr>
    <td>分类：</td>
    <%
      if(classificationInfoAll == null) {%>
    <td>分类不存在，请联系管理员！！！</td>
     <% }else{
       httpSession.removeAttribute("classificationInfoAll");
       Iterator<ClassificationInfo>iter = classificationInfoAll.iterator();
    %>
    <td>
      <select name="classificationId">
        <%
          while(iter.hasNext()){
            ClassificationInfo classificationInfo = iter.next();
        %>
        <option value="<%=classificationInfo.getClassifocationId()%>"><%=classificationInfo.getClassficationName()%></option>
        <%}%>
      </select>
    </td>
    <%}%>
  </tr>
  <tr>
    <td>图片路径：</td>
    <td><input type="text" name="imgePath" /></td>
  </tr>
  <tr>
    <td>视频路径：</td>
    <td><input type="text" name="videoPath" /></td>
  </tr>
  <tr>
    <td>维修优先级：(值为0,1,2,3，优先级依次升高)</td>
    <td><input type="number" name="repairPriority"></td>
  </tr>
  <tr>
    <td>维修备注：</td>
    <td><input type="text" maxlength="20" name="repairNote"/></td>
  </tr>
  <tr>
    <td><button type="submit">提交</button></td>
    <td><a href="/op/toLoginMainPage"><button type="button">取消</button></a></td>
  </tr>
</table>
  </form>
</body>
</html>
