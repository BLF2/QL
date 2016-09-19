<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page import="net.blf2.model.entity.UserInfo" %>
<%--
  Created by IntelliJ IDEA.
  User: blf2
  Date: 16-9-11
  Time: 下午9:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>UpateUserInfo</title>
  <!-- 新 Bootstrap 核心 CSS 文件 -->
  <link rel="stylesheet" href="http://cdn.bootcss.com/bootstrap/3.3.0/css/bootstrap.min.css">
  <!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
  <script src="http://cdn.bootcss.com/jquery/1.11.1/jquery.min.js"></script>

  <!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
  <script src="http://cdn.bootcss.com/bootstrap/3.3.0/js/bootstrap.min.js"></script>
  <script>
    function check(){
      return confirm("确认更新？");
    }
  </script>
</head>
<body>
<form>
<table class="table table-bordered">
  <%
    HttpSession httpSession = request.getSession();
    UserInfo userInfo = (UserInfo)httpSession.getAttribute("updateUserInfo");
    if(userInfo == null){%>
      <h2>无数据</h2>
    <%}else{
      httpSession.removeAttribute("updateUserInfo");
  %>
  <thead>用户信息</thead>
  <tr>
    <td>用户ID</td>
    <td><input type="text" name="userId" readonly="readonly" value="<%=userInfo.getUserId()%>" /></td>
  </tr>
  <tr>
    <td>用户学号</td>
    <td><input type="text" name="userNumber" value="<%=userInfo.getUserNumber()%>"/></td>
  </tr>
  <tr>
    <td>用户密码</td>
    <td><input type="text" name="userPassword" value="<%=userInfo.getUserPassword()%>"/></td>
  </tr>
  <tr>
    <td>用户昵称</td>
    <td><input type="text" name="userNickName" value="<%=userInfo.getUserNickName()%>"/></td>
  </tr>
  <tr>
    <td>用户性别</td>
    <td><input type="number" name="userSex" value="<%=userInfo.getUserSex()%>"/>备注：0为男，1为女</td>
  </tr>
  <tr>
    <td>用户手机号</td>
    <td><input type="text" name="userPhoneNumber" value="<%=userInfo.getUserPhoneNumber()%>"/></td>
  </tr>
  <tr>
    <td>用户邮箱</td>
    <td><input type="email" name="userEmail" value="<%=userInfo.getUserEmail()%>"/></td>
  </tr>
  <tr>
    <td>用户所在大学</td>
    <td><input type="text" name="userUniversity" value="<%=userInfo.getUserUniversity()%>"/></td>
  </tr>
  <tr>
    <td>用户所在学院</td>
    <td><input type="text" name="userCollege" value="<%=userInfo.getUserCollege()%>"/></td>
  </tr>
  <tr>
    <td>用户专业</td>
    <td><input type="text" name="userMajor" value="<%=userInfo.getUserMajor()%>"/></td>
  </tr>
  <tr>
    <td>用户年级</td>
    <td><input type="number" name="userGrade" value="<%=userInfo.getUserMajor()%>"/></td>
  </tr>
  <%}%>
  <tr>
    <td>
      <button type="submit" onclick="return check()">提交</button>
    </td>
    <td>
      <a href="/welcome.jsp"><button type="button">取消</button></a>
    </td>
  </tr>
  </table>
</form>
</body>
</html>
