<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<base href="<%=basePath%>">

<title>门店列表</title>

<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=no">
<link rel="stylesheet" href="static/css/wechat/weui.min.css">
<link type="text/css" rel="stylesheet"
	href="static/css/wechat/weChatQueryStores.css">
<script type="text/javascript" src="static/js/jquery-1.7.2.js"></script>
<script type="text/javascript" src="static/js/weChatQueryStores.js"></script>
<script class="searchbar js_show" type="text/javascript">
	$(function() {
		var $searchBar = $('#searchBar'), $searchResult = $('#searchResult'), $searchText = $('#searchText'), $searchInput = $('#searchInput'), $searchClear = $('#searchClear'), $searchCancel = $('#searchCancel');
		function getStoresBySome(value) {
			$
					.ajax({
						url : "weChatStore/getStoresBySome",
						data : {
							queryParam : value
						},
						type:"POST",
						success : function(data) {
							$("#storeList").empty();
							for ( var i = 0; i < data.length; i++) {
								var store = data[i];
								console.log(store);
								$("#storeList")
										.append(
													"<a class=\"weui-cell weui-cell_access\" href=\"weChatStore/getStoreDetailByStoreId/"+store.storeId+"\">"
														+"<div class=\"weui-cell__hd\">"
														+"<img src=\"static/images/wechat/mengdian.png\" alt=\"\" style=\"width:20px;margin-right:5px;display:block\"></div>"
														+"<div class=\"weui-cell__bd weui-cell_primary\">"
														+"<p>"+store.storeName+"</p>"
														+"</div> <span class=\"weui-cell__ft\"></span> </a>"
														+ "<a class=\"weui-cell weui-cell_access\" href=\"weChatStore/getStoreDetailByStoreId/"+store.storeId+"\">"
														+ "<div class=\"weui-cell__hd\">"
														+ "<img src=\"${pageContext.request.contextPath}/uploadFiles/storeimg/"+store.storeSmallImg+"\"alt=\"门店小图\" style=\"width:80px;height:80px;margin-right:5px;display:block\">"
														+ "</div>"
														+ "<div class=\"weui-cell__bd\">"
														+ "<p>地址:"
														+ store.address
														+ "</p>" + "<p>营业时间："
														+ store.workTime
														+ "</p>"
														+ "</div> </a>");
							}
						}
					});
		}

		function hideSearchResult() {
			$searchResult.hide();
			$searchInput.val('');
		}
		function cancelSearch() {
			hideSearchResult();
			$searchBar.removeClass('weui-search-bar_focusing');
			$searchText.show();
		}

		$searchText.on('click', function() {
			$searchBar.addClass('weui-search-bar_focusing');
			$searchInput.focus();
		});
		$searchInput.on('blur', function() {
			if (!this.value.length)
				cancelSearch();
		}).on('input', function() {
			if (this.value.length) {
				getStoresBySome(this.value);
			} else {
				$searchResult.hide();
			}
		});
		$searchClear.on('click', function() {
			hideSearchResult();
			$searchInput.focus();
		});
		$searchCancel.on('click', function() {
			cancelSearch();
			$searchInput.blur();
		});

	});
</script>

</head>
<body>

	<div style="position: fixed;width:100%; top:0;z-index: 9999;">
		<div class="weui-search-bar" id="searchBar"
			style="height:50px;z-index:4;">
			<!--  <div style="width: 50px;" class="Regional"><span>位置</span></div> -->
			<form class="weui-search-bar__form">
				<div class="weui-search-bar__box">
					<i class="weui-icon-search"></i> <input
						class="weui-search-bar__input" id="searchInput" placeholder="搜索"
						required="" type="search"> <a href="javascript:"
						class="weui-icon-clear" id="searchClear"></a>
				</div>
				<label class="weui-search-bar__label" id="searchText"
					style="transform-origin: 0px 0px 0px; opacity: 1; transform: scale(1, 1);">
					<i class="weui-icon-search"></i> <span>请输入关于门店的名称、地址</span> </label>
			</form>
			<a href="javascript:" class="weui-search-bar__cancel-btn"
				id="searchCancel">取消</a>
		</div>
		<div class="weui-cells searchbar-result" id="searchResult"
			style="transform-origin: 0px 0px 0px;margin-top:0px; opacity: 1; transform: scale(1, 1); display: none;">
		</div>
	</div>
	</div>
	</div>
	<div class="weui-cells" id="storeList"
		style="float: none;margin-top: 55px;">
		<c:forEach items="${stores}" var="store">
			<a class="weui-cell weui-cell_access"
				href="weChatStore/getStoreDetailByStoreId/${store.storeId}">
				<div class="weui-cell__hd">
					<img src="static/images/wechat/mengdian.png" alt=""
						style="width:20px;margin-right:5px;display:block">
				</div>
				<div class="weui-cell__bd weui-cell_primary">
					<p style="font-weight:100">${store.storeName}</p>
				</div> <span class="weui-cell__ft"></span> </a>
			<a class="weui-cell weui-cell_access"
				href="weChatStore/getStoreDetailByStoreId/${store.storeId}">
				<div class="weui-cell__hd">
					<img src="uploadFiles/storeimg/${store.storeSmallImg}" alt="门店小图"
						style="width:80px;height:80px;margin-right:5px;display:block">
				</div>
				<div class="weui-cell__bd">
					<p>地址：${store.address}</p>
					<p>营业时间：${store.workTime}</p>
				</div> </a>
		</c:forEach>
		<!-- <p style="color:#222;padding:0.2rem 0; text-align: center;" onclick="loadMoreElement()">上拉加载更多</p> -->
	</div>
</body>
</html>
