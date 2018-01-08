<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
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

<title>积分明细</title>

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
			<c:when test="${empty creditDetails}">
			您还没有积分记录
		</c:when>
		<c:otherwise>
				<c:forEach items="${creditDetails}" var="creditDetail">
					<div class="weui-cell">
						<div class="weui-cell__bd">
							<p>
								<c:choose>
									<c:when
										test="${creditDetail.nowCredit-creditDetail.originalCredit gt 0}">+</c:when>
								</c:choose>
								<c:out
									value="${creditDetail.nowCredit-creditDetail.originalCredit}"></c:out>
							</p>
							<p>${creditDetail.createTime}</p>
						</div>
						<div class="weui-cell__ft">
							<span>现积分${creditDetail.nowCredit}</span><br> <span>原${creditDetail.originalCredit}</span>
						</div>
					</div>
				</c:forEach>




			 </c:otherwise>
	</c:choose>
	</div>
</body>
</html>
