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
								<%-- <table style="margin-top:20px; margin-left: 100px; table-layout:fixed ;  border-collapse:separate; border-spacing:0px 20px;" >
								<tr>
									<td style="padding-left:10px;">时间：<input
												class="span10 date-picker" name="firstDate" id="firstDate"
												type="text" data-date-format="yyyy-mm-dd" readonly="readonly"
												style="width:99px;" value="${pd.firstDate}"
												placeholder="创建开始日期"/></td>
											<td style="padding-left:2px;"><input
												class="span10 date-picker" name="lastDate" id="lastDate"
												type="text" data-date-format="yyyy-mm-dd" readonly="readonly"
												style="width:99px;" value="${pd.lastDate}"
												placeholder="创建结束日期"/></td>
												<td style="padding-left:100px"><a
													class="btn btn-light btn-xs" onclick="searchs();" title="检索"><i
														id="nav-search-icon"
														class="ace-icon fa fa-search bigger-110 nav-search-icon blue"></i>
												</a>
												</td>
									</tr>
								</table> --%>
								</form>
							<div style="width:1000px;margin:50px auto;">
								<div id="ordersConversion"
									style="text-align:center; width: 1000px;height:600px; margin-bottom: 50px; "></div>
							</div>
							
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
</script>
<script type="text/javascript">
								// 基于准备好的dom，初始化echarts实例
								var myChart = echarts.init(document
										.getElementById('ordersConversion'));
								var list = eval("("+'${list}'+")");
								// 指定图表的配置项和数据
								var option = {
										    title: {
										        text: '客户转化率',
										    },
										    grid:{
										    	show: true,
										    	backgroundColor:'#F8F6F5'
										    },
										    tooltip: {
										        trigger: 'item',
										        formatter: "{a} <br/>{b} : {c}%"
										    },
										   /*  toolbox: {
										        feature: {
										            dataView: {readOnly: false},
										            restore: {},
										            saveAsImage: {}
										        }
										    }, */
										    legend: {
										        data: ['微信关注人数','系统注册人数','完善信息人数','生成订单去重人数']
										    },
										    calculable: true,
										    series: [
										        {
										            name:'漏斗图',
										            type:'funnel',
										            left: '10%',
										            top: 60,
										            //x2: 80,
										            bottom: 60,
										            width: '80%',
										            // height: {totalHeight} - y - y2,
										            min: 0,
										            max: 100,
										            minSize: '0%',
										            maxSize: '100%',
										            sort: 'descending',
										            gap: 2,
										            label: {
										                normal: {
										                    show: true,
										                    position: 'inside'
										                },
										                emphasis: {
										                	show: true,
										                    textStyle: {
										                        fontSize: 20,
										                        color:'#0A0807'
										                    }
										                }
										            },
										            itemStyle: {
									                normal: {
									                    opacity: 0.9,
									                    borderColor: '#fff',
									                    borderWidth: 2
									                }
									            },
										            labelLine: {
										                normal: {
										                    length: 10,
										                    lineStyle: {
										                        width: 1,
										                        type: 'solid'
										                    }
										                }
										            },
										            label: {
											                normal: {
											                    position: 'inside',
											                    formatter: '{b}',
											                    textStyle: {
											                        color: '#090909'
											                    }
											                },
											                emphasis: {
											                    position:'inside',
											                    formatter: '{b}: {c}%',
											                    textStyle: {
											                        color: '#ddd'
											                    }
											                }
											            },
										            data: [
 										                {value: (list[0]/list[0]*100).toFixed(2), name: '微信关注人数'},
										                {value: (list[1]/list[0]*100).toFixed(2), name: '系统注册人数'},
										                {value: (list[2]/list[0]*100).toFixed(2), name: '完善信息人数'},
										                {value: (list[3]/list[0]*100).toFixed(2), name: '生成订单去重人数'}
										            ]
										        }
										    ]
										};

								// 使用刚指定的配置项和数据显示图表。
								myChart.setOption(option);
							</script>
</html>
