<%@ page import="java.util.Map" %>
<%@ page import="net.blf2.model.entity.RepairComment" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%--
  Created by IntelliJ IDEA.
  User: t_mengxh
  Date: 2016/9/19
  Time: 16:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>查看评论</title>
  <!-- Bootstrap core CSS -->
  <!-- 新 Bootstrap 核心 CSS 文件 -->
  <link rel="stylesheet" href="http://cdn.bootcss.com/bootstrap/3.3.0/css/bootstrap.min.css">

  <!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
  <script src="http://cdn.bootcss.com/jquery/1.11.1/jquery.min.js"></script>

  <!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
  <script src="http://cdn.bootcss.com/bootstrap/3.3.0/js/bootstrap.min.js"></script>
</head>
<body>
<table class="table table-bordered">
  <thead>评论列表</thead>
  <tr>
    <td>评论ID</td>
    <td>维修表ID</td>
    <td>评论人</td>
    <td>评论时间</td>
    <td>评论内容</td>
  </tr>
  <%
    HttpSession httpSession = request.getSession();
    Map<String,String> userIdToNameMap = (Map<String, String>) httpSession.getAttribute("userIdToNameMap");
    List<RepairComment> repairComments = (List<RepairComment>) httpSession.getAttribute("repairComments");
    if(userIdToNameMap == null || repairComments == null){
      if(userIdToNameMap != null)
        httpSession.removeAttribute("userIdToNameMap");
      if(repairComments != null)
        httpSession.removeAttribute("repairComments");%>
    <h2>无数据！！！</h2>
  <%}else{
    httpSession.removeAttribute("userIdToNameMap");
    httpSession.removeAttribute("repairComments");
    Iterator<RepairComment>iter = repairComments.iterator();
    while(iter.hasNext()){
      RepairComment repairComment = iter.next();%>
  <tr>
    <td><%=repairComment.getRepairCommentId()%></td>
    <td><%=repairComment.getRepairId()%></td>
    <td><%=userIdToNameMap.get(repairComment.getSubmiterId())%></td>
    <td><%=new SimpleDateFormat("yyyy-MM-dd hh:mm").format(repairComment.getCommentDateTime())%></td>
    <td><%=repairComment.getCommentText()%></td>
  </tr>
    <%}
  }
  %>
</table>
</body>
</html>
