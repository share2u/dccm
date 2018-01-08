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
	.icon-box__desc {
		color: #888;
		font-size: 14px;
		margin-top: 80px;
	}
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
	<c:choose>
		<c:when test="${size == 0}">
			<div>
				<div style="margin:0 auto">
					<div style="width:100%;text-align:center;">
						<p class="icon-box__desc">提示,您暂时还没有订单</p>
					</div>
				</div>
			</div>
		</c:when>
		<c:otherwise>
			<div>
				<c:forEach items="${intercepter}" var="weChatOrder">
					<div class="weui-cells"
						style="width: 100%; height: 35px; margin-bottom: 0px; margin-top: 5px; padding-bottom: 5px;">
						<!-- 跳转 门店详情页面 -->
						<a class="weui-cell weui-cell_access"
							href="weChatStore/getStoreDetailByStoreId/${weChatOrder.store.storeId}">
							<div class="weui-cell__bd">
								<p>
									<b>${weChatOrder.store.storeName}</b>
								</p>
							</div>
							<div class="weui-cell__ft"></div> 
						</a>
					</div>
					<div style="height:340px;">
						<div class="weui-media-box weui-media-box_appmsg"
							style="padding-bottom:10px">
							<div style="width:100%">
								<div class="weui-cell">
									<div class="weui-cell__bd">
										<p>项目</p>
									</div>
									<p class="weui-cell__ft">
										<c:choose>
											<c:when test="${empty weChatOrder.serviceProject.pName}">												
												<c:choose>
													<c:when test="${empty weChatOrder.serviceCostId == '-2'}">
														<b>划价</b>
													</c:when>
													<c:when test="${empty weChatOrder.serviceCostId == '-3'}">
														<b>消耗品</b>
													</c:when>
												</c:choose>
												</p>
											</c:when>
											<c:otherwise>
												<b>${weChatOrder.serviceProject.pName}</b></p>
											</c:otherwise>
										</c:choose>
									</p>
								</div>
								<div class="weui-cell">
									<div class="weui-cell__bd">
										<p>医生</p>
									</div>
									<p class="weui-cell__ft">${weChatOrder.staff.staffName}</p>
								</div>
								<div class="weui-cell">
									<div class="weui-cell__bd">
										<p>付款确认码</p>
									</div>
									<c:choose>
										<c:when test="${empty weChatOrder.appoint.appointCode}">
											<p>此订单没有付款确认码</p>
										</c:when>
										<c:when test="${not empty weChatOrder.appoint.appointCode}">
											<p class="weui-cell__ft">
												${weChatOrder.appoint.appointCode}
											</p>
										</c:when>
									</c:choose>									
								</div>
								<div class="weui-cell">
									<div class="weui-cell__bd">
										<p>订单创建时间</p>
									</div>
									<p class="weui-cell__ft"><b>${weChatOrder.createTime}</b></p>
								</div>
								<div class="weui-cell">
									<div class="weui-cell__bd">
										<p>预约时间</p>
									</div>
									<p class="weui-cell__ft">
									<c:choose>
										<c:when test="${empty weChatOrder.appoint.appointTime}">
											<b>预约时间未确定</b></p>
										</c:when>
										<c:otherwise>
											<b>${weChatOrder.appoint.appointTime}</b></p>
										</c:otherwise>
									</c:choose>
								</div>						
								<div class="weui-cell">
									<div class="weui-cell__bd">
										<p>总价</p>
									</div>
									<p class="weui-cell__ft">${weChatOrder.payMoney}元</p>
								</div>
								<!-- 打补丁,由于增加划价以及消耗品 -->
								<c:choose>
									<c:when test="${empty weChatOrder.serviceCostId == '-2'}">
										<a class="weui-cell weui-cell_access"
											href="weChatOrder/showOrderDetailsPatch/${weChatOrder.orderId}">
											<div class="weui-cell__bd">
												<p>查看详情</p>
											</div>
											<div class="weui-cell__ft"></div> 
										</a>
									</c:when>
									<c:when test="${empty weChatOrder.serviceCostId == '-3'}">
										<a class="weui-cell weui-cell_access"
											href="weChatOrder/showOrderDetailsPatch/${weChatOrder.orderId}">
											<div class="weui-cell__bd">
												<p>查看详情</p>
											</div>
											<div class="weui-cell__ft"></div> 
										</a>
									</c:when>
									<c:otherwise>
										<a class="weui-cell weui-cell_access"
											href="weChatOrder/showOrderDetails/${weChatOrder.orderId}">
											<div class="weui-cell__bd">
												<p>查看详情</p>
											</div>
											<div class="weui-cell__ft"></div> 
										</a>
									</c:otherwise>
								</c:choose>
							</div>
						</div>
						<c:choose>
							<c:when test="${weChatOrder.orderStatus == 4}">
								<a href="weChatOrder/navigateCommentPage/${weChatOrder.orderId}"
									class="weui-btn weui-btn_mini weui-btn_warn"
									style="float:right;margin-right:25px;margin-bottom:10px"> 待评价 </a>
							</c:when>
							<c:when test="${weChatOrder.orderStatus == 6}">
								<a href="javascript:;"
									class="weui-btn weui-btn_mini weui-btn_warn"
									style="float:right;margin-right:25px;margin-bottom:10px"> 已取消 </a>
							</c:when>
							<c:otherwise>
								<a href="javascript:;"
									class="weui-btn weui-btn_mini weui-btn_primary"
									style="float:right;margin-right:25px;margin-bottom:10px"> 已完成 </a>
							</c:otherwise>
						</c:choose>
					</div>
				</c:forEach>
			</div>
		</c:otherwise>
	</c:choose>
</body>
</html>