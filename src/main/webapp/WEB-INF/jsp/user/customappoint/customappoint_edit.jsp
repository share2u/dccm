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
	<%@ include file="../../system/index/top.jsp"%>
	<!-- 日期框 -->
	<link rel="stylesheet" href="static/ace/css/datepicker.css" />
	<link rel="stylesheet" href="static/css/default/venues.css" />
	
	
    <!-- <link href="http://netdna.bootstrapcdn.com/twitter-bootstrap/2.2.2/css/bootstrap-combined.min.css" rel="stylesheet">
    <link rel="stylesheet" type="text/css" media="screen"
     href="http://tarruda.github.com/bootstrap-datetimepicker/assets/css/bootstrap-datetimepicker.min.css"> -->
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
					
					<form action="customappoint/${msg }.do" name="Form" id="Form" method="post">
						<input type="hidden" name="CUSTOMAPPOINT_ID" id="CUSTOMAPPOINT_ID" value="${pd.CUSTOMAPPOINT_ID}"/>
						<input type="hidden" name="U_ID" id="U_ID" value="${pd.U_ID}"/>
						<div id="zhongxin" style="padding-top: 13px;">
						<table id="table_report" class="table table-striped table-bordered table-hover">
								
								<input type="hidden" name="STAFF_ID" id="STAFF_ID" value="${pd.STAFF_ID}" maxlength="255"  title="医生编号" style="width:98%;"/>
							
							<tr>
							
								<td style="width:75px;text-align: right;padding-top: 13px;">请选择预约日期:</td>
											<td><input class="span10 date-picker" name="THE_DATE"
												id="THE_DATE" value="${pd.THE_DATE}" type="text"
												data-date-format="yyyy-mm-dd" readonly="readonly"
												placeholder="日期" title="日期" style="width:98%;"
												onchange="findWeekServiceByDate(this)" /></td>
							</tr>
								<tr>
											<td style="width:220px;text-align: right;padding-top: 13px;">就诊时间:</td>
											<td align="center">
												<div class="main-booking">
													<div class="main-booking-detail">
														<table class="main-table" align="center" width="700">
															<thead align="center">
																<tr>
																	<th class="heads"><span>周日</span>
																	</th>
																	<th class="heads"><span>周一</span>
																	</th>
																	<th class="heads"><span>周二</span>
																	</th>
																	<th class="heads"><span>周三</span>
																	</th>
																	<th class="heads"><span>周四</span>
																	</th>
																	<th class="heads"><span>周五</span>
																	</th>
																	<th class="heads"><span>周六</span>
																	</th>
																</tr>
															</thead>


															<div class="left-time-list">
																<ul style="padding-top: 23px;">
																	<%
																		for (int i = 0; i < 25; i++) {//循环24次 每次输出一行
																	%>
																	<li class="hour"><%=i%>:00</li>
																	<%
																		}
																	%>
																</ul>
															</div>
															<tbody id="dabiao">
																<tr>
																	<c:forEach var="ifCanOrder" items="${ifCanOrder}"
																		varStatus="status">

																		<c:if
																			test="${status.count % 7 == 1 ||status.count==null}">
																</tr>
																<tr class="rows">
																	</c:if>
																	<fmt:formatNumber var="c"
																		value="${status.count%7==0?((status.count-status.count%7)/7-1):(status.count-status.count%7)/7}"
																		maxFractionDigits="0" pattern="#" />
																	<c:choose>
																		<c:when test="${ifCanOrder==1}">
																			<td class="single" status="0"><span
																				name="dearSpan" class="can-not-order"
																				id="${c},${(status.count % 7-1)>-1?(status.count % 7-1):6}"
																				idNum="${status.count}" items="${ifCanOrder}"
																				<%-- idx="${c}"
																			idy="${(status.count % 7-1)>-1?(status.count % 7-1):6}" --%>
																			onclick="bookControl(this);"></span>
																			</td>
																		</c:when>
																		<c:otherwise>
																			<td class="single" status="0"><span
																				name="dearSpan" class="can-order"
																				id="${c},${(status.count % 7-1)>-1?(status.count % 7-1):6}"
																				idNum="${status.count}" items="${ifCanOrder}"
																				<%-- idx="${c}"
																			idy="${(status.count % 7-1)>-1?(status.count % 7-1):6}" --%>
																			onclick="bookControl(this);"></span>
																			</td>
																		</c:otherwise>

																	</c:choose>

																	</c:forEach>
																</tr>

															</tbody>
														</table>
													</div>
													<div class="right-order-plane">
														<div class="title">
															<ul>
																<li><span class="can-order"></span>
																</li>
																<li>可预定</li>
																<li><span class="can-not-order"></span>
																</li>
																<li>不可预定</li>
																 	<li><span class="already-order"></span>
																</li>
																<li>当前选定</li>
															</ul>
														</div>
														<!-- <div class="info">
															<dl>
																<dt>项目：</dt>
																<dd>
																	<strong class="category-name">针灸</strong>
																</dd>
																<dt>日期：</dt>
																<dd data="1482336000" class="current-book-date">2016年12月22日(周四)</dd>
																<dt>时间：</dt>
															</dl>
														</div> -->
													</div>
												</div></td>

										</tr>
										<tr>
							
								<td style="width:75px;text-align: right;padding-top: 13px;">请确认预约时间:</td>
											<td><input  type="text" name="APPOINT_TIME"
												id="APPOINT_TIME" value="${pd.APPOINT_TIME}" /></td>
							</tr>
	
							<tr>
									<td style="width:150px;text-align: right;padding-top: 13px;">请添加订单备注：</td>
									<td><input type="text" id="REMARK" name="REMARK" value="${orderpd.REMARK}"maxlength="255"
										placeholder="这里输入备注" title="备注" style="width:98%;">
									</td>
								</tr>
							<%-- <tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">验证码:</td>
								<td><input type="text" name="APPOINT_CODE" id="APPOINT_CODE" value="${pd.APPOINT_CODE}" maxlength="32" placeholder="这里输入验证码" title="验证码" style="width:98%;"/></td>
							</tr> --%>
							<tr>
								<td style="text-align: center;" colspan="10">
									<a class="btn btn-mini btn-primary" onclick="save();">保存</a>
									<a class="btn btn-mini btn-danger" onclick="top.Dialog.close();">取消</a>
								</td>
							</tr>
						</table>
						</div>
						<div id="zhongxin2" class="center" style="display:none"><br/><br/><br/><br/><br/><img src="static/images/jiazai.gif" /><br/><h4 class="lighter block green">提交中...</h4></div>
					</form>
					</div>
					<!-- /.col -->
				</div>
				<!-- /.row -->
			</div>
			<!-- /.page-content -->
			
			<div id="hint" style="width:200px;height:100px;position:absolute;display:none;border:1px solid silver;background:#EDEDED;">
			<span style="font-size:18px;">您选择的医生该时间已经预约人数：<b id="hintText" style="color:red;"></b></span>
		</div>
		</div>
	</div>
	<!-- /.main-content -->
</div>
<!-- /.main-container -->


	<!-- 页面底部js¨ -->
	<%@ include file="../../system/index/foot.jsp"%>
	<!-- 下拉框 -->
	<script src="static/ace/js/chosen.jquery.js"></script>
	<!-- 日期框 -->
	<script src="static/ace/js/date-time/bootstrap-datepicker.js"></script>
	
	<!-- <script type="text/javascript" src="http://tarruda.github.com/bootstrap-datetimepicker/assets/js/bootstrap-datetimepicker.min.js"></script>
    <script type="text/javascript" src="http://tarruda.github.com/bootstrap-datetimepicker/assets/js/bootstrap-datetimepicker.pt-BR.js"></script> -->
	<!--提示框-->
	<script type="text/javascript" src="static/js/jquery.tips.js"></script>
		<script type="text/javascript">
		$(top.hangge());
		//保存
		function save(){
			if($("#ORDER_ID").val()==""){
				$("#ORDER_ID").tips({
					side:3,
		            msg:'请输入订单号',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#ORDER_ID").focus();
			return false;
			}
			if($("#U_ID").val()==""){
				$("#U_ID").tips({
					side:3,
		            msg:'请输入用户编号',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#U_ID").focus();
			return false;
			}
			if($("#APPOINT_TIME").val()==""){
				$("#APPOINT_TIME").tips({
					side:3,
		            msg:'请输入预约时间',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#APPOINT_TIME").focus();
			return false;
			}
			if($("#SERVICE_STAFF_ID").val()==""){
				$("#SERVICE_STAFF_ID").tips({
					side:3,
		            msg:'请输入客服',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#SERVICE_STAFF_ID").focus();
			return false;
			}
			if($("#APPOINT_CODE").val()==""){
				$("#APPOINT_CODE").tips({
					side:3,
		            msg:'请输入验证码',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#APPOINT_CODE").focus();
			return false;
			}
			$("#Form").submit();
			$("#zhongxin").hide();
			$("#zhongxin2").show();
		}

		
			function savetime(id){//获得当前时间的值
		var stime = id;
		var the_date = giveDate();
		
		if(stime==undefined || stime==""){
				alert("请选择预约时间！");
				return;
			}
		
			$.ajax({
			
				type:'POST',
				url:'customappoint/changeTime.do',
				data:{
					ID:stime,
					THE_DATE:the_date
				},
					dataType:'json',
					success:function(data){
					console.log(data);
					if(data.msg=="ok"){
						$("#APPOINT_TIME").val(data.selectTime);
					}else{
						alert("您不能选择当前时间之前的时间！ ");
					}
			}
					
			});


		}
		function giveDate() {
			var THE_DATE = $("#THE_DATE").val();
			return THE_DATE;
		}
       function giveStaffToDate() {
			var staff_id = $("#STAFF_ID").val();
			return staff_id;
		}

		function findWeekServiceByDate(e) {
			var the_date = $("#THE_DATE").val();
			var staff_id = giveStaffToDate();
			$.ajax({
				type : "POST",
				url : 'servicetime/findWeekServiceByDate',

				data : {
					THE_DATE : the_date,
					STAFF_ID : staff_id
				},

				success : function(data) {
					$("#dabiao").empty();
					for ( var i = 0; i < 24; i++) {
						var tr = document.createElement("tr");
						tr.className = "rows";
						for ( var j = 0; j < 7; j++) {
							var td = document.createElement("td");
							td.className = "single";

							var span = document.createElement("span");
							//	span.appendChild(document.createTextNode(data[7*i +j]));
							span.id = i + ',' + j;
							if (data[7 * i + j] == 1) {
								span.className = "can-not-order";
							} else {
								span.className = "can-order";
							}
							td.appendChild(span);
							tr.appendChild(td);
						}
						$("#dabiao").append(tr);
					}

					for ( var i = 0; i < 24; i++) {
						for ( var j = 0; j < 7; j++) {
							span.id = i + ',' + j;
							var element = document.getElementById(span.id);
							
							var clazz = element.getAttribute("class");
							element.setAttribute("name","dearSpan");
							element.onmouseover = function(event){
								$.ajax({
									url:'userpay/queryAlreadyAppointAmount',
									data: {
										STAFF_ID:$("#STAFF_ID").val(),
										selecttime:$("#THE_DATE").val(),
										id: this.id
									},
									dataType:'json',
									success:function(data){
										$("#hint").show(); 
										$("#hintText").text(data.count);
										$("#hint").css("left", event.offsetX+900); 
										$("#hint").css("top", event.offsetY+600); 
									},
									
									
								});
							};
							element.onclick = function() {
							savetime(this.id);
								if (this.className == "can-order") {
								$("#dabiao tr td span").each(function(){
									if($(this).attr("class") == "already-order"){
										$(this).attr("class","can-order");
									}
								});
								this.className = "already-order";
								}
								}
						}
					}
				}

			});
		}
		function bookControl(e) {
			var id = e.getAttribute("id");
			console.log(id);
			//alert(id);
			var clazz = e.getAttribute("class");
			if (clazz == "can-order") {
				var span = document.getElementById(id);
				span.className = 'can-not-order';
			} else if (clazz == "can-not-order") {
				var span = document.getElementById(id);
				span.className = 'can-order';
			}
		}
		$(function() {
			//日期框
			$('.date-picker').datepicker({
				autoclose : true,
				todayHighlight : true
			});
		});
		
		</script>
</body>
</html>