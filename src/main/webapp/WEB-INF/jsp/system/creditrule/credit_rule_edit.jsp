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
					
					<form action="credit_rule/${msg }.do" name="Form" id="Form" method="post">
						<input type="hidden" name="CREDIT_RULE_ID" id="CREDIT_RULE_ID" value="${pd.CREDIT_RULE_ID}"/>
						<div id="zhongxin" style="padding-top: 13px;">
						<table id="table_report" class="table table-striped table-bordered table-hover">
							<tr>
								<td style="width:115px;text-align: right;padding-top: 13px;">消费换积分比例:</td>
								<td><input type="text" name="CASH_TOCREDIT" id="CASH_TOCREDIT" value="${pd.CASH_TOCREDIT}" maxlength="32" placeholder="请输入消费钱数与返回积分的比例" title="消费换积分" style="width:98%;"/></td>
							</tr>
							<!--
							<tr>
								<td style="width:105px;text-align: right;padding-top: 13px;">积分换现金:</td>
								<td><input type="number" name="CREDIT_TOCASH" id="CREDIT_TOCASH" value="${pd.CREDIT_TOCASH}" maxlength="32" placeholder="这里输入积分换现金" title="积分换现金" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">员工:</td>
								<td><input type="text" name="STAFF_ID" id="STAFF_ID" value="${pd.STAFF_ID}" maxlength="255" placeholder="这里输入员工" title="员工" style="width:98%;"/></td>
							</tr>
							-->
							<!--<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">创建时间:</td>
								<td><input class="span10 date-picker" name="CREATETIME" id="CREATETIME" value="${pd.CREATETIME}" type="text" data-date-format="yyyy-mm-dd" readonly="readonly" placeholder="创建时间" title="创建时间" style="width:98%;"/></td>
							</tr>-->
							
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">积分状态:</td>
								<td><select name="STATUS" id="STATUS">
								      <option value="0" <c:if test="${pd.STATUS=='0'}">selected </c:if>>正常</option>
									  <option value="1" <c:if test="${pd.STATUS=='1'}">selected </c:if>>过期</option>
								</td>
						<!--		<td><input type="number" name="STATUS" id="STATUS" value="${pd.STATUS}" maxlength="32" placeholder="这里输入积分状态" title="积分状态" style="width:98%;"/></td> -->
							</tr>
							<!--<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">项目名:</td>
								<td><input type="text" name="PROJECT_ID" id="PROJECT_ID" value="${pd.PROJECT_ID}" maxlength="255" placeholder="这里输入项目名" title="项目名" style="width:98%;"/></td>
							</tr>
							-->
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
			if($("#CASH_TOCREDIT").val()==""){
				$("#CASH_TOCREDIT").tips({
					side:3,
		            msg:'请输入消费钱数与返回积分的比例',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#CASH_TOCREDIT").focus();
			return false;
			}
			
			if($("#STAFF_ID").val()==""){
				$("#STAFF_ID").tips({
					side:3,
		            msg:'请输入员工',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#STAFF_ID").focus();
			return false;
			}
			if($("#CREATETIME").val()==""){
				$("#CREATETIME").tips({
					side:3,
		            msg:'请输入创建时间',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#CREATETIME").focus();
			return false;
			}
			if($("#STATUS").val()==""){
				$("#STATUS").tips({
					side:3,
		            msg:'请输入积分状态',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#STATUS").focus();
			return false;
			}
			if($("#PROJECT_ID").val()==""){
				$("#PROJECT_ID").tips({
					side:3,
		            msg:'请输入项目名',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#PROJECT_ID").focus();
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