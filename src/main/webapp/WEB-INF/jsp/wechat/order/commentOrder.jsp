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
<title>订单评价</title>
<link rel="stylesheet" href="static/css/wechat/weui.min.css" />
<script type="text/javascript" src="static/js/jquery-1.7.2.js"></script>
<style type="text/css">
	.init_size{
		font-size:15px;	
	}
</style>
<script type="text/javascript">
	function check(){
		return true;
	}
</script>
</head>
<body style="background-color:#f8f8f8">
	<div
		style="background-color:#BF172E;height:50px;margin:0px;padding:0px;width:100%; position:fixed; top:0;z-index: 9999;">
		<div style="padding:0.07rem 0.15rem;">
			<div
				style="text-align: center;height: 50px;  line-height:50px; color: #F3FFFF; ">大诚中医</div>
			</div>
	</div>
	<!-- margin的顺序是上右下左 -->
	<div style="margin-top:50px;"></div>
	<form action="weChatOrder/commentOrder" method="post"
		onsubmit="return check()">
		<div style="display:none">
			<input name="orderId" type="text" value="${weChatOrder.orderId}">
		</div>
		<div style="display:none">
			<input name="storeId" type="text" value="${weChatOrder.store.storeId}">
		</div>
		<div class="weui-panel weui-panel_access">
			<div class="weui-panel__hd" style="font-size:15px">${weChatOrder.store.storeName}</div>
			<div>
				<!-- 图片信息暂时设置为 门店大图 -->
				<img style="width:100%" alt="" src="static/images/wechat/store.png">
			</div>
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
				</div>
			</div>
		</div>
		<div class="weui-cells__title"></div>
        <div class="weui-cells weui-cells_form">
            <div class="weui-cell">
                <div class="weui-cell__bd">
                    <textarea name="messageInfo" class="weui-textarea init_size" placeholder="快来说说你对这项服务的看法吧~" rows="3"></textarea>
                    <div class="weui-textarea-counter init_size"><span>0</span>/200</div>
                </div>
            </div>
        </div>
        <div class="weui-btn-area">
			<input type="submit" class="weui-btn weui-btn_primary init_size" value="保存">
		</div>
	</form>
</body>
</html>