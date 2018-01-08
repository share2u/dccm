<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
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
<%@ include file="../index/top.jsp"%>
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
								<form action="staff/${msg }.do" name="staffForm" id="staffForm" method="post" enctype="multipart/form-data">
									<input type="hidden" name="STAFF_ID" id="staff_id" value="${pd.STAFF_ID }"/>
									<div id="zhongxin" style="padding-top: 13px;">
									<table id="table_report" class="table table-striped table-bordered table-hover">
										<c:if test="${fx != 'head'}">
										<tr>
											<td style="width:79px;text-align: right;padding-top: 13px;">角色:</td>
											<td id="juese">
											<select class="chosen-select form-control" name="ROLE_ID" id="role_id" data-placeholder="请选择角色" style="vertical-align:top;" style="width:98%;" >
											<option value=""></option>
											<c:forEach items="${roleList}" var="role">
												<option value="${role.ROLE_ID }" <c:if test="${role.ROLE_ID == pd.ROLE_ID }">selected</c:if>>${role.ROLE_NAME }</option>
											</c:forEach>
											</select>
											</td>
										</tr>
										</c:if>
										<c:if test="${fx == 'head'}">
											<input name="ROLE_ID" id="role_id" value="${pd.ROLE_ID }" type="hidden" />
										</c:if>
										<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">员工姓名:</td>
								<td><input type="text" name="STAFF_NAME" id="STAFF_NAME" value="${pd.STAFF_NAME}" maxlength="20" placeholder="这里输入员工姓名" title="员工姓名" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">职别:</td>
								<td id="zhibie">
									<select class="chosen-select form-control" name="RANK_ID" id="rank_id" data-placeholder="请选择职别" style="vertical-align:top;" style="width:98%;" >
									<option value=""></option>
										<c:forEach items="${rankList}" var="rank">
											<option value="${rank.RANK_ID}" <c:if test="${pd.RANK_ID==rank.RANK_ID}">selected</c:if> >${rank.RANK_NAME}</option>
										</c:forEach>
									</select>
								</td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">用户名:</td>
								<td><input type="text" name="USERNAME" id="USERNAME" value="${pd.USERNAME}" maxlength="50" placeholder="这里输入用户名" title="用户名" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">密码:</td>
								<td><input type="password" name="PASSWORD" id="PASSWORD" value="${pd.PASSWORD}" maxlength="20" placeholder="这里输入密码" title="密码" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:79px;text-align: right;padding-top: 13px;">确认密码:</td>
								<td><input type="password" name="chkpwd" id="chkpwd" value="${pd.PASSWORD}" maxlength="32" placeholder="确认密码" title="确认密码" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:79px;text-align: right;padding-top: 13px;">门店:</td>
								<td id="mendian">
									<select class="chosen-select form-control" name="STORE_ID" id="store_id" data-placeholder="请选择门店" style="vertical-align:top;" style="width:98%;" >
									<option value=""></option>
										<c:forEach items="${storeList}" var="store">
											<option value="${store.STORE_ID}" <c:if test="${pd.STORE_ID==store.STORE_ID}">selected</c:if> >${store.STORE_NAME}</option>
										</c:forEach>
									</select>
								</td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">性别:</td>
								<td>
									<select name="SEX" id="SEX">
											<option value="0" <c:if test="${pd.SEX=='0'}">selected</c:if> >男</option>
											<option value="1" <c:if test="${pd.SEX=='1'}">selected</c:if> >女</option>
									</select>
								</td>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">身份证号:</td>
								<td><input type="text" name="ID_NUMBER" id="ID_NUMBER" value="${pd.ID_NUMBER}" maxlength="20" placeholder="这里输入身份证号" title="身份证号" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">电话:</td>
								<td><input type="text" name="TELEPHONE" id="TELEPHONE" value="${pd.TELEPHONE}" maxlength="11" placeholder="这里输入电话" title="电话" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">专家介绍:</td>
								<td><input type="text" name="SPECIAL" id="SPECIAL" value="${pd.SPECIAL}" maxlength="500" placeholder="这里输入专长" title="专长" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">出诊信息:</td>
								<td><input type="text" name="INFO" id="INFO" value="${pd.INFO}" maxlength="500" placeholder="这里输入专家介绍" title="专家介绍" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:79px;text-align: right;padding-top: 13px;">部门:</td>
											<td id="bumen">
									<select class="chosen-select form-control" name="DEPART_ID" id="depart_id" data-placeholder="请选择部门" style="vertical-align:top;" style="width:98%;" >
											<c:forEach items="${departList}" var="depart">
												<option value="${depart.DEPART_ID}" <c:if test="${pd.DEPART_ID==depart.DEPART_ID}">selected</c:if> >${depart.DEP_NAME}</option>
											</c:forEach>
									</select>
								</td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">头像:</td>
								<td>
									<input type="file" name="uploadimages"/>
									<input type="hidden" name="STAFF_IMG" id="FILEPATH" value="${pd.STAFF_IMG}"/>
								</td>
							</tr>
							<%-- <tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">部门负责人ID:</td>
								<td><input type="number" name="DEP_HEADER" id="DEP_HEADER" value="${pd.DEP_HEADER}" maxlength="32" placeholder="这里输入部门负责人ID" title="部门负责人ID" style="width:98%;"/></td>
							</tr> --%>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">状态 :</td>
								<td>
									<select name="STATUS" id="STATUS">
											<option value="0" <c:if test="${pd.STATUS=='0'}">selected</c:if> >正常</option>
											<option value="1" <c:if test="${pd.STATUS=='1'}">selected</c:if> >离职</option>
									</select>
								</td>
							</tr>

							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">邮箱:</td>
								<td><input type="text" name="EMAIL" id="EMAIL" value="${pd.EMAIL}" maxlength="32" placeholder="这里输入邮箱" title="邮箱" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="text-align: center;" colspan="10">
									<a class="btn btn-mini btn-primary" onclick="save();">保存</a>
									<a class="btn btn-mini btn-danger" onclick="top.Dialog.close();">取消</a>
								</td>
							</tr>
				</table>
									</div>
									<div id="zhongxin2" class="center" style="display:none"><br/><br/><br/><br/><img src="static/images/jiazai.gif" /><br/><h4 class="lighter block green"></h4></div>
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
	<!-- basic scripts -->
	<!-- 页面底部js¨ -->
	<%@ include file="../index/foot.jsp"%>
	<!-- ace scripts -->
	<script src="static/ace/js/ace/ace.js"></script>
	<!-- inline scripts related to this page -->
	<!-- 下拉框 -->
	<script src="static/ace/js/chosen.jquery.js"></script>
	<!--提示框-->
	<script type="text/javascript" src="static/js/jquery.tips.js"></script>
</body>
<script type="text/javascript">
	$(top.hangge());
	$(document).ready(function(){
		if($("#staff_id").val()!=""){
			$("#username").attr("readonly","readonly");
			$("#username").css("color","gray");
		}
	});
	//保存
	function save(){
		if($("#role_id").val()==""){
			$("#juese").tips({
				side:3,
	            msg:'请选择角色',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#role_id").focus();
			return false;
		}
		
		if($("#STAFF_NAME").val()==""){
				$("#STAFF_NAME").tips({
					side:3,
		            msg:'请输入员工姓名',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#STAFF_NAME").focus();
			return false;
			}
			if($("#RANK_ID").val()==""){
				$("#RANK_ID").tips({
					side:3,
		            msg:'请输入职别',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#RANK_ID").focus();
			return false;
			}
		
		if($("#username").val()=="" || $("#username").val()=="此用户名已存在!"){
			$("#username").tips({
				side:3,
	            msg:'输入用户名',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#username").focus();
			$("#username").val('');
			$("#username").css("background-color","white");
			return false;
		}else{
			$("#username").val(jQuery.trim($('#loginname').val()));
		}
		
		if($("#staff_id").val()=="" && $("#PASSWORD").val()==""){
			$("#PASSWORD").tips({
				side:3,
	            msg:'输入密码',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#PASSWORD").focus();
			return false;
		}
		if($("#PASSWORD").val()!=$("#chkpwd").val()){
			
			$("#chkpwd").tips({
				side:3,
	            msg:'两次密码不相同',
	            bg:'#AE81FF',
	            time:3
	        });
			$("#chkpwd").focus();
			return false;
		}
		
		if($("#store_id").val()==""){
				$("#store_id").tips({
					side:3,
		            msg:'请选择门店',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#store_id").focus();
			return false;
			}
			if($("#SEX").val()==""){
				$("#SEX").tips({
					side:3,
		            msg:'请输入性别 0男 1女',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#SEX").focus();
			return false;
			}
		
		
		var myreg = /^(((13[0-9]{1})|159)+\d{8})$/;
		if($("#TELEPHONE").val()==""){
			
			$("#TELEPHONE").tips({
				side:3,
	            msg:'输入手机号',
	            bg:'#AE81FF',
	            time:3
	        });
			$("#TELEPHONE").focus();
			return false;
		}else if($("#TELEPHONE").val().length != 11 && !myreg.test($("#TELEPHONE").val())){
			$("#TELEPHONE").tips({
				side:3,
	            msg:'手机号格式不正确',
	            bg:'#AE81FF',
	            time:3
	        });
			$("#TELEPHONE").focus();
			return false;
		}
		
		if($("#SPECIAL").val()==""){
				$("#SPECIAL").tips({
					side:3,
		            msg:'请输入专长',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#SPECIAL").focus();
			return false;
			}
			if($("#INFO").val()==""){
				$("#INFO").tips({
					side:3,
		            msg:'请输入专家介绍',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#INFO").focus();
			return false;
			}
			
			if($("#STAFF_IMG").val()==""){
				$("#STAFF_IMG").tips({
					side:3,
		            msg:'请输入头像',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#STAFF_IMG").focus();
			return false;
			}
		
   
	
		
		if($("#staff_id").val()==""){
			hasU();
		}else{
			$("#staffForm").submit();
			$("#zhongxin").hide();
			$("#zhongxin2").show();
		}
	}
	function ismail(mail){
		return(new RegExp(/^(?:[a-zA-Z0-9]+[_\-\+\.]?)*[a-zA-Z0-9]+@(?:([a-zA-Z0-9]+[_\-]?)*[a-zA-Z0-9]+\.)+([a-zA-Z]{2,})+$/).test(mail));
		}
	
	//判断用户名是否存在
	function hasU(){
		var USERNAME = $.trim($("#USERNAME").val());
		$.ajax({
			type: "POST",
			url: '<%=basePath%>staff/hasU.do',
	    	data: {USERNAME:USERNAME,tm:new Date().getTime()},
			dataType:'json',
			cache: false,
			success: function(data){
				 if("success" == data.result){
					$("#staffForm").submit();
					$("#zhongxin").hide();
					$("#zhongxin2").show();
				 }else{
					/* $("#USERNAME").css("background-color","#D16E6C"); */
					$("#USERNAME").tips({
						side:3,
			            msg:'用户名已存在',
			            bg:'#AE81FF',
			            time:3
			        });
					$("#USERNAME").focus();
					return false;
					/* setTimeout("$('#USERNAME').val('此用户名已存在!')",500); */
				 }
			}
		});
	}
	
	//判断邮箱是否存在
	function hasE(USERNAME){
		var EMAIL = $.trim($("#EMAIL").val());
		$.ajax({
			type: "POST",
			url: '<%=basePath%>staff/hasE.do',
	    	data: {EMAIL:EMAIL,USERNAME:USERNAME,tm:new Date().getTime()},
			dataType:'json',
			cache: false,
			success: function(data){
				 if("success" != data.result){
					 $("#EMAIL").tips({
							side:3,
				            msg:'邮箱 '+EMAIL+' 已存在',
				            bg:'#AE81FF',
				            time:3
				        });
					 $("#EMAIL").val('');
				 }
			}
		});
	}
	
	//判断编码是否存在
	function hasN(USERNAME){
		var NUMBER = $.trim($("#NUMBER").val());
		$.ajax({
			type: "POST",
			url: '<%=basePath%>staff/hasN.do',
	    	data: {NUMBER:NUMBER,USERNAME:USERNAME,tm:new Date().getTime()},
			dataType:'json',
			cache: false,
			success: function(data){
				 if("success" != data.result){
					 $("#NUMBER").tips({
							side:3,
				            msg:'编号 '+NUMBER+' 已存在',
				            bg:'#AE81FF',
				            time:3
				        });
					 $("#NUMBER").val('');
				 }
			}
		});
	}
	$(function() {
		//下拉框
		if(!ace.vars['touch']) {
			$('.chosen-select').chosen({allow_single_deselect:true}); 
			$(window)
			.off('resize.chosen')
			.on('resize.chosen', function() {
				$('.chosen-select').each(function() {
					 var $this = $(this);
					 $this.next().css({'width': $this.parent().width()});
				});
			}).trigger('resize.chosen');
			$(document).on('settings.ace.chosen', function(e, event_name, event_val) {
				if(event_name != 'sidebar_collapsed') return;
				$('.chosen-select').each(function() {
					 var $this = $(this);
					 $this.next().css({'width': $this.parent().width()});
				});
			});
			$('#chosen-multiple-style .btn').on('click', function(e){
				var target = $(this).find('input[type=radio]');
				var which = parseInt(target.val());
				if(which == 2) $('#form-field-select-4').addClass('tag-input-style');
				 else $('#form-field-select-4').removeClass('tag-input-style');
			});
		}
	});
	

</script>
</html>