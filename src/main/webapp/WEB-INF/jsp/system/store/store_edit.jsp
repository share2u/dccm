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
  <link rel="stylesheet" href="//code.jquery.com/ui/1.10.4/themes/smoothness/jquery-ui.css">
   <script src="//code.jquery.com/jquery-1.9.1.js"></script>
  <script src="//code.jquery.com/ui/1.10.4/jquery-ui.js"></script>
  <link rel="stylesheet" href="http://jqueryui.com/resources/demos/style.css">
<script>
$(function(){  
$("#cityname").autocomplete({
source: function( request, response ) {  
    $.ajax({  
        type:"post",  
        url: "store/querycityname",  
        dataType: "json",  
        data:{  
            term: request.term  
        },  

        success: function(data) {
            response( $.map(data, function( item ) {   
            	if(data.error){
            		return {   //lable为下拉列表显示数据源。value为选中放入到文本框的值，这种方式可以自定义显示  
	                    lable:"-1",      
	                    value:"该城市不存在"  
	                } ;
            	}else{  
	                return {   //lable为下拉列表显示数据源。value为选中放入到文本框的值，这种方式可以自定义显示  
	                    lable:item.CITY_ID,      
	                    value: item.CITY_NAME  
	                } ;
	            } 

            }));  
        }  
    });  
},  
minLength: 1,  
 mustMatch: true, 
select: function( event, ui ) { //移动键盘上下键，自动将下拉列表的数据放入到文本框，不过不写这个配置也是可以的，默认就行，具体这个还不知道是做什么  
    $("#cityname").val(ui.item.CITY_ID);  
}  
});  
});

</script>
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
					
					<form action="store/${msg }.do" name="Form" id="Form" method="post" enctype="multipart/form-data">
						<input type="hidden" name="STORE_ID" id="STORE_ID" value="${pd.STORE_ID}"/>
						<div id="zhongxin" style="padding-top: 13px;">
						<table id="table_report" class="table table-striped table-bordered table-hover">
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">门店编号:</td>
								<td><input type="text" name="STORE_NUMBER" id="STORE_NUMBER" value="${pd.STORE_NUMBER}" maxlength="32" placeholder="这里输入门店编号" title="门店编号" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">名店名称:</td>
								<td><input type="text" name="STORE_NAME" id="STORE_NAME" value="${pd.STORE_NAME}" maxlength="255" placeholder="这里输入名店名称" title="名店名称" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">所属城市:</td>
								<td><input type="text" name="CITY_NAME" value="${pd.CITY_NAME }" id="cityname"  maxlength="32" placeholder="这里输入所属城市" title="所属城市" style="width:98%;"/></td>
								<!--<div id="message"></div>-->
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">地址:</td>
								<td><input type="text" name="ADDRESS" id="ADDRESS" value="${pd.ADDRESS}" maxlength="255" placeholder="这里输入地址" title="地址" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">交通信息:</td>
								<td><input type="text" name="TRAFFIC_MESSAGE" id="TRAFFIC_MESSAGE" value="${pd.TRAFFIC_MESSAGE}" maxlength="255" placeholder="这里输入交通信息" title="交通信息" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">工作时间:</td>
								<td><input type="text" name="WORKTIME" id="WORKTIME" value="${pd.WORKTIME}" maxlength="255" placeholder="这里输入工作时间" title="工作时间" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">联系电话:</td>
								<td><input type="text" name="TELEPHONE" id="TELEPHONE" value="${pd.TELEPHONE}" maxlength="20" placeholder="这里输入联系电话" title="联系电话" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">所属板块:</td>
								<td>
								<%-- <input type="text" name="M_ID" id="M_ID" value="${pd.M_ID}" maxlength="50" style="width:98%;"/> --%>
								<select name="M_ID" id="M_ID">
									<c:forEach items="${modulelist}" var="module">
										<option value="${module.SERVICEMODULE_ID}" <c:if test="${module.SERVICEMODULE_ID==pd.M_ID}">selected</c:if> >${module.M_NAME}</option>
									</c:forEach>
								</select>
								</td>
							</tr>
							<%-- <tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">负责人:</td>
								<td>
								<input type="text" name="STAFF_ID" id="STAFF_ID" value="${pd.STAFF_ID}"style="width:98%;"/>
								</td>
							</tr> --%>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;" id="tips1">门店小图:</td>
								<td>
									<input type="file" name="uploadimages"/>
									<input type="hidden" name="STORE_SMALL_IMG" id="FILEPATH" value="${pd.STORE_SMALL_IMG}"/>
								</td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;" id="tips2">门店大图:</td>
								<td>
									<input type="file" name="uploadimages"/>
									<input type="hidden" name="STORE_BIG_IMG" id="FILEPATH" value="${pd.STORE_BIG_IMG}"/>
								</td>
							</tr>
							
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">经营模式:</td>
								<td><%-- <input type="number" name="BUSSINPATTERN" id="BUSSINPATTERN" value="${pd.BUSSINPATTERN}" maxlength="32" placeholder="这里输入经营模式" title="经营模式" style="width:98%;"/> --%>
									<select name="BUSSINPATTERN" id="BUSSINPATTERN">
										<option value="01" <c:if test="${pd.BUSSINPATTERN=='01'}">selected</c:if> >自营</option>
										<option value="02" <c:if test="${pd.BUSSINPATTERN=='02'}">selected</c:if> >合作</option>
										<option value="03" <c:if test="${pd.BUSSINPATTERN=='03'}">selected</c:if> >加盟</option>
									</select>
								</td>
							</tr>
							
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">是否有资质:</td>
								<td><%-- <input type="number" name="IS_QUALIFICATION" id="IS_QUALIFICATION" value="${pd.IS_QUALIFICATION}" maxlength="32" placeholder="这里输入是否有资质" title="是否有资质" style="width:98%;"/> --%>
								<select name="IS_QUALIFICATION" id="IS_QUALIFICATION">
									<option value="01" <c:if test="${pd.IS_QUALIFICATION=='01'}">selected</c:if>>是</option>
									<option value="02" <c:if test="${pd.IS_QUALIFICATION=='02'}">selected</c:if>>否</option>
								</select>
								</td>
							</tr>
							
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">状态:</td>
								<%-- <td><input type="number"  value="${pd.STATUS}" maxlength="32" placeholder="这里输入状态" title="状态" style="width:98%;"/></td> --%>
								<td><select name="STATUS" id="STATUS">
									<option value="1" <c:if test="${pd.STATUS==1}">selected</c:if>>营业</option>
									<option value="0" <c:if test="${pd.STATUS==0}">selected</c:if>>停业</option>
								</select></td>
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
			if($("#STORE_NUMBER").val()==""){
				$("#STORE_NUMBER").tips({
					side:3,
		            msg:'请输入门店编号',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#STORE_NUMBER").focus();
			return false;
			}
			if($("#STORE_NAME").val()==""){
				$("#STORE_NAME").tips({
					side:3,
		            msg:'请输入名店名称',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#STORE_NAME").focus();
			return false;
			}
			if($("#ADDRESS").val()==""){
				$("#ADDRESS").tips({
					side:3,
		            msg:'请输入地址',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#ADDRESS").focus();
			return false;
			}
			if($("#TRAFFIC_MESSAGE").val()==""){
				$("#TRAFFIC_MESSAGE").tips({
					side:3,
		            msg:'请输入交通信息',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#TRAFFIC_MESSAGE").focus();
			return false;
			}
			if($("#WORKTIME").val()==""){
				$("#WORKTIME").tips({
					side:3,
		            msg:'请输入工作时间',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#WORKTIME").focus();
			return false;
			}
			if($("#TELEPHONE").val()==""){
				$("#TELEPHONE").tips({
					side:3,
		            msg:'请输入联系电话',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#TELEPHONE").focus();
			return false;
			}
			if($("#M_ID").val()==""){
				$("#M_ID").tips({
					side:3,
		            msg:'请输入服务板块',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#M_ID").focus();
			return false;
			}
			if($("#STAFF_ID").val()==""){
				$("#STAFF_ID").tips({
					side:3,
		            msg:'请输入负责人',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#STAFF_ID").focus();
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
			if($("#BUSSINPATTERN").val()==""){
				$("#BUSSINPATTERN").tips({
					side:3,
		            msg:'请输入经营模式',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#BUSSINPATTERN").focus();
			return false;
			}
			if($("#cityname").val()==""){
				$("#cityname").tips({
					side:3,
		            msg:'请输入所属城市',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#cityname").focus();
			return false;
			}
			if($("#IS_QUALIFICATION").val()==""){
				$("#IS_QUALIFICATION").tips({
					side:3,
		            msg:'请输入是否有资质',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#IS_QUALIFICATION").focus();
			return false;
			}
			
			
			
			$("#Form").submit();
			$("#zhongxin").hide();
			$("#zhongxin2").show();
		}
		
		$(function() {
			//日期框
			$('.date-picker').datepicker({autoclose: true,todayHighlight: true});
		});
		</script>
</body>
</html>