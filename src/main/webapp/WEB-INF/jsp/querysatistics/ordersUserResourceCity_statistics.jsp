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
							<div style=" clear:both;"></div>
							<div id="dituCity" style="text-align:center; margin:0 auto; width: 600px;height:500px;"></div>
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
</body>
<script type="text/javascript">
	$(top.hangge());
</script>
<script type="text/javascript">
		var provinces = ['shanghai', 'hebei','shanxi','neimenggu','liaoning','jilin','heilongjiang','jiangsu','zhejiang','anhui','fujian','jiangxi','shandong','henan','hubei','hunan','guangdong','guangxi','hainan','sichuan','guizhou','yunnan','xizang','shanxi1','gansu','qinghai','ningxia','xinjiang', 'beijing', 'tianjin', 'chongqing', 'xianggang', 'aomen'];
		var provincesText = ['上海', '河北', '山西', '内蒙古', '辽宁', '吉林','黑龙江',  '江苏', '浙江', '安徽', '福建', '江西', '山东','河南', '湖北', '湖南', '广东', '广西', '海南', '四川', '贵州', '云南', '西藏', '陕西', '甘肃', '青海', '宁夏', '新疆', '北京', '天津', '重庆', '香港', '澳门'];
		var name='${pd.province}';
		for(var a=0;a<provincesText.length;a++){
			if(provincesText[a]== name){
				name=provinces[a];
				break;
			}
		}
		$.get('static/js/map/json/province/' + name + '.json', function (geoJson) {
			
			echarts.registerMap(name, geoJson);
			var xyData = eval("("+'${xyData}'+")");
			var xData = xyData[0];//name
			var yData = xyData[1];//value
			var pieData=new Array();
			for(var i=0;i<xData.length;i++){
			var c={value:yData[i], name:xData[i]};
				pieData.push(c);
			}
			var dituCity=echarts.init(document.getElementById('dituCity'));
			var dituoption = {
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
				     /* toolbox: {//工具箱
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
				            mapType: name,
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
			dituCity.setOption(dituoption);
		});

		
		
						
</script>
</html>
