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
<title>待支付</title>
<link rel="stylesheet" href="static/css/wechat/weui.min.css" />
<script type="text/javascript" src="static/js/jquery-1.7.2.js"></script>
<style type="text/css">
	.init_size {
		font-size: 16px;
		background-color: #f8f8f8;
		padding-top: 0;
		padding-left: 5px
	}
	
	.icon-box__desc {
		color: #888;
		font-size: 14px;
		margin-top: 80px;
	}
</style>
<script type="text/javascript">
	// 点击"支付"获取勾选的订单信息
	function collectOrderId() {
		// 当"支付"按钮置灰时,不能跳转到支付页面
		var buttonStatus = $("#pay").attr("class");
		if (buttonStatus == "weui-btn weui-btn_disabled weui-btn_primary") {
			return;
		}
		var orderId = '';
		// 遍历所有被选中的checkbox
		$("input[type='checkbox']").each(function() {
			if ($(this).is(':checked')) {
				orderId += $(this).val();
				orderId += ',';
			}
		});
		var length = orderId.length;
		orderId = orderId.substring(0, length - 1);
		var url = 'weChatOrder/navigatePayPage/' + orderId;
		$("#pay").attr('href', url);
	}

	// 修改支付按钮的样式,在用户没有选择任何订单的情况下,"支付按钮"置灰
	function changeColor(e) {
		if ($("input[type='checkbox']").is(':checked')) {
			$("#pay").attr("class", 'weui-btn weui-btn_primary');
		} else {
			$("#pay").attr("class",
					'weui-btn weui-btn_disabled weui-btn_primary');
		}
	}
</script>
</head>
<body>
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
			<div class="weui-cells weui-cells_checkbox">
				<c:forEach items="${intercepter}" var="weChatOrder"
					varStatus="status">
					<label class="weui-cell weui-check__label init_size"
						for="s${status.count}">
						<div class="weui-cell__hd">
							<c:choose>
								<c:when test="${weChatOrder.orderStatus == 1}">
									<input class="weui-check" name="checkbox${status.count}"
										id="s${status.count}" type="checkbox"
										onchange="changeColor(this)" value="${weChatOrder.orderId}"
										disabled="disabled">
								</c:when>
								<c:otherwise>
									<input class="weui-check" name="checkbox${status.count}"
										id="s${status.count}" type="checkbox"
										onchange="changeColor(this)" value="${weChatOrder.orderId}">
								</c:otherwise>
							</c:choose>
							<i class="weui-icon-checked"></i>
						</div>
						<div class="weui-cell__bd">
							<div class="weui-cells init_size"
								style="width: 100%; height: 35px; margin-bottom: 0px; margin-top: 5px; padding-bottom: 5px;">
								<!-- 跳转 门店详情页面 -->
								<a class="weui-cell weui-cell_access"
									href="weChatStore/getStoreDetailByStoreId/${weChatOrder.store.storeId}">
									<div class="weui-cell__bd">
										<p>
											<b>${weChatOrder.store.storeName}</b>
										</p>
									</div>
									<div class="weui-cell__ft"></div> </a>
							</div>
							<div style="height:260px;">
								<div class="weui-media-box weui-media-box_appmsg"
									style="padding-bottom:10px">
									<div style="width:100%">
										<div class="weui-cell">
											<div class="weui-cell__bd">
												<p>项目</p>
											</div>
											<p class="weui-cell__ft">${weChatOrder.serviceProject.pName}</p>
										</div>
										<div class="weui-cell">
											<div class="weui-cell__bd">
												<p>医生</p>
											</div>
											<p class="weui-cell__ft">${weChatOrder.staff.staffName}</p>
										</div>
										<div class="weui-cell">
											<div class="weui-cell__bd">
												<p>预约时间</p>
											</div>
											<p class="weui-cell__ft">
												<c:choose>
													<c:when test="${empty weChatOrder.recommendTime}">
													预约时间未确定
												</c:when>
													<c:otherwise>
														<b>${weChatOrder.recommendTime}</b>
													</c:otherwise>
												</c:choose>
											</p>
										</div>
										<div class="weui-cell">
											<div class="weui-cell__bd">
												<p>折扣</p>
											</div>
											<p class="weui-cell__ft">
												<b>${weChatOrder.proportion}</b>
											</p>
										</div>
										<div class="weui-cell">
											<div class="weui-cell__bd">
												<p>总价</p>
											</div>
											<p class="weui-cell__ft">¥${weChatOrder.payMoney}</p>
										</div>
									</div>
								</div>
								<c:choose>
									<c:when test="${weChatOrder.orderStatus == 0}">
										<!-- 跳转 支付页面 -->
										<a href="javascript:;"
											class="weui-btn weui-btn_mini weui-btn_warn"
											style="float:right;margin-right:25px;margin-bottom:10px">
											待支付 </a>
									</c:when>
									<c:otherwise>
										<a href="javascript:;"
											class="weui-btn weui-btn_mini weui-btn_default"
											style="float:right;margin-right:25px;margin-bottom:10px">
											已关闭 </a>
									</c:otherwise>
								</c:choose>
							</div>
						</div> </label>
					<c:choose>
						<c:when test="${status.last}"></c:when>
						<c:otherwise>
							<div class="weui-cells__title" style="height:10px;margin-top:0"></div>
						</c:otherwise>
					</c:choose>
				</c:forEach>
			</div>
			<div class="weui-btn-area">
				<a href="javascript:;" id="pay"
					class="weui-btn weui-btn_disabled weui-btn_primary"
					onclick="collectOrderId();">支付</a>
			</div>
		</c:otherwise>
	</c:choose>
</body>
</html>