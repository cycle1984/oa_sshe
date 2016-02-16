<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<title>发布</title>
<meta name="renderer" content="webkit">
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/easyui1.4.4/jquery.min.js"></script>
	
</head>
<body style="width: 95%;height: 95%;">
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/xheditor-1.2.2/xheditor-1.2.2.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/xheditor-1.2.2/xheditor_lang/zh-cn.js"></script>
<%-- 	<script charset="utf-8" src="${pageContext.request.contextPath}/js/kindeditor-4.1.7/kindeditor.js"></script> --%>
<%-- 	<script charset="utf-8" src="${pageContext.request.contextPath}/js/kindeditor-4.1.7/lang/zh_CN.js"></script> --%>
	<form id="news_saveUI_form" method="post">
		<input type="hidden" id="news_saveUI_form_id" name="id">
		<br>
		标题：<input id="news_saveUI_title" name="title" type="text" style="width: 550px">
		<br>
		<br>
		<textarea id="news_saveUI_content" name="content"  style="width:800px;height:400px;"></textarea>
	</form>
	<script type="text/javascript">
		$(function(){
			
		})
		$('#news_saveUI_content').xheditor({
			html5Upload:false,upMultiple:'1',
			upLinkUrl:"upload.php",
			upLinkExt:"zip,rar,txt",
			upImgUrl:"news_uploadImg.action",
			upImgExt:"jpg,jpeg,gif,png",
			upFlashUrl:"upload.php",
			upFlashExt:"swf",
			upMediaUrl:"upload.php",
			upMediaExt:"avi"});
	</script>
</body>
</html>