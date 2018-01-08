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
					
					<form action="orderpaydetail/${msg }.do" name="Form" id="Form" method="post">
						<input type="hidden" name="ORDERPAYDETAIL_ID" id="ORDERPAYDETAIL_ID" value="${pd.ORDERPAYDETAIL_ID}"/>
						<input type="hidden" name="_ID" id="_ID" value="${pd._ID}"/>
						<div id="zhongxin" style="padding-top: 13px;">
						<table id="table_report" class="table table-striped table-bordered table-hover">
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">会员:</td>
								<td><input type="number" name="UID" id="UID" value="${pd.UID}" maxlength="32" placeholder="这里输入会员" title="会员" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">订单:</td>
								<td><input type="text" name="ORDER_ID" id="ORDER_ID" value="${pd.ORDER_ID}" maxlength="32" placeholder="这里输入订单" title="订单" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">订单总金额:</td>
								<td><input type="text" name="ORDER_MONEY" id="ORDER_MONEY" value="${pd.ORDER_MONEY}" maxlength="12" placeholder="这里输入订单总金额" title="订单总金额" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">支付金额:</td>
								<td><input type="text" name="PAY_MONEY" id="PAY_MONEY" value="${pd.PAY_MONEY}" maxlength="12" placeholder="这里输入支付金额" title="支付金额" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">付款方式:</td>
								<td><input type="number" name="PAY_METHOD" id="PAY_METHOD" value="${pd.PAY_METHOD}" maxlength="32" placeholder="这里输入付款方式" title="付款方式" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">支付时间:</td>
								<td><input class="span10 date-picker" name="PAY_TIME" id="PAY_TIME" value="${pd.PAY_TIME}" type="text" data-date-format="yyyy-mm-dd" readonly="readonly" placeholder="支付时间" title="支付时间" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">备注:</td>
								<td><input type="text" name="REMARK" id="REMARK" value="${pd.REMARK}" maxlength="255" placeholder="这里输入备注" title="备注" style="width:98%;"/></td>
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
		            msg:'请输入会员',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#UID").focus();
			return false;
			}
			if($("#ORDER_ID").val()==""){
				$("#ORDER_ID").tips({
					side:3,
		            msg:'请输入订单',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#ORDER_ID").focus();
			return false;
			}
			if($("#ORDER_MONEY").val()==""){
				$("#ORDER_MONEY").tips({
					side:3,
		            msg:'请输入订单总金额',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#ORDER_MONEY").focus();
			return false;
			}
			if($("#PAY_MONEY").val()==""){
				$("#PAY_MONEY").tips({
					side:3,
		            msg:'请输入支付金额',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#PAY_MONEY").focus();
			return false;
			}
			if($("#PAY_METHOD").val()==""){
				$("#PAY_METHOD").tips({
					side:3,
		            msg:'请输入付款方式',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#PAY_METHOD").focus();
			return false;
			}
			if($("#PAY_TIME").val()==""){
				$("#PAY_TIME").tips({
					side:3,
		            msg:'请输入支付时间',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#PAY_TIME").focus();
			return false;
			}
			if($("#REMARK").val()==""){
				$("#REMARK").tips({
					side:3,
		            msg:'请输入备注',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#REMARK").focus();
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