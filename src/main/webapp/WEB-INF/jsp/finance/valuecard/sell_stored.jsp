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
<link rel="stylesheet" href="//code.jquery.com/ui/1.10.4/themes/smoothness/jquery-ui.css">
   <script src="//code.jquery.com/jquery-1.9.1.js"></script>
  <script src="//code.jquery.com/ui/1.10.4/jquery-ui.js"></script>
  <link rel="stylesheet" href="http://jqueryui.com/resources/demos/style.css">
  
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
					
						<form action="storedcategory/listMemberByPhoneOrName.do" method="post" name="Form" id="Form">
						<div id="zhongxin" style="padding-top: 13px;">
						<table style="margin-top:10px;">
							<tr>
								<td>
									<div class="nav-search">
										<span class="input-icon">
											<span><b>请输入用户姓名或者手机号查询用户：</b></span><input type="text" placeholder="用户姓名/手机号" class="nav-search-input" id="nav-search-input" autocomplete="off" name="keywords" value="${pd.keywords }" placeholder="这里输入关键词"/>
											
										</span>
									</div>
								</td>
								
								
								<td style="vertical-align:top;padding-left:2px"><a class="btn btn-light btn-xs" onclick="tosearch();"  title="检索"><i id="nav-search-icon" class="ace-icon fa fa-search bigger-110 nav-search-icon blue"></i></a></td>
								
								
							</tr>
						</table>
						<!-- 检索  -->
					
						<table id="simple-table" class="table table-striped table-bordered table-hover" style="margin-top:5px;">	
							<thead>
								<tr>
									<th class="center" style="width:35px;">
									<label class="pos-rel"><input type="checkbox" class="ace" id="zcheckbox" /><span class="lbl"></span></label>
									</th>
									<th class="center" style="width:50px;">序号</th>
									<th class="center">姓名</th>
									<th class="center">用户名</th>
									<th class="center">性别</th>
									<th class="center">年龄</th>
									<th class="center">电话号码</th>
									<th class="center">所属城市</th>
									<th class="center">客户类别</th>
									<th class="center">折扣</th>
									<th class="center">储值卡余额</th>
									<th class="center">储值卡返点余额</th>
									<th class="center">用户积分</th>
									
								</tr>
							</thead>
													
							<tbody>
							<!-- 开始循环 -->	
								<c:choose>
								<c:when test="${not empty userList}">
									<c:forEach items="${userList}" var="user" varStatus="vs">
										<tr>
											<td class='center'>
												<label class="pos-rel"><input type='radio' name='uid' value="${user.uid}" class="ace" /><span class="lbl"></span></label>
											</td>
											<td class='center' style="width: 30px;">${vs.index+1}</td>
											<td class='center'>${user.name}</td>
											<td class='center'>${user.username}</td>
											<td class='center'>
												<c:if test="${user.sex==1}">男</c:if>
												<c:if test="${user.sex==2}">女</c:if>
												<c:if test="${user.sex==0}">未知</c:if>
											</td>
											<td class='center'>${user.age}</td>
											<td class='center'>${user.phone}</td>
											<td class='center'>${user.city}</td>
											<td class='center'>${user.CATEGORY_NAME}</td>
											<td style="text-align:right">
												<c:if test="${user.proportion le user.category_porportion}">${user.proportion}</c:if>
												<c:if test="${user.proportion gt user.category_porportion}">${user.category_porportion}</c:if>
											</td>
											<td style="text-align:right"><fmt:formatNumber type="number" value="${user.remain_money}" pattern="0.00" maxFractionDigits="2"/></td>
											<td style="text-align:right"><fmt:formatNumber type="number" value="${user.remain_points}" pattern="0.00" maxFractionDigits="2"/></td>
											<td class='center'>${user.credit}</td>
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
						<span>请选择购买储值卡的种类</span>
						<table id="simple-table" class="table table-striped table-bordered table-hover" style="margin-top:5px;">	
							<thead>
								<tr>
									<th class="center" style="width:35px;">
									<label class="pos-rel"><input type="checkbox" class="ace" id="zcheckbox" /><span class="lbl"></span></label>
									</th>
									<!-- <th class="center" style="width:50px;">序号</th> -->
									<th class="center">储值金额</th>
									<th class="center">返点金额</th>
									<th class="center">创建时间</th>
									<th class="center">备注</th>
									<th class="center">状态</th>
								</tr>
							</thead>
													
							<tbody>
							<!-- 开始循环 -->	
							<c:choose>
								<c:when test="${not empty storedcategorylist}">
									
									<c:forEach items="${storedcategorylist}" var="storedcategory" varStatus="vs">
									<c:if test="${storedcategory.STATUS==0}">
										<tr>
											<td class='center'>
												<label class="pos-rel"><input type='radio' name='category_id' id='category_id' onclick="remeberMoney('${storedcategory.STORED_MONEY}')" value="${storedcategory.STOREDCATEGORY_ID}" class="ace" /><span class="lbl"></span></label>
											</td>
											<%-- <td class='center' style="width: 30px;">${vs.index+1}</td> --%>
											<td style="text-align:right"><fmt:formatNumber type="number" value="${storedcategory.STORED_MONEY}" pattern="0.00" maxFractionDigits="2"/></td>
											<td style="text-align:right"><fmt:formatNumber type="number" value="${storedcategory.RETURN_POINTS}" pattern="0.00" maxFractionDigits="2"/></td>
											<td class='center'>${storedcategory.CREATE_TIME}</td>
											<td class='center'>${storedcategory.REMARK}</td>
											<td class='center'>
											<c:if test="${storedcategory.STATUS==0}">正常</c:if>
												<c:if test="${storedcategory.STATUS==1}">过期</c:if>
												
											</td>
										
										</tr>
										</c:if>
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
						
						<div id="paymethod" class="paymethod">  <!--style="display:none;"-->
					      <table width="100px" height="300px" cellpadding="5" cellspacing="5">	
							  <thead style="margin:0 10px;">
							  	<th>请选择支付方式：</th>
								<th></th>
							  </thead>	
							  
							  <tbody>
							  	<tr>
							  		<td><input name="paymethod" type=checkbox id="wechatpay" onclick="show(this.id)">  微信支付</td>
									<td><span id="wechatpay_text" style="display:none">请输入金额：<input name="wechatpay" value="" type="text"></span></td>
							  	</tr>
								<tr>
									<td><input name="paymethod"  type=checkbox id="alipay" onclick="show(this.id)">  支付宝支付</td>
									<td><span id="alipay_text" style="display:none">请输入金额：<input name="wechatpay" value="" type="text"></span> </td>
								</tr>
								<tr>
									<td><input name="paymethod"  type=checkbox id="bankpay" onclick="show(this.id)">  银行卡支付</td>
									<td><span id="bankpay_text"  style="display:none">请输入金额：<input name="wechatpay" value="" type="text"></span> </td>
								</tr>
								<tr>
									<td><input name="paymethod" type=checkbox id="cashpay" onclick="show(this.id)">  现金支付</td>
									<td> <span  id="cashpay_text" style="display:none">请输入金额：<input name="wechatpay" value="" type="text"></span></td>
								</tr>
								<tr>
									<td>备注</td>
									<td><input type="text" name="REMARK" id="REMARK" value="${pd.REMARK}" maxlength="32" placeholder="这里输入备注" title="备注" style="width:98%;"/></td>
								</tr>
								<tr>
									<td> </td>
									<td align="right"><a class="btn btn-mini btn-danger" onclick="sell('确定向选中用户售卖储值卡吗?');" title="售卖储值卡" >售卖储值卡</a></td>
								</tr>
							  </tbody>
					      </table>
				  	</div>  
						
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
	
		function sell(msg){
			bootbox.confirm(msg, function(result) {
				if(result) {
				var uid ='';
				var category_id ='';
				var uid = $('input[name="uid"]:checked').val(); 
				var category_id = $('input[name="category_id"]:checked').val();
				var pay_method = $("#PAY_METHOD").val();   
					console.log(uid);
					if(uid==''||category_id==''||uid==undefined||category_id==undefined){
						bootbox.dialog({
							message: "<span class='bigger-110'>您没有选择用户或者储值卡类型!</span>",
							buttons: 			
							{ "button":{ "label":"确定", "className":"btn-sm btn-success"}}
						});
						return;
					}else{
						
						var wechatpay_money = $("#wechatpay_text").children().val();
						var alipay_money = $("#alipay_text").children().val();
						var bankpay_money = $("#bankpay_text").children().val();
						var cashpay_money = $("#cashpay_text").children().val();
						var remark = $("#REMARK").val();
						if(Number(wechatpay_money)+Number(alipay_money)+Number(bankpay_money)+Number(cashpay_money)==Number(money))	{
						$("#zhongxin").hide();
						$("#zhongxin2").show();
						$.ajax({
							type: "POST",
							url: "storedcategory/sellStored",
					    	data: {uid:uid,category_id:category_id,pay_method:pay_method,wechatpay_money:wechatpay_money,alipay_money:alipay_money,bankpay_money:bankpay_money,cashpay_money:cashpay_money,remark:remark},
							dataType:"json",
							//beforeSend: validateData,
							cache: false,
							success: function(data){
								if(data.success){
									//提示发放成功 
									bootbox.dialog({
										message: "<span class='bigger-110'>储值卡售卖成功!  <br><br> <a href='storedcategory/gosell.do'>返回</a></span>",
									});
									//关闭当前窗口
									//top.Dialog.close();
								}else if(data.error){
									//提示发放失败 
									bootbox.dialog({
										message: "<span class='bigger-110'>储值卡售卖失败，请重试!  <br><br> <a href='storedcategory/gosell.do'>返回</a></span>",
									});
								} 
							},
						});
						}
						else{
						
						alert("您输入的总金额有误！请检查后重新输入！")
						}
					}
				}
			});
			
		};
		
		
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
		
		//新增
		function add(){
			 top.jzts();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="新增";
			 diag.URL = '<%=basePath%>storedcategory/goAddm.do';
			 diag.Width = 450;
			 diag.Height = 355;
			 diag.Modal = true;				//有无遮罩窗口
			 diag. ShowMaxButton = true;	//最大化按钮
		     diag.ShowMinButton = true;		//最小化按钮
			 diag.CancelEvent = function(){ //关闭事件
				 if(diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none'){
					 if('${page.currentPage}' == '0'){
						 top.jzts();
						 setTimeout("self.location=self.location",100);
					 }else{
						 nextPage(${page.currentPage});
					 }
				}
				diag.close();
			 };
			 diag.show();
		}
		
		var money = 0;
		var i = 0;
		
		function show(id){//显示框框，应该再点以下隐藏框框
	if($("#"+id+"_text").is(":hidden")){
       $("#"+id+"_text").show();    //如果元素为隐藏,则将它显现
}else{
      $("#"+id+"_text").hide();     //如果元素为显现,则将其隐藏
    $("#"+id+"_text").children().val(i);
     

}
			/* //$("#"+id+"_text").show();
			var wechatpay_money = $("#wechatpay_text").children().val();
			var alipay_money = $("#alipay_text").children().val();
			var bankpay_money = $("#bankpay_text").children().val();
			var cashpay_money = $("#cashpay_text").children().val();
			
			$("#"+id+"_text").children().val(money);
			
			if(wechatpay_money!=""){
				//alert($("#wechatpay_text").children().val());
				$("#"+id+"_text").children().val(money-wechatpay_money);
			}
			if(alipay_money!=""){
				//alert($("#wechatpay_text").children().val());
				$("#"+id+"_text").children().val(money-wechatpay_money-alipay_money);
			}
			if(bankpay_money!=""){
				//alert($("#wechatpay_text").children().val());
				$("#"+id+"_text").children().val(money-wechatpay_money-alipay_money-bankpay_money);
			}
			 */
		}

		function remeberMoney(m){
			 money = m;
		}
		//修改
		function edit(Id){
			 top.jzts();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="编辑";
			 diag.URL = '<%=basePath%>member/goEditm.do?UID='+Id;
			 diag.Width = 450;
			 diag.Height = 355;
			 diag.Modal = true;				//有无遮罩窗口
			 diag. ShowMaxButton = true;	//最大化按钮
		     diag.ShowMinButton = true;		//最小化按钮 
			 /* diag.CancelEvent = function(){ //关闭事件
			 alert(111111);
		 if(diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none'){
			nextPage(${page.currentPage});
		}
		diag.close();
	 }; */

	 diag.CancelEvent = function(){ //关闭事件
				 if(diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none'){
					 if('${page.currentPage}' == '0'){
						 top.jzts();
						 setTimeout("self.location=self.location",100);
					 }else{
						 nextPage(${page.currentPage});
					 }
				}
				diag.close();
			 };
			 diag.show();
		};

	
	
	</script>


</body>
</html>