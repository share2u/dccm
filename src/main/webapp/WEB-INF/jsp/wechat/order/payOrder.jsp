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
<title>确定支付</title>
<link rel="stylesheet" href="static/css/wechat/weui.min.css" />
<script type="text/javascript" src="static/js/jquery-1.7.2.js"></script>
<style type="text/css">
	.tanchuang_wrap {
		background: #000;
		filter: alpha(opacity = 50); /* IE的透明度 */
		opacity: 0.9; /* 透明度 */
		display: none;
		position: absolute;
		top: 0px;
		left: 0px;
		width: 100%;
		height: 100%;
		z-index: 100; /* 此处的图层要大于页面 */
		display: none;
	}

	.tanchuang_neirong {
		width: 100%;
		height: 88px;
		border: solid 1px #f7dd8c;
		background-color: #FFF;
		position: absolute;
		left: 0;
		bottom: 240px;
	}
</style>
<script type="text/javascript">
	// 当radio改变的时候,执行change(),修改 
	function change(e) {
		var radioValue = e.attributes["value"].value;
		$("#payMethod").val(parseInt(radioValue));
	}

	function payOrder() {
		// 订单编号
		var orderId = $("#orderId").val();
		// 用户拥有的使用的优惠券编号
		var discountId = $("#discountId").val();
		// 实际支付的金额
		var prePayMoney = trim($("#prePayMoney").text());
		// 支付方式
		var payMethod = $("#payMethod").val();

		if (payMethod == "0") {
			$("#payOrder").attr("onclick", "test()");
			// 微信支付
			$.ajax({
				url : 'weChatOrder/payOrder',
				type : 'POST',
				data : {
					"orderId" : orderId,
					"discountId" : discountId,
					"prePayMoney" : prePayMoney,
					"payMethod" : payMethod
				},
				dataType : 'json',
				contentType : "application/x-www-form-urlencoded;charset=utf-8",
				success : function(result) {
					if (typeof WeixinJSBridge == "undefined") {
						alert("WeixinJSBridge = undefined");
						if (document.addEventListener) {
							document.addEventListener(
									'WeixinJSBridgeReady',
									onBridgeReady, false);
						} else if (document.attachEvent) {
							document.attachEvent('WeixinJSBridgeReady',
									onBridgeReady);
							document.attachEvent(
									'onWeixinJSBridgeReady',
									onBridgeReady);
						}
					} else {
						// 根据后台传过来的参数信息,判断当前是否可以调起微信支付
						if (result.errorCode || result.errorDes) {
							alert(result.errorCode + "..."
									+ result.errorDes);
//							alert("支付出现异常,请关闭当前页面重新支付或联系客服");
							window.location.href = "${pageContext.request.contextPath}/weChatUser/pageNavigate/userCenter";
							return;
						}
						onBridgeReady(result);
					}
				}
			});
		} else if (payMethod == "2") {
			$("#payOrder").attr("onclick", "payOrder()");
			// 储值卡支付
			displayDiv("aaaa");
		}
	}

	function test() {
		showToast("toast2");
	}

	function showToast(toastType) {
		if ($("#" + toastType).css('display') != 'none')
			return;
		$("#" + toastType).fadeIn(100);
		setTimeout(function() {
			$("#" + toastType).fadeOut(100);
		}, 2000);
	}

	// 微信支付
	function onBridgeReady(result) {
		WeixinJSBridge
				.invoke(
						'getBrandWCPayRequest',
						{
							"appId" : result.appId,
							"timeStamp" : result.timeStamp,
							"nonceStr" : result.nonceStr,
							"package" : "prepay_id=" + result.payPackage,
							"signType" : result.signType,
							"paySign" : result.paySign
						},
						function(res) {
							// 用户支付成功或者取消支付
							if (res.err_msg == "get_brand_wcpay_request:ok") {
								showToast("toast");
								// 进入"我的预约"查看"已预约"的订单
								window.location.href = "${pageContext.request.contextPath}/weChatOrder/listOrders/2";
							} else {
								alert(res.err_code + res.err_desc + res.err_msg);
								/*
									用户主动取消支付,可以暂时不处理,并且微信不会发通知到notifyUrl;可分为两种情况
									(1)在当前页面再次支付,此时不会报"商户订单号重复"的错误
									(2)用户关闭当前页面,从"用户中心"进入"待支付"页面支付订单,此时不会报"商户订单号重复"的错误
								 */
								$("#payOrder").attr("onclick", "payOrder()");
							}
						});
	}

	// 去空格
	function trim(str) {
		return str.replace(/(^\s*)|(\s*$)/g, "");
	}
	
	function closeDiv(divId) {
		document.getElementById(divId).style.display = 'none';
		$("#payOrder").attr("onclick", "payOrder()");
	}
	
	function displayDiv(divId) {
		// 动态获取页面的宽度与高度,设置遮罩的范围
		var o1 = document.getElementById(divId);
		o1.style.width = document.documentElement.scrollWidth + "px";
		o1.style.height = document.documentElement.scrollHeight + "px";
		o1.style.display = 'block';
	}
	
	// 储值卡支付
	function storedCardPay(){
		// 储值卡密码
		var password = trim($("#password").val());
		if(isNumber(password) == false || password.length != 6){
			alert("您输入的密码包含字母或者特殊符号,请重新输入六位数字密码");
			return;
		}
		
		$("#storedCardPay").attr("onclick", "test()");
		
		// 订单编号
		var orderId = $("#orderId").val();
		// 用户拥有的使用的优惠券编号
		var discountId = $("#discountId").val();
		// 实际支付的金额
		var prePayMoney = trim($("#prePayMoney").text());
		// 支付方式
		var payMethod = $("#payMethod").val();
		
		$.ajax({
				url : 'weChatOrder/storedCardPayOrder',
				type : 'POST',
				data : {
					"orderId" : orderId,
					"discountId" : discountId,
					"prePayMoney" : prePayMoney,
					"payMethod" : payMethod,
					"password" : password
				},
				dataType : 'json',
				contentType : "application/x-www-form-urlencoded;charset=utf-8",
				success : function(result) {
					// 根据后台传过来的参数信息,判断当前是否可以调起微信支付
					if (result.errorCode || result.errorDes) {
						if(result.errorCode == "success"){
							alert(result.errorCode + ":" + result.errorDes);
							showToast("toast");
							window.location.href = "${pageContext.request.contextPath}/weChatOrder/listOrders/2";
						}else if(result.errorCode == "fail"){
							alert(result.errorCode + ":" + result.errorDes);
							window.location.href = "${pageContext.request.contextPath}/weChatOrder/listOrders/3";
						}
					}else{
						
					}
				}
			});
	}
	
	// 判断是否是数字组成
	function isNumber(str){
		var reg = /^\d+$/;
		return reg.test(str);
	}
</script>
</head>
<body>
	<div class="tanchuang_wrap" id="aaaa">
		<div class="tanchuang_neirong">
			<div class="weui-cell" style="background-color:#fff;margin-bottom:0;padding-bottom:5px">
				<div class="weui-cell weui-cell_vcode" style="width:100%">
					<div class="weui-cell__hd" style="width:80px">
						<label class="weui-label init_size">输入密码:</label>
					</div>
					<div class="weui-cell__bd init_size">
						<input id="password" name="password"
							class="weui-input" placeholder="请输入六位数字密码" pattern="[0-9]*"
							type="password" style="ime-mode:Disabled">
					</div>
					<div class="weui-btn-area" style="margin-top:0;margin-bottom:0;height:30px">
						<a href="weChatUser/forgetStoredPassword" style="margin-buttom:5px;float:right;margin-right:6px">
							<font style="font-size:13px">忘记密码？</font>
						</a>
					</div>
				</div>
			</div>
			<div class="weui-btn-area" style="margin-top:0;margin-bottom:0;height:40px">
				<a id="storedCardPay" href="javascript:;" class="weui-btn weui-btn_mini weui-btn_primary" 
					style="margin-top:5px;margin-left:60px" onclick="storedCardPay();">确认</a>
				<a href="javascript:;" class="weui-btn weui-btn_mini weui-btn_warn" 
					style="margin-top:5px;float:right;margin-right:60px" onclick="closeDiv('aaaa');">取消</a>
			</div>
		</div>
	</div>

	<div class="page preview js_show">
		<div class="page__bd">
			<div class="weui-form-preview">
				<c:forEach items="${weChatOrders}" var="weChatOrder"
					varStatus="status">
					<div class="weui-form-preview__hd">
						<div class="weui-form-preview__item">
							<label class="weui-form-preview__label">待支付</label>
							<!-- 乘上折扣之后的价格 -->							
							<font style="color:#999">¥${weChatOrder.payMoney}</font>
						</div>
					</div>
					<div class="weui-form-preview__bd" style="height:230px">
						<div class="weui-form-preview__item">
							<label class="weui-form-preview__label">门店</label> <span
								class="weui-form-preview__value">${weChatOrder.store.storeName}</span>
						</div>
						<div class="weui-form-preview__item">
							<label class="weui-form-preview__label">服务项目</label> <span
								class="weui-form-preview__value">${weChatOrder.serviceProject.pName}</span>
						</div>
						<div class="weui-form-preview__item">
							<label class="weui-form-preview__label">订单金额</label> <span
								class="weui-form-preview__value">¥${weChatOrder.orderMoney}</span>
						</div>
						<div class="weui-form-preview__item">
							<label class="weui-form-preview__label">医生</label> <span
								class="weui-form-preview__value">${weChatOrder.staff.staffName}</span>
						</div>
						<div class="weui-form-preview__item">
							<label class="weui-form-preview__label">初诊复诊</label> <span
								class="weui-form-preview__value"> <c:choose>
									<c:when test="${weChatOrder.serviceCost.isFirst == 0}">
									初诊
								</c:when>
									<c:otherwise>
									复诊
								</c:otherwise>
								</c:choose> </span>
						</div>
						<div class="weui-form-preview__item">
							<label class="weui-form-preview__label">预约时间</label> <span
								class="weui-form-preview__value"> <c:choose>
									<c:when test="${empty weChatOrder.recommendTime}">
									预约时间未确定
								</c:when>
									<c:otherwise>
										<b>${weChatOrder.recommendTime}</b>
									</c:otherwise>
								</c:choose> </span>
						</div>
						<div class="weui-form-preview__item">
							<label class="weui-form-preview__label">折扣</label> <span
								class="weui-form-preview__value">${weChatOrder.proportion}</span>
						</div>
						<div class="weui-form-preview__item">
							<label class="weui-form-preview__label">优惠券</label> 
							<span class="weui-form-preview__value">
								<c:choose>
									<c:when test="${empty weChatOrder.discountId || weChatOrder.discountId == '0'}">已减 ¥0.00</c:when>
									<c:otherwise>
										已减 ¥${weChatOrder.discountId}
									</c:otherwise>
								</c:choose>
							</span>
						</div>
					</div>

					<c:choose>
						<c:when test="${status.last}"></c:when>
						<c:otherwise>
							<div class="weui-cells__title"
								style="background-color:#f8f8f8;height:20px"></div>
						</c:otherwise>
					</c:choose>
				</c:forEach>
				
				<div class="weui-cells__title" style="background-color:#f8f8f8;height:20px"></div>
				
				<%-- 
				<div class="weui-cells" style="margin-top:10px;font-size:14px">
					<!-- 从优惠券选择界面返回订单支付界面 -->
					<a class="weui-cell weui-cell_access"
						href="weChatUserDiscount
						/listUserValidAndTheProjectDiscounts/${projectIds},${orderIds}">
						<div class="weui-cell__bd" style="color:#999">
							<p>优惠券</p>
						</div>
						<div class="weui-cell__ft">
							<c:choose>
								<c:when test="${empty weChatUserDiscount}"></c:when>
								<c:otherwise>
									已减 ¥<span id="discountValue" name="discountValue">${weChatUserDiscount.discount.discountAmount}</span>
									<input type="text" id="discountId" name="discountId"
										style="display:none" value="${weChatUserDiscount.id}">
								</c:otherwise>
							</c:choose>
						</div> 
					</a>
				</div>
				--%>
				
				<div class="weui-form-preview__hd">
					<!-- 订单编号 -->
					<input id="orderId" type="text" value="${orderIds}" style="display:none;">					
					<div class="weui-form-preview__item">
						<label class="weui-form-preview__label">总计</label>						 						
						<font style="color:#BF172E;">
							<%-- 
							<c:choose>
								<c:when test="${empty weChatUserDiscount}">
									¥<span id="prePayMoney" name="prePayMoney">${totalMoney}</span>
								</c:when>
								<c:otherwise>
									¥<span id="prePayMoney" name="prePayMoney">
									<c:if test="${totalMoney - weChatUserDiscount.discount.discountAmount > 0}">
										${totalMoney - weChatUserDiscount.discount.discountAmount}
									</c:if>
									<c:if test="${totalMoney - weChatUserDiscount.discount.discountAmount <= 0}">
										0.01
									</c:if>	
									</span>
								</c:otherwise>
							</c:choose>
							--%>
							<c:choose>
								<c:when test="${totalMoney == '0.00'}">
									¥<span id="prePayMoney" name="prePayMoney">0.01</span>
								</c:when>
								<c:otherwise>
									¥<span id="prePayMoney" name="prePayMoney">${totalMoney}</span>
								</c:otherwise>
							</c:choose>
						</font>
					</div>
				</div>

				<div class="weui-cells__title" style="background-color:#f8f8f8;height:20px"></div>

				<div class="weui-cells__title" style="background-color:#fff;"></div>
				<div style="height:140px;margin-top: 10px;">
					<div class="weui-cells__title">支付方式</div>
					<input type="text" id="payMethod" style="display:none" value="0">
					<!--
						支付方式 
							0：微信支付 	1：支付宝 		2：储值卡 
							3：现金 		4：银行卡 		5:预存支付
					 -->
					<div class="weui-cells weui-cells_radio">
						<label class="weui-cell weui-check__label" for="y12">
							<div class="weui-cell__hd">
								<img alt="" src="static/images/wechat/weixinpay.png"
									style="width:20px;margin-right:5px;display:block">
							</div>
							<div class="weui-cell__bd">
								<p>微信支付</p>
							</div>
							<div class="weui-cell__ft">
								<input id="y12" checked="checked" class="weui-check"
									name="radio1" type="radio" onchange="change(this)" value="0">
								<span class="weui-icon-checked"></span>
							</div> </label>
						<!-- 
							暂时屏蔽储值卡支付与支付宝支付
						<label class="weui-cell weui-check__label" for="y13">
							<div class="weui-cell__hd">
								<img alt="" src="static/images/wechat/zhifubaopay.png"
									style="width:20px;margin-right:5px;display:block">
							</div>
							<div class="weui-cell__bd">
								<p>支付宝支付</p>
							</div>
							<div class="weui-cell__ft">
								<input id="y13" class="weui-check" name="radio1" 
									type="radio" onchange="change(this)" value="1">
								<span class="weui-icon-checked"></span>
							</div> 
						</label>
						-->	
						<c:choose>
							<c:when test="${empty weChatUserStoredCard}"></c:when>
							<c:otherwise>
								<label class="weui-cell weui-check__label" for="y11">
									<div class="weui-cell__hd">
										<img alt="" src="static/images/wechat/chuzhikapay.png"
											style="width:20px;margin-right:5px;display:block">
									</div>
									<div class="weui-cell__bd">
										<p>储值卡支付</p>
									</div>
									<div class="weui-cell__ft">
										<input id="y11" class="weui-check" name="radio1" 
											type="radio" onchange="change(this)" value="2"> 
											<span class="weui-icon-checked"></span>
									</div> 
								</label>
							</c:otherwise>
						</c:choose>					 			
					</div>
				</div>
				<div class="weui-btn-area">
					<a href="javascript:;" id="payOrder"
						class="weui-btn weui-btn_primary" onclick="payOrder();">支付</a>
				</div>

				<div id="loadmore" style="position:absolute;left:-100px;">
					<img src="static/images/loadingi.gif"></img>
				</div>
				<div id="toast" style=" display: none;">
					<div class="weui-mask_transparent"></div>
					<div class="weui-toast">
						<i class="weui-icon-success-no-circle weui-icon_toast"
							id="toastType"></i>
						<p class="weui-toast__content" id="toastMsg">已完成</p>
					</div>
				</div>
				<div id="toast1" style=" display: none;">
					<div class="weui-mask_transparent"></div>
					<div class="weui-toast">
						<i class="weui-icon-cancel weui-icon_toast" id="toastType1"></i>
						<p class="weui-toast__content" id="toastMsg1">已取消</p>
					</div>
				</div>
				<div id="toast2" style=" display: none;">
					<div class="weui-mask_transparent"></div>
					<div class="weui-toast">
						<i class="weui-icon-waiting-circle weui-icon_toast"
							id="toastType2"></i>
						<p class="weui-toast__content" id="toastMsg2">订单提交中，请耐心等待</p>
					</div>
				</div>
				<div id="toast3" style=" display: none;">
					<div class="weui-mask_transparent"></div>
					<div class="weui-toast">
						<i class="weui-icon-warn weui-icon_toast" id="toastType3"></i>
						<p class="weui-toast__content" id="toastMsg3">支付失败</p>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>