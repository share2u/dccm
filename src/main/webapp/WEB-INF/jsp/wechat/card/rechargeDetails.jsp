<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<base href="<%=basePath%>">

<title>储值明细</title>

<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=no">
<link rel="stylesheet" type="text/css" href="static/css/wechat/main.css" />
<link rel="stylesheet" href="static/css/wechat/weui.min.css"
	type="text/css"></link>

</head>

<body>
	<div
		style="background-color:#BF172E;height:50px;margin:0px;padding:0px;width:100%; position:fixed; top:0;z-index: 9999;">
		<div style="padding:0.07rem 0.15rem;">
			<div
				style="text-align: center;height: 50px;  line-height:50px; color: #F3FFFF; ">大诚中医</div>
		</div>
	</div>
	<div style="margin-top:50px;"></div>
	<div class="weui-cells">
		<c:choose>
			<c:when test="${empty rechargeDetails}">
			您还没有储值记录
		</c:when>
			<c:otherwise>
				<c:forEach items="${rechargeDetails}" var="rechargeDetail">
					<div class="weui-cell">
						<c:choose>
							<c:when test="${rechargeDetail.type eq 0}">
								<div class="weui-cell__bd">
									<p>线上充值</p>
									<p>${rechargeDetail.creatTime}</p>
								</div>
								<div class="weui-cell__ft">
									<span>+${rechargeDetail.money}</span><br> <span>返点+${rechargeDetail.points}</span>
								</div>
							</c:when>
							<c:when test="${rechargeDetail.type eq 1}">
								<div class="weui-cell__bd">
									<p>线下充值</p>
									<p>${rechargeDetail.creatTime}</p>
								</div>
								<div class="weui-cell__ft">
									<span>+${rechargeDetail.money}</span><br> <span>返点+${rechargeDetail.points}</span>
								</div>
							</c:when>
							<c:when test="${rechargeDetail.type eq 2}">
								<div class="weui-cell__bd">
									<p>退款</p>
									<p>${rechargeDetail.creatTime}</p>
								</div>
								<div class="weui-cell__ft">
									<span>${rechargeDetail.money}</span><br> <span>返点${rechargeDetail.points}</span>
								</div>
							</c:when>
							<c:when test="${rechargeDetail.type eq 3}">
								<div class="weui-cell__bd">
									<p>老客户录入</p>
									<p>${rechargeDetail.creatTime}</p>
								</div>
								<div class="weui-cell__ft">
									<span>+${rechargeDetail.money}</span><br> <span>返点+${rechargeDetail.points}</span>
								</div>
							</c:when>
							<c:when test="${rechargeDetail.type eq 4}">
								<div class="weui-cell__bd">
									<p>线上消费</p>
									<p>${rechargeDetail.creatTime}</p>
								</div>
								<div class="weui-cell__ft">
									<span>-${rechargeDetail.money}</span><br> <span>返点-${rechargeDetail.points}</span>
								</div>
							</c:when>
							<c:when test="${rechargeDetail.type eq 5}">
								<div class="weui-cell__bd">
									<p>线下消费</p>
									<p>${rechargeDetail.creatTime}</p>
								</div>
								<div class="weui-cell__ft">
									<span>-${rechargeDetail.money}</span><br> <span>返点-${rechargeDetail.points}</span>
								</div>
							</c:when>
							<c:when test="${rechargeDetail.type eq 6}">
								<div class="weui-cell__bd">
									<p>取消订单</p>
									<p>${rechargeDetail.creatTime}</p>
								</div>
								<div class="weui-cell__ft">
									<span>+${rechargeDetail.money}</span><br> <span>返点+${rechargeDetail.points}</span>
								</div>
							</c:when>
							<c:otherwise>
								<div class="weui-cell__bd">
									<p>未知</p>
									<p>${rechargeDetail.creatTime}</p>
								</div>
								<div class="weui-cell__ft">
									<span>未知${rechargeDetail.money}</span><br> <span>返点未知${rechargeDetail.points}</span>
								</div>
							</c:otherwise>
						</c:choose>

					</div>
				</c:forEach>
			</c:otherwise>
		</c:choose>


	</div>
</body>
</html>
