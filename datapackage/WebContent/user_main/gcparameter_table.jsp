<%@page import="org.json.JSONArray"%>
<%@page import="org.json.JSONObject"%>
<%@ page language="java" contentType="text/html; charset=GB18030"
	pageEncoding="GB18030"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GB18030">
<title>Insert title here</title>
</head>
<style type="text/css">
.centh1 {
	text-align: center;
	font-size: 36px;
	font-weight: bold;
}

.table {
	font-size: 14px;
	font-weight: normal;
}
</style>
<%
	JSONArray tablejsonarray = (JSONArray) request.getAttribute("table");
%>
</head>
<body class="centh1">
	种类参数表
	<hr />
	<table width="300" border="1" align="center">
		<tr>
			<td width="125" class="table">种类编号</td>
			<td width="126" class="table">每米重(吨)</td>
			<td width="127" class="table">长度(米)</td>
		</tr>
		
			<%
				if (tablejsonarray != null && tablejsonarray.length() > 0) {
					for (int i = 0; i < tablejsonarray.length(); i++) {
						System.out.println("有数据 ");
						JSONObject jsonObject = (JSONObject) tablejsonarray.get(i);
						System.out.println(jsonObject.toString());
			%>
			<tr>
			<td class="table"><%=jsonObject.getString("kind_id")%></td>
			<td class="table"><%=jsonObject.getString("weight_m")%></td>
			<td class="table"><%=jsonObject.getString("gc_long")%></td>
			</tr>
			<%
				}
				}
			%>
		
	</table>
	<a href="/datapackage/userdateforweb?action=gettable_summary"
		class="table"><img src="/datapackage/image/back_arrow.png"
		alt="返回" width="48" height="48" align="right"></a>
</body>

</html>