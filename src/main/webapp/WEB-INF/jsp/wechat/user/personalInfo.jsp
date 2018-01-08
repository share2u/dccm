<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html lang="en">
<head>
<base href="<%=basePath%>">
<meta charset="UTF-8">
<meta name="viewport"
	content="width=device-width,initial-scale=1,user-scalable=0">
<title>个人设置</title>
<link rel="stylesheet" href="static/css/wechat/weui.min.css"/>
<link rel="stylesheet" href="static/css/wechat/example.css"/>
<script type="text/javascript" src="static/js/jquery-1.7.2.js"></script>
<style type="text/css">
	label{
		font-size:15px;
	}
	.init_size{
		font-size:15px;	
	}
</style>
</head>
<body style="background-color:#f8f8f8;">
	<div style="background-color:#BF172E;height:50px;margin:0px;padding:0px;width:100%; position:fixed; top:0;z-index: 9999;">
  		<div style="padding:0.07rem 0.15rem;">
  			<div style="text-align: center;height: 50px;  line-height:50px; color: #F3FFFF; ">大诚中医</div>
  		</div>
  	</div>
  	<!-- margin的顺序是上右下左 -->
  	<div style="margin-top:50px;"></div>
	<div class="weui-cells">
		<div class="weui-cell">
			<div class="weui-cell__bd">
				<label class="weui-label">头像</label>
			</div>
			<div class="weui-cell__ft">
				<img class="weui-media-box__thumb" alt=""
					src="${weChatUser.userImg}" style="width:40px;height:40px">
			</div>
		</div>
		<div class="weui-cell">
			<div class="weui-cell__bd">
				<label class="weui-label">昵称</label>
			</div>
			<div class="weui-cell__ft init_size">
				${weChatUser.userName}
			</div>
		</div>
	</div>
	<div class="weui-cells__title" style="background-color:#f8f8f8;"></div>
	<div class="weui-cells">
		<div class="weui-cell">
			<div class="weui-cell__bd">
				<label class="weui-label">姓名</label>
			</div>
			<div class="weui-cell__ft init_size">
				${weChatUser.name}
			</div>
		</div>
		<div class="weui-cell">
			<div class="weui-cell__bd">
				<label class="weui-label">性别</label>
			</div>
			<div class="weui-cell__ft init_size">
				<c:choose>
					<c:when test="${weChatUser.sex == 1}">
						男
					</c:when>
					<c:otherwise>
						女
					</c:otherwise>
				</c:choose>
			</div>
		</div>
		<div class="weui-cell">
			<div class="weui-cell__bd">
				<label class="weui-label">出生日期</label>
			</div>
			<div class="weui-cell__ft init_size">
				${weChatUser.birthday}
			</div>
		</div>
		<div class="weui-cell">
			<div class="weui-cell__bd">
				<label class="weui-label">手机号</label>
			</div>
			<div class="weui-cell__ft init_size">
				${weChatUser.phone}
			</div>
		</div>
		<div class="weui-cell">
			<div class="weui-cell__bd">
				<label class="weui-label">城市</label>
			</div>
			<div class="weui-cell__ft init_size">
				${weChatUser.city}
			</div>
		</div>
		<div class="weui-cell">
			<div class="weui-cell__bd">
				<label class="weui-label">住址</label>
			</div>
			<div class="weui-cell__ft init_size">
				${weChatUser.address}
			</div>
		</div>
		<div class="weui-cell">
			<div class="weui-cell__bd">
				<label class="weui-label">证件信息</label>
			</div>
			<div class="weui-cell__ft init_size">
				${weChatUser.idCode}
			</div>
		</div>
		<div class="weui-cell">
			<div class="weui-cell__bd">
				<label class="weui-label">邮箱</label>
			</div>
			<div class="weui-cell__ft init_size">
				${weChatUser.email}
			</div>
		</div>
	</div>
	<div class="weui-cells__title" style="background-color:#f8f8f8;"></div>
	<div class="weui-cells">
		<a class="weui-cell weui-cell_access" href="weChatUser/editUser/${weChatUser.uId}">
			<div class="weui-cell__bd">
				<p style="font-size:15px">个人资料编辑</p>
			</div>
			<div class="weui-cell__ft">				
			</div>
		</a>
	</div>
</body>
</html>