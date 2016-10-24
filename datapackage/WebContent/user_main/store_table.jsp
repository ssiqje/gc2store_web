<%@page import="org.json.JSONArray"%>
<%@page import="org.json.JSONObject"%>
<%@ page language="java" contentType="text/html; charset=GB18030"
    pageEncoding="GB18030"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GB18030">
<title>Insert title here</title>
<style type="text/css">
.centh1 {
	text-align: center;
	font-size: 36px;
}
.table {
	font-size: 14px;
}
</style>
<%
	JSONArray tablejsonarray = (JSONArray) request.getAttribute("table");
%>
</head>
<body class="centh1">
库储表
<hr/>
<table width="600" border="1" align="center">
		<tr>
			<td width="126" class="table">种类编号</td>
			<td width="127" class="table">数量</td>
			<td width="127" class="table">重量</td>
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
			<td class="table"><%=jsonObject.getString("count")%></td>
			<td class="table"><%=jsonObject.getString("wight")%></td>
		</tr>
		<%
			}
			}
		%>

	</table>
<a href="/datapackage/userdateforweb?action=gettable_summary"><img src="/datapackage/image/back_arrow.png" alt="返回" width="48" height="48" align="right"></a>
</body>

</html>