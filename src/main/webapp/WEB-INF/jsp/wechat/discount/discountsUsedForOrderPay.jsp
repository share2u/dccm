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
<title>我的优惠券</title>
<link rel="stylesheet" href="static/css/wechat/weui.min.css"/>
<script type="text/javascript" src="static/js/jquery-1.7.2.js"></script>
<style type="text/css">
	body{
		background-color: #f8f8f8;
		font-family:-apple-system-font,Helvetica Neue,Helvetica,sans-serif;
		font-size:13px;
	}
</style>
<script type="text/javascript">
	// 当radio改变的时候,执行change(),修改
	function change(e){
		var discountValue = e.attributes["discountValue"].value;
		$("#discountValue").text(parseInt(discountValue) + ".00");
		$("#discountId"). val(e.attributes["value"].value);
	}
</script>
</head>
<body ontouchstart="">
	<div style="background-color:#BF172E;height:50px;margin:0px;padding:0px;width:100%; position:fixed; top:0;z-index: 9999;">
  		<div style="padding:0.07rem 0.15rem;">
  			<div style="text-align: center;height: 50px; line-height:50px; color: #F3FFFF;font-size:16px; ">大诚中医</div>
  		</div>
  	</div>
  	<div style="margin-top:50px;"></div> 
  	<div class="weui-cells__title" style="background-color:#ffffff;height:30px;padding-top:12px;">
  		减 ¥<span id="discountValue" name="discountValue">0.00</span></div>
		<form action="weChatOrder/navigatePayPage/selectDiscount" method="post">
		<input type="text" name="orderId" value="${orderId}" style="display:none">
		<input type="text" id="discountId" name="discountId" style="display:none">
		<div class="weui-cells weui-cells_radio" style="font-size:14px">
			<c:forEach items="${weChatUserDiscounts}" var="weChatUserDiscount"
				varStatus="statusParent">
				<label class="weui-cell weui-check__label"
					for="y${statusParent.count}">
					<div class="weui-cell__hd"></div>
					<div class="weui-cell__bd">
						<div class="weui-media-box weui-media-box_appmsg">
							<div class="weui-media-box__bd">
								<h4 class="weui-media-box__title">${weChatUserDiscount.discount.discountName}</h4>
								<div style="height:3px"></div>
								<p class="weui-media-box__desc">
									¥${weChatUserDiscount.discount.discountAmount}
								</p>
								<div style="height:3px"></div>
								<p class="weui-media-box__desc">
									使用条件:仅
									<c:forEach
										items="${weChatUserDiscount.discount.serviceProjects}"
										var="serviceProject" varStatus="status">
										<b>${serviceProject.pName}</b>
										<c:choose>
											<c:when test="${status.last}"></c:when>
											<c:otherwise>、</c:otherwise>
										</c:choose>
									</c:forEach>
									服务项目可用
								</p>
								<div style="height:3px"></div>
								<p class="weui-media-box__desc" style="display:inline;">
									<font style="color:#888;">
										使用时间:${weChatUserDiscount.discount.startTime} 到 ${weChatUserDiscount.discount.endTime}
									</font>
								</p>
							</div>
						</div>
					</div>
					<div class="weui-cell__ft">
						<!-- 记录用户选择的优惠券 -->
						<input id="y${statusParent.count}" class="weui-check"
							name="radio1" type="radio" value="${weChatUserDiscount.id}" 
							onchange="change(this)" discountValue="${weChatUserDiscount.discount.discountAmount}">
						<span class="weui-icon-checked"></span>
					</div> 
				</label>
			</c:forEach>
		</div>
		<div class="weui-btn-area">								
			<input type="submit" class="weui-btn weui-btn_primary" value="选择">		
		</div>
	</form>	
</body>
</html>