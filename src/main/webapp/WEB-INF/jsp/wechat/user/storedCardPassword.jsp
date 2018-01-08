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
<title>设置密码</title>
<link rel="stylesheet" href="static/css/wechat/weui.min.css"/>
<script type="text/javascript" src="static/js/jquery-1.7.2.js"></script>
<style type="text/css">
	body {
		background-color: #f8f8f8;
	}
	.init_size{
		font-size:15px;
	}
</style>
<script type="text/javascript">
	function check(){
		var uId = $("#uId").val();
		var newPassword = $("#newPassword").val();
		var newPasswordConfirm = $("#newPasswordConfirm").val();
		if(newPassword == "" || isNumber(newPassword) == false || newPassword.length != 6){
			alert("您输入的新密码位数有误,或者密码中包含非数字字符;请重新输入");
			return false;
		}
		if(newPasswordConfirm == "" || isNumber(newPasswordConfirm) == false || newPasswordConfirm.length != 6){
			alert("您输入的新密码位数有误,或者密码中包含非数字字符;请重新输入");
			return false;
		}
		if(newPassword != newPasswordConfirm){
			alert("新密码两次输入不一致");
			return false;
		}
		var status;
		// 判断是否是新创建密码
		if($("#originPassword").length > 0){
			var originPassword = $("#originPassword").val();
			if(originPassword == "" || isNumber(originPassword) == false || originPassword.length != 6){
				alert("您输入的原密码位数有误,或者密码中包含非数字字符;请重新输入");
				return false;
			}
			$.ajax({
				url : 'weChatUser/passwordAuthorize',
				type : 'POST',
				async: false,
				data : {"originPassword" : originPassword,"uId" : uId},
				dataType : 'json',
				contentType : "application/x-www-form-urlencoded;charset=utf-8",
				success : function(data){
					console.log(data.result);
					status = data.result;
				}
			});
		}
		if(status == "fail"){
			alert("原密码不正确,请重新输入");
			return false;
		}
	}
	
	// 判断是否是数字组成
	function isNumber(str){
		var reg = /^\d+$/;
		return reg.test(str);
	}
</script>
</head>
<body ontouchstart="">
	<div style="background-color:#BF172E;height:50px;margin:0px;padding:0px;width:100%; position:fixed; top:0;z-index: 9999;">
  		<div style="padding:0.07rem 0.15rem;">
  			<div style="text-align: center;height: 50px;  line-height:50px; color: #F3FFFF; ">大诚中医</div>
  		</div>
	</div>
  	<!-- margin的顺序是上右下左 -->
  	<div style="margin-top:50px;"></div>
	<form action="weChatUser/setStoredCardPassword" method="post" onsubmit="return check();">
		<input id="uId" name="uId" type="text" style="display:none" value="${weChatUserStoredCard.user.uId}">
		<input id="cardId" name="cardId" type="text" style="display:none" value="${weChatUserStoredCard.cardId}">
		<c:choose>
			<c:when test="${flag == 1}">
				<!-- 当前已设置秘密,需要输入原密码信息以修改密码 -->
				<div class="weui-cell" style="padding-left:0;background-color:#fff">
					<div class="weui-cell weui-cell_vcode">
						<div class="weui-cell__hd">
							<label class="weui-label init_size"><span style="color:#FF0000">*&nbsp;</span>原密码</label>
						</div>
						<div class="weui-cell__bd init_size">
							<input id="originPassword" name="originPassword" class="weui-input" 
							placeholder="请输入原密码" pattern="[0-9]*" type="password" style="ime-mode:Disabled">
						</div>
					</div>
				</div>
			</c:when>
			<c:otherwise>
				<!-- 当前未设置密码信息,重新设置密码 -->
			</c:otherwise>
		</c:choose>
		<div class="weui-cell" style="padding-left:0;">
			<div class="weui-cell weui-cell_vcode">
				<div class="weui-cell__hd">
					<label class="weui-label init_size"><span style="color:#FF0000">*&nbsp;</span>输入密码</label>
				</div>
				<div class="weui-cell__bd init_size">
					<input id="newPassword" name="newPassword" class="weui-input" placeholder="请输入六位数字新密码" pattern="[0-9]*" 
						type="password" style="ime-mode:Disabled">
				</div>
			</div>
		</div>
		<div class="weui-cell" style="padding-left:0;background-color:#fff">
			<div class="weui-cell weui-cell_vcode">
				<div class="weui-cell__hd">
					<label class="weui-label init_size"><span style="color:#FF0000">*&nbsp;</span>确认密码</label>
				</div>
				<div class="weui-cell__bd init_size">
					<input id="newPasswordConfirm" name="newPasswordConfirm" class="weui-input" 
						placeholder="请再次输入密码" pattern="[0-9]*" onblur="" type="password" style="ime-mode:Disabled">
				</div>
			</div>
		</div>
		<div class="weui-btn-area">
			<input type="submit" class="weui-btn weui-btn_primary init_size" value="确定">
		</div>
	</form>
</body>
</html>