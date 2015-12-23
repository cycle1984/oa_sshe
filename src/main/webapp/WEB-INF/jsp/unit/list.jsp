<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>机构列表</title>
</head>
<body>
	<!-- 导入页面相关js -->
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jsp/unitList.js"></script>

	<!-- grid表格 -->
	<table id="unit_list_grid"></table>
	
	<!-- 工具面板 -->
	<div id="unit_list_toolbar" style="border: 0px;display: none;">
		<table>
			<tr>
				<td>
					<form id="unit_list_toolbar_form" method="post" style="margin:0px;"><!--style="margin:0px;"可以处理加form表单后，tr间隙变大的问题  -->
						<table style="font-size: 13px;">
							<tr>
								<td >名称(可模糊查询)</td>
								<td><input name="QUERY_t#name_S_LK" style="width: 100px;" class="easyui-textbox"/></td>
								<td >办公室电话(可模糊查询)</td>
								<td><input name="QUERY_t#tel_S_LK" style="width: 100px; " class="easyui-textbox"/></td>
								<td >所属机构</td>
								<td><input name="QUERY_t#myGroup.id_I_EQ" style="width: 100px; " class="easyui-combobox" data-options="valueField:'id',textField:'name',url:'myGroup_findAll.action',editable:false"/></td>
								<td><a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'ext-icon-zoom',plain:true" onclick="unitGrid.datagrid('load',sy.serializeObject($('#unit_list_toolbar_form')));">过滤</a><a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'ext-icon-zoom_out',plain:true" onclick="$('#unit_list_toolbar_form').form('clear');unitGrid.datagrid('load',{});">重置过滤</a></td>
							</tr>
						</table>
					</form>
				</td>
			</tr>
			<tr>
				<td>
					<table>
						<tr>
							<td><s:a onclick="addFunUnit();" href="javascript:void(0);" cssClass="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" title="单位添加">添加</s:a></td>
							<td><s:a onclick="editFunUnit();" href="javascript:void(0);" cssClass="easyui-linkbutton" data-options="plain:true,iconCls:'icon-edit'" title="单位修改">修改</s:a></td>
							<td><s:a onclick="deleteFunUnit();" href="javascript:void(0);" cssClass="easyui-linkbutton" data-options="plain:true,iconCls:'icon-remove'" title="单位删除">删除</s:a></td>
							<td><a onclick="unitGrid.datagrid('reload');" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-reload'">刷新</a></td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
	</div>
</body>
</html>