<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html lang="en">
	<head>
	<base href="<%=basePath%>">
	<!-- 下拉框 -->
	<link rel="stylesheet" href="static/ace/css/chosen.css" />
	<!-- jsp文件头和头部 -->
	<%@ include file="../../system/index/top.jsp"%>
	<!-- 日期框 -->
	<link rel="stylesheet" href="static/ace/css/datepicker.css" />
</head>
<body class="no-skin">
<!-- /section:basics/navbar.layout -->
<div class="main-container" id="main-container">
	<!-- /section:basics/sidebar -->
	<div class="main-content">
		<div class="main-content-inner">
			<div class="page-content">
				<div class="row">
					<div class="col-xs-12">
					
					<form action="customstored/${msg }.do" name="Form" id="Form" method="post">
						<input type="hidden" name="UID" id="UID" value="${pd.UID}"/>
						<div id="zhongxin" style="padding-top: 13px;">
						<table id="table_report" class="table table-striped table-bordered table-hover">
							
							 <tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">手机号:</td>
								<td>${pd.PHONE}</td>
							
							</tr>
							
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">姓名:</td>
								<td>${pd.NAME}</td>
							</tr>
						
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">请输入新密码:</td>
								<td><input type="password" name="PASSWORD" id="PASSWORD" value="" maxlength="255" style="width:98%;"/></td>
							</tr>
							
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">请确认密码:</td>
								<td><input type="password" name="PASSWORD1" id="PASSWORD1" value="" maxlength="255" style="width:98%;"/></td>
							</tr>
							
							<tr>
								<td style="text-align: center;" colspan="10">
									<a class="btn btn-mini btn-primary" onclick="save();">保存</a>
									<a class="btn btn-mini btn-danger" onclick="top.Dialog.close();">取消</a>
								</td>
							</tr>
						</table>
						</div>
						<div id="zhongxin2" class="center" style="display:none"><br/><br/><br/><br/><br/><img src="static/images/jiazai.gif" /><br/><h4 class="lighter block green">提交中...</h4></div>
					</form>
					</div>
					<!-- /.col -->
				</div>
				<!-- /.row -->
			</div>
			<!-- /.page-content -->
		</div>
	</div>
	<!-- /.main-content -->
</div>
<!-- /.main-container -->


	<!-- 页面底部js¨ -->
	<%@ include file="../../system/index/foot.jsp"%>
	<!-- 下拉框 -->
	<script src="static/ace/js/chosen.jquery.js"></script>
	<!-- 日期框 -->
	<script src="static/ace/js/date-time/bootstrap-datepicker.js"></script>
	<!--提示框-->
	<script type="text/javascript" src="static/js/jquery.tips.js"></script>
		<script type="text/javascript">
		$(top.hangge());
		//保存
		function save(){
		
			if($("#PASSWORD").val()!=$("#PASSWORD1").val()){
				$("#PASSWORD1").tips({
					side:3,
		            msg:'两次密码输入不一致！',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#PASSWORD1").focus();
			return false;
			}
			
			
			$("#Form").submit();
			$("#zhongxin").hide();
			$("#zhongxin2").show();
	
		}
		
		</script>
</body>
</html>