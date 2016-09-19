<%--
  Created by IntelliJ IDEA.
  User: t_mengxh
  Date: 2016/8/17
  Time: 19:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <meta name="description" content="">
  <meta name="author" content="">

  <title>QL</title>

  <!-- Bootstrap core CSS -->
  <!-- 新 Bootstrap 核心 CSS 文件 -->
  <link rel="stylesheet" href="http://cdn.bootcss.com/bootstrap/3.3.0/css/bootstrap.min.css">

  <!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
  <script src="http://cdn.bootcss.com/jquery/1.11.1/jquery.min.js"></script>

  <!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
  <script src="http://cdn.bootcss.com/bootstrap/3.3.0/js/bootstrap.min.js"></script>

  <!-- Custom styles for this template -->
  <link href="/static/css/signin.css" rel="stylesheet">


</head>

<body>

<div class="container">

  <form class="form-signin" role="form" method="post" action="/op/login">
    <h2 class="form-signin-heading">Please sign in</h2>
    <input type="email" class="form-control" placeholder="Email address" name="userEmail" required autofocus>
    <input type="password" class="form-control" placeholder="Password" name="userPassword" required>
    <div class="checkbox">
      <label>
        <input type="checkbox" name="remeberme" value="remember-me"> Remember me
      </label>
    </div>
    <button class="btn btn-lg btn-primary btn-block" type="submit">Sign in</button>
  </form>

</div> <!-- /container -->


<!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
<script src="../../static/js/ie10-viewport-bug-workaround.js"></script>
</body>
</html>

