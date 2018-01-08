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
</head>
<body class="no-skin">
	<!-- /section:basics/navbar.layout -->
	<div class="main-container" id="main-container">
		<!-- /section:basics/sidebar -->
		<div class="main-content">
			<div class="main-content-inner">
				<div class="page-content"> <!-- style="padding: 20px 400px 0px"-->
					<div class="row">
						<div class="col-xs-12">

							<form action="member/${msg }.do" name="Form" id="Form"
								method="post">
								<input type="hidden" name="UID" id="UID" value="${pd.uid}" />
								<div id="zhongxin" style="padding-top: 13px;">
									<table id="table_report"
										class="table table-striped table-bordered table-hover">
										<!--<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">openid:</td>
								<td><input type="text" name="OPENID" id="OPENID" value="${pd.openid}" maxlength="255" placeholder="这里输入openid" title="openid" style="width:98%;"/></td>
							</tr>
							-->
										<tr>
											<td style="width:75px;text-align: right;padding-top: 13px;">姓名:</td>
											<td><input type="text" name="NAME" id="NAME"
												value="${pd.name}" maxlength="255" placeholder="这里输入姓名"
												title="姓名" style="width:98%;" />
											</td>
										</tr>
										<tr>
											<td style="width:75px;text-align: right;padding-top: 13px;">用户名:</td>
											<td><input type="text" name="USERNAME" id="USERNAME"
												value="${pd.username}" maxlength="255" placeholder="这里输入用户名"
												title="用户名" style="width:98%;" />
											</td>
										</tr>

										<tr>
											<td style="width:75px;text-align: right;padding-top: 13px;">性别:</td>
											<td><select name="SEX" id="SEX">
													<option value="1"
														<c:if test="${pd.sex=='1'}">selected</c:if>>男</option>
													<option value="2"
														<c:if test="${pd.sex=='2'}">selected</c:if>>女</option>
													<option value="0"
														<c:if test="${pd.sex=='0'}">selected</c:if>>未知</option>
											</select></td>
										</tr>
										<tr>
											<td style="width:75px;text-align: right;padding-top: 13px;">出生日期:</td>
											<td><input class="span10 date-picker" type="text"
												name="AGE" id="AGE" value="${pd.age}"
												maxlength="32" data-date-format="yyyy-mm-dd"
												readonly="readonly" style="width:88px;"
												placeholder="这里输入出生日期" title="出生日期" style="width:98%;" />
											</td>
										</tr>
										<tr>
											<td style="width:75px;text-align: right;padding-top: 13px;">电话号码:</td>
											<td><input type="text" name="PHONE" id="PHONE"
												value="${pd.phone}" maxlength="255" placeholder="这里输入电话号码"
												title="电话号码" style="width:98%;"/>
											</td>
										</tr>
										<tr>
											<td style="width:75px;text-align: right;padding-top: 13px;">所属城市:</td>
											<td><input type="text" name="CITY" id="CITY"
												value="${pd.city}" maxlength="255" placeholder="这里输入所属城市"
												title="所属城市" style="width:98%;" />
											</td>
										</tr>
										<tr>
											<td style="width:75px;text-align: right;padding-top: 13px;">家庭住址:</td>
											<td><input type="text" name="ADDRESS" id="ADDRESS"
												value="${pd.address}" maxlength="255" placeholder="这里输入家庭住址"
												title="家庭住址" style="width:98%;" />
											</td>
										</tr>
										<tr>
											<td style="width:75px;text-align: right;padding-top: 13px;">身份证号:</td>
											<td><input type="text" name="IDCODE" id="IDCODE"
												value="${pd.idcode}" maxlength="255" placeholder="这里输入身份证号"
												title="身份证号" style="width:98%;" />
											</td>
										</tr>
										<tr>
											<td style="width:75px;text-align: right;padding-top: 13px;">初诊日期:</td>
											<td><input class="span10 date-picker" name="FIRST_DATE"
												id="FIRST_DATE" value="${pd.first_date}" type="text"
												data-date-format="yyyy-mm-dd" readonly="readonly"
												style="width:88px;" placeholder="初诊日期" title="初诊日期" />
											</td>

										</tr>
										<tr>
											<td style="width:75px;text-align: right;padding-top: 13px;">会员类型:</td>
                                            <td><select class="chosen-select form-control" name="USERCATEGORY_ID" id="PARENT_ID" data-placeholder="请选择父节点名称" style="vertical-align:top;" style="width:98%;" onChange="getValue(this.value)" >
													<option value="0">--请选择--</option>
													<c:forEach items="${categoryList}" var="category">
														<option value="${category.USERCATEGORY_ID}" <c:if test="${pd.PARENT_ID==category.USERCATEGORY_ID}">selected</c:if> >${category.CATEGORY_NAME}</option>
													</c:forEach>
												</select></td>

											<!--<td><select class="chosen-select form-control"
												name="USERCATEGORY_ID" id="PARENT_ID"
												data-placeholder="请选择用户类别" style="vertical-align:top;"
												style="width:98%;" onChange="getValue(this.value)">
													<option value=""></option>
													<option value="1"
														<c:if test="${pd.usercategory_id==1}">selected</c:if>>普通会员</option>
											</select>
											</td>  -->


										</tr>
										<tr>
											<td style="width:75px;text-align: right;padding-top: 13px;">团购组:</td>
											<td><select class="chosen-select form-control"
												name="USERCATEGORY_ID2" id="CHILD" data-placeholder="请选择团购组"
												style="vertical-align:top;" style="width:98%;">
													<option value=""></option>

											</select></td>
										</tr>
										<tr>
											<td style="width:75px;text-align: right;padding-top: 13px;">个人优惠比例:</td>
											<td><input type="text" name="PROPORTION" id="PROPORTION" value="${pd.proportion}" maxlength="255" placeholder="这里输入个人优惠比例" title="个人优惠比例" style="width:98%;"/>
											</td>

										</tr>
										
										<tr>
											<td style="width:75px;text-align: right;padding-top: 13px;">科室病种:</td>

											<td><input type="text" name=SECTION id="SECTION"
												value="${pd.section}" maxlength="255" placeholder="请输入科室病种"
												style="width:98%;" />
											</td>

										</tr>
							
										<tr>
											<td style="width:75px;text-align: right;padding-top: 13px;">邮箱:</td>

											<td><input type="text" name="EMAIL" id="EMAIL"
												value="${pd.email}" maxlength="255" placeholder="这里输入邮箱"
												style="width:98%;" />
											</td>

										</tr>
<tr>
											<td style="width:75px;text-align: right;padding-top: 13px;">备注:</td>

											<td><input type="text" name="REMARK" id="REMARK"
												value="${pd.remark}" maxlength="255" placeholder="这里输入备注"
												style="width:98%;" />
											</td>

										</tr>


										<tr>
											<td style="text-align: center;" colspan="10"><a
												class="btn btn-mini btn-primary" onclick="save();">保存</a> <a
												class="btn btn-mini btn-danger"
												onclick="top.Dialog.close();">取消</a></td>
										</tr>
									</table>
								</div>
								<div id="zhongxin2" class="center" style="display:none">
									<br />
									<br />
									<br />
									<br />
									<br />
									<img src="static/images/jiazai.gif" /><br />
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

			
			if ($("#NAME").val() == "") {
				$("#NAME").tips({
					side : 3,
					msg : '请输入姓名',
					bg : '#AE81FF',
					time : 2
				});
				$("#NAME").focus();
				return false;
			}
			/* if ($("#USERNAME").val() == "") {
				$("#USERNAME").tips({
					side : 3,
					msg : '请输入用户名',
					bg : '#AE81FF',
					time : 2
				});
				$("#USERNAME").focus();
				return false;
			} */
			
			if ($("#SEX").val() == "") {
				$("#SEX").tips({
					side : 3,
					msg : '请输入性别',
					bg : '#AE81FF',
					time : 2
				});
				$("#SEX").focus();
				return false;
			}
			
			if ($("#PHONE").val() == "") {
				$("#PHONE").tips({
					side : 3,
					msg : '请输入电话号码',
					bg : '#AE81FF',
					time : 2
				});
				$("#PHONE").focus();
				return false;
			}
			/* if ($("#IDCODE").val() == "") {
				$("#IDCODE").tips({
					side : 3,
					msg : '请输入身份证号',
					bg : '#AE81FF',
					time : 2
				});
				$("#IDCODE").focus();
				return false;
			} */
			/*
			if ($("#FIRST_DATE").val() == "") {
				$("#FIRST_DATE").tips({
					side : 3,
					msg : '请输入初诊日期',
					bg : '#AE81FF',
					time : 2
				});
				$("#FIRST_DATE").focus();
				return false;
			}*/
			if ($("#PARENT_ID").val() == "0") {
				$("#PARENT_ID").tips({
					side : 3,
					msg : '请选择会员类型',
					bg : '#AE81FF',
					time : 2
				});
				$("#PARENT_ID").focus();
				return false;
			}
			/*if ($("#SECTION").val() == "") {
				$("#SECTION").tips({
					side : 3,
					msg : '请输入科室/病种',
					bg : '#AE81FF',
					time : 2
				});
				$("#SECTION").focus();
				return false;
			}
			*/
			/* 	if($("#GROUP_ID").val()==""){
					$("#GROUP_ID").tips({
						side:3,
			            msg:'请输入团购组',
			            bg:'#AE81FF',
			            time:2
			        });
					$("#GROUP_ID").focus();
				return false;
				}  */
			if($("#PROPORTION").val()==""){
				$("#PROPORTION").tips({
					side:3,
		            msg:'请输入优惠比例',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#PROPORTION").focus();
			return false;
			}
			else {
				$("#Form").submit();
				$("#zhongxin").hide();
				$("#zhongxin2").show();
			}
		}

		$(function() {
			//日期框
			$('.date-picker').datepicker({
				autoclose : true,
				todayHighlight : true
			});
		});

		function getValue(value){
		
			var sel2 = $("#CHILD");
			var selVal = $("#CHILD").val();
			var parent_id = $("#PARENT_ID").val();
			document.getElementById("CHILD").innerHTML = "";
			
			$.ajax({
				dataType: "json",
				data: {
					PARENT_ID: parent_id
				},
				type: "post",
				
				url: "usercategory/queryChildByParentID",
				success: function(data){
					// var data = eval('(' + data + ')');  
					for (var i = 0; i < data.length; i++) {
					
						var col = data[i];
						if (selVal == data[i].USERCATEGORY_ID) {
							sel2.append("<option value=" + data[i].USERCATEGORY_ID + " selected=\"true\">" +
							data[i].CATEGORY_NAME +
							"</option>");
						}
						else {
							sel2.append("<option value=" + data[i].USERCATEGORY_ID + ">" +
							data[i].CATEGORY_NAME +
							"</option>");
						};
											};
					
									},
				error: function(data){
				}
			});
		

      
			var sel = $("#PROPORTION");
			var selVal = $("#PROPORTION").val();
			var parent_id = $("#PARENT_ID").val();
			document.getElementById("PROPORTION").innerHTML = "";

			
					$.ajax({
						dataType : "json",
						data : {
							PARENT_ID : parent_id
						},
						type : "post",

						url : "member/findProportion",
						success : function(data) {

							sel
									.append("<option value=" + data[0].proportion + " selected=\"true\">"
											+ data[0].proportion + "</option>");

						},
						error : function(data) {
						}
					});

		}
		
		
		function findCityByPhone(e){
			var PHONE = $("#PHONE").val();
			$.ajax({
				type:'post',
				dataType : 'json',
				data:{
					PHONE : PHONE
				},
				url:"member/findCityByPhone",
				success:function(data) {
				if(data[1].isExist=="true"){
					alert("手机号码已存在，请重新输入！");
					$("#PHONE").val("");
				}
					$("#CITY").val(data[0].mobile_area);
				}
			});
		};
	</script>
</body>
</html>