<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE html>
<html>
<head>
<base href="<%=basePath%>">

<title>我的账户</title>
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=no">
<link rel="stylesheet" type="text/css" href="static/css/wechat/main.css" />
<link rel="stylesheet" href="static/css/wechat/weui.min.css"
	type="text/css"></link>
<script type="text/javascript" src="static/js/jquery-1.7.2.js"></script>
<script type="text/javascript">
	function chongzhi() {
		window.location.href = "weChat/recharge";

	}
	function chongzhiminxi() {
		window.location.href = "weChatCard/rechargeDetails";

	}
	function jifengminxi() {
		window.location.href = "weChatCredit/creditDetails";

	}
	
	/*
		modified by scott
	*/
	function yucunminxi() {
		window.location.href = "weChatPreStoreMx/preStoreMxDetail";
	}
	
	/*
		modified by scott
	*/
	function showDialog() {
		window.location.href = "weChatCredit/creditUsedInfo";
	}
	
</script>
</head>

<body>
	<div
		style="background-color:#BF172E;height:50px;margin:0px;padding:0px;width:100%; position:fixed; top:0;z-index: 9999;">
		<div style="padding:0.07rem 0.15rem;">
			<!-- <div style="float: right; padding-right:10px ; height:50px;line-height: 50px;"><a style="color: #F3FFFF;"href="javascript:;">明细</a></div> -->
			<div
				style="text-align: center;height: 50px;padding-left:20px;  line-height:50px; color: #F3FFFF; ">大诚中医</div>
		</div>
	</div>
	<div style="margin-top:50px;"></div>
	<div style="width:100%;height:100%;">
		<!-- <div style="position: absolute;top:0;left:0;bottom:0;right:0;width:50%;height:50%;margin:auto;text-align: center;">
  			<div style="position: absolute;top:0;left:0;bottom:200px;right:0;width:50%;height:50%;margin:auto;border:text-align: center;">
  				<span style="font-size:75px;">0.00</span>
	  			<div style="position:absolute; left:30%;bottom: 0;"><span>账户余额（元）</span></div>
  			</div>
  			<div><a class ="weui-btn weui-btn_warn" style="position:absolute ;bottom: 0;width: 100%;" href="javascript:chongzhi();">充值</a></div>
  		</div> -->
		<div>
			<div
				style="height: 60px;width: 100%;text-align: left; margin-left:20px; line-height: 60px;">
				<span>储值卡余额(元)</span>
			</div>
			<div
				style="height: 70px; width: 100%;text-align: left; margin-left:20px; line-height: 70px;">
				<p style="font-size: 40px;">
					<c:choose>
						<c:when test="${cardMoney != null}">${cardMoney.remainMoney}</c:when>
						<c:otherwise>0.0</c:otherwise>
					</c:choose>
					<c:choose>
						<c:when test="${cardMoney != null}">
							<span style="font-size: 15px;">
								点数：${cardMoney.remainPoints}</span>
						</c:when>
						<c:otherwise>
							<span style="font-size: 20px;">点数：0.0</span>
						</c:otherwise>
					</c:choose>
				</p>
				<br>
			</div>
			<hr color="#ECECEC">
			<div class="weui-media-box weui-media-box_small-appmsg">
				<div class="weui-cells">
					<a class="weui-cell weui-cell_access" href="javascript:chongzhi();">
						<div class="weui-cell__hd">
							<img src="static/images/wechat/chongzhi.png" alt=""
								style="width:20px;margin-right:5px;display:block">
						</div>
						<div class="weui-cell__bd weui-cell_primary">
							<p>充值</p>
						</div> <span class="weui-cell__ft"></span> </a> <a
						class="weui-cell weui-cell_access"
						href="javascript:chongzhiminxi();">
						<div class="weui-cell__hd">
							<img src="static/images/wechat/chongzhiminxi.png" alt=""
								style="width:20px;margin-right:5px;display:block">
						</div>
						<div class="weui-cell__bd weui-cell_primary">
							<p>充值明细</p>
						</div> <span class="weui-cell__ft"></span> </a>
				</div>
			</div>
		</div>
		<hr color="#ECECEC">
		<div style="height: 20px;background-color: #F7F7F7;"></div>
		<hr color="#ECECEC">
		
		<!-- modified by scott -->
		<!-- origin -->
		<!-- 
		<div>
			<div
				style="height: 50px ;width: 100%;text-align: left; margin-left:20px; line-height: 50px;">
				<span>预存余额(元)</span>
			</div>
			<hr color="#ECECEC">
			<div
				style="height: 70px; width: 100%;text-align: left; margin-left:20px; line-height: 70px;">
				<span style="font-size: 30px;"> ${totalPreStored} </span>
			</div>
		</div>
		-->
		
		<div>
			<div
				style="height: 50px ;width: 100%;text-align: left; margin-left:20px; line-height: 50px;">
				<span>钱包余额(元)</span>
			</div>
			<hr color="#ECECEC">
			<div
				style="height: 70px; width: 100%;text-align: left; margin-left:20px; line-height: 70px;">
				<span style="font-size: 30px;"> ${totalPreStored} </span>
			</div>
			<a class="weui-cell weui-cell_access"
				href="javascript:yucunminxi();">
				<div class="weui-cell__hd">
					<img src="static/images/wechat/chongzhiminxi.png" alt=""
						style="width:20px;margin-right:5px;display:block">
				</div>
				<div class="weui-cell__bd weui-cell_primary">
					<p>消费明细</p>
				</div> <span class="weui-cell__ft"></span>
			</a>
		</div>
		
		<div style="height: 20px;background-color: #F7F7F7;"></div>
		<hr color="#ECECEC">
		<div>
			<div
				style="height: 50px ;width: 100%;text-align: left; margin-left:20px; line-height: 50px;">
				<span>积分总额</span>
			</div>
			<hr color="#ECECEC">
			<div
				style="height: 70px; width: 100%;text-align: left; margin-left:20px; line-height: 70px;">
				<span style="font-size: 30px;"> ${totalCredit} </span>
			</div>
			<hr color="#ECECEC">
			<div class="weui-media-box weui-media-box_small-appmsg">
				<div class="weui-cells">
					<a class="weui-cell weui-cell_access"
						href="javascript:jifengminxi();">
						<div class="weui-cell__hd">
							<img src="static/images/wechat/jifeng.png" alt=""
								style="width:20px;margin-right:5px;display:block">
						</div>
						<div class="weui-cell__bd weui-cell_primary">
							<p>积分明细</p>
						</div> <span class="weui-cell__ft"></span> 
					</a>
				</div>
				<!-- 显示积分使用规则 -->
				<div class="weui-cells">
					<a class="weui-cell weui-cell_access"
						href="javascript:;" onclick="showDialog();">
						<div class="weui-cell__hd">
							<img src="static/images/wechat/jifeng.png" alt=""
								style="width:20px;margin-right:5px;display:block">
						</div>
						<div class="weui-cell__bd weui-cell_primary">
							<p>积分使用规则</p>
						</div> <span class="weui-cell__ft"></span> 
					</a>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
