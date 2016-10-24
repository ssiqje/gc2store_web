<%@page import="com.syz.dataPackage.bean.User"%>
<%@page import="org.json.JSONObject"%>
<%@page import="java.io.InputStreamReader"%>
<%@page import="java.io.BufferedReader"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">

<title>登入情况</title>
</head>
<body>
	<img src="image/ok.png" width="72" height="72" align="middle">
	<br />
	<%
	User user = new User();
	JSONObject json = (JSONObject) request.getAttribute("resultJson");
	if ("pass".equals(json.getString("result"))) {
		JSONObject userjson=new JSONObject(json.getString("json_message"));
			request.getSession().setAttribute("user", user);
			if (!userjson.isNull("id")) {
				user.setId(userjson.getInt("id"));
			}
			if (!userjson.isNull("photoImage")) {
				String photo_image = userjson.getString("photoImage");
				user.setPhotoImage(userjson.getString("photoImage"));
				System.out.println("你的照片" + photo_image);
			}
			if (!userjson.isNull("signature")) {
				String signature = userjson.getString("signature");
				user.setSignature(signature);
				System.out.println("你的签名" + signature);
			}
			if (!userjson.isNull("user_name")) {
				String user_name = userjson.getString("user_name");
				user.setUser_name(user_name);
				System.out.println("你的名字" + user_name);
			}
			if (!userjson.isNull("user_psw")) {
				String user_psw = userjson.getString("user_psw");
				user.setUser_psw(user_psw);
				System.out.println("你的密码：" + user_psw);
			}
			if (!userjson.isNull("gander")) {
				String gander = userjson.getString("gander");
				user.setGander(gander);
				System.out.println("你的性别：" + gander);
			}
			if (!userjson.isNull("qq")) {
				String qq = userjson.getString("qq");
				user.setQq(qq);
				System.out.println("你的QQ：" + qq);
			}
			if (!userjson.isNull("hobbly")) {
				String hobbly = userjson.getString("hobbly");
				user.setHobbly(hobbly);
			}
			System.out.println(user.toString());
	%>
	您好，<%=user.getUser_name()%>!已登入成功，
	<a href="/datapackage/userdateforweb?action=gettable_summary">点此</a>进入仓库管理界面！
	<%
		} else {
	%>
	<%=json.getString("json_message")%>
	<a href="index.html">点此</a>返回登入界面！
	<%
		}
	%>
</body>
</html>