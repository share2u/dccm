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
						<input type="hidden" name="CUSTOMSTORED_ID" id="CUSTOMSTORED_ID" value="${pd.CUSTOMSTORED_ID}"/>
						<div id="zhongxin" style="padding-top: 13px;">
						<table id="table_report" class="table table-striped table-bordered table-hover">
							
							 <tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">手机号:</td>
								<td><input type="text" name="PHONE" id="PHONE" value="${pd.PHONE}" maxlength="255" placeholder="这里输入手机号" title="个人优惠比例" style="width:98%;" onChange="getValue(this.value)"/></td>
							
							</tr>
							
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">姓名:</td>
								<td>
								<select class="chosen-select form-control" name="UID" id="UID"  style="vertical-align:top;" style="width:98%;" >
								<option value=""></option>	
								</select>
							   </td>
							</tr>
						
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">余额:</td>
								<td><input type="text" name="REMAIN_MONEY" id="REMAIN_MONEY" value="${pd.REMAIN_MONEY}" maxlength="255" placeholder="请输入储值卡余额" title="个人优惠比例" style="width:98%;"/></td>
								
							</tr>
							
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">剩余点数:</td>
								<td><input type="text" name="REMAIN_POINTS" id="REMAIN_POINTS" value="${pd.REMAIN_POINTS}" maxlength="255" placeholder="请输入剩余点数，没有则填0" title="剩余点数" style="width:98%;"/></td>			
							</tr>
							
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">备注:</td>
								<td><input type="text" name="REMARK" id="REMARK" value="${pd.REMARK}" maxlength="255" placeholder="请输入备注" title="备注" style="width:98%;"/></td>			
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
		
			if($("#PHONE").val()==""){
				$("#PHONE").tips({
					side:3,
		            msg:'请输入手机号',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#OPENID").focus();
			return false;
			}
			if($("#UID").val()==""){
				$("#UID").tips({
					side:3,
		            msg:'请选择客户姓名',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#UID").focus();
			return false;
			
			}
			
			if($("#REMAIN_MONEY").val()==""){
				$("#REMAIN_MONEY").tips({
					side:3,
		            msg:'请输入储值卡余额',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#REMAIN_MONEY").focus();
			return false;
			}
			if($("#REMAIN_POINTS").val()==""){
				$("#REMAIN_POINTS").tips({
					side:3,
		            msg:'请输入剩余点数，没有则填0',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#REMAIN_POINTS").focus();
			return false;
			}
			
			
			$("#Form").submit();
			$("#zhongxin").hide();
			$("#zhongxin2").show();
	
		}
		$(function() {
			//日期框
			$('.date-picker').datepicker({autoclose: true,todayHighlight: true});
		});
		
	function getValue(value){
		var sel = $("#UID");
		var selVal = $("#UID").val();
		var phone = $("#PHONE").val();
		
		document.getElementById("UID").innerHTML = "";
		$.ajax({
				url:"prestoremx/queryUsernameByPhone",
				data:{"phone":value},
				dataType:"json",
				type:'post',
				success: function(data){
					for (var i = 0; i < data.userlist.length; i++) {
						sel.append("<option value='" + data.userlist[i].uid + "'>" + data.userlist[i].name + "</option>");
					}
				}
			});
	}
		</script>
</body>
</html>