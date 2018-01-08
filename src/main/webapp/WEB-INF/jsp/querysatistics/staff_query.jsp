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
<%@ include file="../system/index/top.jsp"%>
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

							<!-- 检索  -->
							<form action="queryStaff/list.do" method="post" name="staffForm"
								id="staffForm">
								<table style="margin-top:5px;">
									<tr>
										<td style="width:150px;padding-left:10px;">
											<div class="nav-search">
												服务板块：<select onchange="modelChange();" class="chosen-select form-control" data-placeholder="服务板块" name="model" id="model">
													<option value=""></option>
												<c:forEach items="${serviceModules}" var="serviceModule">
													<option value="${serviceModule.SERVICEMODULE_ID}"<c:if test="${serviceModule.SERVICEMODULE_ID == pd.model}">selected="selected"</c:if>>
														${serviceModule.M_NAME}
													</option>
												</c:forEach>
											</div>
										</td>
										<td style="width:250px;padding-left:10px;">
											门店：<select onchange="storeChange();" class="chosen-select form-control" name="stores"
											id="stores" data-placeholder="门店">
												<option value=""></option>
												<c:forEach items="${storeList}" var="store">
													<option value="${store.STORE_ID}"<c:if test="${store.STORE_ID == pd.stores}">selected="selected"</c:if>>
														${store.STORE_NAME}
													</option>
												</c:forEach>
											</select>
										</td>
										<td style="width:130px;padding-left:10px;">
											部门：<select  class="chosen-select form-control" name="departId"
											id="departId" data-placeholder="部门">
												<option value=""></option>
												<c:forEach items="${departs}" var="depart">
													<option value="${depart.DEPART_ID}"<c:if test="${depart.DEPART_ID == pd.departId}">selected="selected"</c:if>>
														${depart.DEP_NAME}
													</option>
												</c:forEach>
										</select>
										</td>
										<td style="width:150px;padding-left:10px;">
											员工姓名：<select onchange="staffChange();" class="chosen-select form-control" name="staffNames"
											id="staffNames" data-placeholder="员工姓名">
												<option value=""></option>
										</select>
										</td>
										<td style="width:130px;padding-left:10px;">
											是否在职：<select onchange="statusChange();" class="chosen-select form-control" name="status"
											id="status" data-placeholder="状态">
												<option value=""></option>
												<option value="0" <c:if test="${pd.status == 0}"> selected="selected"</c:if>>在职</option>
												<option value="1" <c:if test="${pd.status == 1}"> selected="selected"</c:if> >离职</option>
										</select>
										</td>
										<c:if test="${QX.cha == 1 }">
											<td style="vertical-align:bottom;padding-left:100px"><a class="btn btn-light btn-xs" onclick="searchs();"  title="检索"><i id="nav-search-icon" class="ace-icon fa fa-search bigger-110 nav-search-icon blue"></i></a></td>
										</c:if>
										<c:if test="${QX.toExcel == 1 }">
											<td style="vertical-align:bottom;padding-left:10px;">
												<a class="btn btn-light btn-xs" onclick="toExcel();" title="导出到EXCEL">
													<i id="nav-search-icon" class="ace-icon fa fa-download bigger-110 nav-search-icon blue"></i>
												</a>
											</td>
										</c:if>
									</tr>
									
								</table>
								<!-- 检索  -->

								<table id="simple-table"
									class="table table-striped table-bordered table-hover"
									style="margin-top:5px;">
									<thead>
										<tr>
											<!--
									<th class="center" style="width:35px;">
									<label class="pos-rel"><input type="checkbox" class="ace" id="zcheckbox" /><span class="lbl"></span></label>
									</th>-->
											<th class="center" style="width:50px;">序号</th>
											<th class="center" style="width:50px;">员工姓名</th>
											<th class="center" style="width:50px;">职别</th>
											<th class="center" style="width:50px;">用户名</th>
											<th class="center" style="width:100px;">门店</th>
											<th class="center" style="width:50px;">性别</th>
											<!-- <th class="center">身份证号</th> -->
											<th class="center" style="width:50px;">电话</th>
											<th class="center" style="width:50px;">专家介绍</th>

											<th class="center"style="width:50px;">所属部门</th>
											<th class="center" style="width:50px;">头像</th>
											<th class="center" style="width:200px;">出诊信息</th>

											<th class="center" style="width:50px;">状态</th>
											<!-- <th class="center">上次登录时间</th> -->
											<!-- <th class="center">登录IP</th> -->
											<th class="center" style="width:50px;">邮箱</th>
											<!-- <th class="center">操作</th> -->
										</tr>
									</thead>

									<tbody>

										<!-- 开始循环 -->
										<c:choose>
											<c:when test="${not empty staffList}">
												<c:if test="${QX.cha == 1 }">
													<c:forEach items="${staffList}" var="staff" varStatus="vs">

														<tr>
															<!--
											<td class='center' style="width: 30px;">
											<label class="pos-rel"><input type='checkbox' name='ids' value="${var.STAFF_ID}" class="ace" /><span class="lbl"></span></label>	
											</td>-->

															<td class='center' style="width: 30px;">${vs.index+1}</td>
															<td class='center'>${staff.STAFF_NAME}</td>
															<td class='center'>${staff.RANK_NAME}</td>
															<td class='center'>${staff.USERNAME}</td>
															<td class='center'>${staff.STORE_NAME}</td>
															<td class='center'><c:if test="${staff.SEX==1}">女</c:if>
																<c:if test="${staff.SEX==0}">男</c:if></td>
															<%-- <td class='center'>${staff.ID_NUMBER}</td> --%>
															<td class='center'>${staff.TELEPHONE}</td>
															<td class='center' style="width:350px;height:45px;"><div
																	title="${staff.SPECIAL}"
																	style="width:350px;height:45px;overflow:hidden;">${staff.SPECIAL}</div>
															</td>

															<td class='center'>${staff.DEP_NAME}</td>
															<td class='center'><img
																src="${pageContext.request.contextPath}/uploadFiles/staffimg/${staff.STAFF_IMG}"
																width="50" height="50">
															</td>
															<td class='center'>${staff.INFO}</td>
															<td class='center'><c:if test="${staff.STATUS==1}">离职</c:if>
																<c:if test="${staff.STATUS==0}">在职</c:if></td>
															<td class='center'>${staff.EMAIL}</td>
														</tr>

													</c:forEach>
												</c:if>
												<c:if test="${QX.cha == 0 }">
													<tr>
														<td colspan="10" class="center">您无权查看</td>
													</tr>
												</c:if>
											</c:when>
											<c:otherwise>
												<tr class="main_info">
													<td colspan="10" class="center">没有相关数据</td>
												</tr>
											</c:otherwise>
										</c:choose>
									</tbody>
								</table>

								<div class="page-header position-relative">
									<table style="width:100%;">
										<tr>
											<%--<td style="vertical-align:top;">
												<c:if test="${QX.add == 1 }">
								<a class="btn btn-mini btn-success" onclick="add();">新增</a>
								</c:if>
								  <c:if test="${QX.FHSMS == 1 }"><a title="批量发送站内信" class="btn btn-mini btn-info" onclick="makeAll('确定要给选中的用户发送站内信吗?');"><i class="ace-icon fa fa-envelope-o bigger-120"></i></a></c:if>
								<c:if test="${QX.email == 1 }"><a title="批量发送电子邮件" class="btn btn-mini btn-primary" onclick="makeAll('确定要给选中的用户发送邮件吗?');"><i class="ace-icon fa fa-envelope bigger-120"></i></a></c:if>
								<c:if test="${QX.sms == 1 }"><a title="批量发送短信" class="btn btn-mini btn-warning" onclick="makeAll('确定要给选中的用户发送短信吗?');"><i class="ace-icon fa fa-envelope-o bigger-120"></i></a></c:if>
								<c:if test="${QX.del == 1 }">
								<a title="批量删除" class="btn btn-mini btn-danger" onclick="makeAll('确定要删除选中的数据吗?');" ><i class='ace-icon fa fa-trash-o bigger-120'></i></a>
								</c:if> </td>--%>
											<td style="vertical-align:top;"><div class="pagination"
													style="float: right;padding-top: 0px;margin-top: 0px;">${page.pageStr}</div>
											</td>
										</tr>
									</table>
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

		<!-- 返回顶部 -->
		<a href="#" id="btn-scroll-up"
			class="btn-scroll-up btn btn-sm btn-inverse"> <i
			class="ace-icon fa fa-angle-double-up icon-only bigger-110"></i> </a>

	</div>
	<!-- /.main-container -->

	<!-- basic scripts -->
	<!-- 页面底部js¨ -->
	<%@ include file="../system/index/foot.jsp"%>
	<!-- 删除时确认窗口 -->
	<script src="static/ace/js/bootbox.js"></script>
	<!-- ace scripts -->
	<script src="static/ace/js/ace/ace.js"></script>
	<!-- 日期框 -->
	<script src="static/ace/js/date-time/bootstrap-datepicker.js"></script>
	<!-- 下拉框 -->
	<script src="static/ace/js/chosen.jquery.js"></script>
	<!--提示框-->
	<script type="text/javascript" src="static/js/jquery.tips.js"></script>
</body>

<script type="text/javascript">
$(top.hangge());

//检索
function searchs(){
	top.jzts();
	$("#staffForm").submit();
}

//模块选择触发门店
function modelChange(){
		var SERVICEMODULE_ID = $("#model option:selected").val();
		if(SERVICEMODULE_ID != null){
			$.ajax({
				type:"POST",
				url:"<%=basePath%>queryStaff/findStoreByModelId.do",
				data:{"SERVICEMODULE_ID":SERVICEMODULE_ID},
				dataType:"json",
				async:false,
				success:function(data){
					$('#stores').empty();
					$('#stores').append("<option value=''></option>");
					for ( var i = 0; i < data.length; i++) {
						var id = '${pd.stores}';
						if(id != data[i].STORE_ID){
							$('#stores').append('<option value="' +data[i].STORE_ID + '">' + data[i].STORE_NAME +'</option>"');
						}else{
							$('#stores').append('<option value="' +data[i].STORE_ID + '" selected="selected">'+ data[i].STORE_NAME + '</option>"');
						}
					}
					$('#stores').chosen("destroy").chosen();
				}
			});
			
			storeChange();
		}else{
			return;
		}
}
//门店选择触发员工姓名
function storeChange(){
		var storeId = $("#stores option:selected").val();
		if(storeId != null){
			$.ajax({
				type:"POST",
				url:"<%=basePath%>queryStaff/findstaffsByStoreId.do",
				data:{"storeId":storeId},
				dataType:"json",
				success:function(data){
					$('#staffNames').empty();
					$('#staffNames').append("<option value=''></option>");
					for ( var i = 0; i < data.length; i++) {
						var id = '${pd.staffNames}';
						if(id != data[i].STAFF_ID){
							$('#staffNames').append('<option value="' +data[i].STAFF_ID + '">'+ data[i].STAFF_NAME + '</option>"');
						}else{
							$('#staffNames').append('<option value="' +data[i].STAFF_ID + '" selected="selected">'+ data[i].STAFF_NAME + '</option>"');
						}
					}
					$('#staffNames').chosen("destroy").chosen();
				}
			});
		}else{
			return;
		}
		
}

$(function() {
	//日期框
	$('.date-picker').datepicker({autoclose: true,todayHighlight: true});
	
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

	
	//复选框全选控制
	var active_class = 'active';
	$('#simple-table > thead > tr > th input[type=checkbox]').eq(0).on('click', function(){
		var th_checked = this.checked;//checkbox inside "TH" table header
		$(this).closest('table').find('tbody > tr').each(function(){
			var row = this;
			if(th_checked) $(row).addClass(active_class).find('input[type=checkbox]').eq(0).prop('checked', true);
			else $(row).removeClass(active_class).find('input[type=checkbox]').eq(0).prop('checked', false);
		});
	});
	
	modelChange();
});

//导出excel
function toExcel(){

	var url ='<%=basePath%>queryStaff/excel.do';
		var beforeUrl = $("#staffForm").attr("action");
		$("#staffForm").attr("action",url);
		$("#staffForm").submit();
		$("#staffForm").attr("action",beforeUrl);
}

//打开上传excel页面
function fromExcel(){
	 top.jzts();
	 var diag = new top.Dialog();
	 diag.Drag=true;
	 diag.Title ="EXCEL 导入到数据库";
	 diag.URL = '<%=basePath%>staff/goUploadExcel.do';
	 diag.Width = 300;
	 diag.Height = 150;
	 diag.CancelEvent = function(){ //关闭事件
		 if(diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none'){
			 if('${page.currentPage}' == '0'){
				 top.jzts();
				 setTimeout("self.location.reload()",100);
			 }else{
				 nextPage('${page.currentPage}');
			 }
		}
		diag.close();
	 };
	 diag.show();
}	

//查看用户
function viewStaff(USERNAME){
	if('admin' == USERNAME){
		bootbox.dialog({
			message: "<span class='bigger-110'>不能查看admin用户!</span>",
			buttons: 			
			{ "button":{ "label":"确定", "className":"btn-sm btn-success"}}
		});
		return;
	}
	 top.jzts();
	 var diag = new top.Dialog();
	 diag.Drag=true;
	 diag.Title ="资料";
	 diag.URL = '<%=basePath%>staff/view.do?USERNAME='+USERNAME;
	 diag.Width = 469;
	 diag.Height = 380;
	 diag.CancelEvent = function(){ //关闭事件
		diag.close();
	 };
	 diag.show();
}
		
</script>
</html>

