<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
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

							<form action="order/${msg }.do" name="Form" id="Form"
								method="post">
								<input type="hidden" name="total_money" id="total_money"
									value="${total_money}" />
								<input type="hidden" name="refund_card_money" id="refund_card_money"
									value="${refund_card_money}" />
								<input type="hidden" name="refund_money" id="refund_money"
									value="${refund_money}" />	
								<input type="hidden" name="refund_card_points" id="refund_card_points"
									value="${refund_card_points}" />
								<input type="hidden" name="ORDER_ID" id="ORDER_ID"
									value="${pd.ORDER_ID}" />
								<input type="hidden" name="UID" id="UID"
									value="${pd.UID}" />
									
								<div id="zhongxin" style="padding-top: 13px;">
									<table id="table_report"
										class="table table-striped table-bordered table-hover">
										<%-- <tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">会员:</td>
								<td><input type="number" name="UID" id="UID" value="${pd.UID}" maxlength="32" placeholder="这里输入会员" title="会员" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">门店:</td>
								<td><input type="text" name="STORE_ID" id="STORE_ID" value="${pd.STORE_ID}" maxlength="255" placeholder="这里输入门店" title="门店" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">服务项目:</td>
								<td><input type="number" name="PROJECT_ID" id="PROJECT_ID" value="${pd.PROJECT_ID}" maxlength="32" placeholder="这里输入服务项目" title="服务项目" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">客服:</td>
								<td><input type="text" name="SERVICE_STAFF_ID" id="SERVICE_STAFF_ID" value="${pd.SERVICE_STAFF_ID}" maxlength="255" placeholder="这里输入客服" title="客服" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">创建时间:</td>
								<td><input type="text" name="CREATE_TIME" id="CREATE_TIME" value="${pd.CREATE_TIME}" maxlength="255" placeholder="这里输入创建时间" title="创建时间" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">订单状态:</td>
								<td><input type="number" name="ORDER_STATUS" id="ORDER_STATUS" value="${pd.ORDER_STATUS}" maxlength="32" placeholder="这里输入订单状态" title="订单状态" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">订单URL:</td>
								<td><input type="text" name="URL" id="URL" value="${pd.URL}" maxlength="255" placeholder="这里输入订单URL" title="订单URL" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">tui:</td>
								<td><input type="text" name="RECOMMEND_TIME" id="RECOMMEND_TIME" value="${pd.RECOMMEND_TIME}" maxlength="255" placeholder="这里输入评价时间" title="评价时间" style="width:98%;"/></td>
							</tr> --%>
										<%-- <tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">过期时间:</td>
								<td><input class="span10 date-picker" name="EXPIRE_TIME" id="EXPIRE_TIME" value="${pd.EXPIRE_TIME}" type="text" data-date-format="yyyy-mm-dd" readonly="readonly" style="width:88px;" placeholder="过期时间" title="过期时间"><input type="text" name="EXPIRE_TIME" id="EXPIRE_TIME" value="${pd.EXPIRE_TIME}" maxlength="255" placeholder="这里输入过期时间" title="过期时间" style="width:98%;"/></td>
							</tr> --%>
										<%-- <tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">备注:</td>
								<td><input type="text" name="REMARK" id="REMARK" value="${pd.REMARK}" maxlength="255" placeholder="这里输入备注" title="备注" style="width:98%;"/></td>
							</tr> --%>
										<%-- <tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">医生:</td>
								<td><input type="text" name="STAFF_ID" id="STAFF_ID" value="${pd.STAFF_ID}" maxlength="255" placeholder="这里输入医生" title="医生" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">应收金额:</td>
								<td><input type="text" name="ORDER_MONEY" id="ORDER_MONEY" value="${pd.ORDER_MONEY}" maxlength="9" placeholder="这里输入应收金额" title="应收金额" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">折扣:</td>
								<td><input type="text" name="PROPORTION" id="PROPORTION" value="${pd.PROPORTION}" maxlength="5" placeholder="这里输入折扣" title="折扣" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">使用积分:</td>
								<td><input type="number" name="CREDIT" id="CREDIT" value="${pd.CREDIT}" maxlength="32" placeholder="这里输入使用积分" title="使用积分" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">优惠券:</td>
								<td><input type="text" name="DISCOUNT_ID" id="DISCOUNT_ID" value="${pd.DISCOUNT_ID}" maxlength="11" placeholder="这里输入优惠券" title="优惠券" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">实收金额:</td>
								<td><input type="text" name="PAY_MONEY" id="PAY_MONEY" value="${pd.PAY_MONEY}" maxlength="9" placeholder="这里输入实收金额" title="实收金额" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">退款金额:</td>
								<td><input type="text" name="REFUND" id="REFUND" value="${pd.REFUND}" maxlength="9" placeholder="这里输入退款金额" title="退款金额" style="width:98%;"/></td>
							</tr> --%>
									</table>
								</div>
								<div id="zhongxin2" class="center" style="display:none">
									<br /> <br /> <br /> <br /> <br /> <img
										src="static/images/jiazai.gif" /><br />
									<h4 class="lighter block green">提交中...</h4>
								</div>
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

	<c:if test="${'editRefund' == msg }">
		<div>
			<iframe name="treeFrame" id="treeFrame" frameborder="0"
				src="<%=basePath%>/ordermx/list.do?ORDER_ID=${pd.ORDER_ID}"
				style="margin:0 auto;width:805px;height:368px;;"></iframe>
				<!-- 需扣除手续费：<b>5</b>元， -->共退还给用户:<b>${total_money}</b>元。
				<br/>其中，退往用户余额：<b>${refund_money}</b>元，储值卡：<b>${refund_card_money}</b>元，
				储值卡返点：<b>${refund_card_points}</b>元.
		</div>
	
</c:if>

	<footer>
		<div style="width: 100%;padding-bottom: 2px;" class="center">
			<a class="btn btn-mini btn-primary" onclick="save();">确认退费</a> <a
				class="btn btn-mini btn-danger" onclick="top.Dialog.close();">取消</a>
		</div>
	</footer>

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
		var isCommitted = false;
		function save() {
			if ($("#EXPIRE_TIME").val() == "") {
				$("#EXPIRE_TIME").tips({
					side : 3,
					msg : '请输入过期时间',
					bg : '#AE81FF',
					time : 2
				});
				$("#EXPIRE_TIME").focus();
				return false;
			}
			if ($("#REMARK").val() == "") {
				$("#REMARK").tips({
					side : 3,
					msg : '请输入备注',
					bg : '#AE81FF',
					time : 2
				});
				$("#REMARK").focus();
				return false;
			}
			
			if(isCommitted==false){
                isCommitted = true;//提交表单后，将表单是否已经提交标识设置为true
	            $("#Form").submit();
				$("#zhongxin").hide();
				$("#zhongxin2").show();
             }else{
                 return false;//返回false那么表单将不提交
             }


		}

		$(function() {
			//日期框
			$('.date-picker').datepicker({
				autoclose : true,
				todayHighlight : true
			});
		});
	</script>
</body>
</html>