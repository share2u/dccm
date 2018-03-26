<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
<head>
    <base href="<%=basePath%>">
    <%@ include file="../system/index/top.jsp"%>
</head>
<body>
${name}----- 仪表盘详情
1、新增图表的按钮--跳到create界面,带参数namebbb
2、编辑旧图表的功能--跳到update界面，带参数,name,optionID
<div>
    ${s}
</div>
<!-- 页面底部js¨ -->
<%@ include file="../system/index/foot.jsp"%>
<script type="text/javascript">
    $(top.hangge());
</script>
</body>
</html>
