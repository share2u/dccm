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

<style>
@media print {
	table {
		page-break-after: always;
	}
}
</style>

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

							<form action="customappoint/${msg }.do" name="Form" id="Form"
								method="post">
								<input type="hidden" name="REFUND_MONEY_ID" id="REFUND_MONEY_ID"
									value="${pd.REFUND_MONEY_ID}" />
								<div id="zhongxin" style="padding-top: 13px;">
									<%-- <table id="table_report" >
										<h3>
											<p class="text-center"><b>大诚中医连锁医疗机构 就诊卡</b></p>
										</h3>
										<br/>
										<br/>
										<tr>
											<td noWrap style="width:150px;"><b>建档门店：</b></td>
											<td noWrap>${pd.STORE_NAME}</td>
											<td noWrap style="width:150px;text-align: right"><b>缴费时间：</b></td>
											<td noWrap style="width:150px;text-align: right">${pd.CREATE_TIME}</td>
										</tr>
										<tr>
											<td noWrap style="width:150px;"><b>订单号：</b></td>
											<td noWrap>${pd.ORDER_ID}</td>
											<td noWrap style="width:150px;text-align: right"><b>预约时间：</b></td>
											<td noWrap style="width:150px;text-align: right">${pd.RECOMMEND_TIME}</td>
										</tr>
										<tr>
											<td noWrap style="width:150px;"><b>预约医生：</b></td>
											<td noWrap>${pd.STAFF_NAME}</td>
											<td noWrap style="width:150px;text-align: right"><b>就诊时间：</b></td>
											<td noWrap style="width:150px;text-align: right">${pd.ORDER_PRINT_TIME}</td>
											<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b>客</b></td>
										</tr>
										<tr>
											<td noWrap style="width:150px;"><b>患者姓名（微信昵称）：</b></td>
											<td noWrap>${pd.WECHAT_NAME}(${pd.username })</td>
											<td noWrap style="width:150px;text-align: right"><b>预约项目：</b></td>
											<td noWrap style="width:150px;text-align: right">${pd.PNAME}</td>
											<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b>户</b></td>
											</tr>
										
										<tr>
											<td noWrap style="width:150px;"><b>性别：</b></td>
											<td noWrap style="width:150px;">
												<c:if test="${pd.sex==1}">男</c:if>
												<c:if test="${pd.sex==2}">女</c:if>
											</td>
											<td noWrap style="width:150px;text-align: right"><b>应付金额：</b></td>
											<td noWrap style="width:150px;text-align: right">${pd.ORDER_MONEY}</td>
											<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b>联</b></td>
											</tr>

										<tr>
											<td noWrap style="width:150px;"><b>联系电话：</b></td>
											<td noWrap style="width:150px;">${pd.WECHAT_PHONE}</td>
											<td noWrap style="width:150px;text-align: right"><b>实付金额：</b></td>
											<td noWrap style="width:150px;text-align: right">${pd.PAY_MONEY}</td>
											
										</tr>
										
										<tr>
											<td></td>
											<td></td>
												
											<td noWrap style="width:150px;text-align: right"><b>收费人员：</b></td>
											<td noWrap style="width:150px;text-align: right">${pd.SERVICE_STAFF_NAME}</td>
										
											</tr>
										<tr>
											<td></td>
											<td></td>
											<td noWrap style="width:150px;text-align: right"><b>操作员：</b></td>
											<td noWrap style="width:150px;text-align: right">${pd.ORDER_PRINTER}</td>
											</tr>
											<tr>
											<td style="width:150px;" colspan=3 rowspan=3 valign = "top"><b>备注：</b>${pd.REMARK}</td>
											
											
											</tr>
										<tr><td>&nbsp;</td></tr>
										<tr><td>&nbsp;</td></tr>

										<tr><td>&nbsp;</td></tr>

										<tr>
											<td noWrap style="width:150px;"><b>主治医生（签字）：</b></td>
											<td></td>
											<td noWrap style="width:150px;"><b>客服人员（签字）：</b></td>

									
											
										</tr>
										<!-- 
							<tr>
								<td style="text-align: center;" colspan="10">
									<a class="btn btn-mini btn-primary" onclick="save();">保存</a>
									<a class="btn btn-mini btn-danger" onclick="top.Dialog.close();">取消</a>
								</td>
							</tr> -->
									</table>
									
									<table id="table_report">
										<h3>
											<p class="text-center"><b>大诚中医连锁医疗机构 就诊卡</b></p>
										</h3>
										<br/>
										<br/>
										<tr>
											<td noWrap style="width:150px;"><b>建档门店：</b></td>
											<td noWrap>${pd.STORE_NAME}</td>
											<td noWrap style="width:150px;text-align: right"><b>缴费时间：</b></td>
											<td noWrap style="width:150px;text-align: right">${pd.CREATE_TIME}</td>
											
										</tr>
										<tr>
											<td noWrap style="width:150px;"><b>订单号：</b></td>
											<td noWrap>${pd.ORDER_ID}</td>
											<td noWrap style="width:150px;text-align: right"><b>预约时间：</b></td>
											<td noWrap style="width:150px;text-align: right">${pd.RECOMMEND_TIME}</td>
										</tr>
										<tr>
											<td noWrap style="width:150px;"><b>预约医生：</b></td>
											<td noWrap>${pd.STAFF_NAME}</td>
											<td noWrap style="width:150px;text-align: right"><b>就诊时间：</b></td>
											<td noWrap style="width:150px;text-align: right">${pd.ORDER_PRINT_TIME}</td>
											<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b>存</b></td>
										</tr>
										<tr>
											<td noWrap style="width:150px;"><b>患者姓名（微信昵称）：</b></td>
											<td noWrap>${pd.WECHAT_NAME}(${pd.username })</td>
											<td noWrap style="width:150px;text-align: right"><b>预约项目：</b></td>
											<td noWrap style="width:150px;text-align: right">${pd.PNAME}</td>
											<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b>根</b></td>
											</tr>
										
										<tr>
											<td noWrap style="width:150px;"><b>性别：</b></td>
											<td noWrap style="width:150px;">
												<c:if test="${pd.sex==1}">男</c:if>
												<c:if test="${pd.sex==2}">女</c:if>
											</td>
											<td noWrap style="width:150px;text-align: right"><b>应付金额：</b></td>
											<td noWrap style="width:150px;text-align: right">${pd.ORDER_MONEY}</td>
											<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b>联</b></td>
											</tr>

										<tr>
											<td noWrap style="width:150px;"><b>联系电话：</b></td>
											<td noWrap style="width:150px;">${pd.WECHAT_PHONE}</td>
											<td noWrap style="width:150px;text-align: right"><b>实付金额：</b></td>
											<td noWrap style="width:150px;text-align: right">${pd.PAY_MONEY}</td>
											
										</tr>
										
										<tr>
											<td></td>
											<td></td>
												
											<td noWrap style="width:150px;text-align: right"><b>收费人员：</b></td>
											<td noWrap style="width:150px;text-align: right">${pd.SERVICE_STAFF_NAME}</td>
										
											</tr>
										<tr>
											<td></td>
											<td></td>
											<td noWrap style="width:150px;text-align: right"><b>操作员：</b></td>
											<td noWrap style="width:150px;text-align: right">${pd.ORDER_PRINTER}</td>
											</tr>
											<tr>
											<td style="width:150px;" colspan=3 rowspan=3 valign = "top"><b>备注：</b>${pd.REMARK}</td>
											
											
											</tr>
										<tr><td>&nbsp;</td></tr>
										<tr><td>&nbsp;</td></tr>

										<tr><td>&nbsp;</td></tr>

										<tr>
											<td noWrap style="width:150px;"><b>主治医生（签字）：</b></td>
											<td></td>
											<td noWrap style="width:150px;"><b>客服人员（签字）：</b></td>
										
										</tr>
										<!-- 
							<tr>
								<td style="text-align: center;" colspan="10">
									<a class="btn btn-mini btn-primary" onclick="save();">保存</a>
									<a class="btn btn-mini btn-danger" onclick="top.Dialog.close();">取消</a>
								</td>
							</tr> -->
									</table> --%>






									<table id="table_report">
										<h3>
											<p class="text-center">
												<b>大诚中医连锁医疗机构 就诊卡</b>
											</p>
										</h3>
										<br />
										<br />
										<h4>
											<p class="text-left">验证码：${pd.APPOINT_CODE}</p>
										</h4>
										<tr>
											<td noWrap style="width:150px;"></td>
											<td noWrap></td>
											<td noWrap style="width:150px;text-align: right"><b>预约项目：</b>
											</td>
											<td noWrap style="width:150px;text-align: right"><b>${pd.PNAME}</b>
											</td>

											</td>
										</tr>
										<tr>
											<td noWrap style="width:150px;"><b>建档门店：</b>
											</td>
											<td noWrap>${pd.STORE_NAME}</td>
											<td noWrap style="width:150px;text-align: right"><b>剩余次数：</b>
											</td>
											<td noWrap style="width:150px;text-align: right">${pd.COUNT}</td>
											
										</tr>
										<tr>
											<td noWrap style="width:150px;"><b>订单号：</b>
											</td>
											<td noWrap>${pd.ORDER_ID}</td>

										</tr>
										<tr>
											<td noWrap style="width:150px;"><b>预约医生：</b>
											</td>
											<td noWrap>${pd.STAFF_NAME}</td>
											<td noWrap style="width:150px;text-align: right"><b>预约时间：</b>
											</td>
											<td noWrap style="width:150px;text-align: right">${pd.RECOMMEND_TIME}</td>
											<%-- <td noWrap style="width:150px;text-align: right"><b>就诊时间：</b>
											</td>
											<td noWrap style="width:150px;text-align: right">${pd.ORDER_PRINT_TIME}</td> --%>
										</tr>
										<tr>
											<td noWrap style="width:150px;"><b>患者姓名（微信昵称）：</b>
											</td>
											<td noWrap>${pd.WECHAT_NAME}(${pd.username })</td>
											<td noWrap style="width:150px;text-align: right"><b>缴费时间：</b>
											</td>
											<td noWrap style="width:150px;text-align: right">${pd.CREATE_TIME}</td>
										</tr>

										<tr>
											<!-- <td noWrap style="width:150px;"><b>性别：</b>
											</td> -->
											<%-- <td noWrap style="width:150px;"><c:if
													test="${pd.sex==1}">男</c:if> <c:if test="${pd.sex==2}">女</c:if>
											</td> --%>
											<td noWrap style="width:150px;"><b>联系电话：</b>
											</td>
											<td noWrap style="width:150px;">${pd.WECHAT_PHONE}</td>
											<td noWrap style="width:150px;text-align: right"><b>缴费方式：</b>
											<td noWrap style="width:150px;text-align: right"><c:if
													test="${pd.PAY_METHOD==0}">微信支付</c:if> <c:if
													test="${pd.PAY_METHOD==1}">支付宝支付</c:if> <c:if
													test="${pd.PAY_METHOD==2}">储值卡支付</c:if> <c:if
													test="${pd.PAY_METHOD==3}">现金支付</c:if> <c:if
													test="${pd.PAY_METHOD==4}">银行卡支付</c:if> <c:if
													test="${pd.PAY_METHOD==5}">用户余额支付</c:if>
											</td>
										</tr>

										<%-- 										<tr>
											
											
											<td noWrap style="width:150px;text-align: right"><b>实付金额：</b>
											</td>
											<td noWrap style="width:150px;text-align: right">${pd.PAY_MONEY}</td>

										</tr> --%>
										<tr>
											<td></td>
											<td></td>
											<td noWrap style="width:150px;text-align: right"><b>应付金额：</b>
											</td>
											<td noWrap style="width:150px;text-align: right">${pd.ORDER_MONEY}</td>
										</tr>
										<tr>
											<td></td>
											<td></td>

											<td noWrap style="width:150px;text-align: right"><b>收费人员：</b>
											</td>
											<td noWrap style="width:150px;text-align: right">${pd.SERVICE_STAFF_NAME}</td>

										</tr>
										<tr>
											<td></td>
											<td></td>
											<!-- <td noWrap style="width:150px;text-align: right"><b>操作员：</b>
											</td> -->
											<%-- 
											<td noWrap style="width:150px;text-align: right">${pd.ORDER_PRINTER}</td> --%>
										</tr>
										<tr>
											<td style="width:150px;" colspan=3 rowspan=3 valign="top"><b>备注：</b>${pd.REMARK}</td>


										</tr>
										<tr>
											<td>&nbsp;</td>
										</tr>
										<tr>
											<td>&nbsp;</td>
										</tr>

										<tr>
											<td>&nbsp;</td>
										</tr>
										<tr>
											<td noWrap style="width:150px;" onClick="window.print()"
												style="border-style:hidden"><b>主治医生（签字）：</b>
											</td>
											<td></td>
											<td noWrap style="width:150px;"><b>客服人员（签字）：</b>
											</td>
										</tr>

										<tr>
											<td>&nbsp;</td>
											<td>&nbsp;</td>
										</tr>

										<tr>
											<td noWrap style="width:150px;" style="border-style:hidden"><b>助理医生（签字）：</b>
											</td>
											<td></td>
											<!-- <td noWrap style="width:150px;"><b>下次就诊时间：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;年&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;月&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;日</b>
											</td> -->
										</tr>
										<tr>
											<td>&nbsp;</td>
											<td>&nbsp;</td>
										</tr>

										<tr>
											<td></td>
											<td></td>
											<!-- <td noWrap style="width:150px;"><b>下次就诊项目：</b>
											</td> -->
										</tr>
										<!-- 
							<tr>
								<td style="text-align: center;" colspan="10">
									<a class="btn btn-mini btn-primary" onclick="save();">保存</a>
									<a class="btn btn-mini btn-danger" onclick="top.Dialog.close();">取消</a>
								</td>
							</tr> -->
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
		function save() {
			if ($("#UID").val() == "") {
				$("#UID").tips({
					side : 3,
					msg : '请输入用户ID',
					bg : '#AE81FF',
					time : 2
				});
				$("#UID").focus();
				return false;
			}
			if ($("#REFUND_PRESTORE_MONEY").val() == "") {
				$("#REFUND_PRESTORE_MONEY").tips({
					side : 3,
					msg : '请输入可退余额',
					bg : '#AE81FF',
					time : 2
				});
				$("#REFUND_PRESTORE_MONEY").focus();
				return false;
			}
			if ($("#REFUND_REMAIN_MONEY").val() == "") {
				$("#REFUND_REMAIN_MONEY").tips({
					side : 3,
					msg : '请输入可退储值卡',
					bg : '#AE81FF',
					time : 2
				});
				$("#REFUND_REMAIN_MONEY").focus();
				return false;
			}
			if ($("#TIME").val() == "") {
				$("#TIME").tips({
					side : 3,
					msg : '请输入退款时间',
					bg : '#AE81FF',
					time : 2
				});
				$("#TIME").focus();
				return false;
			}
			if ($("#SERVICE_ID").val() == "") {
				$("#SERVICE_ID").tips({
					side : 3,
					msg : '请输入操作员',
					bg : '#AE81FF',
					time : 2
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
			$('.date-picker').datepicker({
				autoclose : true,
				todayHighlight : true
			});
		});
	</script>
</body>
</html>