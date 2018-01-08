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
<meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=0">
<title>订单详情</title>
<link rel="stylesheet" href="static/css/wechat/weui.min.css" />
<script type="text/javascript" src="static/js/jquery-1.7.2.js"></script>
</head>
<body>
	<div class="page preview js_show">
		<div class="page__bd">
			<div class="weui-form-preview">
				<div class="weui-form-preview__hd">
					<div class="weui-form-preview__item">
						<label class="weui-form-preview__label">实付金额</label>
						<!-- 需要暂时修改这块,支付明细与订单时多对一的关系 --> 
						<em class="weui-form-preview__value">¥${payMoney}</em>
					</div>
				</div>
				
				<div class="weui-form-preview__bd">
					<div class="weui-form-preview__item">
						<label class="weui-form-preview__label">订单编号</label> <span
							class="weui-form-preview__value">${weChatOrder.orderId}</span>
					</div>
					<div class="weui-form-preview__item">
						<label class="weui-form-preview__label">门店</label> <span
							class="weui-form-preview__value">${weChatOrder.store.storeName}</span>
					</div>
					<div class="weui-form-preview__item">
						<label class="weui-form-preview__label">服务项目</label> <span
							class="weui-form-preview__value">
								<c:choose>
									<c:when test="${weChatOrder.serviceCost.serviceCostId == -2}">
										划价
									</c:when>
									<c:when test="${weChatOrder.serviceCost.serviceCostId == -3}">
										消耗品
									</c:when>
								</c:choose>
							</span>
					</div>					
					<div class="weui-form-preview__item">
						<label class="weui-form-preview__label">医生</label> <span
							class="weui-form-preview__value">${weChatOrder.staff.staffName}</span>
					</div>
					<div class="weui-form-preview__item">
						<label class="weui-form-preview__label">付款确认码</label> <span
							class="weui-form-preview__value">该订单没有付款确认码</span>
					</div>
					<div class="weui-form-preview__item">
						<label class="weui-form-preview__label">预约时间</label> 
						<span
							class="weui-form-preview__value">
							<c:choose>
								<c:when test="${empty weChatOrder.appoint.appointTime}">
									<b>预约时间未确定</b></p>
								</c:when>
								<c:otherwise>
									${weChatOrder.appoint.appointTime}</p>
								</c:otherwise>
							</c:choose>
						</span>
					</div>
					<div class="weui-form-preview__item">
						<label class="weui-form-preview__label">支付时间</label> <span
							class="weui-form-preview__value">${payTime}</span>
					</div>
					<div class="weui-form-preview__item">
						<label class="weui-form-preview__label">支付方式</label> 
						<span class="weui-form-preview__value"> 
							<!-- 
								0:微信支付	 	1:支付宝 		2:储值卡
								3:现金 		4:银行卡 		5:预存支付 
								6:储值卡返点支付
							 --> 
							<c:choose>
								<c:when test="${payMethod == 0}">
									微信
								</c:when>
								<c:when test="${payMethod == 1}">
									支付宝
								</c:when>
								<c:when test="${payMethod == 2}">
									储值卡
								</c:when>
								<c:when test="${payMethod == 3}">
									现金
								</c:when>
								<c:when test="${payMethod == 4}">
									银行卡
								</c:when>
								<c:when test="${payMethod == 5}">
									钱包
								</c:when>
								<c:when test="${payMethod == 6}">
									储值卡
								</c:when>
								<c:otherwise>
									其他方式
								</c:otherwise>
							</c:choose>
						</span>
					</div>
					<div class="weui-form-preview__item">
						<label class="weui-form-preview__label">应付金额</label> 
						<span class="weui-form-preview__value">¥${weChatOrder.orderMoney}</span>
					</div>
					<div class="weui-form-preview__item">
						<label class="weui-form-preview__label">折扣</label> <span
							class="weui-form-preview__value">${weChatOrder.proportion}</span>
					</div>
					<div class="weui-form-preview__item">
						<label class="weui-form-preview__label">优惠券</label> 
						<span class="weui-form-preview__value">
							<c:choose>
								<c:when test="${empty discountAmount}">
									未使用优惠券
								</c:when>
								<c:otherwise>
									减免¥${discountAmount}
								</c:otherwise>
							</c:choose>
						</span>
					</div>
					<!--  
					<div class="weui-form-preview__item">
						<label class="weui-form-preview__label">返积分值</label> <span
							class="weui-form-preview__value"><b>暂时不在页面上展示积分值</b></span>
					</div>
					-->
				</div>
				<%-- 
				<div class="weui-btn-area">
					<!-- 
						根据订单的状态判断跳转的页面
						(1) 如果是"我的订单"查询"订单详情",点击"确定"之后,返回"我的订单"
						(2) 如果是"我的预约"查询"订单详情",点击"确定"之后,返回"我的预约"
					 -->
					<c:choose>
						<c:when test="${weChatOrder.orderStatus == 2}">
							<a href="weChatOrder/listOrders/2" class="weui-btn weui-btn_primary">确定</a>
						</c:when>
						<c:when test="${weChatOrder.orderStatus == 3}">
							<a href="weChatOrder/listOrders/2" class="weui-btn weui-btn_primary">确定</a>
						</c:when>
						<c:otherwise>
							<a href="weChatOrder/listOrders/1" class="weui-btn weui-btn_primary">确定</a>
						</c:otherwise>
					</c:choose>
				</div>
				--%>
				
				<!-- 退款详情 -->
				<c:choose>
					<c:when test="${empty weChatRefund}"></c:when>
					<c:otherwise>
						<div class="weui-cells__title" style="background-color:#f8f8f8;height:25px;padding-top:4px">退款详情</div>
						<div class="weui-form-preview__bd">
							<div class="weui-form-preview__item">
								<label class="weui-form-preview__label">退储值卡余额</label> 
								<span class="weui-form-preview__value">¥${storeCardMoney}</span>
							</div>
							<div class="weui-form-preview__item">
								<label class="weui-form-preview__label">退储值卡点数</label> 
								<span class="weui-form-preview__value">¥${storeCardPoint}</span>
							</div>
							<div class="weui-form-preview__item">
								<label class="weui-form-preview__label">退钱包余额</label> 
								<span class="weui-form-preview__value">¥${refundMoney}</span>
							</div>
							<div class="weui-form-preview__item">
								<label class="weui-form-preview__label">退款时间</label> 
								<span class="weui-form-preview__value">${weChatRefund.TIME}</span>
							</div>
						</div>
					</c:otherwise>
				</c:choose>
				
			</div>
		</div>
	</div>
</body>
</html>