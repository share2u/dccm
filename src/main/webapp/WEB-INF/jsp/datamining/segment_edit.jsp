<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html lang="en">
	<head>
	<base href="<%=basePath%>">
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
					
					<form action="cluster/goquery.do" name="Form" id="Form" method="post">
						<div id="zhongxin" style="padding-top: 13px;">
						<input type="hidden" name="label" id="label" value="${pd.label}"/>
						<table style="margin-top:5px;">
							<tr>	
								<td>请输入：</td>
								<td style="padding-left:2px;"><input type="text" placeholder="姓名/手机号" class="nav-search-input" id="nav-search-input" autocomplete="off" name="keywords" value="${pd.keywords }" placeholder="这里输入关键词"/>
								</td>
								
								<td style="vertical-align:top;padding-left:2px">
								<a class="btn btn-light btn-xs" onclick="tosearch();"  title="检索">
									<i id="nav-search-icon" class="ace-icon fa fa-search bigger-110 nav-search-icon blue">	
								</i></a></td>
							</tr>
						</table>
								
						<!-- 检索  -->
						
						<table id="simple-table" class="table table-striped table-bordered table-hover" style="margin-top:5px;">	
							<thead>
								<tr>
									<th class="center" style="width:50px;">序号</th>
									<th class="center">用户姓名</th>
									<th class="center">用户电话</th>
									<th class="center">订单金额</th>
									<th class="center">退款金额</th>
									<th class="center">订单次数</th>
									<th class="center">退款次数</th>
									<th class="center">最大订单金额</th>
									<th class="center">最大退款金额</th>
									<th class="center">用户等级</th>
								</tr>
							</thead>
													
							<tbody>
							<!-- 开始循环 -->	
							<c:choose>
								<c:when test="${not empty varList}">
									<c:forEach items="${varList}" var="var" varStatus="vs">
										<tr>
											<td class='center' style="width: 30px;">${vs.index+1}</td>
											<td class='center'>${var.name}</td>
											<td class='center'>${var.phone}</td>
									
											<td class='center'>
												<fmt:formatNumber value="${var.ordermoney_allsum}" pattern="0.00"/>
												</td>
											<td class='center'>
												<fmt:formatNumber value="${var.refund_allsum}" pattern="0.00"/>
												</td>
											<td class='center'>
												<fmt:formatNumber value="${var.cishu}" pattern="0"/>
												</td>
											<td class='center'>
												<fmt:formatNumber value="${var.cishu_refund}" pattern="0"/>
												</td>
											<td class='center'>
												<fmt:formatNumber value="${var.ordermoney_max}" pattern="0.00"/>
												</td>
											<td class='center'>
												<fmt:formatNumber value="${var.refund_max}" pattern="0.00"/>
												</td>
											<td class='center'>${var.level}</td>
										</tr>
									</c:forEach>
								</c:when>
								<c:otherwise>
									<tr class="main_info">
										<td colspan="100" class="center" >没有相关数据</td>
									</tr>
								</c:otherwise>
							</c:choose>
							</tbody>
						</table>
						</div>
						<div class="page-header position-relative">
						<table style="width:100%;">
							<tr>
								<td style="vertical-align:top;"><div class="pagination" style="float: right;padding-top: 0px;margin-top: 0px;">${page.pageStr}</div></td>
							</tr>
						</table>
						</div>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>


	<!-- /.main-container -->

	<!-- basic scripts -->
	<!-- 页面底部js¨ -->
	<%@ include file="../system/index/foot.jsp"%>
	<!-- 删除时确认窗口 -->
	<script src="static/ace/js/bootbox.js"></script>
	<!-- ace scripts -->
	<script src="static/ace/js/ace/ace.js"></script>
	<!-- 下拉框 -->
	<script src="static/ace/js/chosen.jquery.js"></script>
	<!-- 日期框 -->
	<script src="static/ace/js/date-time/bootstrap-datepicker.js"></script>
	<!--提示框-->
	<script type="text/javascript" src="static/js/jquery.tips.js"></script>
	
		<script type="text/javascript">
		$(top.hangge());
			//检索
		function tosearch(){
			top.jzts();
			$("#Form").submit();
		}
		//保存
		function save(){
			if($("#UID").val()==""){
				$("#UID").tips({
					side:3,
		            msg:'请输入用户姓名',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#UID").focus();
			return false;
			}
			if($("#SUM_MONEY").val()==""){
				$("#SUM_MONEY").tips({
					side:3,
		            msg:'请输入总金额',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#SUM_MONEY").focus();
			return false;
			}
			$("#Form").submit();
			$("#zhongxin").hide();
			$("#zhongxin2").show();
		}
		
		$(function() {
			//日期框
			$('.date-picker').datepicker({autoclose: true,todayHighlight: true});
		});
		</script>
</body>
</html>