<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<head>
    <meta charset="utf-8">
    <title>ECharts</title>
    <script src="https://cdn.bootcss.com/jquery/1.12.4/jquery.min.js"></script>
      <script src="http://echarts.baidu.com/build/dist/echarts.js"></script>
     
</head>


<body>
    <!-- 为ECharts准备一个具备大小（宽高）的Dom -->
    <div id="main" style="height:400px"></div>
     <div id="main2" style="height:400px"></div>
    <script type="text/javascript">
        // 路径配置
            require.config({
            paths: {
                echarts: 'http://echarts.baidu.com/build/dist'
            }
        });
        // 使用
          require(
            [
                'echarts',
                'echarts/chart/bar' // 使用柱状图就加载bar模块，按需加载
            ],
            drewEcharts 
        );
         function drewEcharts(ec) {
        chart1(ec);
         chart2(ec);
         }
          function chart1(ec) {
                // 基于准备好的dom，初始化echarts图表
                myChart = ec.init(document.getElementById('main')); 
                var option = {
                title : {
                     text: 'K-means算法改进前后对比',
                    
                     x:'center'
                    },
                    tooltip: {
                        show: true
                    },
                    legend: {
                     orient : 'vertical',
                         x : 'left',
                        data:['经典K-means算法','改进K-means算法']
                    },
                    xAxis : [
                         {
                            type : 'category',
                            data : ["平均类内距","平均类间距","平均类外距"]
                        }
                    ],
                    yAxis : [
                        {
                            type : 'value'
                        }
                    ],
                    series :  [
        {
            name:'经典K-means算法',
            type:'bar',
            data:[0.0014, 0.120, 0.224],
            itemStyle : { normal: {label : {show: true, position: 'top'}}, color:'#6495ED'}
            },
        {
            name:'改进K-means算法',
            type:'bar',
            data:[0.0012, 0.126, 0.255],
            itemStyle : { normal: {label : {show: true, position: 'top'}}, color:'#7CCD7C'}
           
        }
    ],
    label:{
    normal:{
    show : true,
    position :'top',
    testStyle :{
    color:'black'
    }
    }
    }
                };               
                // 为echarts对象加载数据 
                myChart.setOption(option); 
                $(top.hangge());//关闭加载状态
            };
            function chart2(ec) {
                // 基于准备好的dom，初始化echarts图表
                myChart2 = ec.init(document.getElementById('main2')); 
                var option = {
                title : {
                     text: '协同过滤算法改进前后对比',
                    
                     x:'center'
                    },
                    tooltip: {
                        show: true
                    },
                    legend: {
                     orient : 'vertical',
                         x : 'left',
                        data:['经典协同过滤算法','改进协同过滤算法']
                    },
                    xAxis : [
                         {
                            type : 'category',
                            data : ["准确率","召回率","F1值"]
                        }
                    ],
                    yAxis : [
                        {
                            type : 'value'
                        }
                    ],
                    series :  [
        {
            name:'经典协同过滤算法',
            type:'bar',
            data:[14.2, 24.33, 17.93],
            itemStyle : { normal: {label : {show: true, position: 'top'}}, color:'#6495ED'}
         
        },
        {
            name:'改进协同过滤算法',
            type:'bar',
            data:[16.23, 26.32, 20.07],
            itemStyle : { normal: {label : {show: true, position: 'top'}}, color:'#7CCD7C'}
           
        }
    ],
    label:{
    normal:{
    show : true,
    position :'top'
    }
    }
                };               
                // 为echarts对象加载数据 
                myChart2.setOption(option); 
                $(top.hangge());//关闭加载状态
            };
    </script>
</body>