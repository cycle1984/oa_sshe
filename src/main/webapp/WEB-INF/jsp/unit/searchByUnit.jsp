<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/common.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>选择单位查询页面</title>
</head>
<body>
	<h2>Basic Tabs</h2>
	<p>Click tab strip to swap tab panel content.</p>
	<div style="margin:20px 0 10px 0;"></div>
	<div class="easyui-tabs" style="height:250px">
		<div title="群组列表选择" style="padding:10px">
			<ul id="document_searchByUnit_tree">
			</ul>
		</div>
		<div title="My Documents" style="padding:10px">
		</div>
		<div title="Help" data-options="iconCls:'icon-help',closable:true" style="padding:10px">
			This is the help content.
		</div>
	</div>
	
	<script type="text/javascript">
			$('#document_searchByUnit_tree').tree({
				url : '${pageContext.request.contextPath}/unit_getUnitTree.action',
				parentField : 'pid'
			});
	</script>
	
</body>
</html>