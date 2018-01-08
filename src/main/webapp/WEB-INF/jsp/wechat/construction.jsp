<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">
<meta name="viewport"
	content="width=device-width,initial-scale=1,user-scalable=0">
<title>网站建设中</title>

<link rel="stylesheet" href="static/css/wechat/main.css" type="text/css"></link>
<link rel="stylesheet" href="static/css/wechat/weui.min.css"
	type="text/css"></link>
</head>

<body>
	<div
		style="background-color:#BF172E;height:50px;margin:0px;padding:0px;width:100%; position:fixed; top:0;z-index: 9999;">
		<div style="padding:0.07rem 0.15rem;">
			<!--<div style="float: right;height:50px;line-height: 50px;"><a style="color: #F3FFFF;"href="javascript:;">明细</a></div>-->
			<div
				style="text-align: center;height: 50px;  line-height:50px; color: #F3FFFF; ">大诚中医</div>
		</div>
	</div>
	<div style="margin-top:50px;"></div>
	网站建设中
</body>
</html>
