<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/common.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>系统主页</title>
	<script type="text/javascript">
		$(function(){
			 $('#home_index_west_div').panel({
				 href:'home_west.action'
			 });
			 $('#home_index_main_div').panel({
				 href:'home_main.action'
			 });
			
		});
	</script>
</head>
<body>
	<div id="home_index" class="easyui-layout" style="width:90%;height:100%;margin:0 auto; ">   
	    <div data-options="region:'north',split:true" style="height:98px;"><jsp:include page="north.jsp"></jsp:include></div>   
	    <div data-options="region:'south',split:true" style="height:50;"></div>   
	    <div id="home_index_west_div" data-options="region:'west',split:true" style="width:200px;padding: 1px"></div>   
	    <div id="home_index_main_div" data-options="region:'center'" style="background:#eee;padding: 1px"></div>   
	</div> 
</body>
</html>