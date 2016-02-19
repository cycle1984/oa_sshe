<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>已发信息资讯列表</title>
</head>
<body>
	<!-- 导入页面相关js -->
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jsp/news_list.js"></script>
	<!-- grid表格 -->
	<table id="news_list_grid"></table>
	<div id="news_list_toolbar" style="border: 0px;display: none;">
		<form id="news_list_toolbar_form" style="margin:0px;"><!--style="margin:0px;"可以处理加form表单后，tr间隙变大的问题  -->
			<table style="font-size: 13px;">
				<tr>
					<td>信息标题</td>
					<td><input name="QUERY_t#title_S_LK" style="width: 100px; " class="easyui-textbox" data-options="prompt:'可模糊查询'"/></td>
					<td>发布单位</td>
					<!--<td><select class="easyui-combobox" id="cyNews_cyNewsList_searchForm_cyUnit" name="QUERY_t#cyUnit.id_L_EQ" style="width: 100px" data-options="valueField:'id',textField:'name',url:'cyUnit_findAllCyUnit.action'"></select></td> -->
					<td id="news_list_toolbar_form_unit_td"><input id="news_list_toolbar_form_unit" name="QUERY_t#unit.name_S_LK" class="easyui-textbox" data-options="prompt:'可模糊查询'" style="width: 100px"></input></td>
					<td>作者</td>
					<td><input name="QUERY_t#userName_S_LK" style="width: 100px; " class="easyui-textbox" data-options="prompt:'可模糊查询'"/></td>
					<td>创建时间</td>
					<td><input type="text" name="QUERY_t#createTime_D_GE" class="easyui-datetimebox" data-options="showSeconds:false,editable:false" style="width: 135px;"/>-<input name="QUERY_t#createTime_D_LE" type="text" class="easyui-datetimebox"  data-options="showSeconds:false,editable:false" style="width: 135px;"/></td>
					<td><a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'ext-icon-zoom',plain:true" onclick="newsGrid.datagrid('load',sy.serializeObject($('#news_list_toolbar_form')));">过滤</a><a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'ext-icon-zoom_out',plain:true" onclick="$('#news_list_toolbar_form').form('clear');newsGrid.datagrid('load',{});">重置过滤</a></td>
				</tr>
			</table>
		</form>
	</div>
</body>
</html>