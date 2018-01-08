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
<title>用户中心</title>
<link rel="stylesheet" href="static/css/wechat/weui.min.css"/>
<script type="text/javascript" src="static/js/jquery-1.7.2.js"></script>
<style type="text/css">
	.left {
		float: left;
	}
	
	body {
		background-color: #f8f8f8;
	}
	
	.weui-badge {
		display: inline-block;
		padding: .15em .4em;
		min-width: 8px;
		border-radius: 18px;
		background-color: #F4A460;
		color: #FFFFFF;
		line-height: 1.2;
		text-align: center;
		font-size: 12px;
		vertical-align: middle;
	}
</style>
</head>
<body ontouchstart="">
	<div style="background-color:#BF172E;height:50px;margin:0px;padding:0px;width:100%; position:fixed; top:0;z-index: 9999;">
  		<div style="padding:0.07rem 0.15rem;">
  			<div style="text-align: center;height: 50px;  line-height:50px; color: #F3FFFF; ">大诚中医</div>
  		</div>
  	</div>
  	<!-- margin的顺序是上右下左 -->
  	<div style="margin-top:50px;"></div>
	<div>
		<img alt="" src="static/images/wechat/tcm.jpg" style="width: 100%;height: 160px;">
	</div>
	
	<div class="weui-grids">
        <a href="weChatOrder/listOrders/2" class="weui-grid" style="padding-bottom:0">
            <div class="weui-grid__icon" style="position:relative;">
                <img src="static/images/wechat/appoint.png" alt="" style="border-radius:10px;">
                <!-- 已预约订单数量 -->
				<c:choose>
					<c:when test="${empty appointOrderAmount || appointOrderAmount == 0}"></c:when>
					<c:otherwise>
						<span class="weui-badge" style="position: absolute;top: -2px;right: -13px;">
							${appointOrderAmount}
						</span>
					</c:otherwise>
				</c:choose>
            </div>
            <p class="weui-grid__label">我的预约</p>
        </a>
        <a href="weChatOrder/listOrders/3" class="weui-grid" style="padding-bottom:0">
            <div class="weui-grid__icon" style="position:relative;">
                <img src="static/images/wechat/prepay.png" alt="" style="border-radius:10px;">
                <!-- 待支付订单数量 -->
                <c:choose>
					<c:when test="${empty prePayOrderAmount || prePayOrderAmount == 0}"></c:when>
					<c:otherwise>
						<span class="weui-badge" style="position: absolute;top: -2px;right: -13px;">
							${prePayOrderAmount}
						</span>
					</c:otherwise>
				</c:choose>
            </div>
            <p class="weui-grid__label">待支付</p>
        </a>
        <a href="weChatOrder/listOrders/1" class="weui-grid" style="padding-bottom:0">
            <div class="weui-grid__icon" style="position:relative;">
                <img src="static/images/wechat/myorder.png" alt="" style="border-radius:10px;">
            </div>
            <p class="weui-grid__label">我的订单</p>
        </a>
        <a href="weChatCard/cardCenter" class="weui-grid">
            <div class="weui-grid__icon">
                <img src="static/images/wechat/account.png" alt="" style="border-radius:10px;">
            </div>
            <p class="weui-grid__label">账户</p>
        </a>
        <a href="weChatCard/rechargeDetails" class="weui-grid">
            <div class="weui-grid__icon">
                <img src="static/images/wechat/charge.png" alt="" style="border-radius:10px;">
            </div>
            <p class="weui-grid__label">储值卡</p>
        </a>
        <a href="weChatUserDiscount/listUserValidDiscounts" class="weui-grid">
            <div class="weui-grid__icon" style="position:relative;">
                <img src="static/images/wechat/sale.png" alt="" style="border-radius:10px;">
                <!-- 优惠券的个数信息 -->
				<c:choose>
					<c:when test="${empty discountAmount || discountAmount == 0}"></c:when>
					<c:otherwise>
						<span class="weui-badge" style="position: absolute;top: -2px;right: -13px;">
							${discountAmount}
						</span>
					</c:otherwise>
				</c:choose>
            </div>
            <p class="weui-grid__label">优惠券</p>
        </a>
    </div>

	<div class="weui-cells" style="width:100%;height:35px;margin-bottom:0px;margin-top:5px;padding-bottom:5px;">
		<a class="weui-cell weui-cell_access" href="weChatUser/userInfo" style="">
			<div class="weui-cell__bd">
				<p style="font-size:14px">个人资料编辑</p>
			</div>
			<div class="weui-cell__ft"></div>
		</a>
	</div>
	<div class="weui-cells" style="width:100%;height:35px;margin-bottom:0px;margin-top:0;padding-bottom:5px;">
		<a class="weui-cell weui-cell_access" href="weChatUser/pageNavigate/storedCardPassword" style="">
			<div class="weui-cell__bd">
				<p style="font-size:14px">储值卡密码设置</p>
			</div>
			<div class="weui-cell__ft"></div>
		</a>
	</div>
</body>
</html>