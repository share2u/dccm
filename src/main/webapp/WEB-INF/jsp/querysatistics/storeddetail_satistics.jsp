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
							<form action="queryStoreddetail/satisticsStoredDetail.do" method="post"
								name="storeddetailForm" id="storeddetailForm">

                                <table style="margin-top:20px; margin-left: 100px; table-layout:fixed ;  border-collapse:separate; border-spacing:0px 20px;" >
								
									<td style="padding-left:10px;">储值时间：<input
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
						   <div style="width:1000px;margin:0 auto;">
								<div id="storeddetailzhu"
									style="text-align:center; float:left; width: 500px;height:400px;"></div>
								<div id="storedDetail"
									style="text-align:center; float:left; width: 500px;height:400px;"></div>
							</div>
<script type="text/javascript">
        // 基于准备好的dom，初始化echarts实例
var myChart = echarts.init(document.getElementById('storeddetailzhu'));
var storeddetail = eval('${storeddetail}');
var xData = new Array();
var yData = new Array();
var idData = new Array();
for ( var x in storeddetail) {
	xData[x] = storeddetail[x].STORE_NAME;
	yData[x] = storeddetail[x].moneyCount;
	idData[x] = storeddetail[x].STORE_ID;
}
// 指定图表的配置项和数据
var option = {
	title : {
		text : '储值卡销售统计'
	},
	tooltip : {},
	legend : {
		data : [ '销售金额' ]
	},
	xAxis : {
		data : xData,
		nameRotate :30,
		data : xData,
	 	axisLabel : {//设置全部x显示标签
		interval : 0,
		rotate:-30
		}
	/* axisLabel : {//设置全部x显示标签
	interval : 0
	}  */
	},
	yAxis : {},
	dataZoom : [ { // 这个dataZoom组件，默认控制x轴。
		type : 'slider', // 这个 dataZoom 组件是 slider 型 dataZoom 组件
		start : 0, // 左边在 10% 的位置。
		end : 100
	// 右边在 60% 的位置。
	}, { // 这个dataZoom组件，也控制x轴。
		type : 'inside', // 这个 dataZoom 组件是 inside 型 dataZoom 组件
		start : 0, // 左边在 10% 的位置。
		end : 100
	// 右边在 60% 的位置。
	} ],
	series : [ {
		name : '销售金额',
		type : 'bar',
		data : yData,
		itemStyle: {
            normal: {
                color: '#4E7CCC'
            }
        }
	} ]
};

// 使用刚指定的配置项和数据显示图表。
myChart.setOption(option);
  // 处理点击事件
myChart
		.on(
				'click',
				function(params) {
					$
							.get(
									'queryStoreddetail/selectStoredCategory?STORE_ID='
											+ idData[params.dataIndex],
								
									function(detail) {
										// 基于准备好的dom，初始化echarts实例
										var storedDetail = echarts.init(document.getElementById('storedDetail'));
										
										// 指定图表的配置项和数据
										var xDetailData = new Array();
										var yDetailData = new Array();
										
										for ( var x in detail) {
										xDetailData[x] = detail[x].STORED_MONEY;
										yDetailData[x] = detail[x].moneySum;
										//idData[x] = storeddetail[x].STORE_ID;
									}
										var option1 = {
											title : {
												text : "储值卡类型销售统计"
											},
											tooltip : {},
											legend : {
												data : [{
														    name: '储值卡类型',
														    
														    // 设置文本为红色
														    textStyle: {
														        color: 'blue'
														    }
														  },
											
														  ]
											},
											xAxis : {
												data :xDetailData
												 /* axisLabel : {//设置全部x显示标签
													interval : 0
													} */
											}, 
											yAxis : {},
											dataZoom : [
													{ // 这个dataZoom组件，默认控制x轴。
														type : 'slider', // 这个 dataZoom 组件是 slider 型 dataZoom 组件
														start : 0, // 左边在 10% 的位置。
														end : 100
													// 右边在 60% 的位置。
													},
													{ // 这个dataZoom组件，也控制x轴。
														type : 'inside', // 这个 dataZoom 组件是 inside 型 dataZoom 组件
														start : 0, // 左边在 10% 的位置。
														end : 100
													// 右边在 60% 的位置。
													} ],
											series : [ {
													name : '销售金额',
													type : 'bar',
													itemStyle: {
											            normal: {
											                color: '#EB4456'
											            }
											        },
													data : yDetailData
												}]
										};
										// 使用刚指定的配置项和数据显示图表。
										storedDetail
												.setOption(option1);
									},"json");
				});
function ObjStory(name,type,itemstyle,data) //声明对象
	{
	    this.name = name;
	    this.type= type;
	 
	    this.itemstyle = itemstyle;
	    this.data = data;
	}


    </script>
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
	$("#storeddetailForm").submit();
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
</html>

