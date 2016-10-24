<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>无标题文档</title>
<style type="text/css">
.cent {
	color: #C30;
	text-align: center;
}
.centlow {
	text-align: center;
	font-size: 16px;
}
</style>
</head>

<body bgcolor="#00CCFF">
<p>&nbsp;</p>
<h1 class="cent"><strong>欢迎使用本仓库管理系统</strong></h1>
<hr/>
<form action="userdateforweb" method="post" enctype="application/x-www-form-urlencoded">
<table border="1" align="center">
  <tr>
    <td colspan="2">请你先登入：</td>
  </tr>
  <tr>
    <td>用户ID：</td>
    <td><input name="user_id"/></td>
  </tr>
  <tr>
    <td>用户密码：</td>
    <td><input name="user_psw" type="password"/></td>
  </tr>
  <tr>
    <td colspan="2" align="center"><input name="" type="reset" value="取消"><input name="" type="submit" value="提交"><input name="action" type="hidden" value="login_user"></td>
  </tr>
</table>
</form>
<br/>
<p class="centlow">还没有帐号，赶紧点<a href="/datapackage/userdateforweb?action=download">下载手机App</a>！注册帐号！</p>
<p class="centlow">直接下载下载<a href="app/store.apk">手机App</a>！注册帐号！</p>
<%
	String result = (String)request.getAttribute("result");
	if(result!=null&&!"".equals(result))
	{
		%>
		<p class="centlow"><%=request.getAttribute("result") %></p>
		<%
	}

%>
</body>
</html>
