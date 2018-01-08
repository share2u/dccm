<%@page import="java.util.*"%>
<%@page import="cn.ncut.util.*"%>
<%@page import="java.text.SimpleDateFormat"%>
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
<!-- 下拉框 -->
<link rel="stylesheet" href="static/ace/css/chosen.css" />
<!-- jsp文件头和头部 -->
<%@ include file="../../system/index/top.jsp"%>
<!-- 日期框 -->
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

							<form action="servicetime/${msg }.do" name="Form" id="Form"
								method="post">
								<input id="Mdzz" type="hidden" name="Mdzz"> <input
									type="hidden" name="SERVICETIME_ID" id="SERVICETIME_ID"
									value="${pd.SERVICETIME_ID}" />
								<div id="zhongxin" style="padding-top: 13px;">
									<table id="table_report"
										class="table table-striped table-bordered table-hover">

										<tr>
											<td style="width:79px;text-align: right;padding-top: 13px;">门店:</td>
											<td id="mendian"><select
												class="chosen-select form-control" name="STORE_ID"
												id="store_id" data-placeholder="请选择门店"
												style="vertical-align:top;" style="width:98%;">
													<!-- <option value=""></option> -->
													<c:forEach items="${storeList}" var="store">
														<option value="${store.STORE_ID}"
															<c:if test="${pd.STORE_ID==store.STORE_ID}">selected</c:if>>${store.STORE_NAME}</option>
													</c:forEach>
											</select></td>
										</tr>

										<tr>
											<td style="width:79px;text-align: right;padding-top: 13px;">员工:</td>
											<td id="yuangong"><select
												class="chosen-select form-control" name="STAFF_ID"
												id="staff_id" data-placeholder="请选择员工"
												style="vertical-align:top;" style="width:98%;">
													<c:forEach items="${staffList}" var="staff">
														<option value="${staff.STAFF_ID}"
															<c:if test="${pd.STAFF_ID==staff.STAFF_ID}">selected</c:if>>${staff.STAFF_NAME}</option>
													</c:forEach>
													<%-- <option value="${pd.STAFF_ID}">${pd.STAFF_NAME}</option> --%>
													<%-- <c:forEach items="${staffList}" var="staff">
														<option value="${staff.STAFF_ID}"
															<c:if test="${pd.STAFF_ID==staff.STAFF_ID}">selected</c:if>>${staff.STAFF_NAME}</option>
													</c:forEach> --%>
											</select></td>
										</tr>
										<tr>
											<td style="width:75px;text-align: right;padding-top: 13px;">日期:</td>
											<td><input class="span10 date-picker" name="THE_DATE"
												id="THE_DATE" value="${pd.THE_DATE}" type="text"
												data-date-format="yyyy-mm-dd" readonly="readonly"
												placeholder="日期" title="日期" style="width:98%;"
												onchange="findWeekServiceByDate(this)" />
											</td>
										</tr>

										<tr>
											<td style="width:75px;text-align: right;padding-top: 13px;">一键添加:</td>
											<td style="text-align: left;" colspan="10"><a
												class="btn btn-mini btn-success" onclick="convertDate()">保存添加</a>
												<a class="btn btn-mini btn-yellow" onclick="clearAdd()">清空添加</a>
											</td>
										</tr>

										<tr>
											<td style="width:220px;text-align: right;padding-top: 13px;">就诊时间:</td>
											<td align="center">
												<div class="main-booking">
													<div class="main-booking-detail">
														<table class="main-table" align="center" width="700">
															<thead align="center">
																<tr>
																	<th class="heads"><span>周日</span></th>
																	<th class="heads"><span>周一</span></th>
																	<th class="heads"><span>周二</span></th>
																	<th class="heads"><span>周三</span></th>
																	<th class="heads"><span>周四</span></th>
																	<th class="heads"><span>周五</span></th>
																	<th class="heads"><span>周六</span></th>
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
																<li><span class="can-order"></span></li>
																<li>可预定</li>
																<li><span class="can-not-order"></span></li>
																<li>不可预定</li>
																<!-- 	<li><span class="chosen"></span>
																</li>
																<li>当前选定</li> -->
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
												</div>
											</td>

										</tr>
										<tr>
											<td style="text-align: center;" colspan="10"><a
												class="btn btn-mini btn-primary" onclick="save();">保存</a> <a
												class="btn btn-mini btn-success" onclick="convertDate()">保存添加</a>
												<a class="btn btn-mini btn-yellow" onclick="clearAdd()">清空添加</a><a
												class="btn btn-mini btn-danger"
												onclick="top.Dialog.close();">取消</a></td>
										</tr>
									</table>
								</div>
								<div id="zhongxin2" class="center" style="display:none">
									<br /> <br /> <br /> <br /> <br /> <img
										src="static/images/jiazai.gif" /><br />
									<h4 class="lighter block green">提交中...</h4>
								</div>
							</form>
						</div>
						<!-- /.col -->
					</div>
					<!-- /.row -->
				</div>
				<!-- /.page-content -->
			</div>
			<div style="display:none;" id="10000,20000"></div>
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
	<!--提示框-->
	<script type="text/javascript" src="static/js/jquery.tips.js"></script>
	<script type="text/javascript">
		$(top.hangge());
		//保存
		function save() {
			if ($("#STAFF_ID").val() == "") {
				$("#STAFF_ID").tips({
					side : 3,
					msg : '请选择员工',
					bg : '#AE81FF',
					time : 2
				});
				$("#STAFF_ID").focus();
				return false;
			}
			if ($("#STORE_ID").val() == "") {
				$("#STORE_ID").tips({
					side : 3,
					msg : '请选择门店',
					bg : '#AE81FF',
					time : 2
				});
				$("#STORE_ID").focus();
				return false;
			}
			if ($("#THE_DATE").val() == "") {
				$("#THE_DATE").tips({
					side : 3,
					msg : '请选择日期',
					bg : '#AE81FF',
					time : 2
				});
				$("#THE_DATE").focus();
				return false;
			}

			var array = new Array();

			for ( var i = 0; i < document.getElementsByName("dearSpan").length; i++) {

				if (document.getElementsByName("dearSpan")[i]
						.getAttribute("class") == "can-order") {
					array.push(0);
				} else if (document.getElementsByName("dearSpan")[i]
						.getAttribute("class") == "can-not-order") {
					array.push(1);
				}
			}
			var s = array.join(",");
			$("#Mdzz").val(s);
			$("#Form").submit();
			$("#zhongxin").hide();
			$("#zhongxin2").show();
		}

		$(function() {
			//日期框
			$('.date-picker').datepicker({
				autoclose : true,
				todayHighlight : true
			});
		});

		function bookControl(e) {
			var id = e.getAttribute("id");
			var clazz = e.getAttribute("class");
			if (clazz == "can-order") {
				var span = document.getElementById(id);
				span.className = 'can-not-order';
			} else if (clazz == "can-not-order") {
				var span = document.getElementById(id);
				span.className = 'can-order';
			}
		}

		function getStaffByStore() {
			var store_id = $("#store_id").val();
			$.ajax({
				type : "POST",
				url : 'servicetime/findStaffByStore',
				data : {
					STORE_ID : store_id
				},
				success : function(data) {
					$("#staff_id").empty();
					for ( var i = 0; i < data.length; i++) {
						$("#staff_id").append(
								"<option onclick=\"giveStaffToDate()\" value="
										+ data[i].STAFF_ID + ">"
										+ data[i].STAFF_NAME + "</option>");
					}

				}
			});
		}

		function giveStaffToDate() {
			var staff_id = $("#staff_id").val();
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
							//		span.appendChild(document.createTextNode(data[7*i +j]));
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
							element.setAttribute("name", "dearSpan");
							element.onclick = function() {
								if (this.className == "can-order") {
									this.className = "can-not-order";
								} else if (this.className == "can-not-order") {
									this.className = "can-order";
								}
							}
						}
					}
				}

			});
		}

		function convertDate() {

			var the_date = new Date(new Date($("#THE_DATE").val()) - 7 * 24
					* 3600 * 1000 + 8 * 3600 * 1000).toJSON().slice(0, 10);
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
							//		span.appendChild(document.createTextNode(data[7*i +j]));
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
							element.setAttribute("name", "dearSpan");
							element.onclick = function() {
								if (this.className == "can-order") {
									this.className = "can-not-order";
								} else if (this.className == "can-not-order") {
									this.className = "can-order";
								}
							}
						}
					}
				}

			});
		}

		function clearAdd() {
			$("#dabiao").html("");
			for ( var i = 0; i < 24; i++) {
				for ( var j = 0; j < 7; j++) {
					var tr = document.createElement("tr");
					tr.className = "rows";
					for ( var j = 0; j < 7; j++) {
						var td = document.createElement("td");
						td.className = "single";

						var span = document.createElement("span");
						//		span.appendChild(document.createTextNode(data[7*i +j]));
						span.id = i + ',' + j;
						span.className = "can-order";
						td.appendChild(span);
						tr.appendChild(td);
					}
					$("#dabiao").append(tr);
				}
			}

			for ( var i = 0; i < 24; i++) {
				for ( var j = 0; j < 7; j++) {
					span.id = i + ',' + j;
					var element = document.getElementById(span.id);
					var clazz = element.getAttribute("class");
					element.setAttribute("name", "dearSpan");
					element.onclick = function() {
						if (this.className == "can-order") {
							this.className = "can-not-order";
						} else if (this.className == "can-not-order") {
							this.className = "can-order";
						}
					}
				}
			}
		}
	</script>

</body>
</html>