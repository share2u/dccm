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
	
	
	<script type="text/javascript" src="static/js/jquery-1.7.2.js"></script>
	<script type="text/javascript" src="plugins/zTree/3.5/jquery.ztree.core-3.5.js"></script>
	<link rel="stylesheet" href="plugins/zTree/3.5/zTreeStyle.css" />
	<link rel="stylesheet" href="static/jQueryUIDatepicker/jquery-ui.css">
	<link rel="stylesheet" href="static/jQueryUIDatepicker/jquery-ui-timepicker-addon.css">
	
	
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
					
					<form action="discount/${msg }.do" name="Form" id="Form" method="post">
						<input type="hidden" name="DISCOUNT_ID" id="DISCOUNT_ID" value="${pd.DISCOUNT_ID}"/>
						<input type="hidden"  id="oldDiscountNumber" value="${pd.NUMBER}"/>
						<input type="hidden"  name="A_NUMBER" value="${pd.A_NUMBER}"/>
						<div id="zhongxin" style="padding-top: 13px;">
						<table id="table_report" class="table table-striped table-bordered table-hover">
							<tr>
								<td style="width:110px;text-align: right;padding-top: 13px;">优惠券名称:</td>
								<td><input type="text" name="DISCOUNT_NAME" id="DISCOUNT_NAME" value="${pd.DISCOUNT_NAME}" maxlength="50" placeholder="这里输入优惠券名称" title="优惠券名称" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:110px;text-align: right;padding-top: 13px;">优惠券金额:</td>
								<td><input type="text" name="DISCOUNT_AMOUNT" id="DISCOUNT_AMOUNT" value="${pd.DISCOUNT_AMOUNT}" maxlength="32" placeholder="这里输入优惠券金额" title="优惠券金额" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:110px;text-align: right;padding-top: 13px;">优惠券发放数量:</td>
								<td><input type="text" name="NUMBER" id="NUMBER" value="${pd.NUMBER}" maxlength="32" placeholder="这里输入优惠券可用数量" title="优惠券可以发放的数量" style="width:98%;" onblur="judgeNumber(this)"/></td>
							</tr>
							<tr>
								<td style="width:110px;text-align: right;padding-top: 13px;">开始时间:</td>
								<td><input class="span10" name="STARTTIME" id="STARTTIME" value="${pd.STARTTIME}" type="text" readonly="readonly" placeholder="开始时间" title="开始时间" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:110px;text-align: right;padding-top: 13px;">结束时间:</td>
								<td><input class="span10" name="ENDTIME" id="ENDTIME" value="${pd.ENDTIME}" type="text" data-date-format="yyyy-mm-dd" readonly="readonly" placeholder="结束时间" title="结束时间" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:110px;text-align: right;padding-top: 13px;">请选择项目类别<span style="font-size:12px;color:red;">(修改时要重新选择)</span> :</td>
								<td>
									<div id="treeDemo" class="ztree"></div>
								</td>
							</tr>
							<tr>
								<td style="width:110px;text-align: right;padding-top: 13px;">请选择项目<span style="font-size:12px;color:red;">(修改时要重新选择)</span>:</td>
								<td>
									<div id="projectArea"></div>
								</td>
							</tr>
							<tr>
								<td style="width:110px;text-align: right;padding-top: 13px;">可用项目:</td>
								<td><div id="projects">
									<input type="hidden" id="PROJECT_IDS" name="PROJECT_IDS" value="">
									<ul id="selectdProjects"></ul>
									</div></td>
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
	<script src="static/jQueryUIDatepicker/jquery-ui.min.js"></script>
	
	<script src="static/jQueryUIDatepicker/jquery-ui-timepicker-addon.js"></script>
	<script src="static/jQueryUIDatepicker/jquery.ui.datepicker-zh-CN.js" ></script>
	<script src="static/jQueryUIDatepicker/jquery-ui-timepicker-zh-CN.js" ></script>
	
	<!--提示框-->
	<script type="text/javascript" src="static/js/jquery.tips.js"></script>
		<script type="text/javascript">
		$(top.hangge());
		//保存
		function save(){
			if($("#DISCOUNT_NAME").val()==""){
				$("#DISCOUNT_NAME").tips({
					side:3,
		            msg:'请输入优惠券名称',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#DISCOUNT_NAME").focus();
			return false;
			}
			if($("#DISCOUNT_AMOUNT").val()==""){
				$("#DISCOUNT_AMOUNT").tips({
					side:3,
		            msg:'请输入优惠券金额',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#DISCOUNT_AMOUNT").focus();
			return false;
			}
			if(Number($("#NUMBER").val())< Number($("#oldDiscountNumber").val())){
				$("#NUMBER").tips({
					side:3,
		            msg:'优惠券可以发放的数量只能增加不能减少',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#NUMBER").focus();
			return false;
			}
			if($("#STARTTIME").val()==""){
				$("#STARTTIME").tips({
					side:3,
		            msg:'请输入开始时间',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#STARTTIME").focus();
			return false;
			}
			if($("#ENDTIME").val()==""){
				$("#ENDTIME").tips({
					side:3,
		            msg:'请输入结束时间',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#ENDTIME").focus();
			return false;
			}
			
			$("#PROJECT_IDS").val(parr.join(","));
			$("#Form").submit();
			$("#zhongxin").hide();
			$("#zhongxin2").show();
		}
		
		$(function() {
			//日期框
			$('#STARTTIME').datetimepicker({
				 timeFormat: "hh:mm:ss",  
                 dateFormat: "yy-mm-dd" ,
				 monthNames: ['一月','二月','三月','四月','五月','六月', 
       						 '七月','八月','九月','十月','十一月','十二月'], 
        	     monthNamesShort: ['一','二','三','四','五','六', 
       							 '七','八','九','十','十一','十二'], 
        		 dayNames: ['星期日','星期一','星期二','星期三','星期四','星期五','星期六'], 
        		 dayNamesShort: ['周日','周一','周二','周三','周四','周五','周六'], 
        		 dayNamesMin: ['日','一','二','三','四','五','六'], 
        		 weekHeader: '周',  
				 yearSuffix: '年',	
				});
			$('#ENDTIME').datetimepicker({
				 timeFormat: "hh:mm:ss",  
                 dateFormat: "yy-mm-dd" ,
				 monthNames: ['一月','二月','三月','四月','五月','六月', 
       						 '七月','八月','九月','十月','十一月','十二月'], 
        	     monthNamesShort: ['一','二','三','四','五','六', 
       							 '七','八','九','十','十一','十二'], 
        		 dayNames: ['星期日','星期一','星期二','星期三','星期四','星期五','星期六'], 
        		 dayNamesShort: ['周日','周一','周二','周三','周四','周五','周六'], 
        		 dayNamesMin: ['日','一','二','三','四','五','六'], 
        		 weekHeader: '周',  
				 yearSuffix: '年',	
				});
			var setting = {
	        view : {
	            showLine : true,
	            selectedMulti : false,
	            dblClickExpand : false
	        },
	        data : {
	            simpleData : {
	                enable : true
	            }
	        },
	        callback : {
	            onNodeCreated : this.onNodeCreated,
	            onClick : onTreeClick
	        }
	    };  

	    $.ajax({
	        url : 'servicecategory/getAllCategorys.do',
	        dataType : 'json',
	        contenttype : "application/x-www-form-urlencoded;charset=utf-8",
	        type : 'POST',
	        success : function(data) {
	            if (data.success) {
	                $.fn.zTree.init($("#treeDemo"), setting, data.returnvalue);
	            } else {
	                alert("加载分类树出错1！");
	            }
	        },
	        error : function(data) {
	            alert("加载分类树出错2");
	        }
	    }); 
		
	});
		
		var parr = new Array();
		
		function onTreeClick(e, treeId, treeNode) {
   			 if (treeNode.isParent) //如果不是叶子结点，结束
				{
					alert("不能选择父分类");
					return;
				}
			else {
				$("#projectArea").text("");
				$.ajax({
	            	type: "POST",
	            	url: "servicecost/queryProjectByCID",
	            	data: {cid:treeNode.id},
	           	 	dataType: "json",
	            	success: function (data) {
	            		$("#projectArea").append("<label><input onclick='checkAll("+treeNode.id+")' type='button' value='全选'></label>&nbsp;&nbsp;&nbsp;&nbsp;");
	            		$("#projectArea").append("<label><input onclick='uncheck("+treeNode.id+")' type='button' value='反选'></label><br/>");
						for(var i=0; i<data.length; i++){
							var index = parr.indexOf(data[i].PROJECT_ID);
							if (index > -1) {
                                $("#projectArea").append("<label><input id=" + data[i].PROJECT_ID + " checked='checked' type='checkbox' onclick='selectProject(" + data[i].PROJECT_ID + ")' value=" + data[i].PROJECT_ID + " />" + data[i].PNAME + "</label>&nbsp;&nbsp;&nbsp;&nbsp;");
							}
							else {
								$("#projectArea").append("<label><input id=" + data[i].PROJECT_ID + "  type='checkbox' onclick='selectProject(" + data[i].PROJECT_ID + ")' value=" + data[i].PROJECT_ID + " />" + data[i].PNAME + "</label>&nbsp;&nbsp;&nbsp;&nbsp;");
							}
						}
					},
	            	error: function (msg) {
	                	alert(msg);
	           	 	}
        		});
			}
  		}; 
		
		function checkAll(id){
			$('#projectArea input').slice(2).not('input:checked').each(function() {$(this).click()});
		}
		
		function uncheck() {
			$('#projectArea input').slice(2).each(function() {$(this).click()});
		}
		
		//拼接可用项目的id串，用，隔开
		function selectProject(id){
			var a = $("#"+id).parent().text();
			var index = parr.indexOf(id);
			if(index>-1){ //已经存在则从数组中去掉
				parr.splice(index,1);
				$("#projects ul li").remove("li[id=sp"+id+"]");
				//parr.remove(id);
			}else{//不存在则添加到数组末尾
				parr.push(id);
				$("#projects ul").append("<li style='float:left;margin-right:20px;' id='sp"+id+"'>"+a+"</li>");
			}
		
		}

		
		function judgeNumber(data){
			var old = $("#oldDiscountNumber").val();
			if(Number(data.value) < Number(old)){
				alert("优惠券可用数量只能增加不能减少！");
			}
		}
		</script>
</body>
</html>