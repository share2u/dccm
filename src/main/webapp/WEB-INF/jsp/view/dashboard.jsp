<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
<head>
    <base href="<%=basePath%>">
    <%@ include file="../system/index/top.jsp"%>
    <link rel="stylesheet" href="static/css/view/dashboard.css" type="text/css">
    <!-- 引入 ECharts 文件 -->
    <script src="js/echarts.js"></script>
</head>
<body>
<div class="view_main">
    <div class="view_top">
        <div class="top_l">
            <span style="font-size: 17px;">${name}多维分析仪表盘</span>
        </div>
        <div class="top_r">
            <span style="font-size: 17px;">
            <a onclick="newView()">
                新增图表
            </a></span>
        </div>

    </div>
    <div class="view_content">
        <c:forEach items="${data}" var="viewOption" varStatus="vs">
            <div class="chart_wrapper">
                <img src="static/images/delete.svg" class="delete"/>
                <div class="chart" id="${viewOption.id}">
                </div>
            </div>
        </c:forEach>
    </div>
</div>
<!-- 页面底部js¨ -->
<%@ include file="../system/index/foot.jsp"%>

<script type="text/javascript">
    $(top.hangge());
    var arrayOption = new Array();
    var arrayId = new Array();
    <c:forEach items="${data}" var="t">
        arrayOption.push(${t.option1});
        arrayId.push(${t.id});
    </c:forEach>
    for(var i=0;i<arrayId.length;i++)
    {
        var myChart = echarts.init(document.getElementById(arrayId[i]));
        myChart.setOption(arrayOption[i]);
    }
    function newView(){
        window.open('http://www.liangliangempire.cn/view/chart/dashborad/create/'+${id});
      /*  top.jzts();
        var diag = new top.Dialog();
        diag.Drag=true;
        diag.Title="新增图表"
        diag.URL="http://localhost:8080/dccm/queryDiscount/list.do?groupId="+Id;
        diag.Width = 1200;
        diag.Height = 800;
        diag.Modal = true;				//有无遮罩窗口
        diag. ShowMaxButton = true;	//最大化按钮
        diag.ShowMinButton = true;		//最小化按钮
        diag.CancelEvent = function(){ //关闭事件
            /!*  if(diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none'){
             nextPage(1);
             } *!/
            diag.close();
        };
        diag.show();*/
    }
    function dele(optionId){
        $.ajax({
            url:"http://wwww.liangliangempire.cn/view/chart/option/dele",
            data:{"optionId":optionId},
            type:"POST",
            success:function(msg){
                console.log("删除成功，成功刷新页面");
            }
        });
    }

    $(function(){
        $('.view_content').on('click','.chart_wrapper .delete',function(){
            var optionId = $(this).parent().find('.chart').attr("id");
            dele(optionId);
        })
    });
</script>
<%--<script type="text/javascript" src="static/js/view/myjs.js"/>--%>
</body>
</html>
