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
							<form action="statisticsServiceStaff/statisticsService.do" method="post"
								name="discountForm" id="discountForm">
								<table style="margin-top:20px; margin-left: 100px; table-layout:fixed ;  border-collapse:separate; border-spacing:0px 20px;" >
								
									<td style="padding-left:10px;">创建时间：<input
												class="span10 date-picker" name="firstDate" id="firstDate"
												type="text" data-date-format="yyyy-mm-dd" readonly="readonly"
												style="width:99px;" value="${pd.firstDate}"
												placeholder="创建开始日期"/>
											<td style="padding-left:2px;"><input
												class="span10 date-picker" name="lastDate" id="lastDate"
												type="text" data-date-format="yyyy-mm-dd" readonly="readonly"
												style="width:99px;" value="${pd.lastDate}"
												placeholder="创建结束日期"/></td>
										
										<td style="vertical-align:top;padding-left:2px;" ><b>请选择门店：</b>
											 	<select class="chosen-select form-control" name="STORE_ID" id="STORE_ID" data-placeholder="请选择门店" style="vertical-align:top;width: 220px;">
										        <option value="${STORE_ID}"></option>
												<c:forEach items="${storeList}" var="store">
													<option value="${store.STORE_ID}" <c:if test="${pd.STORE_ID==store.STORE_ID}">selected</c:if>>${store.STORE_NAME}</option>
												</c:forEach>  
											  	</select>
								          </td>
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
							<div style="width:1000px;margin:0 auto;">
								<div id="ordersCount"
									style="text-align:center; width: 900px;height:400px; margin-bottom: 50px;"></div>
								<div id="ordersMoney"
									style="text-align:center; width: 900px;height:400px;"></div>
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
										.getElementById('ordersCount'));
								var ordersSum = eval("("+'${ordersSum}'+")");
								var xData = new Array();
								var y0DetailData = new Array();
								var y1DetailData = new Array();
								var y2DetailData = new Array();
								var y3DetailData = new Array();
								var y4DetailData = new Array();
								var y5DetailData = new Array();
								var y6DetailData = new Array();
								var y7DetailData = new Array();
								$.each(ordersSum,function(storeId,storeOrderDetail){
									xData.push(storeOrderDetail[0]);
									y0DetailData.push(storeOrderDetail[3]);
									y1DetailData.push(storeOrderDetail[5]);
									y2DetailData.push(storeOrderDetail[7]);
									y3DetailData.push(storeOrderDetail[9]);
									y4DetailData.push(storeOrderDetail[11]);
									y5DetailData.push(storeOrderDetail[13]);
									y6DetailData.push(storeOrderDetail[15]);
									y7DetailData.push(storeOrderDetail[1]);
								});
								// 指定图表的配置项和数据
								var option = {
									title : {
										text : '客服订单数量统计'
									},
									tooltip : {},
									legend : {
										data : [ '待支付','已关闭','已预约','已过期','待评价','已完成','已取消','总数']
									},
									xAxis : {
										name:'客服',
										nameRotate :0,
										data : xData,
									 	axisLabel : {//设置全部x显示标签
										interval : 0,
										rotate:0
										}
									},
									yAxis :  [
								                {
								                    'type':'value',
								                    'name':'订单数量（个）'
								                }/* ,
								                {
								                    'type':'value',
								                    'name':'订单金额（元）'
								                } */
								            ],
									dataZoom : [ { // 这个dataZoom组件，默认控制x轴。
										type : 'slider', // 这个 dataZoom 组件是 slider 型 dataZoom 组件
										start : 20, // 左边在 10% 的位置。
										end : 100
									// 右边在 60% 的位置。
									}, { // 这个dataZoom组件，也控制x轴。
										type : 'inside', // 这个 dataZoom 组件是 inside 型 dataZoom 组件
										start : 20, // 左边在 10% 的位置。
										end : 100
									// 右边在 60% 的位置。
									} ],
									series : [
									 {
										name : '待支付',
										type : 'bar',
										stack: 'a',
										yAxisIndex:'0',
										data : y0DetailData,
										itemStyle: {
								            normal: {
								                color: '#4E7CCC'
								            }
								        },
									},
									 {
										name : '已关闭',
										type : 'bar',
										stack: 'a',
										yAxisIndex:'0',
										data : y1DetailData,
										itemStyle: {
								            normal: {
								                color: '#36B3C3'
								            }
								        },
									},
									 {
										name : '已预约',
										type : 'bar',
										stack: 'a',
										yAxisIndex:'0',
										data : y2DetailData,
										itemStyle: {
								            normal: {
								                color: '#E2F194'
								            }
								        }
									},
									 {
										name : '已过期',
										type : 'bar',
										stack: 'a',
										yAxisIndex:'0',
										data : y3DetailData,
										itemStyle: {
								            normal: {
								                color: '#F8AC62'
								            }
								        }
									},
									 {
										name : '待评价',
										type : 'bar',
										stack: 'a',
										yAxisIndex:'0',
										data : y4DetailData,
										itemStyle: {
								            normal: {
								                color: '#C82B3D'
								            }
								        },
									},
									 {
										name : '已完成',
										type : 'bar',
										stack: 'a',
										yAxisIndex:'0',
										data : y5DetailData,
										itemStyle: {
								            normal: {
								                color: '#36B3C3'
								            }
								        }
									},
									 {
										name : '已取消',
										type : 'bar',
										stack: 'a',
										yAxisIndex:'0',
										data : y6DetailData,
										itemStyle: {
								            normal: {
								                color: '#EB4456'
								            }
								        }
									},
									 {
										name : '总数',
										type : 'bar',
										stack: 'b',
										yAxisIndex:'0',
										barGap: '-100%',
										z:1,
										itemStyle: {
								            normal: {
								                color: '#CDD0D5'
								            }
								        },
										label: {
							                 normal: {
							                    show: true,
							                    position: 'top',
							                    formatter: '{c}',
							                    textStyle:{
							                    	color:"black"
							                    }
							                }
							            },
										data : y7DetailData
									}
									 ]
								};
								// 使用刚指定的配置项和数据显示图表。
								myChart.setOption(option);
								// 基于准备好的dom，初始化echarts实例
								var myChartMoney = echarts.init(document
										.getElementById('ordersMoney'));
								var x1Data = new Array();
								var y8DetailData = new Array();
								var y9DetailData = new Array();
								var y10DetailData = new Array();
								var y11DetailData = new Array();
								var y12DetailData = new Array();
								var y13DetailData = new Array();
								var y14DetailData = new Array();
								var y15DetailData = new Array();
								$.each(ordersSum,function(storeId,storeOrderDetail){
									x1Data.push(storeOrderDetail[0]);
									y8DetailData.push(storeOrderDetail[4]);
									y9DetailData.push(storeOrderDetail[6]);
									y10DetailData.push(storeOrderDetail[8]);
									y11DetailData.push(storeOrderDetail[10]);
									y12DetailData.push(storeOrderDetail[12]);
									y13DetailData.push(storeOrderDetail[14]);
									y14DetailData.push(storeOrderDetail[16]);
									y15DetailData.push(storeOrderDetail[2]);
								});
								// 指定图表的配置项和数据
								var optionMoney = {
									title : {
										text : '客服订单金额统计'
									},
									tooltip : {},
									legend : {
										data : [ '待支付','已关闭','已预约','已过期','待评价','已完成','已取消','总钱']
									},
									xAxis : {
										name:'门店名称',
										nameRotate :5,
										data : x1Data,
									 	axisLabel : {//设置全部x显示标签
										interval : 0,
										rotate:-5
										}
									},
									yAxis :  [
								                {
								                    'type':'value',
								                    'name':'订单金额（元）'
								                }/* ,
								                {
								                    'type':'value',
								                    'name':'订单金额（元）'
								                } */
								            ],
									dataZoom : [ { // 这个dataZoom组件，默认控制x轴。
										type : 'slider', // 这个 dataZoom 组件是 slider 型 dataZoom 组件
										start : 20, // 左边在 10% 的位置。
										end : 100
									// 右边在 60% 的位置。
									}, { // 这个dataZoom组件，也控制x轴。
										type : 'inside', // 这个 dataZoom 组件是 inside 型 dataZoom 组件
										start : 20, // 左边在 10% 的位置。
										end : 100
									// 右边在 60% 的位置。
									} ],
									series : [
									 {
										name : '待支付',
										type : 'bar',
										stack: 'a',
										yAxisIndex:'0',
										data : y8DetailData,
										itemStyle: {
								            normal: {
								                color: '#4E7CCC'
								            }
								        },
									},
									 {
										name : '已关闭',
										type : 'bar',
										stack: 'a',
										yAxisIndex:'0',
										data : y9DetailData,
										itemStyle: {
								            normal: {
								                color: '#36B3C3'
								            }
								        },
									},
									 {
										name : '已预约',
										type : 'bar',
										stack: 'a',
										yAxisIndex:'0',
										data : y10DetailData,
										itemStyle: {
								            normal: {
								                color: '#E2F194'
								            }
								        },
									},
									 {
										name : '已过期',
										type : 'bar',
										stack: 'a',
										yAxisIndex:'0',
										data : y11DetailData,
										itemStyle: {
								            normal: {
								                color: '#F8AC62'
								            }
								        },
									},
									 {
										name : '待评价',
										type : 'bar',
										stack: 'a',
										yAxisIndex:'0',
										data : y12DetailData,
										itemStyle: {
								            normal: {
								                color: '#C82B3D'
								            }
								        },
									},
									 {
										name : '已完成',
										type : 'bar',
										stack: 'a',
										yAxisIndex:'0',
										data : y13DetailData,
										itemStyle: {
								            normal: {
								                color: '#36B3C3'
								            }
								        },
									},
									 {
										name : '已取消',
										type : 'bar',
										stack: 'a',
										yAxisIndex:'0',
										data : y14DetailData,
										itemStyle: {
								            normal: {
								                color: '#EB4456'
								            }
								        },
									},
									 {
										name : '总钱',
										type : 'bar',
										stack: 'b',
										yAxisIndex:'0',
										barGap: '-100%',
										z:1,
										itemStyle: {
								            normal: {
								                color: '#CDD0D5'
								            }
								        },
										label: {
							                normal: {
							                    show: true,
							                    position: 'top',
							                    formatter: '{c}',
							                    textStyle:{
							                    	color:"black"
							                    }
							                }
							            },
										data : y15DetailData
									}
									 ]
								};
								// 使用刚指定的配置项和数据显示图表。
								myChartMoney.setOption(optionMoney);
							</script>
</html>
