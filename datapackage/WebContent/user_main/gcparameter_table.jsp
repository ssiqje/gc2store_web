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
	���������
	<hr />
	<table width="300" border="1" align="center">
		<tr>
			<td width="125" class="table">������</td>
			<td width="126" class="table">ÿ����(��)</td>
			<td width="127" class="table">����(��)</td>
		</tr>
		
			<%
				if (tablejsonarray != null && tablejsonarray.length() > 0) {
					for (int i = 0; i < tablejsonarray.length(); i++) {
						System.out.println("������ ");
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
		alt="����" width="48" height="48" align="right"></a>
</body>

</html>