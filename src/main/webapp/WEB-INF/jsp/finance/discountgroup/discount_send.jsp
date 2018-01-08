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
<link rel="stylesheet" href="static/ace/css/datepicker.css" />

<style type="text/css">
.attentionbutton{
	margin-left:15px;
  line-height:25px;
  height:35px;
  width:100px;
  color:#ffffff;
  background-color:#4a8cf7;
  font-size:14px;
  font-weight:normal;
  font-family:Arial;
  border:0px solid #dcdcdc;
  -webkit-border-top-left-radius:3px;
  -moz-border-radius-topleft:3px;
  border-top-left-radius:3px;
  -webkit-border-top-right-radius:3px;
  -moz-border-radius-topright:3px;
  border-top-right-radius:3px;
  -webkit-border-bottom-left-radius:3px;
  -moz-border-radius-bottomleft:3px;
  border-bottom-left-radius:3px;
  -webkit-border-bottom-right-radius:3px;
  -moz-border-radius-bottomright:3px;
  border-bottom-right-radius:3px;
  -moz-box-shadow: inset 0px 0px 0px 0px #ffffff;
  -webkit-box-shadow: inset 0px 0px 0px 0px #ffffff;
  box-shadow: inset 0px 0px 0px 0px #ffffff;
  text-align:center;
  display:inline-block;
  text-decoration:none;
}
</style>
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
						<form action="discountgroupsend/goSend.do" method="post" name="Form" id="Form">
						<div id="zhongxin">
						<table style="margin-top:5px;">
							<tr>
								<td>
									<div class="nav-search"><b>请输入用户信息关键字：</b>
										<span class="input-icon">
											<input type="text" placeholder="姓名/手机号/城市/病种/用户类型" class="nav-search-input" id="nav-search-input" autocomplete="off" name="keywords" value="${pd.keywords}" placeholder="这里输入关键词" style="width:220px;"/>
											<i class="ace-icon fa fa-search nav-search-icon"></i>
										</span>
									</div>									
								</td>
							
								<td style="vertical-align:top;padding-left:10px;">
								 	<select class="chosen-select form-control" name="sex" id="sex" data-placeholder="性别" style="vertical-align:top;width: 120px;">
									<option value="${SelectSex}"></option>
									<option value="1">男</option>
									<option value="2">女</option>
								  	</select>
								</td>
								
								<td style="padding-left:2px;"><input class="span10 date-picker" name="lastStart" id="lastStart"  value="${pd.lastStart}" type="text" data-date-format="yyyy-mm-dd" readonly="readonly" style="width:88px;" placeholder="关注时间开始日期" title="关注时间开始日期"/></td>
								<td style="padding-left:2px;"><input class="span10 date-picker" name="lastEnd" name="lastEnd"  value="${pd.lastEnd}" type="text" data-date-format="yyyy-mm-dd" readonly="readonly" style="width:88px;" placeholder="关注时间结束日期" title="关注时间结束日期"/></td>
								<td style="vertical-align:top;padding-left:2px"><a class="btn btn-light btn-xs" onclick="tosearch();"  title="检索"><i id="nav-search-icon" class="ace-icon fa fa-search bigger-110 nav-search-icon blue"></i></a></td>
							</tr>
						</table>
						<!-- 检索  -->
					
						<table id="simple-table" class="table table-striped table-bordered table-hover" style="margin-top:5px;">	
							<thead>
								<tr>
									<th class="center" style="width:35px;">
									<label class="pos-rel"><input type="checkbox" class="ace" id="zcheckbox"/><span class="lbl"></span></label>
									</th>
									<th class="center" style="width:50px;">序号</th>
									<th class="center">用户姓名</th>
									<th class="center">用户昵称</th>
									<th class="center">性别</th>
									<th class="center">出生日期</th>
									<th class="center">联系电话</th>
									<th class="center">身份证号</th>
									<th class="center">所属城市</th>
									<th class="center">关注时间</th>
									<th class="center">科室/病种</th>
									<th class="center">会员类型</th>
									<!--<th class="center">团购组名称</th>-->
									<th class="center">折扣</th>
								</tr>
							</thead>
													
							<tbody>
							<!-- 开始循环 -->	
							<c:choose>
								<c:when test="${not empty varList}">
									<c:forEach items="${varList}" var="var" varStatus="vs">
										<tr>
											<td class='center'>
												<label class="pos-rel"><input type='checkbox' name='ids' value="${var.uid}" class="ace" /><span class="lbl"></span></label>
											</td>
											<td class='center' style="width: 30px;">${vs.index+1}</td>
											<td class='center'>${var.name}</td>
											<td class='center'>${var.username}</td>
											<td class='center'>
											    <c:if test="${var.sex=='1'}">男</c:if>
												 <c:if test="${var.sex=='2'}">女</c:if> 
											</td>
											<td class='center'>${var.age}</td>
											<td class='center'>${var.phone}</td>
											<td class='center'>${var.idcode}</td>
											<td class='center'>${var.city}</td>
											<td class='center'>${var.attention_time}</td>
											<td class='center'>${var.section}</td>
											<td class='center'>${var.CATEGORY_NAME}</td>
											<!--<td class='center'>${var.GROUP_NAME}</td>-->
											<td class='center'>${var.proportion}</td>
										</tr>
									</c:forEach>
								</c:when>
								<c:otherwise>
									
								</c:otherwise>
							</c:choose>
							</tbody>
						</table>
						<table style="width:100%;">
							<tr>
								<td style="vertical-align:top;">
									<a class="btn btn-mini btn-danger" onclick="makeAll('确定向选中用户发放优惠券吗?');" title="发放优惠券" >确认发放</a>
								</td>
								<td style="vertical-align:top;"><div class="pagination" style="float: right;padding-top: 0px;margin-top: 0px;">${page.pageStr}</div></td>
							</tr>
						</table>
						</div>
						<input type="hidden" name="GROUP_ID" id="GROUP_ID" value="${GROUP_ID}">
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
		//检索
		function tosearch(){
			top.jzts();
			$("#Form").submit();
		}
		$(function() {
		
			//日期框
			$('.date-picker').datepicker({
				autoclose: true,
				todayHighlight: true
			});
			
			//下拉框
			if(!ace.vars['touch']) {
				$('.chosen-select').chosen({allow_single_deselect:true}); 
				$(window)
				.off('resize.chosen')
				.on('resize.chosen', function() {
					$('.chosen-select').each(function() {
						 var $this = $(this);
						 $this.next().css({'width': $this.parent().width()});
					});
				}).trigger('resize.chosen');
				$(document).on('settings.ace.chosen', function(e, event_name, event_val) {
					if(event_name != 'sidebar_collapsed') return;
					$('.chosen-select').each(function() {
						 var $this = $(this);
						 $this.next().css({'width': $this.parent().width()});
					});
				});
				$('#chosen-multiple-style .btn').on('click', function(e){
					var target = $(this).find('input[type=radio]');
					var which = parseInt(target.val());
					if(which == 2) $('#form-field-select-4').addClass('tag-input-style');
					 else $('#form-field-select-4').removeClass('tag-input-style');
				});
			}
			
			
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
		
		
		//批量操作
		function makeAll(msg){
			bootbox.confirm(msg, function(result) {
				if(result) {
					var str = '';
					for(var i=0;i < document.getElementsByName('ids').length;i++){
					  if(document.getElementsByName('ids')[i].checked){
					  	if(str=='') str += document.getElementsByName('ids')[i].value;
					  	else str += ',' + document.getElementsByName('ids')[i].value;
					  }
					}
					
					if(str==''){
						bootbox.dialog({
							message: "<span class='bigger-110'>您没有选择任何内容!</span>",
							buttons: 			
							{ "button":{ "label":"确定", "className":"btn-sm btn-success"}}
						});
						$("#zcheckbox").tips({
							side:1,
				            msg:'点这里全选',
				            bg:'#AE81FF',
				            time:8
				        });
						return;
					}else{
					
						$("#zhongxin").hide();
						$("#zhongxin2").show();
						$.ajax({
							type: "POST",
							url: "discountgroupsend/sendDiscount",
					    	data: {DATA_IDS:str,GROUP_ID:$('#GROUP_ID').val()},
							dataType:"json",
							//beforeSend: validateData,
							cache: false,
							success: function(data){
								if(data.success){
									bootbox.dialog({
										message: "<span class='bigger-110'>发放成功!</span>",
										buttons: 			
										{ 
											"button":
												{ 
													"label":"确定", 
													"className":"btn-sm btn-success", 
													"callback": function() { 
										 					top.Dialog.close();
														
										 				}		
												}
										},
									});
									//关闭当前窗口
									//top.Dialog.close();
								}else{
									bootbox.dialog({
										message: "<span class='bigger-110'>发放失败，请确认优惠券可用数量后重新发放!</span>",
										buttons: 			
										{ 
											"button":
												{ 
													"label":"确定", 
													"className":"btn-sm btn-success", 
													"callback": function() { 
										 					top.Dialog.close();
														
										 				}		
												}
										},
									});
								} 
							},
						});
					}
				}
			});
		};
		
		
	</script>


</body>
</html>