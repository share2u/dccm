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
					
					<form action="refund/${msg }.do" name="Form" id="Form" method="post">
						<input type="hidden" name="REFUND_ID" id="REFUND_ID" value="${pd.REFUND_ID}"/>
						<div id="zhongxin" style="padding-top: 13px;">
						<table id="table_report" class="table table-striped table-bordered table-hover">
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">用户ID:</td>
								<td><input type="number" name="UID" id="UID" value="${pd.UID}" maxlength="32" placeholder="这里输入用户ID" title="用户ID" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">退款时间:</td>
								<td><input type="text" name="TIME" id="TIME" value="${pd.TIME}" maxlength="255" placeholder="这里输入退款时间" title="退款时间" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">储值卡退入:</td>
								<td><input type="number" name="PRECARD_ADD" id="PRECARD_ADD" value="${pd.PRECARD_ADD}" maxlength="32" placeholder="这里输入储值卡退入" title="储值卡退入" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">储值卡返点退入:</td>
								<td><input type="number" name="PRECARD_POINTS_ADD" id="PRECARD_POINTS_ADD" value="${pd.PRECARD_POINTS_ADD}" maxlength="32" placeholder="这里输入储值卡返点退入" title="储值卡返点退入" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">积分减去:</td>
								<td><input type="number" name="INTEGRATION_MINUS" id="INTEGRATION_MINUS" value="${pd.INTEGRATION_MINUS}" maxlength="32" placeholder="这里输入积分减去" title="积分减去" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">退款金额:</td>
								<td><input type="number" name="PAY_MONEY" id="PAY_MONEY" value="${pd.PAY_MONEY}" maxlength="32" placeholder="这里输入退款金额" title="退款金额" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">订单创建时间:</td>
								<td><input type="text" name="CREATE_TIME" id="CREATE_TIME" value="${pd.CREATE_TIME}" maxlength="255" placeholder="这里输入订单创建时间" title="订单创建时间" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">备注:</td>
								<td><input type="text" name="CONTENT" id="CONTENT" value="${pd.CONTENT}" maxlength="255" placeholder="这里输入备注" title="备注" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">操作员:</td>
								<td><input type="text" name="SERVICE_ID" id="SERVICE_ID" value="${pd.SERVICE_ID}" maxlength="100" placeholder="这里输入操作员" title="操作员" style="width:98%;"/></td>
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
			if($("#UID").val()==""){
				$("#UID").tips({
					side:3,
		            msg:'请输入用户ID',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#UID").focus();
			return false;
			}
			if($("#TIME").val()==""){
				$("#TIME").tips({
					side:3,
		            msg:'请输入退款时间',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#TIME").focus();
			return false;
			}
			if($("#PRECARD_ADD").val()==""){
				$("#PRECARD_ADD").tips({
					side:3,
		            msg:'请输入储值卡退入',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#PRECARD_ADD").focus();
			return false;
			}
			if($("#PRECARD_POINTS_ADD").val()==""){
				$("#PRECARD_POINTS_ADD").tips({
					side:3,
		            msg:'请输入储值卡返点退入',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#PRECARD_POINTS_ADD").focus();
			return false;
			}
			if($("#INTEGRATION_MINUS").val()==""){
				$("#INTEGRATION_MINUS").tips({
					side:3,
		            msg:'请输入积分减去',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#INTEGRATION_MINUS").focus();
			return false;
			}
			if($("#PAY_MONEY").val()==""){
				$("#PAY_MONEY").tips({
					side:3,
		            msg:'请输入退款金额',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#PAY_MONEY").focus();
			return false;
			}
			if($("#CREATE_TIME").val()==""){
				$("#CREATE_TIME").tips({
					side:3,
		            msg:'请输入订单创建时间',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#CREATE_TIME").focus();
			return false;
			}
			if($("#CONTENT").val()==""){
				$("#CONTENT").tips({
					side:3,
		            msg:'请输入备注',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#CONTENT").focus();
			return false;
			}
			if($("#SERVICE_ID").val()==""){
				$("#SERVICE_ID").tips({
					side:3,
		            msg:'请输入操作员',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#SERVICE_ID").focus();
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
		</script>
</body>
</html>