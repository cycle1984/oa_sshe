<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/common.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>选择单位查询页面</title>
</head>
<body>
	<input id="unit_searchByUnit_unit" class="easyui-validatebox" style="width: 100%">
	<div class="easyui-tabs" style="height:400px">
		<div title="群组列表" style="padding:10px">
			<ul id="document_searchByUnit_tree">
			</ul>
		</div>
		<div title="所有单位列表" >
			<table id="unit_searchByUnit_table"></table>
		</div>
		<!-- <div title="按热度排序"  style="padding:10px">
			开发中。。。
		</div> -->
	</div>
	
	<script type="text/javascript">
			$('#document_searchByUnit_tree').tree({
				url : '${pageContext.request.contextPath}/unit_getUnitTree2.action',
				parentField : 'pid',
				onClick:function(node){//因为onCheck和onClick捆绑在一起，所以2个需要一样的设置
					
			    	//var node = $('#document_searchByUnit_tree').tree('getSelected');//获得选择的节点
			    	var text = '';//文本框内容
		    		var isLeaf = $('#document_searchByUnit_tree').tree('isLeaf',node.target);
					if(isLeaf){
						text=node.text;
					}else{
						$(this).tree(node.state === 'closed' ? 'expand' : 'collapse', node.target);  
				        node.state = node.state === 'closed' ? 'open' : 'closed';
					}
			    	$('#unit_searchByUnit_unit').val(text);
			    },
			    onSelect:function(node){
			    	var isLeaf = $('#document_searchByUnit_tree').tree('isLeaf',node.target);
					if(isLeaf){
					}else{
						$(this).tree(node.state === 'closed' ? 'expand' : 'collapse', node.target);  
				        node.state = node.state === 'closed' ? 'open' : 'closed';
					}
			    }
			});
			$(function(){
				$("#unit_searchByUnit_table").datalist({
					url: '${pageContext.request.contextPath}/unit_listByInitial.action',
					border:false,
					columns:[[{
						field : 'name'
					}]],
					onSelect:function(index, row){
						$('#unit_searchByUnit_unit').val(row.name);
					}
				});
			});
	</script>
	
</body>
</html>