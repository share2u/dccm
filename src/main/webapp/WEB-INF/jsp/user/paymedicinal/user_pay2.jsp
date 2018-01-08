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
						<form action="paymedicinal/show2.do" method="post" name="Form" id="Form">
						
						<div>
							<span style="margin-top:8px;float:left;">请输入用户姓名/昵称/手机号查询用户：</span>
							<div class="nav-search" style="width:80%; float:left;text-align:left;">
								<span class="input-icon">
									<input type="text" placeholder="用户姓名/昵称/手机号" class="nav-search-input" id="nav-search-input" autocomplete="off" name="keywords" value="${pd.keywords }" placeholder="这里输入关键词"/>
									<i class="ace-icon fa fa-search nav-search-icon"></i>
								</span>
								<a class="btn btn-light btn-xs" onclick="tosearch();"  title="检索"><i id="nav-search-icon" class="ace-icon fa fa-search bigger-110 nav-search-icon blue"></i></a>
							</div>
						</div>
						
						<table id="simple-table" class="table table-striped table-bordered table-hover" style="margin-top:5px;">	
							<thead>
								<tr>
									<th class="center" style="width:35px;">
									<label class="pos-rel"><span class="lbl"></span></label>
									</th>
									<th class="center" style="width:50px;">序号</th>
									<th class="center">姓名</th>
									<th class="center">昵称</th>
									<th class="center">性别</th>
									<th class="center">出生年月</th>
									<th class="center">电话号码</th>
									<th class="center">所属城市</th>
									<th class="center">客户类别</th>
									<th class="center">折扣</th>
									<th class="center">储值卡</th>
									<th class="center">返点</th>
									<th class="center">钱包</th>
									<th class="center">优惠券</th>
								</tr>
							</thead>
													
							<tbody>
							<!-- 开始循环 -->	
							<c:choose>
								<c:when test="${not empty userList}">
									<c:forEach items="${userList}" var="user" varStatus="vs">
										<tr>
											<td class='center'>
												<input type='radio' name='uid' value="${user.uid}" chuzhika="${user.remain_money}" fandian="${user.remain_points}" qianbao="${user.SUM_MONEY}" class="ace"/><span class="lbl"></span>
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
											<td class='center'>
												<c:if test="${user.proportion le user.category_porportion}">${user.proportion}</c:if>
												<c:if test="${user.proportion gt user.category_porportion}">${user.category_porportion}</c:if>
											</td>
											<td style="text-align:right"><fmt:formatNumber type="number" value="${user.remain_money}" pattern="0.00" maxFractionDigits="2"/></td>
											<td style="text-align:right"><fmt:formatNumber type="number" value="${user.remain_points}" pattern="0.00" maxFractionDigits="2"/></td>
											<td style="text-align:right"><fmt:formatNumber type="number" value="${user.SUM_MONEY}" pattern="0.00" maxFractionDigits="2"/></td>
											<td class='center'>
												<a class="btn btn-xs btn-info" title="查看用户优惠券" onclick="discount('${user.uid}');">
														<i class="ace-icon fa fa-file bigger-120" title="查看用户优惠券"></i>
												</a></td>
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
						
						<!--检索 end-->
						<div style="clear:both;"></div>
						
						 
						<div id="name" style="text-align:left;margin-bottom:20px;">
						项目名称：消耗品
						</div>
					
					<div id="selectdoctor" style="width:100%;text-align: left;">
						请选择医生：
						<select name="STAFF_ID" id="STAFF_ID" >
							<option value="" >---请选择---</option>
							<c:forEach items="${staffPdlist}" var="staff">
								<c:if test="${staff.STATUS==0}">
								<option value="${staff.STAFF_ID}" <c:if test="${staff.STAFF_ID==pd.STAFF_ID}">selected</c:if> >${staff.STAFF_NAME}(${staff.STORE_NAME})</option>
								</c:if>
							</c:forEach>
						</select>
						
					   </div>
					<div id="price" style="width:100%;text-align:left;margin-top:20px;">
					价格：<input type="text" name="PRICE" id="PRICE" min="0" maxlength="255" placeholder="这里输入价格" title="价格" style="width:400px;height:35px;" onblur="checkPriceForm(this.value)">&nbsp;&nbsp;元
					</div>
					
					<div id="remark" style="width:100%;text-align:left;margin-top:20px;">
					备注：<input type="text" name="REMARK" id="REMARK" placeholder="这里输入备注" title="备注" style="width:600px;height:35px;">
					</div>
					
					<div style="width:100%;text-align:left;margin-top:20px;color:red;">
					应收金额：<b id="newprice"></b>
					</div>
					
					<div id="paymethod" class="paymethod">  
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
									<td> <span  id="cashpay_text" style="display:none">请输入金额：<input name="wechatpay" value="" type="text" ></span></td>
								</tr>
								<tr>
									<td><input name="paymethod" type=checkbox id="storedpay" onclick="show(this.id)">储值卡支付</td>
									<td> <span  id="storedpay_text" style="display:none">请输入金额：<input name="storedpay" value="" type="text" onchange="checkChuzhikaMoney(this.value)"></span></td>
								</tr>
								<tr>
									<td><input name="paymethod" type=checkbox id="prestorepay" onclick="show(this.id)">余额支付</td>
									<td> <span  id="prestorepay_text" style="display:none">请输入金额：<input name="prestorepay" value="" type="text" onchange="checkPreStoreMoney(this.value)"></span></td>
								</tr>
								
								<tr>
									<td style="text-align: center;" colspan="10"><a
										class="btn btn-mini btn-primary"
										onclick="submitOrder('确定提交该订单吗？')">提交订单</a></td>
								</tr>
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
		
		//搜索用户		
		function tosearch(){
			top.jzts();
			$("#Form").submit();
		}
	 	
	 	//查看用户优惠券
		function discount(Id){
			 top.jzts();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="用户优惠券";
			 diag.URL = '<%=basePath%>userdiscount/goShowDiscount.do?UID='+Id;
			 diag.Width = 1200;
			 diag.Height = 800;
			 diag.Modal = true;				//有无遮罩窗口
			 diag. ShowMaxButton = true;	//最大化按钮
		     diag.ShowMinButton = true;		//最小化按钮 
			 		 diag.CancelEvent = function(){ //关闭事件
				 if(diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none'){
					 nextPage(${page.currentPage});
				}
				diag.close();
			 };
			 diag.show();
		}
	 	
	 	//提交订单
		function submitOrder(msg) {
			
			var wechatpay_money = $("#wechatpay_text").children().val();
			var alipay_money = $("#alipay_text").children().val();
			var bankpay_money = $("#bankpay_text").children().val();
			var cashpay_money = $("#cashpay_text").children().val();
			var prestorepay_money = $("#prestorepay_text").children().val();
			var storedpay_money = $("#storedpay_text").children().val();
			var uid = $('input[name="uid"]:checked').val(); 
			
			var price = $("#PRICE").val(); 
			var doctor_id = $("#STAFF_ID").val();
			
			console.log(price);
			console.log(wechatpay_money);
			console.log(alipay_money);
			console.log(bankpay_money);
			console.log(cashpay_money);
			console.log(prestorepay_money);
			console.log(storedpay_money);
			console.log(Number(wechatpay_money)+Number(alipay_money)+Number(bankpay_money)+Number(cashpay_money)+Number(prestorepay_money)+Number(storedpay_money));
			console.log(doctor_id);
			if(uid==undefined){
				alert("请选择用户！");
				return;
			}
			if(price==undefined || price==""){
				alert("请填写价钱！");
				return;
			}
			if(doctor_id==""){
				alert("请选择医生");
				return;
			}
			
			if(Number(wechatpay_money)+Number(alipay_money)+Number(bankpay_money)+Number(cashpay_money)+Number(prestorepay_money)+Number(storedpay_money)!=price){
				alert("您填写的总金额不正确，请核对后重新输入！");
				return;
			}
			
			bootbox.confirm(msg, function(result) {
				if (result) {
				 	$("#zhongxin").hide();
			 		$("#zhongxin2").show();
					$.ajax({
						type : "POST",
						url : "paymedicinal/createOrder2",
						data : {
							UID : uid,
							PNAME:$("#PNAME").val(),
							PRICE:$("#PRICE").val(),
							STAFF_ID:$("#STAFF_ID").val(),
							REMARK : $("#REMARK").val(),
							WECHATPAY_MONEY : wechatpay_money,
							ALIPAY_MONEY : alipay_money,
							BANKPAY_MONEY : bankpay_money,
							CASHPAY_MONEY : cashpay_money,
							PRESTOREPAY_MONEY : prestorepay_money,
							STOREDPAY_MONEY : storedpay_money
						},
						dataType:"json",
						cache:false,
						success : function(data) {
							if(data.success){
								 bootbox.dialog({
									message: "<span class='bigger-110'>订单创建成功！  <br><br> <a href='paymedicinal/show2.do'>返回</a></span>",    
								
								}); 
								
							}else{
								bootbox.dialog({
									message: "<span class='bigger-110'>创建订单失败!  <br><br> <a href='paymedicinal/show2.do'>返回</a></span>",    
								});
							}
						}
					});
				}

			});
		}
		function show(id){//显示框框，应该再点以下隐藏框框
			if($("#"+id+"_text").is(":hidden")){
       		$("#"+id+"_text").show();    //如果元素为隐藏,则将它显现
		}else{
      		$("#"+id+"_text").hide();     //如果元素为显现,则将其隐藏
   			 $("#"+id+"_text").children().val(i);
			}
		}
		
		//检查客服输入的金额是否正确
		function checkPriceForm(value){
			if(value<0){
				alert("收费金额应大于0！")
				$("#PRICE").val(0);
				return;
			}
			$("#newprice").text(Number(value).toFixed(2));
			
		}
		
	   //检查预存余额
		function checkPreStoreMoney(value){
			var qianbao = $('input[name="uid"]:checked').attr("qianbao");
			if(Number(qianbao)<Number(value)){
				alert("该用户钱包余额不足！");
				$("#prestorepay_text").children().val(qianbao);
				return;
			}
		}
		
		//检查储值卡余额
		function checkChuzhikaMoney(value){
			var chuzhika = $('input[name="uid"]:checked').attr("chuzhika");
			var fandian = $('input[name="uid"]:checked').attr("fandian");
			var zong = Number(chuzhika) + Number(fandian);//用户储值卡的钱
			if(zong<Number(value)){
				alert("该用户储值卡余额不足！");
				$("#storedpay_text").children().val(zong);
				return;
			}
		} 
	</script>
</body>
</html>