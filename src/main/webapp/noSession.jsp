<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>登录超时</title>
<!-- easyUI组件 -->
<script type="text/javascript" src="${pageContext.request.contextPath}/js/easyui1.4.4/jquery.min.js"></script>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/js/easyui1.4.4/themes/default/easyui.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/js/easyui1.4.4/themes/icon.css" />
<script type="text/javascript" src="${pageContext.request.contextPath}/js/easyui1.4.4/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/easyui1.4.4/locale/easyui-lang-zh_CN.js"></script>
</head>
<body >
	<script type="text/javascript" charset="utf-8">
		try{parent.$.messager.progress('close');parent.sy.progressBar('close');}catch(e){}
		$(function(){
			/* var w = $(window).width()/2; 
			var h =  $(window).height()/2; */
			
			$('#nosession').window({    
				  width:500,    
				  height:150,
				  collapsible:false,
				  minimizable:false,
				  maximizable:false,
				  closable:false,
				  draggable:false,
				  resizable:false,
				  title: '用户登录超时提示',    
				  content:'<br><a href="user_loginUI.action">${msg}'+ '点击返回登陆</a>' 
				}); 
		})
		
		  

   


	</script>
<div id="nosession" style="text-align: center;font-size: 19px;"></div>
</body>
</html>