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
					
					<form action="prestoremx/${msg }.do" name="Form" id="Form" method="post">
						<input type="hidden" name="PRESTOREMX_ID" id="PRESTOREMX_ID" value="${pd.PRESTOREMX_ID}"/>
						<input type="hidden" name="PRESTORE_ID" id="PRESTORE_ID" value="${pd.PRESTORE_ID}"/>
						<input type="hidden" name="token" id="token" value="${token}">
						<div id="zhongxin" style="padding-top: 13px;">
						<table id="table_report" class="table table-striped table-bordered table-hover">
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">手机号:</td>
								<td><input type="text" name="PHONE" id="PHONE" value="${pd.PHONE}" maxlength="32" placeholder="这里输入用户手机号" title="用户手机号" style="width:50%;" onchange="queryUsername(this.value)"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">用户姓名:</td>
								<td>
									<select id="UID" name="UID"></select>
								</td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">汇款单号:</td>
								<td><input type="text" name="REMITNO" id="REMITNO" value="${pd.REMITNO}" maxlength="100" placeholder="这里输入汇款单号" title="汇款单号" style="width:50%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">银行名称:</td>
								<td><input type="text" name="BANK" id="BANK" value="${pd.BANK}" maxlength="100" placeholder="这里输入银行名称" title="银行名称" style="width:50%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">汇款地址:</td>
								<td><input type="text" name="ADDRESS" id="ADDRESS" value="${pd.ADDRESS}" maxlength="100" placeholder="这里输入汇款地址" title="汇款地址" style="width:50%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">预存金额:</td>
								<td><input type="text" name="PRESTOREMONEY" id="PRESTOREMONEY" value="${pd.PRESTOREMONEY}" maxlength="10" placeholder="这里输入预存金额" title="预存金额" style="width:50%;"/></td>
							</tr>
							<tr>
								<td style="text-align: left;padding-left:100px;" colspan="10">
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
	<!-- 删除时确认窗口 -->
	<script src="static/ace/js/bootbox.js"></script>
	<!-- ace scripts -->
	<script src="static/ace/js/ace/ace.js"></script>
	
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
		            msg:'请输入用户姓名',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#PHONE").focus();
			return false;
			}
			if($("#UID").val()==""){
				$("#PHONE").tips({
					side:3,
		            msg:'请选择用户',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#UID").focus();
			return false;
			}
			if($("#PRESTOREMONEY").val()==""){
				$("#PRESTOREMONEY").tips({
					side:3,
		            msg:'请输入预存金额',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#PRESTOREMONEY").focus();
			return false;
			}
			
			$("#zhongxin").hide();
			$("#zhongxin2").show();
			
			$.ajax({
				type:"post",
				url:"prestoremx/save.do",
				data:{
					"PHONE":$("#PHONE").val(),
					"UID":$("#UID").val(),
					"REMITNO":$("#REMITNO").val(),
					"BANK":$("#BANK").val(),
					"ADDRESS":$("#ADDRESS").val(),
					"PRESTOREMONEY":$("#PRESTOREMONEY").val(),
					"token":$("#token").val()
					
					},
				dataType:"text",
				success: function(data){
					bootbox.dialog({
						message: "<span class='bigger-110'>添加成功!  <br><br> <a href='prestore/list.do'>点击查看</a></span>",    
					});
				}
			});
		}
		
		$(function() {
			//日期框
			$('.date-picker').datepicker({autoclose: true,todayHighlight: true});
		});
		
		function queryUsername(value){
			$.ajax({
				type:"post",
				url:"prestoremx/queryUsernameByPhone",
				data:{"phone":value},
				dataType:"json",
				success: function(data){
					for (var i = 0; i < data.userlist.length; i++) {
						$("#UID").append("<option value='" + data.userlist[i].uid + "'>" + data.userlist[i].name + "</option>");
					}
				}
			});
		}
		</script>
</body>
</html>