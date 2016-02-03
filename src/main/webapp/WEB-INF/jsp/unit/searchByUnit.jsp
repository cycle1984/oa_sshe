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
				parentField : 'pid',
				checkbox:true,
				onClick:function(node){//因为onCheck和onClick捆绑在一起，所以2个需要一样的设置
			    	var nodes = $('#document_searchByUnit_tree').tree('getChecked');//勾选的节点数
			    	var num=0;//收文的单位数
			    	var text = '';//文本框内容
			    	for(var i =0;i<nodes.length;i++){
			    		var isLeaf = $('#document_searchByUnit_tree').tree('isLeaf',nodes[i].target);
						if(isLeaf){
							num++;
							if(i!=nodes.length-1){
								text+=nodes[i].text+",";
							}else{
								text+=nodes[i].text+",      共"+num+"个单位";
							}
							
						}
			    	}
			    	$('#document_saveUI_unitCombotree').textbox('setText',text);//设置combotree文本框内容
			    }, 
			    onCheck:function(node){//因为onCheck和onClick捆绑在一起，所以2个需要一样的设置
			    	var nodes = $('#document_searchByUnit_tree').tree('getChecked');//勾选的节点数
			    	var num=0;//收文的单位数
			    	var text = '';
			    	
			    	for(var i =0;i<nodes.length;i++){
			    		var isLeaf = $('#document_searchByUnit_tree').tree('isLeaf',nodes[i].target);
						if(isLeaf){
							num++;
							if(i!=nodes.length-1){
								text+=nodes[i].text+",";
							}else{
								text+=nodes[i].text+",      共"+num+"个单位";
							}
							
						}
			    	}
			    	$('#document_saveUI_unitCombotree').textbox('setText',text);
			    }
			});
	</script>
	
</body>
</html>