<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'MyJsp.jsp' starting page</title>
	
  	<link rel="stylesheet" href="../../../static/css/wechat/main.css" type="text/css"></link>
  	<link rel="stylesheet" href="../../../static/css/wechat/weui.min.css" type="text/css"></link></head>
  	
  <body>
  	<jsp:include page="<c:url value="header.jsp"></c:url>"></jsp:include>
  </body>
</html>
