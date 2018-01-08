<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html lang="en">
<head>
<base href="<%=basePath%>">
<meta charset="UTF-8">
<meta name="viewport"
	content="width=device-width,initial-scale=1,user-scalable=0">
<title>优惠券详情</title>
<link rel="stylesheet" href="static/css/wechat/weui.min.css" />
<script type="text/javascript" src="static/js/jquery-1.7.2.js"></script>
<style type="text/css">
body {
	background-color: #f8f8f8;
	font-family: -apple-system-font, Helvetica Neue, Helvetica, sans-serif;
	font-size: 13px;
}

.icon-box__desc {
	color: #888;
	font-size: 14px;
	margin-top: 80px;
}
</style>
</head>
<body ontouchstart="">
	<div
		style="background-color:#BF172E;height:50px;margin:0px;padding:0px;width:100%; position:fixed; top:0;z-index: 9999;">
		<div style="padding:0.07rem 0.15rem;">
			<div
				style="text-align: center;height: 50px; line-height:50px; color: #F3FFFF;font-size:16px; ">大诚中医</div>
		</div>
	</div>
	<div style="margin-top:50px;"></div>
	<div class="weui-panel__bd" style="background-color:#ffffff">
		<a href="javascript:void(0);"
			class="weui-media-box weui-media-box_appmsg">
			<div class="weui-media-box__hd" style="margin-right:10px">
				
			</div>
			<div class="weui-media-box__bd" style="width:100%">
				<h4 class="weui-media-box__title">${weChatUserDiscount.discount.discountName}</h4>
				<div style="height:3px"></div>
				<p class="weui-media-box__desc">
					¥${weChatUserDiscount.discount.discountAmount}
				</p>
				<div style="height:3px"></div>
				<p class="weui-media-box__desc">
					使用条件:仅
					<c:forEach items="${weChatUserDiscount.discount.serviceProjects}"
						var="serviceProject" varStatus="status">
						<b>${serviceProject.pName}</b>
						<c:choose>
							<c:when test="${status.last}"></c:when>
							<c:otherwise>
								、
							</c:otherwise>
						</c:choose>
					</c:forEach>
					服务项目可用
				</p>
				<div style="height:3px"></div>
				<p class="weui-media-box__desc" style="display:inline;">
					<font style="color:#888;">
						${weChatUserDiscount.discount.startTime} 到 ${weChatUserDiscount.discount.endTime}
					</font>
				</p>
				<p class="weui-media-box__desc" style="float:right;margin-right:10px">
					<font style="color:#888;">详细</font>
					<img alt="" src="static/images/wechat/shangla.png" style="height:13px;width:16px">
				</p>
			</div>
		</a>
	</div>
</body>
</html>