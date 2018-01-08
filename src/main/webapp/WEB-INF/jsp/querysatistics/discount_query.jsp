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
							<form action="queryDiscount/list.do" method="post"
								name="discountForm" id="discountForm">
								<table style="margin-top:5px;">
									<tr>
										<td style="padding-left:10px;">
											<input  placeholder="输入用户名或微信名" name="name" value='${pd.name}' id="name" type="text">
										</td>
										 <td style="padding-left:10px;">
											<input  placeholder="输入优惠劵名称" name="discountName" value="${pd.discountName}" id="discountName" type="text">
										</td>
										 <td style="padding-left:10px;">
										 	<select onchange=""
											class="chosen-select form-control" name="isUsed"
											id="isUsed" data-placeholder="是否已使用">
												<option value=""></option>
												<option value="0"
													<c:if test="${'0' == pd.isUsed}">selected="selected"</c:if>>未使用</option>
												<option value="1"
													<c:if test="${'1' == pd.isUsed}">selected="selected"</c:if>>已使用</option>
										</select>
										</td>
										<%-- <td style="padding-left:10px;">
											<input  placeholder="输入客服名称" name="staffName" value="${pd.staffName}" id="staffName" type="text">
										</td> --%>
										<td style="display: none"><input  name="groupId" value="${pd.groupId}"type="text"></td>
										<%-- <td style="padding-left:10px;"><input
											class="span10 date-picker" name="firstDate" id="firstDate"
											type="text" data-date-format="yyyy-mm-dd" readonly="readonly"
											style="width:99px;" value="${pd.firstDate}"
											placeholder="发放开始日期"/></td>
										<td style="padding-left:2px;"><input
											class="span10 date-picker" name="lastDate" id="lastDate"
											type="text" data-date-format="yyyy-mm-dd" readonly="readonly"
											style="width:99px;" value="${pd.lastDate}"
											placeholder="发放结束日期"/></td> --%>

										<c:if test="${QX.cha == 1 }">
											<td style="padding-left:100px"><a
												class="btn btn-light btn-xs" onclick="searchs();" title="检索"><i
													id="nav-search-icon"
													class="ace-icon fa fa-search bigger-110 nav-search-icon blue"></i>
											</a></td>
										</c:if>
										<c:if test="${QX.toExcel == 1 }">
											<td style="vertical-align:top;padding-left:100px;">
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
											<th class="center" style="width:50px;">序号</th>
											<th class="center" style="width:50px;">姓名/微信名</th>
											<th class="center" style="width:130px;">优惠劵名称</th>
											<th class="center" style="width:60px;">优惠券所属组</th>
											<th class="center" style="width:60px;">客服</th>
											<th class="center" style="width:150px;">发放时间</th>
											<th class="center" style="width:100px;">开始日期</th>
											<th class="center" style="width:100px;">结束日期</th>
											<th class="center" style="width:60px;">是否已经使用</th>
										</tr>
									</thead>
									<tbody>
										<!-- 开始循环 -->
										<c:choose>
											<c:when test="${not empty userDiscounts}">
												<c:forEach items="${userDiscounts}" var="userDiscount" varStatus="vs">
													<tr>
														<td class='center' style="width: 30px;">${vs.index+1}</td>
														<td class='center'><a href="javascript:;" data-toggle="tooltip" title='手机号：${userDiscount.user.phone};地址：${userDiscount.user.city};'>${userDiscount.user.name}/${userDiscount.user.userName}</td>
														<td class='center'>${userDiscount.discount.DISCOUNT_NAME}</td>
														<td class='center'>${userDiscount.discountGroup.GROUP_NAME}</td>
														<td class='center'>${userDiscount.staff.STAFF_NAME}</td>
														<td class='center'>${userDiscount.createTime}</td>
														<td class='center'>${userDiscount.startTime}</td>
														<td class='center'>${userDiscount.endTime}</td>
														<td class='center'><c:if
																test="${userDiscount.isUsed==0}">
																<font color="red">未使用</font>
															</c:if> <c:if test="${userDiscount.isUsed==1}">
																<font color="gray">已使用</font>
															</c:if>
														</td>
													</tr>
												</c:forEach>
											</c:when>
											<c:otherwise>
												<tr class="main_info">
													<td colspan="100" class="center">没有相关数据</td>
												</tr>
											</c:otherwise>
										</c:choose>
									</tbody>
								</table>

								</table>

								<div class="page-header position-relative">
									<table style="width:100%;">
										<tr>
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
	$("#discountForm").submit();
}
	$(function() {
		//日期框
		$('.date-picker').datepicker({
			autoclose : true,
			todayHighlight : true
		});

		//下拉框
		if (!ace.vars['touch']) {
			$('.chosen-select').chosen({
				allow_single_deselect : true
			});
			$(window).off('resize.chosen').on('resize.chosen', function() {
				$('.chosen-select').each(function() {
					var $this = $(this);
					$this.next().css({
						'width' : $this.parent().width()
					});
				});
			}).trigger('resize.chosen');
			$(document).on('settings.ace.chosen',
					function(e, event_name, event_val) {
						if (event_name != 'sidebar_collapsed')
							return;
						$('.chosen-select').each(function() {
							var $this = $(this);
							$this.next().css({
								'width' : $this.parent().width()
							});
						});
					});
			$('#chosen-multiple-style .btn').on('click', function(e) {
				var target = $(this).find('input[type=radio]');
				var which = parseInt(target.val());
				if (which == 2)
					$('#form-field-select-4').addClass('tag-input-style');
				else
					$('#form-field-select-4').removeClass('tag-input-style');
			});
		}

		//复选框全选控制
		var active_class = 'active';
		$('#simple-table > thead > tr > th input[type=checkbox]').eq(0).on(
				'click',
				function() {
					var th_checked = this.checked;//checkbox inside "TH" table header
					$(this).closest('table').find('tbody > tr').each(
							function() {
								var row = this;
								if (th_checked)
									$(row).addClass(active_class).find(
											'input[type=checkbox]').eq(0).prop(
											'checked', true);
								else
									$(row).removeClass(active_class).find(
											'input[type=checkbox]').eq(0).prop(
											'checked', false);
							});
				});
				$("[data-toggle='tooltip']").tooltip();
	});
	
	//导出excel
		function toExcel(){
			var url ='<%=basePath%>queryDiscount/excel.do';
			var beforeUrl = $("#discountForm").attr("action");
			$("#discountForm").attr("action",url);
			$("#discountForm").submit();
			$("#discountForm").attr("action",beforeUrl);
		}
</script>
</html>

