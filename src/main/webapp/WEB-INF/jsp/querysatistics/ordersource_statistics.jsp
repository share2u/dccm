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
<!-- 引入 ECharts 文件 -->
<script src="js/echarts.js"></script>
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
							<form action="statisticsOrder/staticticsOrdersSource.do" method="post"
								name="orderForm" id="orderForm">
								<table style="margin-top:20px; margin-left: 100px; table-layout:fixed ;  border-collapse:separate; border-spacing:0px 20px;" >
								<tr>
									<td style="padding-left:10px;">订单创建时间：<input
												class="span10 date-picker" name="firstDate" id="firstDate"
												type="text" data-date-format="yyyy-mm-dd" readonly="readonly"
												style="width:99px;" value="${pd.firstDate}"
												placeholder="创建开始日期"/></td>
											<td style="padding-left:2px;"><input
												class="span10 date-picker" name="lastDate" id="lastDate"
												type="text" data-date-format="yyyy-mm-dd" readonly="readonly"
												style="width:99px;" value="${pd.lastDate}"
												placeholder="创建结束日期"/></td>
											<td style="vertical-align:middle;padding-left:2px;" ><b>请选择门店：</b>
											 	<select onchange="storeChange();"class="chosen-select form-control" name="STORE_ID" id="STORE_ID" data-placeholder="请选择门店" style="vertical-align:top;width: 180px;">
										        <option value=""></option>
												<c:forEach items="${storeList}" var="store">
													<option value="${store.STORE_ID}" >${store.STORE_NAME}</option>
												</c:forEach>  
											  	</select>
											</td>
											<td style="padding-left:10px;vertical-align:middle;">
											<b>医生姓名：</b>
											<select  class="chosen-select form-control" name="staffNames"
											id="staffNames" data-placeholder="员工姓名" style="vertical-align:top;width: 180px;">
												<option value=""></option>
										   </select>
										   </td>
									
												<td style="padding-left:100px"><a
													class="btn btn-light btn-xs" onclick="searchs();" title="检索"><i
														id="nav-search-icon"
														class="ace-icon fa fa-search bigger-110 nav-search-icon blue"></i>
												</a>
												</td>
									</tr>
								</table>
								</form>
								<hr>
							<div style="width:1000px;margin:0 auto;">
								<div id="ordersource"
									style="text-align:center;width: 500px;height:400px;margin: 0 auto"></div>
								
							</div>
							<script type="text/javascript">
							
							function searchs(){
								
								$("#orderForm").submit();
							}
							  var myChart = echarts.init(document
										.getElementById('ordersource'));
								var xyData = eval("("+'${xyData}'+")");
								var xData = xyData[0];//name
								var yData = xyData[1];//value
								var pieData=new Array();
								for(var i=0;i<xData.length;i++){
								var c={value:yData[i], name:xData[i]};
								pieData.push(c);
								}
								
								// 指定图表的配置项和数据
								var option = {
									title : {
										text : '订单来源统计',
										 x:'center'
									},
									tooltip : {
									   trigger: 'item',
                                       formatter: "{a} <br/>{b} : {c} ({d}%)"
									},
									 legend: {
								        orient : 'vertical',
								        x : 'left',
								        data:['微信下单','面对面下单','药品']
								    },
									series : [
									 {
										name : '订单数量',
										type : 'pie',
									
										data : pieData
									}
									],
									   color: ['#EE0000','#3A5FCD','green']
								};
								// 使用刚指定的配置项和数据显示图表。
								myChart.setOption(option);
							</script>
							<div style=" clear:both;"></div>
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
	function searchs() {
		top.jzts();
		$("#orderForm").submit();
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
			//门店选择触发员工姓名
		function storeChange(){
				var storeId = $("#STORE_ID option:selected").val();
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
</script>
</html>
