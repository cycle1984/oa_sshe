<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<title>发布</title>
<meta name="renderer" content="webkit">
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
			
			//定义确定按钮
			var news_saveUI_submitForm = function($dialog,$newsGrid){
				console.info("bb");
			}
		</script>
		
</head>
<body>
	
	<form>
		<br>
		标题：<input name="title" type="text" style="width: 550px">
		<br>
		<br>
		<textarea name="content" style="width:400px;height:200px;"></textarea>
	</form>
</body>
</html>