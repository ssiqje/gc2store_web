<%@page import="org.apache.jasper.tagplugins.jstl.core.ForEach"%>
<%@page import="java.io.File"%>
<%@ page language="java" contentType="text/html; charset=GB18030"
	pageEncoding="GB18030"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GB18030">
<title>Insert title here</title>
<%
	String path = application.getRealPath("/");
	File f = new File(path + "\\photo_user");
	File[] file_list = f.listFiles();
%>
</head>
<body>
��Ŀ¼����ڽ�ľ���·��<%=request.getRequestURI() %>
<br>
�ļ��ľ���·��<%=application.getRealPath(request.getRequestURI()) %> 
<br>
��ǰWEBӦ�õľ���·��<%=application.getRealPath("/") %>
<br>
<hr>
<%
 for(int i=0;i<file_list.length;i++)
 {
	 String add = file_list[i].getPath().substring(file_list[i].getPath().indexOf("datapackage"), file_list[i].getPath().length());
	 %>
	 <img src="<%="http://localhost/"+add %>" width="100" height="100">
	 <br>
	 <%
 }
%>
</body>
</html>