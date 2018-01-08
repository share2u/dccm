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
	
	<script type="text/javascript" src="static/js/jquery-1.7.2.js"></script>
	<script type="text/javascript" src="plugins/zTree/3.5/jquery.ztree.core-3.5.js"></script>
	<link rel="stylesheet" href="plugins/zTree/3.5/zTreeStyle.css" />
	
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
					
					<form action="servicecategory/${msg }.do" name="Form" id="Form" method="post">
						<input type="hidden" name="SERVICECATEGORY_ID" id="SERVICECATEGORY_ID" value="${pd.SERVICECATEGORY_ID}"/>
						<div id="zhongxin" style="padding-top: 13px;">
						<table id="table_report" class="table table-striped table-bordered table-hover">
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">类别名称:</td>
								<td><input type="text" name="CATEGORY_NAME" id="CATEGORY_NAME" value="${pd.CATEGORY_NAME}" maxlength="50" placeholder="这里输入类别名称" title="类别名称" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">父分类:</td>
								<td><input type="hidden" name="F_CATEGORY_ID" id="F_CATEGORY_ID" value="${pd.F_CATEGORY_ID}">
								<div id="treeDemo" class="ztree"></div>
								<%-- <input type="number" name="F_CATEGORY_ID" id="F_CATEGORY_ID" value="${pd.F_CATEGORY_ID}" maxlength="32" placeholder="这里输入父类别" title="父类别" style="width:98%;"/> --%>
								</td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">备注:</td>
								<td><input type="text" name="DESCRIPTION" id="DESCRIPTION" value="${pd.DESCRIPTION}" maxlength="255" placeholder="这里输入备注" title="备注" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">状态:</td>
								<td>
								<select name="STATUS" id="STATUS">
									<option value="1" <c:if test="${pd.STATUS==1}">selected</c:if>>有效</option>
									<option value="0" <c:if test="${pd.STATUS==0}">selected</c:if>>无效</option>
								</select>
								<%-- <input type="number" name="STATUS" id="STATUS" value="${pd.STATUS}" /> --%>
								</td>
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
			if($("#CATEGORY_NAME").val()==""){
				$("#CATEGORY_NAME").tips({
					side:3,
		            msg:'请输入类别名称',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#CATEGORY_NAME").focus();
			return false;
			}
			if($("#F_CATEGORY_ID").val()==""){
				$("#F_CATEGORY_ID").tips({
					side:3,
		            msg:'请选择父分类',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#F_CATEGORY_ID").focus();
			return false;
			}
			if($("#DESCRIPTION").val()==""){
				$("#DESCRIPTION").tips({
					side:3,
		            msg:'请输入备注',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#DESCRIPTION").focus();
			return false;
			}
			if($("#STATUS").val()==""){
				$("#STATUS").tips({
					side:3,
		            msg:'请输入状态',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#STATUS").focus();
			return false;
			}
			$("#Form").submit();
			$("#zhongxin").hide();
			$("#zhongxin2").show();
		}
		
		$(function() {
			//日期框
			$('.date-picker').datepicker({autoclose: true,todayHighlight: true});
			
	    
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
		
		function onTreeClick(e, treeId, treeNode) {
   			/* if (treeNode.isParent) //如果不是叶子结点，结束
   				return; */
  			//alert(treeNode.id); //获取当前结点上的相关属性数据，执行相关逻辑
  			$("#F_CATEGORY_ID").val(treeNode.id);
  			}; 
		</script>
</body>
</html>