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
<script src="static/js/map/china.js"></script>
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
							<form action="statisticsOrder/UserSourceSum" method="post"
								name="orderResouceForm" id="orderResouceForm">
								<table style="margin-top:20px; margin-left: 100px; table-layout:fixed ;  border-collapse:separate; border-spacing:0px 20px;" >
								
									<td style="padding-left:10px;">订单创建时间：<input
												class="span10 date-picker" name="firstDate" id="firstDate"
												type="text" data-date-format="yyyy-mm-dd" readonly="readonly"
												style="width:99px;" value="${pd.firstDate}"
												placeholder="创建开始日期"/>
											<td style="padding-left:2px;"><input
												class="span10 date-picker" name="lastDate" id="lastDate"
												type="text" data-date-format="yyyy-mm-dd" readonly="readonly"
												style="width:99px;" value="${pd.lastDate}"
												placeholder="创建结束日期"/></td>
									<c:if test="${QX.cha == 1 }">
												<td style="padding-left:100px"><a
													class="btn btn-light btn-xs" onclick="searchs();" title="检索"><i
														id="nav-search-icon"
														class="ace-icon fa fa-search bigger-110 nav-search-icon blue"></i>
												</a>
												</td>
									</c:if>
								</table>
								</form>
								<hr>
									<div id="ditu" style="text-align:center; width: 800px;height:580px;margin: 0 auto"></div>
							
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
		$("#orderResouceForm").submit();
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
</script>
<script type="text/javascript">
	//市详情页面
		function cityDetial(province){
			 top.jzts();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="省份详情";
			 diag.URL = '<%=basePath%>statisticsOrder/UserSourceCitySum?province='+encodeURI(province)+'&firstDate=${pd.firstDate}&lastDate=${pd.lastDate}';
			 diag.Width = 650;
			 diag.Height = 500;
			 diag.Modal = true;				//有无遮罩窗口
			 diag. ShowMaxButton = true;	//最大化按钮
		     diag.ShowMinButton = true;		//最小化按钮
			 diag.CancelEvent = function(){ //关闭事件
				diag.close();
			 };
			 diag.show();
		}
	

		var xyData = eval("("+'${xyData}'+")");
		var xData = xyData[0];//name
		var yData = xyData[1];//value
		var pieData=new Array();
		for(var i=0;i<xData.length;i++){
		var c={value:yData[i], name:xData[i]};
		pieData.push(c);
		}
		var ditu=echarts.init(document
				.getElementById('ditu'));
				dituoption = {
					    title: {
					        text: '客户来源',
					        left: 'center'
					    },
					    tooltip: {
					        trigger: 'item'
					    },
					    legend: {
					        orient: 'vertical',
					        left: 'left',
					        data:xData
					    },
					    visualMap: {
					        left: 'left',
					        top: 'bottom',
					        text: ['高','低'],           // 文本，默认为数值文本
					        calculable: true
					    },
					   /*  toolbox: {//工具箱
					        show: true,
					        orient: 'vertical',
					        left: 'right',
					        top: 'center',
					        feature: {
					            dataView: {readOnly: false},
					            restore: {},
					            saveAsImage: {}
					        }
					    }, */
					    series: [
					        {
					            name: '订单数量',
					            type: 'map',
					            map: 'china',
					            roam: false,
					            label: {
					                normal: {
					                    show: true
					                },
					                emphasis: {
					                    show: true
					                }
					            },
					            data:pieData
					        }
					    ]
					};
		ditu.setOption(dituoption);
		ditu.on('click', function (params) {
		    var city = params.name;
		    cityDetial(city);
		});
		
						
</script>
</html>
