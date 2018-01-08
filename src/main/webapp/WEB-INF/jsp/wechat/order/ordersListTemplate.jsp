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
<title>我的订单</title>
<link rel="stylesheet" href="static/css/wechat/weui.min.css" />
<script type="text/javascript" src="static/js/jquery-1.7.2.js"></script>
<style type="text/css">
</style>
</head>
<body style="background-color:#f8f8f8">
	<div
		style="background-color: #BF172E; height: 50px; margin: 0px; padding: 0px; width: 100%; position: fixed; top: 0; z-index: 9999;">
		<div style="padding: 0.07rem 0.15rem;">
			<div
				style="text-align: center; height: 50px; line-height: 50px; color: #F3FFFF;">大诚中医</div>
		</div>
	</div>
	<!-- margin的顺序是上右下左 -->
	<div style="margin-top: 50px;"></div>
	<div>
		<c:forEach items="${intercepter}" var="pageData">
			<div class="weui-cells"
				style="width: 100%; height: 35px; margin-bottom: 0px; margin-top: 5px; padding-bottom: 5px;">
				<!-- 跳转 门店详情页面 -->
				<a class="weui-cell weui-cell_access"
					href="weChatStore/getStoreDetailByStoreId/${pageData.storeId}">
					<div class="weui-cell__bd">
						<p>
							<b>${pageData["storeName"]}</b>
						</p>
					</div>
					<div class="weui-cell__ft"></div> </a>
			</div>
			<div style="height:290px;">
				<div class="weui-media-box weui-media-box_appmsg"
					style="padding-bottom:10px">
					<!--
						暂时去掉图片信息
						<div class="weui-media-box__hd">
							<img class="weui-media-box__thumb" alt="" src="uploadFiles/storeimg/${pageData['storeSmallImg']}">
						</div>
					-->
					<div style="width:100%">
						<div class="weui-cell">
							<div class="weui-cell__bd">
								<p>项目</p>
							</div>
							<p class="weui-cell__ft">${pageData["projectName"]}</p>
						</div>
						<div class="weui-cell">
							<div class="weui-cell__bd">
								<p>医生</p>
							</div>
							<p class="weui-cell__ft">${pageData["staffName"]}</p>
						</div>
						<div class="weui-cell">
							<div class="weui-cell__bd">
								<p>验证码</p>
							</div>
							<p class="weui-cell__ft">${pageData["appointCode"]}</p>
						</div>
						<div class="weui-cell">
							<div class="weui-cell__bd">
								<p>预约时间</p>
							</div>
							<p class="weui-cell__ft"><b>${pageData["appointTime"]}</b></p>
						</div>
						<div class="weui-cell">
							<div class="weui-cell__bd">
								<p>总价</p>
							</div>
							<p class="weui-cell__ft">${pageData["orderMoney"]}元</p>
						</div>
					</div>
				</div>
				<c:choose>
					<c:when test="${pageData['orderStatus'] == 0}">
						<!-- 跳转 支付页面 -->
						<a href="javascript:;"
							class="weui-btn weui-btn_mini weui-btn_warn"
							style="float:right;margin-right:25px"> 待支付 </a>
					</c:when>
					<c:when test="${pageData['orderStatus'] == 1}">
						<a href="javascript:;"
							class="weui-btn weui-btn_mini weui-btn_warn"
							style="float:right;margin-right:25px"> 已关闭 </a>
					</c:when>
					<c:when test="${pageData['orderStatus'] == 2}">
						<a href="javascript:;"
							class="weui-btn weui-btn_mini weui-btn_primary"
							style="float:right;margin-right:25px"> 已预约 </a>
					</c:when>
					<c:when test="${pageData['orderStatus'] == 3}">
						<a href="javascript:;"
							class="weui-btn weui-btn_mini weui-btn_warn"
							style="float:right;margin-right:25px"> 已过期 </a>
					</c:when>
					<c:when test="${pageData['orderStatus'] == 4}">
						<!-- 跳转 评价页面 -->
						<a href="javascript:;"
							class="weui-btn weui-btn_mini weui-btn_warn"
							style="float:right;margin-right:25px"> 待评价 </a>
					</c:when>
					<c:otherwise>
						<a href="javascript:;"
							class="weui-btn weui-btn_mini weui-btn_primary"
							style="float:right;margin-right:25px"> 已完成 </a>
					</c:otherwise>
				</c:choose>
			</div>
		</c:forEach>
	</div>
</body>
</html>