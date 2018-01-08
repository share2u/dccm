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
					
					<form action="member/${msg }.do" name="Form" id="Form" method="post">
						<input type="hidden" name="UID" id="UID" value="${pd.uid}"/>
						<div id="zhongxin" style="padding-top: 13px;">
						<table id="table_report" class="table table-striped table-bordered table-hover">
							
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">姓名:</td>
								<td><input type="text" name="NAME" id="NAME" value="${pd.name}" maxlength="255" placeholder="这里输入姓名" title="姓名" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">用户名:</td>
								<td><input type="text" name="USERNAME" id="USERNAME" value="${pd.username}" maxlength="255" placeholder="这里输入用户名" title="用户名" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">用户头像:</td>
								<td><input type="text" name="USERIMG" id="USERIMG" value="${pd.userimg}" maxlength="255" placeholder="这里输入用户头像" title="用户头像" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">性别:</td>
								<td><input type="number" name="SEX" id="SEX" value="${pd.sex}" maxlength="32" placeholder="这里输入性别" title="性别" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">年龄:</td>
								<td><input type="number" name="AGE" id="AGE" value="${pd.age}" maxlength="32" placeholder="这里输入年龄" title="年龄" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">电话号码:</td>
								<td><input type="text" name="PHONE" id="PHONE" value="${pd.phone}" maxlength="255" placeholder="这里输入电话号码" title="电话号码" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">身份证号:</td>
								<td><input type="text" name="IDCODE" id="IDCODE" value="${pd.idcode}" maxlength="255" placeholder="这里输入身份证号" title="身份证号" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">所属城市:</td>
								<td><input type="text" name="CITY" id="CITY" value="${pd.city}" maxlength="255" placeholder="这里输入所属城市" title="所属城市" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">初诊日期:</td>
								<td><input class="span10 date-picker" name="FIRST_DATE" id="FIRST_DATE"  value="${pd.first_date}" type="text" data-date-format="yyyy-mm-dd" readonly="readonly" style="width:88px;" placeholder="初诊日期" title="初诊日期"/></td>
								
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">会员类型:</td>
								
								<td><select name="USER_CATEGORY" id="USER_CATEGORY">
									<option value="1" <c:if test="${pd.user_category==1}">selected</c:if>>普通会员</option>
									<option value="2" <c:if test="${pd.user_category==2}">selected</c:if>>保险会员</option>
									<option value="3" <c:if test="${pd.user_category==3}">selected</c:if>>团购会员</option>
									<option value="4" <c:if test="${pd.user_category==4}">selected</c:if>>vip会员</option>
								</select></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">团购组:</td>
								<td>
								<select class="chosen-select form-control" name="GROUP_ID" id="GROUP_ID" data-placeholder="请选择团购组" style="vertical-align:top;" style="width:98%;" >
									<option value=""></option>
										<c:forEach items="${groupList}" var="group">
											<option value="${group.GROUP_ID}" <c:if test="${pd.group_id==group.GROUP_ID}">selected</c:if> >${group.GROUP_NAME}</option>
										</c:forEach>
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
		
			
			if($("#NAME").val()==""){
				$("#NAME").tips({
					side:3,
		            msg:'请输入姓名',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#NAME").focus();
			return false;
			}
			
			
			
			
			if($("#PHONE").val()==""){
				$("#PHONE").tips({
					side:3,
		            msg:'请输入电话号码',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#PHONE").focus();
			return false;
			}
			
			
			
		/* 	if($("#GROUP_ID").val()==""){
				$("#GROUP_ID").tips({
					side:3,
		            msg:'请输入团购组',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#GROUP_ID").focus();
			return false;
			}  */
			else{
			$("#Form").submit();
			$("#zhongxin").hide();
			$("#zhongxin2").show();
			}
		}
		
		$(function() {
			//日期框
			$('.date-picker').datepicker({autoclose: true,todayHighlight: true});
		});
		</script>
</body>
</html>