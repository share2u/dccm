<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html lang="en">
<head>
<base href="<%=basePath%>">
<meta charset="UTF-8">
<meta name="viewport"
	content="width=device-width,initial-scale=1,user-scalable=0">
<title>完善个人信息</title>
<link rel="stylesheet" href="static/css/wechat/weui.min.css" />
<link rel="stylesheet" href="static/css/wechat/example.css" />
<script type="text/javascript" src="static/js/jquery-1.7.2.js"></script>
<style type="text/css">
	.init_size{
		font-size:15px;	
	}
</style>
<script type="text/javascript">
	// 客户端数据校验 暂时省略
	function check(){
		var birthday = trim($("#birthday").val());
		var phone = trim($("#phone").val());
		var name = trim($("#name").val());
		if(name == ""){
			alert("请填写姓名");
			return false;
		}
		if(birthday == "" || !isDate(birthday)){
			alert("请选择出生日期");
			return false;
		}
		if(phone == "" || !isMobile(phone) || phone.length != 11){
			alert("手机号格式不正确,请输入正确的手机号");
			return false;
		}
		
		// 检测手机号是否重复
		var flag = "fail";
		var uId = $("input[name=uId]").val();
		$.ajax({
			url : 'weChatMobileInfo/checkMobileIsDuplicate',
			type : 'POST',
			data : {"phone" : phone,"uId" : uId},
			dataType : 'json',
			contentType : "application/x-www-form-urlencoded;charset=utf-8",
			async : false,
			success : function(result){
				if(result.result == "success"){
					flag = "success";
					return;
				}
			}
		});
		
		if(flag == "fail"){
			alert("该手机号已注册");
			return false;
		}
		
		var idCode = trim($("#idCode").val());
		if(idCode != ""){
			if(!isCardNo(idCode)){
				alert("身份证号格式不正确,请输入正确的身份证号");
				return false;
			}
		}
		var email = trim($("#email").val());
		if(email != ""){
			if(!isEmail(email)){
				alert("邮箱格式不正确,请输入正确的邮箱");
				return false;
			}
		}
		return true;
	}
	
	// 输入数字
	function keyPress(){
		var keyCode = event.keyCode;
		if(keyCode >= 48 && keyCode <= 57){
			event.returnValue = true;
		}else{
			event.returnValue = false;
		}
	}	
	
	// 去空格
	function trim(str){
	    return str.replace(/(^\s*)|(\s*$)/g, "");
	}
	
	// 验证身份证 
	function isCardNo(str) { 
	 	var pattern = /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/; 
	 	return pattern.test(str); 
	}
	
	//检查email邮箱
	function isEmail(str){
		var reg = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\.[a-zA-Z0-9_-]{2,3}){1,2})$/;
		return reg.test(str);
	}
	
	// 检查手机号
	function isMobile(str){ 
	    var reg = /^1[3|5|7|8][0-9]\d{4,8}$/;
	    return reg.test(str);
	}
	
	// 判断出生日期
	function isDate(str){
		var reg = /(([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})-(((0[13578]|1[02])-(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)-(0[1-9]|[12][0-9]|30))|(02-(0[1-9]|[1][0-9]|2[0-8]))))|((([0-9]{2})(0[48]|[2468][048]|[13579][26])|((0[48]|[2468][048]|[3579][26])00))-02-29)$/;
		return reg.test(str);
	}
	
	// 通过手机号获取城市信息,并将该信息放入标签
	function getCityByMobileNum(){
		var phone = $("#phone").val();
		phone = trim(phone);
		if(phone.length < 7){
			alert("手机号格式不正确,请输入正确的手机号");
			return false;
		}
		console.log(phone);
		if(phone != ""){
			$.ajax({
				url : 'weChatMobileInfo/getMobileInfoByMobileNum',
				type : 'POST',
				data : {"phone" : phone},
				dataType : 'json',
				contentType : "application/x-www-form-urlencoded;charset=utf-8",
				success : function(result){
					console.log(result);
					$("#city").val(result.mobileArea);
				}
			});
		}
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
	<form action="weChatUser/updateUser" method="post" onsubmit="return check();">
		<div style="display:none">
			<input name="uId" type="text" value="${weChatUser.uId}">
		</div>
		<div style="display:none">
			<input name="userName" type="text" value="${weChatUser.userName}">
		</div>
		<div style="display:none">
			<input name="userImg" type="text" value="${weChatUser.userImg}">
		</div>
		<div class="weui-cell init_size">
            <div class="weui-cell__hd">
            	<label for="" class="weui-label"><span style="color:#FF0000">*&nbsp;</span>姓名</label>
            </div>
            <div class="weui-cell__bd">
                <input id="name" name="name" class="weui-input" 
                	value="${weChatUser.name}" placeholder="姓名" type="text">
            </div>
        </div>
		<div class="weui-cells weui-cells_radio" style="margin-top:0">
			<label class="weui-cell weui-check__label" for="x11">
				<div class="weui-cell__bd">
					<p class="init_size">&nbsp;&nbsp;男</p>
				</div>
				<div class="weui-cell__ft">
					<input class="weui-check" name="sex" id="x11" type="radio" value="1">
					<span class="weui-icon-checked"></span>
				</div> 
			</label>
			<label name="sex" class="weui-cell weui-check__label" for="x12">
				<div class="weui-cell__bd">
					<p class="init_size">&nbsp;&nbsp;女</p>
				</div>
				<div class="weui-cell__ft">
					<input name="sex" class="weui-check" id="x12" checked="checked" value="2"
						type="radio"> 
					<span class="weui-icon-checked"></span>
				</div>
			</label>
		</div>
		<div class="weui-cell init_size">
			<div class="weui-cell__hd">
					<label class="weui-label"><span style="color:#FF0000">*&nbsp;</span>出生日期</label>
				</div>
			<div class="weui-cell__bd">
				<!-- type为date时,选择的日期显示格式为1992/01/01;但是通过js获取控件的值为1992-01-01,注意 -->
				<input id="birthday" name="birthday" class="weui-input" placeholder="请选择出生日期" type="date"
					value="${weChatUser.birthday}" style="ime-mode:Disabled">
			</div>
		</div>
		<div class="weui-cell init_size" style="padding-left:0;background-color:#fff">
			<div class="weui-cell weui-cell_vcode">
				<div class="weui-cell__hd">
					<label class="weui-label"><span style="color:#FF0000">*&nbsp;</span>手机号</label>
				</div>
				<div class="weui-cell__bd">
					<input id="phone" name="phone" class="weui-input" placeholder="请输入手机号" pattern="[0-9]*" 
						value="${weChatUser.phone}" 
						type="tel" onkeypress="keyPress()" style="ime-mode:Disabled">
				</div>
			</div>
		</div>
        <div class="weui-cell init_size">
            <div class="weui-cell__hd">
            	<label for="" class="weui-label">&nbsp;&nbsp;住址</label>
            </div>
            <div class="weui-cell__bd">
                <input name="address" class="weui-input" 
                	value="${weChatUser.address}" placeholder="住址" type="text">
            </div>
        </div>
        <div class="weui-cell init_size" style="padding-left:0;background-color:#fff">
			<div class="weui-cell weui-cell_vcode">
				<div class="weui-cell__hd">
					<label class="weui-label">&nbsp;&nbsp;证件信息</label>
				</div>
				<div class="weui-cell__bd">
					<input id="idCode" name="idCode" class="weui-input" placeholder="请输入证件信息" 
						value="${weChatUser.idCode}"
						type="text" style="ime-mode:Disabled">
				</div>
			</div>
		</div>
		<div class="weui-cell init_size" style="padding-left:0;">
			<div class="weui-cell weui-cell_vcode">
				<div class="weui-cell__hd">
					<label class="weui-label">&nbsp;&nbsp;邮箱</label>
				</div>
				<div class="weui-cell__bd">
					<input id="email" name="email" class="weui-input" placeholder="请输入邮箱" 
						value="${weChatUser.email}"
						type="text" style="ime-mode:Disabled">
				</div>
			</div>
		</div>
		<div class="weui-btn-area">
			<input type="submit" class="weui-btn weui-btn_primary init_size" value="保存">
		</div>
	</form>
</body>
</html>