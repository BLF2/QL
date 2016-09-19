<%@ page import="net.blf2.model.entity.RepairForm" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Map" %>
<%--
  Created by IntelliJ IDEA.
  User: t_mengxh
  Date: 2016/9/7
  Time: 20:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>RepairForm</title>
  <!-- 新 Bootstrap 核心 CSS 文件 -->
  <link rel="stylesheet" href="http://cdn.bootcss.com/bootstrap/3.3.0/css/bootstrap.min.css">
  <!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
  <script src="http://cdn.bootcss.com/jquery/1.11.1/jquery.min.js"></script>

  <!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
  <script src="http://cdn.bootcss.com/bootstrap/3.3.0/js/bootstrap.min.js"></script>
  <script>
    function tishi(){
      return confirm("提交将会增加一个维修跟踪，维修表变为不可更改状态，您确定？");
    }
  </script>
</head>
<body>
<table class="table table-bordered" id="table1">
  <tr>
    <td>维修单ID</td>
    <td>提交人邮箱</td>
    <td>分类名</td>
    <td>地点</td>
    <td>描述</td>
    <td>图片路径</td>
    <td>视频路径</td>
    <td>提交时间</td>
    <td>维修优先级</td>
    <td>当前维修状态</td>
    <td>维修备注</td>
    <td>操作1</td>
    <td>操作2</td>
    <td>操作3</td>
  </tr>
  <%
    HttpSession httpSession = request.getSession();
    List<RepairForm>repairFormList = (List<RepairForm>) httpSession.getAttribute("repairFormAll");
    Map<String,String>userIdToEmailMap = (Map<String, String>) httpSession.getAttribute("userIdToEmailMap");
    Map<String,String>classificationIdToNameMap = (Map<String, String>) httpSession.getAttribute("classificationInfoAllMap");
    if(repairFormList == null || userIdToEmailMap == null || classificationIdToNameMap == null){
      if(userIdToEmailMap != null) {
        httpSession.removeAttribute("userIdToEmailMap");
      }
      if(repairFormList != null)
        httpSession.removeAttribute("repairFormAll");
      if(classificationIdToNameMap != null)
        httpSession.removeAttribute("classificationInfoAllMap");
  %>
  <h2>无数据！！！</h2>
  <%}else{
    Iterator<RepairForm>iter = repairFormList.iterator();
    httpSession.removeAttribute("repairFormAll");
    httpSession.removeAttribute("userIdToEmailMap");
    httpSession.removeAttribute("classificationInfoAllMap");
  while(iter.hasNext()){
    RepairForm repairForm = iter.next();
  %>
  <tr>
    <td><%=repairForm.getRepairId()%></td>
    <td><%=userIdToEmailMap.get(repairForm.getRepairerId())%></td>
    <td><%=classificationIdToNameMap.get(repairForm.getClassificationId())%></td>
    <td><%=repairForm.getDescriptionTitle()%></td>
    <td><%=repairForm.getDescriptionText()%></td>
    <td><a href="<%=repairForm.getImgePath()%>"><%=repairForm.getImgePath()%></a></td>
    <td><a href="<%=repairForm.getVideoPath()%>"><%=repairForm.getVideoPath()%></a></td>
    <td><%=new SimpleDateFormat("yyyy-MM-dd hh:mm").format(repairForm.getSubmitTime())%></td>
    <td><%=repairForm.getRepairPriority()%></td>
    <td><%=repairForm.getCurrentStatus() == 0 ? "不可更改" : "可修改"%></td>
    <td><%=repairForm.getRepairNote()%></td>
    <td>
       <%
         if(repairForm.getCurrentStatus() == 1){%>
      <a href="/op/goToRepair?repairId=<%=repairForm.getRepairId()%>" onclick="return tishi()">去维修</a>
         <%}else{%>
           去维修
         <%}
       %>
    </td>
    <td><a href="/op/queryScheduleInfoByRepairFormId?repairFormId=<%=repairForm.getRepairId()%>">查看/增加维修跟踪</a></td>
  <td><a href="/op/queryCommentByRepairId?repairId=<%=repairForm.getRepairId()%>">查看评论</a></td>
  <tr>
  <%}
  }%>
</table>
<a href="/op/toAddRepairForm"><button class="btn-primary" type="button">我要报修</button></a>
</body>

</html>
