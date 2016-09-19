<%@ page import="net.blf2.model.entity.RepairForm" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%--
  Created by IntelliJ IDEA.
  User: t_mengxh
  Date: 2016/9/14
  Time: 21:03
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>我的提交</title>
  <link rel="stylesheet" href="http://cdn.bootcss.com/bootstrap/3.3.0/css/bootstrap.min.css">

  <!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
  <script src="http://cdn.bootcss.com/jquery/1.11.1/jquery.min.js"></script>

  <!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
  <script src="http://cdn.bootcss.com/bootstrap/3.3.0/js/bootstrap.min.js"></script>
</head>
<body>

<table class="table table-bordered" id="table1">
  <thead>我的提交</thead>
  <tr>
    <td>流水号</td>
    <td>分类</td>
    <td>地点</td>
    <td>描述</td>
    <td>图片路径</td>
    <td>视频路径</td>
    <td>提交时间</td>
    <td>优先级</td>
    <td>是否可更改(1是0否)</td>
    <td>备注</td>
    <td>查看评论</td>
    <td>增加评论</td>
    <td>查看维修跟踪</td>
  </tr>
  <%
    HttpSession httpSession = request.getSession();
    List<RepairForm>myRepairForms = (List<RepairForm>) httpSession.getAttribute("myRepairForms");
    Map<String,String> classificationInfoAllMap = (Map<String, String>) httpSession.getAttribute("classificationInfoAllMap");
    if(myRepairForms == null || classificationInfoAllMap == null){
      if(myRepairForms != null)
        httpSession.removeAttribute("myRepairForms");
      if(classificationInfoAllMap != null)
        httpSession.removeAttribute("classificationInfoAllMap");
  %>
      <h2>无数据！！！</h2>
    <%}else{
      httpSession.removeAttribute("myRepairForms");
      httpSession.removeAttribute("classificationInfoAllMap");
      Iterator<RepairForm>iter = myRepairForms.iterator();
      while(iter.hasNext()){
        RepairForm repairForm = iter.next();%>
  <tr>
    <td><%=repairForm.getRepairId()%></td>
    <td><%=classificationInfoAllMap.get(repairForm.getClassificationId())%></td>
    <td><%=repairForm.getDescriptionTitle()%></td>
    <td><%=repairForm.getDescriptionTitle()%></td>
    <td><a href="<%=repairForm.getImgePath()%>"><%=repairForm.getImgePath()%></a></td>
    <td><a href="<%=repairForm.getVideoPath()%>"><%=repairForm.getVideoPath()%></a></td>
    <td><%=new SimpleDateFormat("yyyy年MM月dd日 HH时mm分").format(repairForm.getSubmitTime().getTime())%></td>
    <td><%=repairForm.getRepairPriority()%></td>
    <td><%=repairForm.getCurrentStatus() == 0 ? "不可更改" : "可修改"%></td>
    <td><%=repairForm.getRepairNote()%></td>
    <td><a href="/op/queryCommentByRepairId?repairId=<%=repairForm.getRepairId()%>">查看评论</a></td>
    <td><a href="/op/toAddComment?repairId=<%=repairForm.getRepairId()%>">增加评论</a></td>
    <td><a href="/op/queryScheduleInfoByRepairFormId?repairFormId=<%=repairForm.getRepairId()%>">查看维修跟踪</a></td>
  </tr>
      <%}
    }
  %>
</table>
</body>
</html>
