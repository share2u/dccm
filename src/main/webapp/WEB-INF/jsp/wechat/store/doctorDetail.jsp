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

<title>医生详情</title>

<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=no">
<link rel="stylesheet" href="static/css/wechat/weui.min.css">
<link rel="stylesheet" type="text/css" href="static/css/wechat/main.css" />
<link rel="stylesheet" type="text/css"
	href="static/css/wechat/docterDetail.css" />
<script type="text/javascript" src="static/js/jquery-1.7.2.js"></script>
<script type="text/javascript">
	function putNameDown(Id, name) {
		$("#docName").text("");
		staffId = Id;
		$("#docName").text(name);
		$("#mianfeihuidainDialog").css('display', 'block');
		$("#mianfeihuidainDialog").css('opacity', '1');
	}
	function dialogDisplay() {
		$("#mianfeihuidainDialog").css('display', 'none');
		$("#mianfeihuidainDialog").css('opacity', '0');
	}
	function showToast() {
		if ($toast.css('display') != 'none')
			return;
		$toast.fadeIn(100);
		setTimeout(function() {
			$toast.fadeOut(100);
		}, 2000);
	}
	var reg = /^1[0-9]{10}$/;
	function sendTel2Straff() {
		var callbackTel = $("#callbackTel").val();
		var callbackName = $("#callbackName").val();
		if (callbackName.replace(/(^s*)|(s*$)/g, "").length != 0) {
			if (callbackTel != "" && callbackTel != null) {
				var flag = reg.test(callbackTel);
				if (flag) {
					/* alert(staffId + "," + callbackTel + "," + callbackName); */
					sendTelForStaff(staffId, callbackTel, callbackName);
				} else {
					$("#callbackTel").focus();
					$("#callbackTel").val("");
					$("#callbackTel").attr("placeholder", "手机号不合法，请重新输入");
				}
			} else {
				$("#callbackTel").focus();
				$("#noTelWarn").css('display', 'block');
			}
		} else {
			$("#callbackName").focus();
			$("#callbackName").val("");
			$("#callbackName").attr("placeholder", "姓名不能为空");
		}

	}
	function sendTelForStaff(docId, tel, name) {
		$.ajax({
			url : "weChatStore/sendTelToStore",
			data : {
				"docId" : docId,
				"tel" : tel,
				"storeId" : '${doctor.STORE_ID}',
				"name" : name
			},
			type : "POST",
			dataType : "json",
			success : function(data) {
				if (data.key == "success") {
					dialogDisplay();
					showToast();
				}
			}
		});
	}
	$(function() {
		var staffId = "";

		$toast = $('#toast');
		$("#callbackTel").on('input', function() {
			if (this.value.length) {
				$("#noTelWarn").css('display', 'none');
			}
		});
	});
	
	/*
		modified by scott
	*/
	function showDiv(){
		var flag = false;
		// 判断当前是否隐藏div标签
		$("div[name=hiddenTag]").each(function(){
			if($(this).css('display') == "none"){
				flag = true;
				return;
			}
		});
		
		// 若隐藏,点击"查看更多"时显示
		if(flag == true){
			$("div[name=hiddenTag]").each(function(){
				$(this).css('display','');	
			});
		}
		
		// 若显式,点击"查看更多"时隐藏
		if(flag == false){
			$("div[name=hiddenTag]").each(function(){
				$(this).css('display','none');	
			});
		}
	}
</script>
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
	<div style="height: 50px;"></div>
	<div class="wraper-list-doctor">
		<ul id="doctor-list" class="fn-clear">
		<c:choose>
			<c:when test="${storeType =='02'}">
			<li>
				<div class="header-doctor" >
						<img width="240" height="360" alt="医生头像"
						src="uploadFiles/staffimg/${doctor.STAFF_IMG}" />
				</div>
				<hr>
				<div class="doctor-name">
					<div class="weui-media-box weui-media-box_text">
						<h4 class="weui-media-box__title" style="font-size: 22px;text-align: center;">${doctor.STAFF_NAME}</h4>
					</div>
				</div>
			</li>
		</c:when>
			<c:when test="${storeType =='01'}">
			<li style="margin:0; width: 100%;">
				<div class="header-doctor" style="height: 231px;margin: 0px auto;overflow: hidden;width: 100%;">
						<img width="100%" height="231" alt="培训课程头像"
						src="uploadFiles/staffimg/${doctor.STAFF_IMG}" />
				</div>
				<hr>
				<div class="doctor-name">
					<div class="weui-media-box weui-media-box_text">
						<h4 class="weui-media-box__title" style="font-size: 16px;text-align: center;">${doctor.STAFF_NAME}</h4>
					</div>
				</div>
			</li>
		</c:when>
		</c:choose>
		</ul>
	</div>
	<div style="clear: both;"></div>
	<div style="width: 96%;margin-left: 2%;margin-right: 2%;">
		
		<div class="weui-panel weui-panel_access">
		<div class="weui-panel__hd" style="font-size: 20px;">
					<c:choose>
						<c:when test="${storeType =='02'}"> 医师信息</c:when>
						<c:when test="${storeType =='01'}"> 课程内容</c:when>
					</c:choose>
			</div>
			<div class="weui-panel__bd">
					<div class="weui-media-box weui-media-box_text">
						<p class="weui-media-box__desc">${doctor.INFO}</p>
					</div>
			</div>
		</div>
		<div class="weui-panel weui-panel_access">
		<div class="weui-panel__hd" style="font-size: 20px;">
					<c:choose>
						<c:when test="${storeType =='02'}"> 医师擅长</c:when>
						<c:when test="${storeType =='01'}"> 授课讲师</c:when>
					</c:choose>
			</div>
			<div class="weui-panel__bd">
					<div class="weui-media-box weui-media-box_text">
						<p class="weui-media-box__desc">${doctor.SPECIAL}</p>
					</div>
			</div>
		</div>
		
		<div class="weui-panel weui-panel_access">
			<div class="weui-panel__hd" style="font-size: 20px;">
				<c:if test="${not empty serviceProjects}">
					<c:choose>
						<c:when test="${storeType =='02'}"> 医疗项目</c:when>
						<%-- <c:when test="${storeType ==01}"> 培训科目</c:when> --%>
					</c:choose>
				</c:if>
			</div>
			<div class="weui-panel__bd">
				<!-- modified by scott -->
				<c:forEach items="${serviceProjects}" var="serviceProject" varStatus="status">
					<c:choose>
						<c:when test="${status.index < 3}">
							<div class="weui-media-box weui-media-box_text">
						</c:when>
						<c:otherwise>
							<div class="weui-media-box weui-media-box_text" name="hiddenTag" style="display:none">
						</c:otherwise>
					</c:choose>
								<h3 class="weui-media-box__title">项目名称：${serviceProject.PNAME}</h3>
								<p class="weui-media-box__desc">项目介绍：${serviceProject.REMARK}</p>
							</div>
					<c:if test="${status.last && status.index >= 3}">						
						<a class="weui-media-box weui-media-box_appmsg" href="javascript:;" onclick="showDiv();">
							<p class="weui-media-box__desc" style="float:right;margin-right:10px">
								<font style="color:#0000FF;">查看更多服务项目</font>							
							</p>
						</a>
					</c:if>										
				</c:forEach>
			</div>
		</div>

		<br>
		<div class="doctor-totel">
			<span><a class="weui-btn weui-btn_warn"
				href="javascript:putNameDown('${doctor.STAFF_ID}','${doctor.STAFF_NAME}');">免费咨询</a>
			</span>
		</div>

	</div>


	<div style="clear:both;"></div>

	<div class="js_dialog" id="mianfeihuidainDialog"
		style="opacity: 0; display: none;">
		<div class="weui-mask"></div>
		<div class="weui-dialog weui-skin_android">
			<div class="weui-dialog__hd">
				<strong class="weui-dialog__title">免费回电</strong>
			</div>
			<div class="weui-dialog__bd">
				在下方输入您的姓名和手机号，客户稍后给您回电安排 &nbsp;<span style="color: red;"
					id="docName"></span>&nbsp;医生的预约！
				<div class="weui-cell weui-cell_vcode"
					style="border: 1px solid red;">
					<div class="weui-cell__bd">
						<input class="weui-input" placeholder="请输入手机号" type="tel"
							id="callbackTel">
					</div>
					<div class="weui-cell__ft" style="display: none" id="noTelWarn">
						<i class="weui-icon-warn"></i>
					</div>
				</div>
				<div class="weui-cell weui-cell_vcode"
					style="border: 1px solid red;">
					<div class="weui-cell__bd">
						<input class="weui-input" placeholder="请输入患者姓名" type="text"
							id="callbackName">
					</div>
				</div>
			</div>
			<div class="weui-dialog__ft">
				<a href="javascript:dialogDisplay();"
					class="weui-dialog__btn weui-dialog__btn_default">取消</a> <a
					href="javascript:sendTel2Straff();"
					class="weui-dialog__btn weui-dialog__btn_primary">确定</a>
			</div>
		</div>
	</div>
	<div id="toast" style=" display: none;">
		<div class="weui-mask_transparent"></div>
		<div class="weui-toast">
			<i class="weui-icon-success-no-circle weui-icon_toast"></i>
			<p class="weui-toast__content">已完成</p>
		</div>
	</div>

</body>
</html>