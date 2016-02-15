<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>发布</title>
	<style>
			form {
				margin: 0;
			}
			textarea {
				display: block;
			}
		</style>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/js/kindeditor-4.1.10/themes/default/default.css" />
		<script charset="utf-8" src="${pageContext.request.contextPath}/js/kindeditor-4.1.10/kindeditor-min.js"></script>
		<script charset="utf-8" src="${pageContext.request.contextPath}/js/kindeditor-4.1.10/lang/zh_CN.js"></script>
		<script>
			KindEditor.ready(function(K) {
				K.create('textarea[name="content"]', {
					autoHeightMode : true,
					afterCreate : function() {
						this.loadPlugin('autoheight');
					}
				});
			});
		</script>
</head>
<body>
	<h3>自动调整高度</h3>
	<form>
		<textarea name="content" style="width:800px;height:200px;"></textarea>
	</form>
</body>
</html>