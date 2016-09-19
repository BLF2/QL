<%@ page import="net.blf2.model.entity.UserInfo" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Iterator" %>
<%--
  Created by IntelliJ IDEA.
  User: t_mengxh
  Date: 2016/9/10
  Time: 12:38
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
<a href="/op/toAddClassificationInfo"><button class="btn-primary" type="button">查看/增加分类信息</button></a>
<a href="/op/toAddRepairForm"><button class="btn-primary" type="button">提交维修表</button></a>
<a href="/op/toMyRepairForm"><button class="btn-primary" type="button">我的维修表</button></a>
<a href="/op//queryRepairFormOp"><button class="btn-primary" type="button">所有维修表</button></a>
<table class="table table-bordered" id="table1">
  <tr>
    <td>用户ID</td>
    <td>用户学号</td>
    <td>用户登录密码</td>
    <td>用户昵称</td>
    <td>用户性别（0男1女）</td>
    <td>用户手机号</td>
    <td>用户邮箱</td>
    <td>用户所在大学</td>
    <td>用户所在学院</td>
    <td>用户专业</td>
    <td>入学年份</td>
    <td>用户身份</td>
    <td>用户状态（0为冻结1为正常）</td>
    <td>操作1</td>
    <td>操作2</td>
    <td>操作3</td>
  </tr>
  <%
    HttpSession httpSession = request.getSession();
    List<UserInfo>userInfoAll = (List<UserInfo>) httpSession.getAttribute("userInfoAll");
    if(userInfoAll == null){%>
      <h2>无数据</h2>
    <%}else{
      httpSession.removeAttribute("userInfoAll");
      Iterator<UserInfo>iter = userInfoAll.iterator();
      while(iter.hasNext()){
        UserInfo userInfo = iter.next();%>
  <tr>
    <td><%=userInfo.getUserId()%></td>
    <td><%=userInfo.getUserNumber()%></td>
    <td><%=userInfo.getUserPassword()%></td>
    <td><%=userInfo.getUserNickName()%></td>
    <td><%=userInfo.getUserSex()%></td>
    <td><%=userInfo.getUserPhoneNumber()%></td>
    <td><%=userInfo.getUserEmail()%></td>
    <td><%=userInfo.getUserUniversity()%></td>
    <td><%=userInfo.getUserCollege()%></td>
    <td><%=userInfo.getUserMajor()%></td>
    <td><%=userInfo.getUserGrade()%></td>
    <td>
      <%
        if(userInfo.getUserRule() == (1 << 18) - 1)
        {%>
          超级管理员
        <%}else if((userInfo.getUserRule() >> 17 & 1) == 1){%>
          教师
      <%}else if((userInfo.getUserRule() >> 10 & 1) == 1){%>
        维修人员
        <%}else{%>
          学生
        <%}
      %>
    </td>
    <td><%=(userInfo.getUserRule() & 1)%></td>
    <td><a href="/op/freezeAccount?userId=<%=userInfo.getUserId()%>">冻结账号</a></td>
    <td><a href="/op/thawAccount?userId=<%=userInfo.getUserId()%>">解封账号/审核通过</a></td>
    <td><a href="/op/toUpdateAccount?userId=<%=userInfo.getUserId()%>">更新信息</a></td>
  </tr>
      <%}
    }
  %>
</table>

</body>
</html>
