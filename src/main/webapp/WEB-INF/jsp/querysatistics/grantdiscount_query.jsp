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
							<form action="queryDiscount/grantlist.do" method="post"
								name="discountForm" id="discountForm">
								<table style="table-layout:fixed ; width:1200px;  border-collapse:separate; border-spacing:0px 20px;">
									<tr>
										<td style="padding-left:10px;">
											优惠券组名称：<input  placeholder="优惠劵组名称" name="groupName" value="${pd.groupName}" id="groupName" type="text">
										</td>
										<td style="padding-left:10px;">
											客服姓名：<input  placeholder="客服姓名" name="staffName" value="${pd.staffName}" id="staffName" type="text">
										</td>
										<td style="padding-left:10px;"><input
											class="span10 date-picker" name="firstDate" id="firstDate"
											type="text" data-date-format="yyyy-mm-dd" readonly="readonly"
											style="width:99px;" value="${pd.firstDate}"
											placeholder="发放开始日期"/>
										<input
											class="span10 date-picker" name="lastDate" id="lastDate"
											type="text" data-date-format="yyyy-mm-dd" readonly="readonly"
											style="width:99px;margin-left: 10px;" value="${pd.lastDate}"
											placeholder="发放结束日期"/></td>

											<td style="padding-left:100px"><a
												class="btn btn-light btn-xs" onclick="searchs();" title="检索"><i
													id="nav-search-icon"
													class="ace-icon fa fa-search bigger-110 nav-search-icon blue"></i>
											</a>
												<a class="btn btn-light btn-xs" onclick="toExcel();" title="导出到EXCEL">
													<i id="nav-search-icon" class="ace-icon fa fa-download bigger-110 nav-search-icon blue"></i>
												</a>
											</td>
									</tr>
								</table>
								<!-- 检索  -->

								<table id="simple-table"
									class="table table-striped table-bordered table-hover"
									style="margin-top:5px;">
									<thead>
										<tr>
											<th class="center" style="width:50px;">序号</th>
											<th class="center" style="width:130px;">优惠券组名称</th>
											<th class="center" style="width:150px;">发放时间</th>
											<th class="center" style="width:60px;">客服</th>
											<th class="center" style="width:60px;">操作</th>
										</tr>
									</thead>
									<tbody>
										<!-- 开始循环 -->
										<c:choose>
											<c:when test="${not empty grantDiscounts}">
												<c:forEach items="${grantDiscounts}" var="grantDiscount" varStatus="vs">
													<tr>
														<td class='center' style="width: 30px;">${vs.index+1}</td>
														<td class='center'>${grantDiscount.groupName}</td>
														<td class='center'>${grantDiscount.createTime}</td>
														<td class='center'>${grantDiscount.staffName}</td>
														<td class='center'>
															<div class="hidden-sm hidden-xs btn-group">
																<a class="btn btn-xs btn-info" title="查看详情" onclick="discountGroupDetail('${grantDiscount.discountGroupId}');">
																	<i class="ace-icon fa fa-file bigger-120" title="查看详情"></i>
																</a>
		                                       				 </div>
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
	});
	
	//导出excel
		function toExcel(){
			var url ='<%=basePath%>queryDiscount/grantExcel.do';
			var beforeUrl = $("#discountForm").attr("action");
			$("#discountForm").attr("action",url);
			$("#discountForm").submit();
			$("#discountForm").attr("action",beforeUrl);
		}
		//查看优惠券发放详情
		function discountGroupDetail(Id){
			 top.jzts();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="优惠券发放详情";
			 diag.URL = '<%=basePath%>queryDiscount/list.do?groupId='+Id;
			 diag.Width = 1200;
			 diag.Height = 800;
			 diag.Modal = true;				//有无遮罩窗口
			 diag. ShowMaxButton = true;	//最大化按钮
		     diag.ShowMinButton = true;		//最小化按钮 
			 		 diag.CancelEvent = function(){ //关闭事件
				/*  if(diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none'){
					 nextPage(${page.currentPage});
				} */
				diag.close();
			 };
			 diag.show();
		}
</script>
</html>

