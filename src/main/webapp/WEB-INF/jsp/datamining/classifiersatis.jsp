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
							<form action="classifier/listclassifier.do" method="post"
								name="discountForm" id="Form">
							</form>
							
							<div style="width:1000px;margin:0 auto;">
								<div id="aa" style="height:50px;"></div>
								<div id="classifier"
									style="text-align:center; float:left; width: 450px;height:400px;"></div>
							    <div id="liushigroups"
									style="text-align:center; float:right; width:450px;height:427px;"></div>
								
							</div>
							<script type="text/javascript">
								//fen类的标准
							var myChart1 = echarts.init(document
									.getElementById('classifier'));
							var classifier = eval('${classifier}');
							var xData = new Array();
							var y1Data = new Array();
							var y2Data = new Array();
							var y3Data = new Array();
							for ( var x in classifier) {
								xData[x] = classifier[x].name;
								y1Data[x] = classifier[x].accuracy;
								y2Data[x] = classifier[x].recall;
								y3Data[x] = classifier[x].fscore;
							}
							var option1 = {
						    tooltip : {
						        trigger: 'axis',
						        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
						            type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
						        }
						    },
						    legend: {
						        data: ['准确率', '召回率','f1分数']
						    },
						    grid: {
						        left: '3%',
						        right: '4%',
						        bottom: '3%',
						        containLabel: true
						    },
						    xAxis:  { },
						    yAxis: {
						        //type: 'category',
						        data: xData
						    },
						    series: [
						        {
						            name: '准确率',
						            type: 'bar',
						            stack: '总量',
						            label: {
						                normal: {
						                    show: true,
						                    position: 'insideRight'
						                }
						            },
						            data: y1Data
						        },
						        {
						            name: '召回率',
						            type: 'bar',
						            stack: '总量',
						            label: {
						                normal: {
						                    show: true,
						                    position: 'insideRight'
						                }
						            },
						            data: y2Data
						        },
								   {
						            name: 'f1分数',
						            type: 'bar',
						            stack: '总量',
						            label: {
						                normal: {
						                    show: true,
						                    position: 'insideRight',
											
						                }
						            },
						            data: y3Data
						        },
			   
						    ]
						};
					
								//VIP客户数目变化
							var myChart2 = echarts.init(document
									.getElementById('liushigroups'));
							var liushigroups = eval('${liushigroups}');
							var xData = new Array();
							var yData = new Array();
							//var idData = new Array();
							for ( var x in liushigroups) {
								xData[x] = liushigroups[x].date;
								yData[x] = liushigroups[x].c;
								//idData[x] = cluster[x].groupId;
							}
							var option2 = {
							      title: {
								        text: '流失客户数目'
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
							                lte: 5,
							                color: '#096'
							            }, {
							                gt: 5,
							                lte: 10,
							                color: '#ffde33'
							            }, {
							                gt: 10,
							                lte: 15,
							                color: '#ff9933'
							            }, {
							                gt: 15,
							                lte: 20,
							                color: '#cc0033'
							            }, {
							                gt: 20,
							                lte: 25,
							                color: '#660099'
							            }, {
							                gt: 25,
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