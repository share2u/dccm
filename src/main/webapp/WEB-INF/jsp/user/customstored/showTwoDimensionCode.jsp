<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<!-- jsp文件头和头部 -->
<%@ include file="../../system/index/top.jsp"%>

	<!-- 页面底部js¨ -->
	<%@ include file="../../system/index/foot.jsp"%>

	<!-- ace scripts -->
	<script src="static/ace/js/ace/ace.js"></script>
	
	
	
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>用户二维码</title>
</head>
<body>
<img src="${pageContext.request.contextPath}/uploadFiles/code/twoDimensionCode.png" onClick="window.print()"/>
</body>
<script type="text/javascript">
$(top.hangge());//关闭加载状态
		//检索
		function tosearch(){
			top.jzts();
			$("#Form").submit();
		}
</script>
</html>