<%@page import="org.apache.jasper.tagplugins.jstl.core.ForEach"%>
<%@page import="org.json.JSONArray"%>
<%@page import="com.syz.dataPackage.bean.User"%>
<%@page import="org.json.JSONObject"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>用户仓库管理</title>
<style type="text/css">
.centh1 {
	text-align: center;
}
.right {
	text-align: right;
}
</style>
<%
	User user = (User)request.getSession(false).getAttribute("user"); 
	JSONArray tablejsonarray = (JSONArray)request.getAttribute("table");
%>
</head>
<body>
<table align="right">
  <tr>
    <td><%
	if(user!=null)
	{
		%>
		您好,<%=user.getUser_name() %>！
		<%
	}else
	{
		%>
		您好,陌生人！
		<%
	}
%></td>
    <td><%
	if(user!=null)
	{
		%>
		<a href="/datapackage/userdateforweb?action=logout">登出</a>
		<%
	}else
	{
		%>
		<a href="/datapackage/index.jsp">登入</a>
		<%
	}
%></td>
  </tr>
</table>
<h1 class="centh1">仓库管理控制台</h1>
<hr/>
<table>
  <tr>
   <%
   	if(user!=null){
		%>
         <td>您现在拥有的表格如下：</td>
    
    <td width="100"><a href="/datapackage/userdateforweb?action=gettable_in_store">进货表</a></td>
    <td width="100"><a href="/datapackage/userdateforweb?action=gettable_out_store">出货表</a></td>
    <td width="100"><a href="/datapackage/userdateforweb?action=gettable_store">库储表</a></td>
    <td width="100"><a href="/datapackage/userdateforweb?action=gettable_gcparameter">种类参数表</a></td>
  </tr>
		<%
		}
   %>
</table>
<p class="centh1">您目前的情况是：</p>
<table width="400" border="1" align="center">
	<tr>
	<td width="100">盈亏</td><td width="110">库储数（吨）</td><td width="100">总付款</td><td width="100">总收款</td>
	</tr>
	<tr>
	<%
		if(tablejsonarray!=null&&tablejsonarray.length()>0)
		{
			System.out.println("有数据 ");
			JSONObject jsonObject = (JSONObject)tablejsonarray.get(0);
			System.out.println(jsonObject.toString());
			%>
			<td width="100"><%=jsonObject.getString("in_or_out_pay") %></td>
			<td width="110"><%=jsonObject.getString("weight_all") %></td>
			<td width="100"><%=jsonObject.getString("outpay_all") %></td>
			<td width="100"><%=jsonObject.getString("inpay_all") %></td>
			<%
		}
	%>
	</tr>
</table>
</body>
</html>










