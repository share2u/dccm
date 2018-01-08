<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html lang="en">
<head>
<base href="<%=basePath%>">
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=0">
<title>账单明细</title>
<link rel="stylesheet" href="static/css/wechat/weui.min.css"/>
<script type="text/javascript" src="static/js/jquery-1.7.2.js"></script>
<style type="text/css">
	body{
		background-color: #f8f8f8;
		font-family:-apple-system-font,Helvetica Neue,Helvetica,sans-serif;
		font-size:13px;
	}
	.icon-box__desc {
		color: #888;
		font-size: 14px;
		margin-top: 80px;
	}
</style>
</head>
<body ontouchstart="">
	<div style="background-color:#BF172E;height:50px;margin:0px;padding:0px;width:100%; position:fixed; top:0;z-index: 9999;">
  		<div style="padding:0.07rem 0.15rem;">
  			<div style="text-align: center;height: 50px; line-height:50px; color: #F3FFFF;font-size:16px; ">大诚中医</div>
  		</div>
  	</div>
  	<div style="margin-top:50px;"></div>
  	<c:choose>
		<c:when test="${size == 0}">
			<div>
				<div style="margin:0 auto">
					<div style="width:100%;text-align:center;">
						<p class="icon-box__desc">提示,您暂时还没有账单明细</p>
					</div>
				</div>
			</div>
		</c:when>
		<c:otherwise>
			<div class="weui-panel__bd" style="background-color:#ffffff">
				<c:forEach items="${weChatPreStoreMxs}" var="weChatPreStoreMx">
					<a href="javascript:void;"
					class="weui-media-box weui-media-box_appmsg" style="padding-bottom:5px">
						<div class="weui-media-box__hd" style="margin-right:5px"></div>
						<div class="weui-media-box__bd" style="width:100%">
							<h4 class="weui-media-box__title" style="display:inline;font-size:15px">
								<c:choose>
									<c:when test="${weChatPreStoreMx.type == 1}">
										收入- 电汇预存
									</c:when>
									<c:when test="${weChatPreStoreMx.type == 2}">
										支出- 订单支付
									</c:when>
									<c:when test="${weChatPreStoreMx.type == 3}">
										支出 - 钱包余额返现
									</c:when>
									<c:when test="${weChatPreStoreMx.type == 4}">
										收入- 订单取消
									</c:when>											
									<c:otherwise></c:otherwise>
								</c:choose>
							</h4>
							<p class="weui-media-box__desc" style="float:right;margin-right:10px">
								<font style="color:#BF172E;font-size:16px">
									<c:choose>
									<c:when test="${weChatPreStoreMx.type == 1}">
										+${weChatPreStoreMx.money}
									</c:when>
									<c:when test="${weChatPreStoreMx.type == 2}">
										-${weChatPreStoreMx.money}
									</c:when>
									<c:when test="${weChatPreStoreMx.type == 3}">																					
										-${weChatPreStoreMx.money}
									</c:when>																										
									<c:when test="${weChatPreStoreMx.type == 4}">
										+${weChatPreStoreMx.money}
									</c:when>
									<c:otherwise></c:otherwise>
								</c:choose>
								</font>
							</p>
							<p class="weui-media-box__desc" style="margin-top:6px">
								<font style="color:#888;font-size:14px">
									${weChatPreStoreMx.createTime}
								</font>
							</p>
						</div>
					</a>
					<div style="background-color:#f8f8f8;height:5px"></div>
				</c:forEach>
			</div>
		</c:otherwise>
	</c:choose>
</body>
</html>