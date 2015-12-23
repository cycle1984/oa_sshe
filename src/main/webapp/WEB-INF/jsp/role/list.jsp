<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>权限组列表</title>
</head>
<body>
	<!-- 导入页面相关js -->
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jsp/roleList.js"></script>

	<!-- grid表格 -->
	<table id="role_list_grid"></table>
	
	<!-- 工具面板 -->
	<div id="role_list_toolbar" style="border: 0px;display: none;">
		<table>
			<tr>
				<td>
					<form id="role_list_toolbar_form" method="post" style="margin:0px;"><!--style="margin:0px;"可以处理加form表单后，tr间隙变大的问题  -->
						<table style="font-size: 13px;">
							<tr>
								<td >名称(可模糊查询)</td>
								<td><input name="QUERY_t#name_S_LK" style="width: 100px; " class="easyui-textbox"/></td>
								<td><a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'ext-icon-zoom',plain:true" onclick="roleGrid.datagrid('load',sy.serializeObject($('#role_list_toolbar_form')));">过滤</a><a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'ext-icon-zoom_out',plain:true" onclick="$('#role_list_toolbar_form').form('clear');roleGrid.datagrid('load',{});">重置过滤</a></td>
							</tr>
						</table>
					</form>
				</td>
			</tr>
			<tr>
				<td>
					<table>
						<tr>
							<td><s:a onclick="addFunRole();" href="javascript:void(0);" cssClass="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" title="权限组添加">添加</s:a></td>
							<td><s:a onclick="editFunRole();" href="javascript:void(0);" cssClass="easyui-linkbutton" data-options="plain:true,iconCls:'icon-edit'" title="权限组修改">修改</s:a></td>
							<td><s:a onclick="deleteFunRole();" href="javascript:void(0);" cssClass="easyui-linkbutton" data-options="plain:true,iconCls:'icon-remove'" title="权限组删除">删除</s:a></td>
							<td><s:a onclick="setFunMyResource();" href="javascript:void(0);" cssClass="easyui-linkbutton" data-options="plain:true,iconCls:'ext-icon-group_key'" title="设置权限">设置权限</s:a></td>
							<td><a onclick="roleGrid.datagrid('reload');" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-reload'">刷新</a></td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
	</div>
	
	<jsp:include page="setMyResourceUI.jsp"></jsp:include>
</body>
</html>