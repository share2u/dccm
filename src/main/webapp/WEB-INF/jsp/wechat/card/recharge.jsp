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

<title>充值</title>

<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=no">
<link rel="stylesheet" type="text/css" href="static/css/wechat/main.css" />
<link rel="stylesheet" href="static/css/wechat/weui.min.css"
	type="text/css"></link>
<link rel="stylesheet" type="text/css" href="static/css/wechat/example.css" />
<link rel="stylesheet" href="static/css/wechat/overlay.css"
	type="text/css"></link>
<script type="text/javascript" src="static/js/jquery-1.7.2.js"></script>
<script type="text/javascript" src="static/js/overlay.js"></script>
<script type="text/javascript">
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
	function rechargeNow() {
		$("#rechargeNow").attr("onclick", "test()");
		if (typeof (WeixinJSBridge) == "undefined") {
			if (document.addEventListener) {
				document.addEventListener('WeixinJSBridgeReady', onBridgeReady,
						false);
			} else if (document.attachEvent) {
				document.attachEvent('WeixinJSBridgeReady', onBridgeReady);
				document.attachEvent('onWeixinJSBridgeReady', onBridgeReady);
			}
		} else {
			// 1.将用户信息和选择的内容发给后台拿到统一下单的返回值 //2.调用微信支付js发起支付 //3.发货和通知 
			var url = "${pageContext.request.contextPath}/weChatCard/packageUnifiedOrder";
			var storedCategoryId = $("input[name=radio2]:checked").attr("id");// 储值卡类型编号
			var payMethod = "weChatPay";
			// 支付方式
			$.post(url, {
				storedCategoryId : storedCategoryId,
				payMethod : payMethod
			}, function(result) {
				alert(result.appId);
				if (null != result && result != undefined) {
					onBridgeReady(result);
				} else {
					alert("下单失败");
				}
			});
		}
	

	}

	function onBridgeReady(result) {
		WeixinJSBridge
				.invoke(
						'getBrandWCPayRequest',
						{
							"appId" : result.appId, // 公众号名称，由商户传入
							"timeStamp" : result.timeStamp, // 时间戳，自1970年以来的秒数
							"nonceStr" : result.nonceStr, // 随机串
							"package" : "prepay_id=" + result.payPackage,
							"signType" : result.signType, // 微信签名方式：
							"paySign" : result.paySign
						// 微信签名
						},
						function(res) {
							if (res.err_msg == "get_brand_wcpay_request:ok") {
								showToast("toast");
								alert("储值卡支付密码123456，请前往个人中心，及时修改！")
								window.location.href = "${pageContext.request.contextPath}/weChatCard/cardCenter";

							} else if (res.err_msg == "get_brand_wcpay_request:cancel") {
								showToast("toast1");

							} else if (res.err_msg == "get_brand_wcpay_request:fail") {
								alert(res.err_code + res.err_desc + res.err_msg);
								showToast("toast3");
							} else if (res.err_msg == undefined) {
								alert("系统内部错误，联系管理员");
							}
							$("#rechargeNow").attr("onclick", "rechargeNow()");
						});

	}
	
	/*
		modified by scott
	*/
	function showRemark(e){
		if(trim(e) == ""){
			return;
		}
		alert(trim(e));
	}
	
	// 去空格
	function trim(str) {
		return str.replace(/(^\s*)|(\s*$)/g, "");
	}
	
</script>
</head>

<body>
	<div id="overlay"></div>
	<div
		style="background-color:#BF172E;height:50px;margin:0px;padding:0px;width:100%; position:fixed; top:0;z-index: 9999;">
		<div style="padding:0.07rem 0.15rem;">
			<!--<div style="float: right;height:50px;line-height: 50px;"><a style="color: #F3FFFF;"href="javascript:;">明细</a></div>-->
			<div
				style="text-align: center;height: 50px;  line-height:50px; color: #F3FFFF; ">大诚中医</div>
		</div>
	</div>
	<div style="margin-top:50px;"></div>
	<!--  
	<div class="weui-cells">
		<div class="weui-cell">
		    <div class="weui-cell__bd">
		        <p>注意</p>
		    </div>
		    <div class="weui-cell__ft"><u><b><font color="red">当前储值卡充值处于测试阶段,请勿充值</font></b></u></div>
		</div>
    </div>
    -->
	<div
		style="height: 80px;line-height:50px;text-align: left;margin-left: 20px;">
		当前余额(元)：<br>
		<c:choose>
			<c:when test="${cardMoney !=null}">
				<span style="color: red;font-size: 40px;">
					${cardMoney.remainMoney}</span>
				<span style="color: red;font-size: 20px;">点数${cardMoney.remainPoints}</span>
			</c:when>
			<c:otherwise>
				<span style="color: red;font-size: 40px;"> 0</span>
				<span style="color: red;font-size: 20px;">点数:0</span>
			</c:otherwise>
		</c:choose>
	</div>
	
	<div>
		<div class="weui-cells__title">充值金额</div>
		<div class="weui-cells weui-cells_radio">
			<c:forEach items="${cardTypes}" var="cardType"
				varStatus="cardTypeData">
				<label class="weui-cell weui-check__label"
					for="${cardType.storedCategoryId}">
					<div class="weui-cell__bd">
						<span style="font-size: 20px">充${cardType.storedMoney}元 </span>
						<%-- 
						<span style="font-size: 10px">返${cardType.returnPoints}元</span>
						<img src="static/images/wechat/why.png" style="width:20px; height:20px;"></img>
						--%>
					</div>
					<div class="weui-cell__ft">
						<input id="${cardType.storedCategoryId}" class="weui-check"
							name="radio2" onclick="showRemark('${cardType.remark}');"
							<c:if test="${cardTypeData.index==0}"><c:out value="checked=\"checked\""/></c:if>
							type="radio"> <span class="weui-icon-checked"></span>
					</div> </label>
			</c:forEach>
		</div>
	</div>
	<div style="margin-bottom:20px;margin-top: 20px;">
		<div class="weui-cells__title">支付方式</div>
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
					<input id="y12" class="weui-check" name="radio1" checked="checked"
						type="radio"> <span class="weui-icon-checked"></span>
				</div> </label>
			<!-- <label class="weui-cell weui-check__label" for="y13">
					<div class="weui-cell__hd">
						<img alt="" src="static/images/wechat/zhifubaopay.png"
							style="width:20px;margin-right:5px;display:block">
					</div>
					<div class="weui-cell__bd">
						<p>支付宝支付</p>
					</div>
					<div class="weui-cell__ft">
						<input id="y13" class="weui-check" name="radio1" type="radio">
						<span class="weui-icon-checked"></span>
					</div> 
			</label> -->
		</div>
	</div>

	<div style="clear:both;"></div>
	<div style="width: 90%;margin-left: 5%;margin-right: 5%;">
		<span><a class="weui-btn weui-btn_warn" id="rechargeNow"
			href="javascript:void(0)" onclick="rechargeNow();">马上充值</a> </span>
	</div>


	<div id="loadmore" style="position:absolute;left:-100px;">
		<img src="static/images/loadingi.gif"></img>
	</div>
	<div id="toast" style=" display: none;">
		<div class="weui-mask_transparent"></div>
		<div class="weui-toast">
			<i class="weui-icon-success-no-circle weui-icon_toast" id="toastType"></i>
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
</body>
</html>
