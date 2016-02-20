<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>main内容显示区域</title>
</head>
<body>
	<script type="text/javascript">
	    function addTab(opts){
	    
	    	var t = $('#home_main_tabs');
	    	if(t.tabs('exists',opts.title)){
	    		t.tabs('select',opts.title);
	    	}else{
	    		t.tabs('add',opts);
	    	}
	    	
	    }
	    
	</script>
	<div id="home_main_tabs" class="easyui-tabs" data-options="fit:true,plain:false">
		<s:if test="%{#session.userSession.loginName!='admin'}"><!-- 如果不是超级管理员，则显示收文列表 -->
		    <div title="待办公文列表">
		        <jsp:include page="../signInfo/nDocumentAcceptList.jsp" ></jsp:include>
		    </div>
		    <div title="已办公文列表">
		        <jsp:include page="../signInfo/yDocumentAcceptList.jsp" ></jsp:include>
		    </div>
		    <s:if test='%{#session.userSession.hasCyResourceByTitle("发文管理")}'>
		    	<div title="发文管理">
				</div>
			</s:if>
	    </s:if>
	    <s:else>
		    <div title="发文管理">
		    	<jsp:include page="../document/publishList.jsp"></jsp:include>
			</div>
		</s:else>
	</div>
</body>
</html>