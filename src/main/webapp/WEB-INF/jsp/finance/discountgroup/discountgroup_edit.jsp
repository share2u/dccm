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
					
					<form action="discountgroup/${msg }.do" name="Form" id="Form" method="post">
						<input type="hidden" name="GROUP_ID" id="DISCOUNTGROUP_ID" value="${pd.GROUP_ID}"/>
						<input type="hidden" name="jsonData" id="jsonData" value=""/>
						<div id="zhongxin" style="padding-top: 13px;">
						<table id="table_report" class="table table-striped table-bordered table-hover">
							
							<tr>
								<td style="width:105px;text-align: right;padding-top: 13px;">优惠券组合名称:</td>
								<td><input type="text" name="GROUP_NAME" id="GROUP_NAME" value="${pd.GROUP_NAME}" maxlength="255" placeholder="这里输入优惠券组合名称" title="优惠券组合名称" style="width:98%;"/></td>
							</tr>
							<%-- <tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">优惠券id:</td>
								<td><input type="text" name="DISCOUNT_ID" id="DISCOUNT_ID" value="${pd.DISCOUNT_ID}" maxlength="50" placeholder="这里输入优惠券id" title="优惠券id" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">组合中该种优惠券的数量:</td>
								<td><input type="text" name="DISCOUNT_NUMBER" id="DISCOUNT_NUMBER" value="${pd.DISCOUNT_NUMBER}" maxlength="255" placeholder="这里输入组合中该种优惠券的数量" title="组合中该种优惠券的数量" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">创建时间:</td>
								<td><input type="text" name="CREATE_TIME" id="CREATE_TIME" value="${pd.CREATE_TIME}" maxlength="50" placeholder="这里输入创建时间" title="创建时间" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">客服id:</td>
								<td><input type="text" name="STAFF_ID" id="STAFF_ID" value="${pd.STAFF_ID}" maxlength="255" placeholder="这里输入客服id" title="客服id" style="width:98%;"/></td>
							</tr> --%>
							
							<tr>
								<td style="width:105px;text-align: right;padding-top: 13px;">请选择优惠券<span style="font-size:12px;color:red;">(修改时要重新选择)</span>:</td>
								<td>
									<table id="simple-table" class="table table-striped table-bordered table-hover" >
										<thead>
										<tr>
											<th class="center" style="width:35px;">
											<label class="pos-rel"><input type="checkbox" class="ace" id="zcheckbox" /><span class="lbl"></span></label>
											</th>
											<th class="center" style="width:50px;">序号</th>
											<th class="center">优惠券名称</th>
											<th class="center">金额</th>
											<th class="center">组合中该优惠券的个数</th>
										</tr>
										</thead>
										<tbody>
										<c:forEach items="${alldiscountlist}" var="discount" varStatus="vs">
										<tr>
											<td class='center'>
												<label class="pos-rel"><input type='checkbox' name="singlediscount" value="${discount.DISCOUNT_ID}" class="ace" /><span class="lbl"></span></label>
											</td>
											<td class='center' style="width: 30px;">${vs.index+1}</td>
											<td class="center">${discount.DISCOUNT_NAME}</td>
											<td class="center">${discount.DISCOUNT_AMOUNT}</td>
											<td class="center"><input type="text" id="discount${discount.DISCOUNT_ID}"></td>
										</tr>
										</c:forEach>
										</tbody>
									</table>
								</td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">备注:</td>
								<td><input type="text" name="REMARK" id="REMARK" value="${pd.REMARK}" maxlength="255" placeholder="这里输入备注" title="备注" style="width:98%;"/></td>
							</tr>
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
	<!--提示框-->
	<script type="text/javascript" src="static/js/jquery.tips.js"></script>
		<script type="text/javascript">
		$(top.hangge());
		//保存
		function save(){
			var json = "";
			var flag=0;
			if($("#GROUP_NAME").val()==""){
				flag=1;
				$("#GROUP_NAME").tips({
					side:3,
		            msg:'请输入优惠券组合名称',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#GROUP_NAME").focus();
			return false;
			}
			if(flag==0){
				//检查选中的优惠券是否数量为空
				var selected=0;
	 			$("input[name='singlediscount']").each(function(){
	 				if($(this).is(":checked")){
	 					selected=1;
	 					var str = $(this).val();
	 					if($("#discount"+str).val()==0){
	 						flag=1;
	 						$("#discount"+str).tips({
							side:3,
				            msg:'请输入数量',
				            bg:'#AE81FF',
				            time:2
				        });
						$("#discount"+str).focus();
						return false;
	 					}
	 				}
	  			});
	  			if(flag==0){
	  				if(selected==0){
	  					$("#GROUP_NAME").tips({
							side:3,
				            msg:'请至少选择一种优惠券进行组合！',
				            bg:'#AE81FF',
				            time:2
				        });
				        return false;
	  				}else{
					$("input[name='singlediscount']").each(function(){
		 				if($(this).is(":checked")){
		 					//数据填写正确，则拼成json串传递给后台
							var s = $(this).val();
							var number = $("#discount"+s).val();
						
							json += "{";
							json += "\"discountid\":" + "\"" + s + "\"" + ",\"number\":" + "\"" +number + "\"" +",";
							json = json.substring(0, json.length - 1) + "},";
		 				}
		 			});
		 			json = "[" + json.substring(0, json.length - 1) + "]";
		 			$("#jsonData").val(json);
	  			
			
			
					$("#Form").submit();
					$("#zhongxin").hide();
					$("#zhongxin2").show();
					}
				}
			}
 		}
			
		
		$(function() {
			//日期框
			$('.date-picker').datepicker({autoclose: true,todayHighlight: true});
			
			//复选框全选控制
			var active_class = 'active';
			$('#simple-table > thead > tr > th input[type=checkbox]').eq(0).on('click', function(){
				var th_checked = this.checked;//checkbox inside "TH" table header
				$(this).closest('table').find('tbody > tr').each(function(){
					var row = this;
					if(th_checked) $(row).addClass(active_class).find('input[type=checkbox]').eq(0).prop('checked', true);
					else $(row).removeClass(active_class).find('input[type=checkbox]').eq(0).prop('checked', false);
				});
			});
		});
		</script>
</body>
</html>