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
					
					<form action="usercategory/${msg }.do" name="Form" id="Form" method="post">
						<input type="hidden" name="USERCATEGORY_ID" id="USERCATEGORY_ID" value="${pd.USERCATEGORY_ID}"/>
						<div id="zhongxin" style="padding-top: 13px;">
						<table id="table_report" class="table table-striped table-bordered table-hover">
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">类型名称:</td>
								<td><input type="text" name="CATEGORY_NAME" id="CATEGORY_NAME" value="${pd.CATEGORY_NAME}" maxlength="255" placeholder="这里输入类型名称" title="类型名称" style="width:98%;" /></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">父节点名称:</td>
								<td id="PARENT_ID">
									<select class="chosen-select form-control" name="PARENT_ID" id="PARENT_ID" data-placeholder="请选择父节点名称" style="vertical-align:top;" style="width:98%;" >
									<option value=0>无</option>
										<c:forEach items="${categoryList}" var="category">
											<option value="${category.USERCATEGORY_ID}" <c:if test="${pd.PARENT_ID==category.USERCATEGORY_ID}">selected</c:if> >${category.CATEGORY_NAME}</option>
										</c:forEach>
									</select>
								</td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">优惠比例:</td>
								<td><input type="text" name="PROPORTION" id="PROPORTION" value="${pd.PROPORTION}" maxlength="255" placeholder="这里输入优惠比例" title="优惠比例" style="width:98%;"/></td>
								
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">状态:</td>
								
								<td>
									<select name="STATUS" id="STATUS"  >
											<option value="0" <c:if test="${pd.STATUS=='0'}">selected</c:if> >正常</option>
											<option value="1" <c:if test="${pd.STATUS=='1'}">selected</c:if> >注销</option>
											
									</select>
								</td>
								
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
			if($("#CATEGORY_NAME").val()==""){
				$("#CATEGORY_NAME").tips({
					side:3,
		            msg:'请输入类型名称',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#CATEGORY_NAME").focus();
			return false;
			}
			if($("#PROPORTION").val()==""){
				$("#PROPORTION").tips({
					side:3,
		            msg:'请输入优惠比例',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#PROPORTION").focus();
			return false;
			}
			if($("#STATUS").val()==""){
				$("#STATUS").tips({
					side:3,
		            msg:'请输入状态 ',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#STATUS").focus();
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