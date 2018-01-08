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
<title>忘记密码</title>
<link rel="stylesheet" href="static/css/wechat/weui.min.css" />
<link rel="stylesheet" href="static/css/wechat/example.css" />
<script type="text/javascript" src="static/js/jquery-1.7.2.js"></script>
<style type="text/css">
	.init_size {
		font-size: 15px;
	}
	
	.fix_size {
		font-size: 12px;
	}
</style>
<script type="text/javascript">
	// 
	var wait = 60;
 	
   	function time() {
   		var phone = trim($("#phone").val());
		if(phone == "" || !isMobile(phone) || phone.length != 11){
			alert("手机号格式不正确,请输入正确的手机号");
			return false;
		} 		
        if (wait == 0) {
            $("#test").attr("class","weui-btn weui-btn_mini weui-btn_primary fix_size");
            $("#test").val("点击获取验证码");
            $("#test").attr("disabled",false);
            wait = 60;
        } else {
        	if(wait == 60){
        		getCheckCode();
				$("#test").attr("disabled",true);
        	}
        	$("#test").attr("class","weui-btn weui-btn_mini weui-btn_disabled weui-btn_primary fix_size");    	
        	$("#test").val(wait + "秒后可以重新发送");    	
            wait --;
            setTimeout(function() {
            	time();
            }, 1000);
        }
    }
    
	// 获取验证码
	function getCheckCode(){
		var phone = trim($("#phone").val());
			
		$.ajax({
			url : 'weChatUser/getCheckCode',
			type : 'POST',
			data : {
				"phone" : phone,
			},
			dataType : 'json',
			contentType : "application/x-www-form-urlencoded;charset=utf-8",
			success : function(result) {
				
			}
		});
	}
	
	function modifyStoredPassword(){
		// 检测验证码格式的正确性
		var checkCode = trim($("#checkCode").val());
		alert(checkCode);
		if(!isNumberAndChar(checkCode) || checkCode.length != 6){
			alert("您输入的验证码包含字母或符号或长度不符合要求");
			return false;
		}
		
		// 后端服务器验证验证码是否正确
		var flag = true;
		$.ajax({
			url : 'weChatUser/checkCodeIsRight',
			type : 'POST',
			data : {
				"checkCode" : checkCode,
			},
			dataType : 'json',
			async : false,
			contentType : "application/x-www-form-urlencoded;charset=utf-8",
			success : function(result) {
				if(result.errorCode == "fail"){
					alert(result.errorDes);
					flag = false;
					return;
				}
			}
		});
		
		if(flag == false){
			return false;
		}
		
		var uId = trim($("#uId").val());
		// 转到修改密码界面
		$("#passwordModify").attr("href","weChatUser/pageNagivatePasswordSet/" + uId);
	}
	
	// 检查手机号
	function isMobile(str){ 
	    var reg = /^1[3|5|8][0-9]\d{4,8}$/;
	    return reg.test(str);
	}
	
	// 去空格
	function trim(str){
	    return str.replace(/(^\s*)|(\s*$)/g, "");
	}
	
	// 判断是否是数字组成
	function isNumberAndChar(str){
		var reg = /^[0-9a-zA-Z]*$/g;
		return reg.test(str);
	}
</script>
</head>
<body ontouchstart="">
	<div
		style="background-color:#BF172E;height:50px;margin:0px;padding:0px;width:100%; position:fixed; top:0;z-index: 9999;">
		<div style="padding:0.07rem 0.15rem;">
			<div
				style="text-align: center;height: 50px;  line-height:50px; color: #F3FFFF; ">大诚中医</div>
		</div>
	</div>
	<!-- margin的顺序是上右下左 -->
	<div style="margin-top:50px;"></div>
	<form action="weChatUser/updateUser" method="post"
		onsubmit="return check();">
		<div style="display:none">
			<input id="uId" type="text" value="${uId}"> <input id="code"
				type="text" value="">
		</div>
		<div class="weui-cell init_size">
			<div class="weui-cell__hd">
				<label class="weui-label" style="width: 60px;">手机号</label>
			</div>
			<div class="weui-cell__bd">
				<input id="phone" name="phone" class="weui-input"
					placeholder="请输入手机号" pattern="[0-9]*" type="tel"
					style="ime-mode:Disabled">
			</div>
			<input type="button" id="test" style="display:inline;"
				class="weui-btn weui-btn_mini weui-btn_primary fix_size"
				onclick="time()" value="点击发送验证码">
		</div>
		<div class="weui-cell init_size">
			<div class="weui-cell__hd">
				<label class="weui-label" style="width: 60px;">验证码</label>
			</div>
			<div class="weui-cell__bd">
				<input id="checkCode" name="checkCode" class="weui-input"
					placeholder="请输入验证码" pattern="[0-9]*" type="text"
					style="ime-mode:Disabled">
			</div>
		</div>
		<div class="weui-btn-area">
			<a id="passwordModify" href="javascript:;"
				class="weui-btn weui-btn_primary init_size"
				onclick="modifyStoredPassword();"> 确定 </a>
		</div>
	</form>
</body>
</html>