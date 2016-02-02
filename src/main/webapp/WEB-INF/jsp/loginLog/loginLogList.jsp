<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/common.jsp"%>
<!DOCTYPE html >
<html>
<head>
<title>用户登录日志</title>
</head>
<body>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jsp/loginLog_loginLogList.js"></script>
    <table id="loginLog_loginLogList_grid"></table>
    <div id="loginLog_loginLogList_toolbar" style="display: none;">
    	<table>
			<tr>
				<td><s:form id="loginLog_loginLogList_searchForm" style="margin:0px;">
						<table style="font-size: 13px;">
							<tr>
								<td>用户姓名</td>
								<td><input name="QUERY_t#userName_S_LK" style="width: 150px;" class="easyui-textbox"/></td>
								<td>单位名称</td>
								<td><input name="QUERY_t#unitName_S_LK" style="width: 150px;" class="easyui-textbox"/></td>
								<td>登陆时间</td>
								<td><input type="text" name="QUERY_t#logDate_D_GE" class="easyui-datetimebox" data-options="showSeconds:false,editable:false" style="width: 120px;"/>-<input name="QUERY_t#logDate_D_LE" type="text" class="easyui-datetimebox"  data-options="showSeconds:false,editable:false" style="width: 120px;"/></td>
								<td><a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'ext-icon-zoom',plain:true" onclick="$('#loginLog_loginLogList_grid').datagrid('load',sy.serializeObject($('#loginLog_loginLogList_searchForm')));">过滤</a><a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'ext-icon-zoom_out',plain:true" onclick="$('#loginLog_loginLogList_searchForm input').val('');$('#loginLog_loginLogList_grid').datagrid('load',{});">重置过滤</a></td>
							</tr>
						</table>
					</s:form></td>
			</tr>
			<tr>
				<td>
					<table>
						<tr>
							<td><s:a onclick="delFunCyLog();" href="javascript:void(0);" cssClass="easyui-linkbutton" data-options="plain:true,iconCls:'icon-remove'" title="登陆日志删除">删除</s:a></td>
							<td><a onclick="$('#loginLog_loginLogList_grid').datagrid('reload');" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-reload'">刷新</a></td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
    </div>
</body>
</html>