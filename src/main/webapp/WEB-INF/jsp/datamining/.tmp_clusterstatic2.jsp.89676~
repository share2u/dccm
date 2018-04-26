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
							<form action="cluster/listcluster.do" method="post"
								name="discountForm" id="discountForm">
							</form>
							
							<div style="width:1000px;margin:0 auto;">
							<!--	<div id="aa" style="height:50px;"></div>-->
								<div id="cluster"
									style="text-align:center; float:left;; width: 800px;height:450px;margin:50px 50px 50px 100px"></div>
							    <div id="bb" style="height:150px;"></div>
								<div id="VIPgroups"
									style="text-align:center; float:left; width: 825px;height:450px;margin:50px 50px 50px 100px"></div>
								
							</div>
							<script type="text/javascript">
								//聚类的标准
							var myChart1 = echarts.init(document
									.getElementById('cluster'));
							var cluster = eval('${cluster}');
							var y1Data = new Array();
							var y2Data = new Array();
							for ( var x in cluster) {
								
								y1Data[x] = cluster[x].distance;
								y2Data[x] = cluster[x].rate;	
							}
						option1 = {
					    backgroundColor: '#0f375f',
					    tooltip: {
					        trigger: 'axis',
					        axisPointer: {
					            type: 'shadow'
					        }
					    },
					    legend: {
					        data: ['类内距离', '泛化率'],
					        textStyle: {
					            color: '#ccc'
					        }
					    },
					    xAxis: {
					        data: ['聚类数目二','聚类数目三','聚类数目四','聚类数目五','聚类数目六','聚类数目七','聚类数目八'],
					        axisLine: {
					            lineStyle: {
					                color: '#ccc'
					            }
					        }
					    },
					    yAxis: {
					        splitLine: {show: false},
					        axisLine: {
					            lineStyle: {
					                color: '#ccc'
					            }
					        }
					    },
					    series: [{
					        name: '类内距离',
					        type: 'line',
					        smooth: true,
					        showAllSymbol: true,
					        symbol: 'emptyCircle',
					        symbolSize: 15,
					        data: y1Data
					    }, {
					        name: '泛化率',
					        type: 'bar',
					        barWidth: 10,
					        itemStyle: {
					            normal: {
					                barBorderRadius: 5,
					                color: new echarts.graphic.LinearGradient(
					                    0, 0, 0, 1,
					                    [
					                        {offset: 0, color: '#14c8d4'},
					                        {offset: 1, color: '#43eec6'}
					                    ]
					                )
					            }
					        },
					        data: y2Data
					    }, {
					        name: 'line',
					        type: 'bar',
					        barGap: '-100%',
					        barWidth: 10,
					        itemStyle: {
					            normal: {
					                color: new echarts.graphic.LinearGradient(
					                    0, 0, 0, 1,
					                    [
					                        {offset: 0, color: 'rgba(20,200,212,0.5)'},
					                        {offset: 0.2, color: 'rgba(20,200,212,0.2)'},
					                        {offset: 1, color: 'rgba(20,200,212,0)'}
					                    ]
					                )
					            }
					        },
					        z: -12,
					        data: y1Data
					    }, {
					        name: 'dotted',
					        type: 'pictorialBar',
					        symbol: 'rect',
					        itemStyle: {
					            normal: {
					                color: '#0f375f'
					            }
					        },
					        symbolRepeat: true,
					        symbolSize: [12, 4],
					        symbolMargin: 1,
					        z: -10,
					        data: y1Data
					    }]
					};
								//VIP客户数目变化
							var myChart2 = echarts.init(document
									.getElementById('VIPgroups'));
							var VIPgroups = eval('${VIPgroups}');
							var xData = new Array();
							var yData = new Array();
							//var idData = new Array();
							for ( var x in VIPgroups) {
								xData[x] = VIPgroups[x].date;
								yData[x] = VIPgroups[x].c;
								//idData[x] = cluster[x].groupId;
							}
							var option2 = {
							      title: {
								        text: 'VIP客户数目'
								    },
								xAxis: {
							     
							        data: xData
							    },
							    yAxis: {
							    },
								visualMap: {
					            top: 10,
					            right: 10,
					            pieces: [{
					                gt: 0,
					                lte: 50,
					                color: '#096'
					            }, {
					                gt: 50,
					                lte: 100,
					                color: '#ffde33'
					            }, {
					                gt: 100,
					                lte: 150,
					                color: '#ff9933'
					            }, {
					                gt: 150,
					                lte: 200,
					                color: '#cc0033'
					            }, {
					                gt: 200,
					                lte: 250,
					                color: '#660099'
					            }, {
					                gt: 250,
					                color: '#7e0023'
					            }],
					            outOfRange: {
					                color: '#999'
					            }
					        },
		
							    series: [{
							        data: yData,
							        type: 'line'
							    }]
							};

			                    // 使用刚指定的配置项和数据显示图表。
								myChart1.setOption(option1);
								myChart2.setOption(option2);
							function ObjStory1(name,type,stack,data,label) //声明对象
									{
									    this.name = name;
									    this.type= type;
									    this.stack= stack;
									   
									    this.data = data;
										this.label = label;
									}
							function ObjStory2(type,data) //声明对象
								{   
								    this.type= type;	    
								    this.data = data;
								}
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
	<script type="text/javascript">
	$(top.hangge());
	</script>
</body>
</html>