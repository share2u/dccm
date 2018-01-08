<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html;charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta name="viewport"
	content="width=device-width,initial-scale=1,user-scalable=0">
<base href="<%=basePath%>">

<title>投诉与建议</title>

<link rel="stylesheet" href="static/css/wechat/main.css" type="text/css"></link>
<link rel="stylesheet" href="static/css/wechat/weui.min.css"
	type="text/css"></link>
<script type="text/javascript" src="static/js/jquery-1.7.2.js"></script>
<script type="text/javascript">
	var reg = /^1[3|5|8][0-9]\d{4,8}$/;
	function sendSuggest(){
		// 尝试替换$的内容
		var suggestText = $("textarea").val();
		var tel = $("input").val();
		if(suggestText.trim() ==""){
			$("textarea").focus();
			$("textarea").val("");
			$("textarea").attr("placeholder", "投诉和建议的内容不能为空");
			return;
		}
		var flag = reg.test(tel);
		if(flag){
			$.ajax({
			url : "weChatSuggest/sendSuggest",
			data : {
				"suggestText" : suggestText,
				"tel" : tel
			},
			type:"POST",
			dataType : "json",
			success : function(data) {
				if (data.key == "success") {
				//请求成功
				alert("提交成功");
					window.location.href="http://www.liangliangempire.cn/dccm/weChat/home";
				}
			}
		});
		}else{
			$("input").focus();
			$("input").val("");
			$("input").attr("placeholder", "手机号不合法，请重新输入");
			return;
		}
	}
</script>
</head>

<body>
	<div style="background-color:#BF172E;height:50px;margin:0px;padding:0px;width:100%; position:fixed; top:0;z-index: 9999;">
		<div style="padding:0.07rem 0.15rem;">
			<!--<div style="float: right;height:50px;line-height: 50px;"><a style="color: #F3FFFF;"href="javascript:;">明细</a></div>-->
			<div
				style="text-align: center;height: 50px;  line-height:50px; color: #F3FFFF; ">大诚中医</div>
		</div>
	</div>
	<div style="margin-top:50px;"></div>
	<div class="weui-cells weui-cells_form">
		<div class="weui-cell">
			<div class="weui-cell__bd">
				<textarea class="weui-textarea" placeholder="请输入投诉或者建议，我们会及时反馈"
					rows="3"></textarea>
				<div class="weui-textarea-counter">
					<span>0</span>/200
				</div>
			</div>
		</div>
		<div class="weui-cell">
			<div class="weui-cell__bd">
				<input class="weui-input" pattern="[0-9]*" placeholder="请输入手机号码"
					type="number">
			</div>
		</div>
		<div class="weui-cell">
			<div class="weui-cell__bd">
				<a href="javascript:sendSuggest();" class="weui-btn weui-btn_warn">提交</a>
			</div>
		</div>
	</div>
</div>
</body>
</html>
