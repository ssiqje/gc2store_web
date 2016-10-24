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
</style></head>
<body class="centh1">
总表
<hr/>
<%=request.getAttribute("table") %>
<a href="/datapackage/userdateforweb?action=gettable_summary"><img src="../image/back_arrow.png" alt="返回" width="48" height="48" align="right"></a>
</body>
</html>