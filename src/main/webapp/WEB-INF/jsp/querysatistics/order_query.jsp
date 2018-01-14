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
							<form action="queryOrder/list.do" method="post" name="orderForm"
								id="orderForm">
								<table
									style="margin-top:5px; table-layout:fixed ; width:1200px;  border-collapse:separate; border-spacing:0px 20px;">
									<tr>
										<td>
											<div class="nav-search">
												服务板块:<select onchange="modelChange();"
													class="chosen-select form-control" data-placeholder="服务板块"
													name="mId" id="mId">
													<option value=""></option>
													<c:forEach items="${serviceModules}" var="serviceModule">
														<option value="${serviceModule.SERVICEMODULE_ID}"
															<c:if test="${serviceModule.SERVICEMODULE_ID == pd.mId}">selected="selected"</c:if>>
															${serviceModule.M_NAME}</option>
													</c:forEach>
											</div>
										</td>

										<td>选择门店：<select onchange="storeChange();"
											class="chosen-select form-control" name="store" id="store"
											data-placeholder="门店">
												<%-- <option value=""/>
												<c:forEach items="${storeList}" var="store">
													<option value="${store.STORE_ID}" <c:if test="${store.STORE_ID == pd.store}">selected="selected"</c:if> >
														${store.STORE_NAME}</option>
												</c:forEach> --%>
										</select>
										</td>
										<td>服务项目： <input style="width: 100%;"
											placeholder="输入项目名称" name="projectName" id="projectName"
											value="${pd.projectName}" type="text"></td>
										<td>医生姓名:<select onchange=""
											class="chosen-select form-control" name="doctorName"
											id="doctorName" data-placeholder="医生姓名">

										</select>
										</td>
										<td>客服姓名:<select onchange=""
											class="chosen-select form-control" name="staffName"
											id="staffName" data-placeholder="客服姓名">

										</select>
										</td>


									</tr>
									<tr>
										<td>
											微信手机号：
											 <input style="width: 100%;"
											placeholder="微信手机号" name="userPhone" id="userPhone"
											value="${pd.userPhone}" type="text">

										</td>
										<td>预约类型:<select onchange=""
											class="chosen-select form-control" name="isFirst"
											id="isFirst" data-placeholder="预约类型">
												<option value=""></option>
												<option value="0"
													<c:if test="${'0' == pd.isFirst}">selected="selected"</c:if>>初诊</option>
												<option value="1"
													<c:if test="${'1' == pd.isFirst}">selected="selected"</c:if>>复诊</option>
												<option value="2"
													<c:if test="${'2' == pd.isFirst}">selected="selected"</c:if>>课程</option>
										</select>
										</td>
										<td>订单来源:<select onchange=""
											class="chosen-select form-control" name="url" id="url"
											data-placeholder="订单来源">
												<option value=""></option>
												<option value="0"
													<c:if test="${'0' == pd.url}">selected="selected"</c:if>>线上付款</option>
												<option value="1"
													<c:if test="${'1' == pd.url}">selected="selected"</c:if>>面对面付款</option>
												<option value="2"
													<c:if test="${'2' == pd.url}">selected="selected"</c:if>>退款手续费</option>
										</select>
										</td>
										<td>订单状态:<select onchange=""
											class="chosen-select form-control" name="orderStatus"
											id="orderStatus" data-placeholder="订单状态">
												<option value=""></option>
												<option value="0"
													<c:if test="${'0' == pd.orderStatus}">selected="selected"</c:if>>待支付</option>
												<option value="1"
													<c:if test="${'1' == pd.orderStatus}">selected="selected"</c:if>>已关闭</option>
												<option value="2"
													<c:if test="${'2' == pd.orderStatus}">selected="selected"</c:if>>已预约</option>
												<option value="3"
													<c:if test="${'3' == pd.orderStatus}">selected="selected"</c:if>>已过期</option>
												<option value="4"
													<c:if test="${'4' == pd.orderStatus}">selected="selected"</c:if>>待评价</option>
												<option value="5"
													<c:if test="${'5' == pd.orderStatus}">selected="selected"</c:if>>已完成</option>
												<option value="6"
													<c:if test="${'6' == pd.orderStatus}">selected="selected"</c:if>>已取消</option>
										</select>
										</td>
										<td>支付方式:<select onchange=""
											class="chosen-select form-control" name="payMethod"
											id="payMethod" data-placeholder="支付方式">
												<option value=""></option>
												<option value="0"
													<c:if test="${'0' == pd.payMethod}">selected="selected"</c:if>>微信支付</option>
												<option value="1"
													<c:if test="${'1' == pd.payMethod}">selected="selected"</c:if>>支付宝支付</option>
												<option value="2"
													<c:if test="${'2' == pd.payMethod}">selected="selected"</c:if>>储值卡余额支付
												</option>
												<option value="3"
													<c:if test="${'3' == pd.payMethod}">selected="selected"</c:if>>现金支付</option>
												<option value="4"
													<c:if test="${'4' == pd.payMethod}">selected="selected"</c:if>>银行卡</option>
												<option value="5"
													<c:if test="${'5' == pd.payMethod}">selected="selected"</c:if>>预存支付</option>
												<option value="6"
													<c:if test="${'6' == pd.payMethod}">selected="selected"</c:if>>储值卡返点支付</option>
										</select></td>
										</tr>
									<tr>
										<td colspan="2" align="center" valign="bottom"><br>
											<input class="span10 date-picker" name="yuyuefirstDate"
											id="yuyuefirstDate" type="text" data-date-format="yyyy-mm-dd"
											readonly="readonly" value="${pd.yuyuefirstDate}"
											placeholder="预约开始日期" title="预约开始日期" /><input
											class="span10 date-picker" style="margin-left:10px;" name="yuyuelastDate" id="yuyuelastDate"
											type="text" data-date-format="yyyy-mm-dd" readonly="readonly"
											value="${pd.yuyuelastDate}" placeholder="预约结束日期" title="预约结束日期"
											style="" />
										</td>
										<td colspan="2" align="center" valign="bottom"><br>
											<input class="span10 date-picker" name="firstDate"
											id="firstDate" type="text" data-date-format="yyyy-mm-dd"
											readonly="readonly" value="${pd.firstDate}"
											placeholder="创建开始日期" title="创建开始日期" /><input
											class="span10 date-picker" style="margin-left:10px;" name="lastDate" id="lastDate"
											type="text" data-date-format="yyyy-mm-dd" readonly="readonly"
											value="${pd.lastDate}" placeholder="创建结束日期" title="创建结束日期"
											style="" />
										</td>
										<td align="center" valign="bottom">
										<c:if test="${QX.cha == 1 }">
											<a
												class="btn btn-light btn-xs" onclick="searchs();" title="检索"><i
													id="nav-search-icon"
													class="ace-icon fa fa-search bigger-110 nav-search-icon blue"></i>
											</a>
										</c:if>
										<c:if test="${QX.toExcel == 1 }">
											<a class="btn btn-light btn-xs" onclick="toExcel();"
												title="导出到EXCEL" style="margin-left: 10px;"> <i
													id="nav-search-icon"
													class="ace-icon fa fa-download bigger-110 nav-search-icon blue"></i>
											</a>
										</c:if>
										</td>
									</tr>
								</table>
								<!-- 检索  -->

								<table id="simple-table"
									class="table table-striped table-bordered table-hover"
									style="margin-top:5px;">
									<thead>
										<tr>
											<th class="center" style="width:50px;">序号</th>
											<th class="center" style="width:100px;">微信姓名/昵称</th>
											<th class="center" style="width:80px;">微信手机号</th>
											<th class="center" style="width:50px;">患者姓名</th>
											<th class="center" style="width:130px;">门店</th>
											<th class="center" style="width:80px;">科室</th>
											<th class="center" style="width:80px;">服务项目</th>
											<th class="center" style="width:60px;">医生</th>
											<th class="center" style="width:100px;">创建时间</th>
											<th class="center" style="width:50px;">预约类型</th>
											<th class="center" style="width:60px;">订单状态</th>
											<th class="center" style="width:100px;">预约时间</th>
											<th class="center" style="width:80px;">应收金额</th>
											<th class="center" style="width:50px;">折扣</th>
											<th class="center" style="width:80px;">优惠券(元)</th>
											<th class="center" style="width:80px;"><font color="red">待收</font>/<font
												color="green">实收</font> 金额(元)</th>
											<th class="center" style="width:80px;">订单来源</th>
											<th class="center" style="width:80px;">取消订单</th>
											<th class="center" style="width:60px;">客服</th>
											<th class="center" style="width:50px;">备注</th>
										</tr>
									</thead>

									<tbody>
										<!-- 开始循环 -->
										<c:choose>
											<c:when test="${not empty orders}">
												<c:if test="${QX.cha == 1 }">
													<c:forEach items="${orders}" var="order" varStatus="vs">
														<tr>
															<td class='center' style="width: 30px;">${vs.index+1}</td>
															<td class='center'><a href="javascript:;"
																data-toggle="tooltip"
																title='手机号：${order.user.phone};'>${order.user.name}/${order.user.userName}


															</td>
															<td class='center'>
																	${order.user.phone}
															</td>
															<td class='center'><a href="javascript:;"
																data-toggle="tooltip"
																title='手机号：${order.weChatPhone};'>${order.weChatName}
															</td>
															<td class='center'>${order.store.STORE_NAME}</td>

															<c:choose>
																<c:when test="${order.serviceCostId == -3}">
																	<td class='center'>消耗品</td>
																	<td class='center'>消耗品</td>
																</c:when>
																<c:when test="${order.serviceCostId == -2}">
																	<td class='center'>药品</td>
																	<td class='center'>药品</td>
																</c:when>
																<c:when test="${order.serviceCostId == -1}">
																	<td class='center'>退费</td>
																	<td class='center'>退费</td>
																</c:when>
																<c:otherwise>
																	<td class='center'>${order.serviceCost.f2serviceCategory.CATEGORY_NAME}</td>
																	<td class='center'>${order.serviceCost.serviceProject.pName}</td>
																</c:otherwise>
															</c:choose>

															<td class='center'>${order.staff.STAFF_NAME}</td>
															<td class='center'>${order.createTime}</td>
															<td class='center'><c:if
																	test="${order.serviceCost.isFirst ==0}">初诊</c:if> <c:if
																	test="${order.serviceCost.isFirst ==1}">复诊</c:if> <c:if
																	test="${order.serviceCost.isFirst ==2}">课程</c:if></td>
															<td class='center'><c:if
																	test="${order.orderStatus==0}">
																	<font color="red">待支付</font>
																</c:if> <c:if test="${order.orderStatus==1}">
																	<font color="gray">已关闭</font>
																</c:if> <c:if test="${order.orderStatus==2}">
																	<font color="green">已预约</font>
																</c:if> <c:if test="${order.orderStatus==3}">
																	<font color="red">已过期</font>
																</c:if> <c:if test="${order.orderStatus==4}">
																	<font color="green">待评价</font>
																</c:if> <c:if test="${order.orderStatus==6}">
																	<font color="gray">已取消</font>
																</c:if> <c:if test="${order.orderStatus==5}">已完成</c:if>
															</td>
															<td class='center'>${order.recommendTime}</td>
															<td align='right'><fmt:formatNumber type="number"
																	value="${order.orderMoney}" pattern="0.00"
																	maxFractionDigits="2" />
															</td>
															<td align='right'><fmt:formatNumber type="number"
																	value="${order.proportion}" pattern="0.00"
																	maxFractionDigits="2" />
															</td>
															<td align="right"><c:choose>
																	<c:when test="${empty order.disCountId}">无</c:when>
																	<c:otherwise>
																		<fmt:formatNumber type="number"
																			value="${order.disCountId}" pattern="0"
																			maxFractionDigits="2" />
																	</c:otherwise>
																</c:choose>
															</td>
															<td align='right'><c:choose>
																	<c:when
																		test="${ 1 == order.orderStatus ||order.orderStatus == 6}">
																		<fmt:formatNumber type="number"
																			value="${order.payMoney}" pattern="0.00"
																			maxFractionDigits="2" />
																	</c:when>
																	<c:when test="${0 == order.orderStatus}">
																		<font color="red"><fmt:formatNumber
																				type="number" value="${order.payMoney}"
																				pattern="0.00" maxFractionDigits="2" /> </font>
																	</c:when>
																	<c:otherwise>
																		<a href="javascript:;" style="color: green"
																			data-toggle="tooltip"
																			title='<c:forEach items="${order.payDetails}" var="payDetail"><c:if test="${payDetail.payMethod ==0}">微信支付</c:if><c:if test="${payDetail.payMethod ==1}">支付宝支付</c:if><c:if test="${payDetail.payMethod ==2}">储值卡余额支付</c:if><c:if test="${payDetail.payMethod ==3}">现金支付</c:if><c:if test="${payDetail.payMethod ==4}">银行卡支付</c:if><c:if test="${payDetail.payMethod ==5}">钱包支付</c:if><c:if test="${payDetail.payMethod ==6}">储值卡返点支付</c:if>:${payDetail.payMoney};</c:forEach>'
																			id="payMoneyMethod">${order.payMoney}<%-- <fmt:formatNumber
																				type="number" value="${order.payMoney}"
																				pattern="0.00" maxFractionDigits="2" /> --%> </a>
																	</c:otherwise>
																</c:choose></td>
															<td align='right'><c:choose>
																	<c:when test="${order.url == 0}">线上付款</c:when>
																	<c:when test="${order.url == 1}">面对面付款</c:when>
																	<c:when test="${order.url == 2}">退款手续费</c:when>
																	<c:otherwise>未知</c:otherwise>
																</c:choose></td>
															<td align='right'><c:choose>
																	<c:when
																		test="${not empty order.refund && order.refund != 0}">
																		<fmt:formatNumber type="number"
																			value="${order.refund}" pattern="0.00"
																			maxFractionDigits="2" />
																	</c:when>
																	<c:otherwise>无退款</c:otherwise>
																</c:choose>
															</td>
															<td class='center'>${order.serviceStaff.STAFF_NAME}</td>
															<td class='center'>${order.remark}</td>
														</tr>

													</c:forEach>
												</c:if>
												<c:if test="${QX.cha == 0 }">
													<tr>
														<td colspan="100" class="center">您无权查看</td>
													</tr>
												</c:if>
											</c:when>
											<c:otherwise>
												<tr class="main_info">
													<td colspan="100" class="center">没有相关数据</td>
												</tr>
											</c:otherwise>
										</c:choose>
									</tbody>
								</table>

								<div class="page-header position-relative">
									<table style="width:100%;">
										<tr>
											<td style="vertical-align:top;"><div class="pagination"
													style="float: right;padding-top: 0px;margin-top: 0px;">${page.pageStr}</div>
											</td>
										</tr>
									</table>
								</div>
							</form>
							<div class="panel-group" id="accordion">
								<div class="panel panel-info">
									<div class="panel-heading">
										<h4 class="panel-title">
											<a data-toggle="collapse" data-parent="#accordion"
												href="#collapseexample"> 点击统计营业额 </a>
										</h4>
									</div>
									<div id="collapseexample" class="panel-collapse collapse">
										<div class="panel-body">

											<div class="table-responsive">
												<table class="table table-hover">
													<tbody>
														<tr class="success">
															<td>应收总额(元)</td>
															<td ><div id="00">0.00</div></td>
															<td>优惠券总额(元)</td>
															<td ><div id="01">0.00</div></td>
															<td>实收总额(元)</td>
															<td><div id="02">0.00</div></td>
															<td>取消订单总额(元)</td>
															<td><div id="03">0.00</div></td>
														</tr>
														<tr>
															<td>微信支付总额(元)</td>
															<td><div id="0">0.00</div></td>
															<td>支付宝支付总额(元)</td>
															<td><div id="1">0.00</div></td>
															<td>储值卡余额支付总额(元)</td>
															<td><div id="2">0.00</div></td>
															<td>现金支付总额(元)</td>
															<td><div id="3">0.00</div></td>
														</tr>
														<tr>
															<td>银行卡支付总额(元)</td>
															<td><div id="4">0.00</div></td>
															<td>钱包支付总额(元)</td>
															<td><div id="5">0.00</div></td>
															<td>储值卡返点支付总额(元)</td>
															<td><div id="6">0.00</div></td>
														</tr>
													</tbody>
												</table>
											</div>
										</div>
									</div>
								</div>
							</div>
							<div></div>
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
	$("#orderForm").submit();


}

//模块选择触发门店
function modelChange(){
		var SERVICEMODULE_ID = $("#mId option:selected").val();
		if(null !=SERVICEMODULE_ID){
			$.ajax({
				type:"POST",
				url:"<%=basePath%>queryStaff/findStoreByModelId.do",
				data:{"SERVICEMODULE_ID":SERVICEMODULE_ID},
				dataType:"json",
				async: false,
				success:function(data){
					$('#store').empty();
					$('#store').append("<option value=''></option>");
					for ( var i = 0; i < data.length; i++) {
						var id = '${pd.store}';
						if(id != data[i].STORE_ID){
							$('#store').append('<option value="' +data[i].STORE_ID+ '">' + data[i].STORE_NAME +'</option>');
						}else{
							$('#store').append('<option value="' +data[i].STORE_ID+ '"selected="selected">' + data[i].STORE_NAME +'</option>');
						}
					}
					$('#store').chosen("destroy").chosen();
				}
			});

			storeChange();

		}else{
			return;
		}
}
//门店选择触发员工姓名,服务项目，客服，医生
function storeChange(){

			var storeId = $("#store option:selected").val();
			if(null !=storeId){
					$.ajax({
						type:"POST",
						url:"<%=basePath%>queryOrder/findstaffProjectServiceStaffByStoreId.do",
						data : {
						"storeId" : storeId
					},
					dataType : "json",
					success : function(data) {
						$('#doctorName').empty();

						$('#doctorName').append("<option value=''></option>");
						for ( var i = 0; i < data[0].length; i++) {
							var id = '${pd.doctorName}';
							if(id != data[0][i].STAFF_ID){
								$('#doctorName').append('<option value="' +data[0][i].STAFF_ID + '">'+ data[0][i].STAFF_NAME + '</option>"');
							}else{
								$('#doctorName').append('<option value="' +data[0][i].STAFF_ID + '" selected="selected">'+ data[0][i].STAFF_NAME + '</option>"');
							}
						}
						$('#doctorName').chosen("destroy").chosen();

						$('#staffName').empty();
						$('#staffName').append("<option value=''></option>");
						for ( var i = 0; i < data[1].length; i++) {
							var id = '${pd.staffName}';
							if(id != data[1][i].STAFF_ID){
								$('#staffName').append('<option value="' +data[1][i].STAFF_ID + '">'+ data[1][i].STAFF_NAME + '</option>"');
							}else{
								$('#staffName').append('<option value="' +data[1][i].STAFF_ID + '" selected="selected">'+ data[1][i].STAFF_NAME + '</option>"');
							}
						}
						$('#staffName').chosen("destroy").chosen();
					}
				});
			}else{
			return;
			}

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
				//为了解决第二次查询，门店，的选择框丢失
		modelChange();
		$('#collapseexample').on('show.bs.collapse', function () {
				$.ajax({
					cache: true,
					type: "POST",
					url:'<%=basePath%>queryOrder/sum.do',
					data:$('#orderForm').serialize(),// 你的formid
					async: false,
					dataType:"JSON",
				    error: function(request) {
				        alert("Connection error");
				    },
				    success: function(data) {
					    $("#00").html(data.orderSum[0].orderMoneySum);
					    $("#01").html(data.orderSum[0].discountSum);
					    $("#02").html(data.orderSum[0].payMoneySum);
					    $("#03").html(data.orderSum[0].refundSum);
					    for(var p in data.orderMxSum){
					    	if(null !=data.orderMxSum[p].payMxMoneySum){
   								$("#"+data.orderMxSum[p].payMethod).html(data.orderMxSum[p].payMxMoneySum);
					    	}
						}
				    }
				});

			})
	});

	//导出excel
		function toExcel(){
		var url ='<%=basePath%>queryOrder/excel.do';
		var beforeUrl = $("#orderForm").attr("action");
		$("#orderForm").attr("action", url);
		$("#orderForm").submit();
		$("#orderForm").attr("action", beforeUrl);
	}
</script>
</html>

