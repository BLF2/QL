<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page import="java.util.List" %>
<%@ page import="net.blf2.model.entity.ScheduleInfo" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="net.blf2.model.entity.UserInfo" %>
<%--
  Created by IntelliJ IDEA.
  User: t_mengxh
  Date: 2016/9/8
  Time: 21:34
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
    <!-- 新 Bootstrap 核心 CSS 文件 -->
    <link rel="stylesheet" href="http://cdn.bootcss.com/bootstrap/3.3.0/css/bootstrap.min.css">
    <!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
    <script src="http://cdn.bootcss.com/jquery/1.11.1/jquery.min.js"></script>

    <!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
    <script src="http://cdn.bootcss.com/bootstrap/3.3.0/js/bootstrap.min.js"></script>
</head>
<body>
<form method="post" action="/op/addScheduleInfo">
<table class="table table-bordered" id="table1">
  <tr>
    <td>维修单跟踪表ID</td>
    <td>处理人员邮箱</td>
    <td>维修单ID</td>
    <td>当前状态</td>
    <td>修改时间</td>
  </tr>
<%
    Map<String,String>statusMap = new HashMap<String,String>();
    statusMap.put("1","已提交，等待维修");
    statusMap.put("2","正在维修，等待完成");
    statusMap.put("3","维修完成，等待评价");
    statusMap.put("4","评价完成,维修流程结束");
  HttpSession httpSession = request.getSession();
  List<ScheduleInfo>scheduleInfoList = (List<ScheduleInfo>)httpSession.getAttribute("scheduleInfoList");
    Map<String,String>userIdToEmailMap = (Map<String, String>) httpSession.getAttribute("userIdToEmailMap");
    UserInfo userInfo = (UserInfo) httpSession.getAttribute("loginInfo");
  if(scheduleInfoList == null || userIdToEmailMap == null){
      if(scheduleInfoList != null)
          httpSession.removeAttribute("scheduleInfoList");
      if(userIdToEmailMap != null)
          httpSession.removeAttribute("userIdToEmailMap");
    %>
    <h2>无数据！！！</h2>
  <%}else{
    httpSession.removeAttribute("scheduleInfoList");
      httpSession.removeAttribute("userIdToEmailMap");

    Iterator<ScheduleInfo>iter = scheduleInfoList.iterator();
    while(iter.hasNext()){
      ScheduleInfo scheduleInfo = iter.next();
  %>
      <tr>
      <td><%=scheduleInfo.getScheduleId()%></td>
      <td><%=userIdToEmailMap.get(scheduleInfo.getHandlerUserId())%></td>
      <td><%=scheduleInfo.getRepairId()%></td>
      <td><%=statusMap.get(scheduleInfo.getScheuleStatus())%></td>
      <td><%=new SimpleDateFormat("yyyy-MM-dd hh:mm").format(scheduleInfo.getChangeTime())%></td>
      </tr>
   <%}
  }
%>
    <%if(userInfo != null && ((userInfo.getUserRule() >> 5 & 1) == 1 || (userInfo.getUserRule() >> 10 & 1) == 1)){
      %>
        <tr>
            <td colspan="5">添加跟踪</td>
        </tr>
    <%
        int a = 0;
        try {
           a = Integer.parseInt(scheduleInfoList.get(scheduleInfoList.size() - 1).getScheuleStatus());
        }catch (Exception e){%>
            <h2>状态读取错误！！！</h2>
        <%}
        a++;
            if(a <= 4){
        %>
        <tr>
            <td>新增状态：</td>
            <td colspan="3">
                <select name="scheuleStatus">
                    <option value="<%=a%>"><%=statusMap.get(String.valueOf(a))%></option>
                </select>
            </td>
        </tr>
        <%}
    %>
    <input name="repairId" value="<%=scheduleInfoList.get(0).getRepairId()%>" type="hidden"/>
    <tr>
        <td><button type="submit">提交</button></td>
        <td colspan="3"><a href="/op/queryRepairFormOp"><button type="button">返回</button></a></td>
    </tr>
    <%}%>

</table>
</form>
</body>
</html>
