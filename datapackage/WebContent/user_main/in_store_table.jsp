<%@page import="java.util.ArrayList"%>
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
<script type="text/javascript">
	function add()
	{
		var value = document.getElementById("addform").getAttribute("kind_id");
		Ҫ�ϴ���������+value;
	}
</script>
<%
	JSONArray tablejsonarray = (JSONArray) request.getAttribute("table");
	JSONArray parameter = (JSONArray)request.getAttribute("parameter");
	ArrayList<String> parameter_list = new ArrayList<String>();
	JSONObject jsonObject=null;
	if(parameter!=null)
	{
		System.out.println("��ȡ�Ĳ�����:"+parameter.toString());
		for (int i = 0; i < parameter.length(); i++) {
			jsonObject = (JSONObject) parameter.get(i);
			parameter_list.add(jsonObject.getString("kind_id"));
		}
	}
%>
</head>
<body class="centh1">
	������
	<hr />
	<table width="600" border="1" align="center">
		<tr>
			<td width="125" class="centh1"><span class="table">��ˮ���</span></td>
			<td width="126" class="table">������</td>
			<td width="127" class="table">����</td>
			<td width="127" class="table">����</td>
			<td width="127" class="table">����</td>
			<td width="127" class="table">�ܸ���</td>
			<td width="127" class="table">����</td>
		</tr>

		<%
			if (tablejsonarray != null && tablejsonarray.length() > 0) {
				for (int i = 0; i < tablejsonarray.length(); i++) {
					System.out.println("������ ");
					jsonObject = (JSONObject) tablejsonarray.get(i);
					System.out.println(jsonObject.toString());
		%>
		<tr>
			<td class="table"><%=jsonObject.getString("id")%></td>
			<td class="table"><%=jsonObject.getString("kind_id")%></td>
			<td class="table"><%=jsonObject.getString("inpay_m")%></td>
			<td class="table"><%=jsonObject.getString("count")%></td>
			<td class="table"><%=jsonObject.getString("wight")%></td>
			<td class="table"><%=jsonObject.getString("allpay")%></td>
			<td class="table"><%=jsonObject.getString("date")%></td>
		</tr>
		<%
			}
			}
		%>

	</table>
	<a href="/datapackage/userdateforweb?action=gettable_summary"><img
		src="/datapackage/image/back_arrow.png" alt="����" width="48" height="48"
		align="right"></a>
</body>

</html>