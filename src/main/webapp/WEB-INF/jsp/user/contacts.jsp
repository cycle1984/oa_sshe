<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>通讯录</title>
</head>
<body>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jsp/user_contacts.js"></script>
	<table id="user_contacts_table"></table>
	<!-- 工具面板 -->
	<div id="user_contacts_toolbar" style="border: 0px;display: none;">
		<table>
			<tr>
				<td>
					<form id="user_contacts_toolbar_form" style="margin:0px;"><!--style="margin:0px;"可以处理加form表单后，tr间隙变大的问题  -->
						<table style="font-size: 13px;">
							<tr>
								<td >姓名</td>
								<td><input name="QUERY_t#name_S_LK" style="width: 100px; " class="easyui-textbox" data-options="prompt:'可模糊查询'"/></td>
								<td>所属单位</td>
								<!--<td><select class="easyui-combobox" id="cyUser_cyUserList_searchForm_cyUnit" name="QUERY_t#cyUnit.id_L_EQ" style="width: 100px" data-options="valueField:'id',textField:'name',url:'cyUnit_findAllCyUnit.action'"></select></td> -->
								<td id="user_contacts_toolbar_form_unit_td"><input id="user_contacts_toolbar_form_unit" name="QUERY_t#unit.name_S_LK" class="easyui-textbox" style="width: 100px" data-options="prompt:'可模糊查询'"></input></td>
								<td><a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'ext-icon-zoom',plain:true" onclick="contactsGrip.datagrid('load',sy.serializeObject($('#user_contacts_toolbar_form')));">过滤</a><a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'ext-icon-zoom_out',plain:true" onclick="$('#user_contacts_toolbar_form').form('clear');contactsGrip.datagrid('load',{});">重置过滤</a></td>
							</tr>
						</table>
					</form>
				</td>
			</tr>
			<tr>
			</tr>
		</table>
	</div>
</body>
</html>