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

<title>门店详情</title>

<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=no">
<link rel="stylesheet" href="static/css/wechat/weui.min.css">
<link rel="stylesheet" type="text/css" href="static/css/wechat/main.css" />
<link rel="stylesheet" type="text/css"
	href="static/css/wechat/docterList.css" />
<script type="text/javascript" src="static/js/jquery-1.7.2.js"></script>

</head>

<body>
	<div
		style="background-color:#BF172E;height:50px;margin:0px;padding:0px;width:100%; position:fixed; top:0;z-index: 9999;">
		<div style="padding:0.07rem 0.15rem;">
			<div
				style="float: right; width: 10%;margin-top: 0.5rem;margin-right: 0.5rem;">
				<a href="weChat/userCenter"> <img alt=""
					src="static/images/wechat/wechatUserCenter.png"> </a>
			</div>
			<div
				style="margin-left:10%; text-align: center;height: 50px;  line-height:50px; color: #F3FFFF; ">大诚中医</div>
		</div>
	</div>
	<div style="margin-top:50px;height:120px;">
		<img style="width: 100%;height: 120px;"
			src="${pageContext.request.contextPath }/uploadFiles/storeimg/${store.storeBigImg}" />
	</div>
	<div class="weui-panel"
		style="border-bottom: 1px solid #e6e6e6;">
		<div class="weui-panel__bd">
			<div class="weui-media-box weui-media-box_text">
				<h4 class="weui-media-box__title">${store.storeName}</h4>
				<p class="weui-media-box__desc">介绍：${store.storeInfo}</p>
				<p class="weui-media-box__desc">地址：${store.address}</p>
				<p class="weui-media-box__desc">营业时间：${store.workTime}</p>
				<p class="weui-media-box__desc">联系电话：${store.telephone}</p>
			</div>
		</div>
	</div>
	<div
		style="border-bottom: 1px solid #e6e6e6; padding: 0 15px;text-align: left;">
		<span
			style="border-bottom: 2px solid #009ff0;color: #BF172E;display: inline-block;opacity:0.8; font-size: 12px;line-height: 3.5; margin-bottom: -1px;">
			<c:choose>
				<c:when test="${storeType =='02'}"> 医师列表</c:when>
				<c:when test="${storeType =='01'}"> 培训课程</c:when>
			</c:choose> 
			</span>
	</div>

	<div class="wraper-list-doctor">
		<ul id="doctor-list" class="fn-clear">
		
			<c:choose>
				<c:when test="${empty docters}">
					<span> <c:choose>
							<c:when test="${storeType ==02}"> 暂时没有医生，查看其他门店哦！</c:when>
							<c:when test="${storeType ==01}">暂时没有培训课程，查看其他门店哦！</c:when>
						</c:choose> </span>
				</c:when>
				<c:otherwise>
					<c:forEach items="${docters}" var="docter">
						<a href="weChatStore/getDoctorDetailById/${docter.STAFF_ID}">
							<c:choose>
								<c:when test="${storeType =='02'}"> 
									<li style="border: 1px solid #dedede;">
										<div style="height: 180px; margin: 0px auto;overflow: hidden;width: 120px;">
										<img width="120" height="180" alt=""
										src="uploadFiles/staffimg/${docter.STAFF_IMG}" />
										</div>
										<div class="doctor-name">
											<span>${docter.STAFF_NAME}</span>
										</div>
									</li>
								</c:when>
								<c:when test="${storeType =='01'}"> 
									<li style="border: 0px;">
										<div style="height: 99.5px; margin: 0px auto;overflow: hidden;width: 150px;">
										<img width="150px" height="107" alt=""
										src="uploadFiles/staffimg/${docter.STAFF_IMG}" />
										</div>
										<div class="doctor-name">
											<span>${docter.STAFF_NAME}</span>
										</div>
									</li>
								</c:when>
							</c:choose>
					</a>
					</c:forEach>
			 	</c:otherwise>
			</c:choose>

		</ul>
	</div>

	<div style="clear:both;"></div>
	<div
		style="border-bottom: 1px solid #e6e6e6; padding: 0 15px;text-align: left;">
		<span
			style="border-bottom: 2px solid #009ff0;color: #BF172E;display: inline-block;opacity:0.8;  font-size: 12px;line-height: 3.5; margin-bottom: -1px;">用户点评</span>
	</div>
	<div class="weui-cells" style="margin-top: 0;margin-bottom: 10px;">
		 <c:choose>
			<c:when test="${empty comments}">
  			该门店暂时没有订单，不如你来做第一个！
  		</c:when>
			<c:otherwise>
				<c:forEach items="${comments}" var="comment">
					<div class="weui-cell">
						<div class="weui-cell_hd">
							<img src="${comment.userImg}" alt="评论用户头像"
								style="width:40px;margin-right:5px;display:block;border-radius: 50px" />
						</div>
						<div class="weui-cell_bd">
							${comment.messageInfo }
							<div style="position:absolute;right: 0.15rem;top:0;">${comment.createTime}</div>
						</div>
					</div>
				</c:forEach>
		 	</c:otherwise>
		</c:choose>

		<!-- <a href="comment.jsp" style=" margin-bottom:20px; text-align: center;color: #666;display: block;font-size: 1.0rem;height: 1.3rem;line-height: 1.3rem;position: relative;width: 100%;">查看全部评价 &lt;<span>14</span>&gt;条</a> -->
	</div>

</body>
</html>