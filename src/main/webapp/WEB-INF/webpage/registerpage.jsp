<%--
  Created by IntelliJ IDEA.
  User: t_mengxh
  Date: 2016/9/7
  Time: 20:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <!-- 新 Bootstrap 核心 CSS 文件 -->
  <link rel="stylesheet" href="http://cdn.bootcss.com/bootstrap/3.3.0/css/bootstrap.min.css">
  <!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
  <script src="http://cdn.bootcss.com/jquery/1.11.1/jquery.min.js"></script>

  <!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
  <script src="http://cdn.bootcss.com/bootstrap/3.3.0/js/bootstrap.min.js"></script>
  <title>register</title>
</head>
<body>
  <h2>注册信息</h2>
  <form role="form" method="post" action="/op/register">
    <div class="form-group">
      <label for="userEmail">邮箱：</label>
      <input type="email" class="form-control" id="userEmail" name="userEmail" placeholder="Enter email" required>
    </div>
    <div class="form-group">
      <label for="userPassword">密码</label>
      <input type="password" class="form-control" id="userPassword" name="userPassword" placeholder="Password" required>
    </div>
    <div class="form-group">
      <label for="userNickName">昵称</label>
      <input type="text" class="form-control" id="userNickName" name="userNickName" placeholder="userNickName" />
    </div>
    <div class="form-group">
      <label for="userNumber">学号</label>
      <input type="text" class="form-control" id="userNumber" name="userNumber" placeholder="userNumber" />
    </div>
    <div class="form-group">
      <label for="userSex">性别(0为男，1为女)</label>
      <input type="number" class="form-control" id="userSex" name="userSex" placeholder="userSex" />
    </div>
    <div class="form-group">
      <label for="userPhoneNumber">手机号</label>
      <input type="text" class="form-control" id="userPhoneNumber" name="userPhoneNumber" placeholder="userPhoneNumber">
    </div>
    <div class="form-group">
      <label for="userUniversity">大学</label>
      <input type="text" class="form-control" id="userUniversity" name="userUniversity" placeholder="userUniversity">
    </div>
    <div class="form-group">
      <label for="userCollege">学院</label>
      <input type="text" class="form-control" id="userCollege" name="userCollege" placeholder="userCollege">
    </div>
    <div class="form-group">
      <label for="userMajor">专业</label>
      <input type="text" class="form-control" id="userMajor" name="userMajor" placeholder="userMajor">
    </div>
    <div class="form-group">
      <label for="userGrade">年级</label>
      <input type="number" class="form-control" id="userGrade" name="userGrade" placeholder="userGrade">
    </div>
    <div class="form-group">
      <label for="userRule">角色(1普通学生2维修人员3教师)</label>
      <input type="number" class="form-control" id="userRule" name="userRule" placeholder="userRule">
    </div>
    <button type="submit" class="btn btn-default">Submit</button>
  </form>
</body>
</html>
