<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>服务直通车</title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=no">
	<link rel="stylesheet" type="text/css" href="static/css/wechat/main.css"/>
	<link rel="stylesheet" type="text/css" href="static/css/wechat/home.css"/>
	<link rel="stylesheet" href="static/css/wechat/weui.min.css" type="text/css"></link>
	<script type="text/javascript" src="static/js/jquery-1.7.2.js"></script>
   	<script type="text/javascript" src="static/js/wechatMajor.js" ></script>
   	<script type="text/javascript">
   		function toUserCenter(){
   			var uId="${sessionScope.uId}";
   			if(uId != -1 && uId !=""){
				window.location.href="weChat/userCenter";
  			}else{
  				$("#androidDialog2").css('opacity','1');
  				$("#androidDialog2").css('display','none');
   			}
   		}
   	</script>
  </head> 
  <body>
  	<div style="background-color:#BF172E;height:50px;margin:0px;padding:0px;width:100%;">
  		<div style="padding:0.07rem 0.15rem;">
  			<div
				style="float: right; width: 10%;margin-top: 0.5rem;margin-right: 0.5rem;">
				<a href="weChat/userCenter"> <img alt=""
					src="static/images/wechat/wechatUserCenter.png"> </a>
			</div>
			<div
				style="margin-left:10%; text-align: center;height: 50px;  line-height:50px; color: #F3FFFF; ">大诚中医</div>
		</div>
  	</div >
  	
  	<div class="js_dialog" id="androidDialog2" style="opacity: 0; display:none;">
            <div class="weui-mask"></div>
            <div class="weui-dialog weui-skin_android">
                <div class="weui-dialog__bd">
                    	您未授权，请返回微信重新进行授权
                </div>
                <div class="weui-dialog__ft">
                    <a href="javascript:;" class="weui-dialog__btn weui-dialog__btn_primary">知道了</a>
                </div>
            </div>
        </div>
  	<div  class="major1"  >
         <a href="javascript:void(0);" class="lt"></a>
   		<a href="javascript:;" class="rt"></a>
		   <ul>
			  <li style="display:block;"><a href="javascript:void(0);"><img src="static/images/wechat/687314943950162146.jpg"  /></a></li>
		          <!--   <li ><a href="javascript:void(0);"><img src="static/images/a102.jpg" /></a></li>
		            <li ><a href="javascript:void(0);"><img src="static/images/a103.jpg" /></a></li> -->
		  </ul>
    </div>
	<div id="lists"  >
		<ul id="pics">
		<c:forEach items="${modules}" var="module">
			<li class="info">
		    	<a href="weChatStore/getStoresByModuleId/${module.mId}">
		           <img src="uploadFiles/uploadFile/${module.mImg}" alt="${module.mInfo}"/>
		           <p class="name">${module.mName}</p>
		        </a>
		    </li>
		</c:forEach>
	    </ul>
  	</div>
  	  
  </body>
</html>
