<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
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
<!-- 下拉框 -->
<link rel="stylesheet" href="static/ace/css/chosen.css" />
<!-- jsp文件头和头部 -->
<%@ include file="../../system/index/top.jsp"%>
<!-- 日期框 -->
<style type="text/css">
	.paymethod{
		width:100%;
		min-height:350px;
		text-align:left;
		margin-top:50px;
	}
	.paymethod table{
		font-size:20px;
		width:500px;
		height:300px;
	}
	.paymethod input[type=checkbox]:checked {
   	  background-color: red;
	}
</style>
<script type="text/javascript" src="static/js/jquery-1.7.2.js"></script>
<link rel="stylesheet" href="static/ace/css/datepicker.css" />
<link rel="stylesheet" href="static/css/default/venues.css" />
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
						<form action="recommend/showUser.do" method="post" name="Form1" id="Form1">
						
						<div>
							<span style="margin-top:8px;float:left;">请输入用户uid：</span>
							<div class="nav-search" style="width:80%; float:left;text-align:left;">
								<span class="input-icon">
									<input type="text" placeholder="请输出用户uid" class="nav-search-input" id="nav-search-input" autocomplete="off" name="keywords" value="${pd.keywords}" placeholder="这里输入关键词"/>
									<i class="ace-icon fa fa-search nav-search-icon"></i>
								</span>
								<a class="btn btn-light btn-xs" onclick="tosearch1();"  title="检索"><i id="nav-search-icon" class="ace-icon fa fa-search bigger-110 nav-search-icon blue"></i></a>
							</div>
						</div>
						
						<table id="simple-table" class="table table-striped table-bordered table-hover" style="margin-top:5px;">	
							<thead>
								<tr>
									<th class="center" style="width:35px;">
									<label class="pos-rel"><span class="lbl"></span></label>
									</th>
									<th class="center" style="width:50px;">序号</th>
									<th class="center">uid</th>
									<th class="center">姓名</th>
									<th class="center">昵称</th>
									<th class="center">性别</th>
									<th class="center">出生年月</th>
									<th class="center">电话号码</th>
					
								</tr>
							</thead>
													
							<tbody>
							<!-- 开始循环 -->	
							<c:choose>
								<c:when test="${not empty userList}">
									<c:forEach items="${userList}" var="user" varStatus="vs">
										<tr>
											<td class='center'>
												<input type='radio' name='uid' value="${user.uid}" class="ace" onclick="refreshProjectAndRecommend(this.value);"/><span class="lbl"></span>
											</td>
											<td class='center' style="width: 30px;">${vs.index+1}</td>
											<td class='center'>${user.uid}</td>
											<td class='center'>${user.name}</td>
											<td class='center'>${user.username}</td>
											<td class='center'>
												<c:if test="${user.sex==1}">男</c:if>
												<c:if test="${user.sex==2}">女</c:if>
												<c:if test="${user.sex==0}">未知</c:if>
											</td>
											<td class='center'>${user.age}</td>
											<td class='center'>${user.phone}</td>
											
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
						
						<table style="width:100%;">
							<tr>
								<td style="vertical-align:top;"><div class="pagination" style="float: right;padding-top: 0px;margin-top: 0px;">${page.pageStr}</div></td>
							</tr>
						</table>
						</form>	

						<!--检索用户 end-->
						<!-- 查询用户购买的项目 -->
						<div style="clear:both;"></div>
						<div id="projectdiv" style="width:100%;display:none;text-align: left;">
						<span style="display:block;text-align:center;font-size: 16px;color: red;">该用户购买过的项目：</span>
						<table id="project-table" class="table table-striped table-bordered table-hover" style="margin-top:5px;">	
							<thead>
								<tr>
									<!-- <th class="center" style="width:35px;">
									<label class="pos-rel"><span class="lbl"></span></label>
									</th> -->
									<th class="center">项目编号</th>
									<th class="center">项目名称</th>
									<th class="center">门店</th>
									<th class="center">医生</th>
								</tr>
							</thead>
							<tbody id="project_tbody">
								
							</tbody>
							</table>
						</div> 
						<div id="projectdiv2">
						<span style="float:left;font-size: 16px;color: red;">当前用户没有购买过服务项目！</span>
						</div>
						
						<!-- 推荐 -->
						<div style="clear:both;"></div>
						<div id="recommenddiv" style="width:100%;text-align: left;">
						<span style="display:block;text-align:center;font-size: 16px;color: red;">为该用户推荐的项目：</span>
						<table id="recommend-table" class="table table-striped table-bordered table-hover" style="margin-top:5px;">	
							<thead>
								<tr>
									<!-- <th class="center" style="width:35px;">
									<label class="pos-rel"><span class="lbl"></span></label>
									</th> -->
									<th class="center">门店</th>
									<th class="center">医生</th>
									<th class="center">项目名称</th>
									<th class="center">价格</th>
								</tr>
							</thead>
							<tbody id="recommend_tbody">
								
							</tbody>
							</table>
						</div> 
	
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
		<a href="#" id="btn-scroll-up" class="btn-scroll-up btn btn-sm btn-inverse">
			<i class="ace-icon fa fa-angle-double-up icon-only bigger-110"></i>
		</a>
		
		

	</div>
	<!-- /.main-container -->

	<!-- basic scripts -->
	<!-- 页面底部js¨ -->
	<%@ include file="../../system/index/foot.jsp"%>
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
		$(top.hangge());//关闭加载状态
		
		$(function() {
			//日期框
			$('.date-picker').datepicker({
				autoclose : true,
				todayHighlight : true
			});
		});
		
		var servicetime;
		var correctservicetime;
		
		var serviceproject_json;
		
		//搜索用户		
		function tosearch1(){
			top.jzts();
			$("#Form1").submit();
		}
			//进行推荐
		function refreshProjectAndRecommend(uid){
		$("#recommend_tbody").html("");
		$("#project_tbody").html("");
		$.ajax({
		url:'recommend/refreshProjectAndRecommend.do',
				data:{UID:uid},
				method:'POST',
				dataType:'json',
				success:function(data){
				
				serviceproject_json = data[0];//推荐的项目列表
				project_json = data[1];//购买的项目列表
				for(var t=0; t<data[0].length; t++){
			
						//构造推荐项目表
						var tr = document.createElement("tr");
						tr.id = "recommend"+data[0][t].SERVICECOST_ID;
						
						var td1 = document.createElement("td");
						td1.className="center";
						td1.appendChild(document.createTextNode(data[0][t].STORE_NAME));
	
						var td2 = document.createElement("td");
						td2.className="center";
						td2.appendChild(document.createTextNode(data[0][t].STAFF_NAME));
						
						var td3 = document.createElement("td");
						td3.className="center";
						td3.appendChild(document.createTextNode(data[0][t].PNAME));
						
						var td4 = document.createElement("td");
						td4.className="center";
						td4.appendChild(document.createTextNode(data[0][t].PRICE));
					   
						tr.appendChild(td1);
						tr.appendChild(td2);
						tr.appendChild(td3);
						tr.appendChild(td4);
						$("#recommend_tbody").append(tr);
					}
					if(data[1].length>0){
					for(var t=0; t<data[1].length; t++){
					 $("#projectdiv2").css('display','none');
				      $("#projectdiv").css('display','block');
						//构造曾经购买过的项目表
						var tr = document.createElement("tr");
						tr.id = "project"+data[1][t].SERVICECOST_ID;
					
						var td1 = document.createElement("td");
						td1.className="center";
						td1.appendChild(document.createTextNode(data[1][t].SERVICECOST_ID));
						console.log(data[1][t].SERVICECOST_ID);
						
						var td2 = document.createElement("td");
						td2.className="center";
						td2.appendChild(document.createTextNode(data[1][t].PNAME));
						console.log(data[1][t].PNAME);
						var td3 = document.createElement("td");
						td3.className="center";
						td3.appendChild(document.createTextNode(data[1][t].STORE_NAME));
						console.log(data[1][t].STORE_NAME);
						
						var td4 = document.createElement("td");
						td4.className="center";
						td4.appendChild(document.createTextNode(data[1][t].STAFF_NAME));
						console.log(data[1][t].STAFF_NAME);
					
						tr.appendChild(td1);
						tr.appendChild(td2);
						tr.appendChild(td3);
						tr.appendChild(td4);
						$("#project_tbody").append(tr);
					}
					}else if(data[1].length==0){
					$("#projectdiv").css('display','none');
					$("#projectdiv2").css('display','block');
					//$("#projectdiv2").html("当前用户没有购买过项目！");
					}
					$(top.hangge());//关闭加载状态
					
					},
					error: function(data){
					alert("error");
					}
				
		});
		}
		
	</script>
</body>
</html>