<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page import="cycle.oa_sshe.domain.User"%>
<%
    User user = (User)session.getAttribute("userSession");
	
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>公文接收列表</title>
</head>
<body>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jsp/signInfo_historyDocumentAcceptList.js"></script>
	<!-- 菜单栏 -->
	<div id="signInfo_historyDocumentAcceptList_toolbar" style="display: none;">
		 <table>
			<tr>
				<td><form id="signInfo_historyDocumentAcceptList_searchForm" style="margin:0px;"><!--style="margin:0px;"可以处理加form表单后，tr间隙变大的问题  -->
						<table style="font-size: 13px;">
							<tr>
								<td>公文标题(可模糊查询)</td>
								<td><input name="QUERY_t#document.documentTitle_S_LK" style="width: 120px; " class="easyui-textbox"/></td>
								<s:if test="%{#session.userSession.loginName=='admin'}">
									<td>发文单位</td>
									<td><select id="signInfo_historyDocumentAcceptList_searchForm_unit" name="QUERY_t#document.publishUnit.id_I_EQ" style="width: 80px" ></select></td>
								</s:if>
								<td>发布时间</td>
								<td><input type="text" name="QUERY_t#document.createdatetime_D_GE" class="easyui-datetimebox" data-options="showSeconds:false,editable:false" style="width: 120px;"/>-<input name="QUERY_t#document.createdatetime_D_LE" type="text" class="easyui-datetimebox"  data-options="showSeconds:false,editable:false" style="width: 120px;"/></td>
								<td><a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'ext-icon-zoom',plain:true" onclick="$('#signInfo_historyDocumentAcceptList_grid').datagrid('load',sy.serializeObject($('#signInfo_historyDocumentAcceptList_searchForm')));">过滤</a><a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'ext-icon-zoom_out',plain:true" onclick="$('#signInfo_historyDocumentAcceptList_searchForm input').val('');$('#signInfo_historyDocumentAcceptList_grid').datagrid('load',{});">重置过滤</a></td>
							</tr>
						</table>
					</form></td>
			</tr>
		</table>
	</div>
	<table id="signInfo_historyDocumentAcceptList_grid"></table>
	
	<!-- 文件签收窗口 -->
	<div id="signInfo_historyDocumentAcceptList_acceptDialog" style="display: none;padding: 10px;overflow: hidden;" >
	    <form  method="post" id="signInfo_historyDocumentAcceptList_acceptDialog_form" style="margin:0px;">
	    <input id="signInfo_historyDocumentAcceptList_acceptDialog_id" name="signInfo_historyDocumentAcceptList_acceptDialog_id" type="hidden">
	    <table style="width: 100%; height: 100%;" class="table">
	        <tr>
	            <th style="text-align: center;">签收人</th><th style="text-align: center;">签收密码</th>
	        </tr>
	        <tr>
	            <td style="text-align: center;"><input name="name" type="text" value="<%=user.getName() %>" class="easyui-textbox" data-options="required:true,iconCls:'icon-man',editable:false"></td>
	            <td style="text-align: center;"><input id="accept_pwd" name="pwd" type="password" class="easyui-textbox" data-options="required:true,iconCls:'icon-lock'"></td>
	        </tr>
	    </table>
	    </form>
	</div>
</body>
</html>