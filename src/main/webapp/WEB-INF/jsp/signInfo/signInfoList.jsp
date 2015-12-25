<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/common.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jsp/signInfo_signInfoList.js"></script>
	<div class="easyui-layout" style="width: 100%;height:100%;overflow: hidden;">
		<div data-options="region:'north',border:false" >
			<input id="signInfo_signInfoList_id" name="id" type="hidden" value="${id}">
			<table class="table" style="width: 100%">
				<tr>
					<td colspan="2" style="text-align: center;">文件签收情况</td>
				</tr>
				<tr>
					<th style="width: 100px;">公文标题:</th>
					<td >${documentTitle }</td>
				</tr>
				<tr>
					<th style="width: 100px;">发布时间:</th>
					<td >${createdatetime }</td>
				</tr>
				<tr>
					<th style="width: 100px;">备注:</th>
					<td >${description }</td>
				</tr>
				<tr>
					<th style="width: 100px;">附件列表:</th>
					<td >
					<s:iterator value="myFiles">
	                <a onclick="fileDown(<s:property value="id" />);" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'ext-icon-arrow_down'"><s:text name="fileName"></s:text></a>
	                <br>
	            </s:iterator>
	            </td>
				</tr>
			</table>
		</div>
		<div data-options="region:'center',border:false">
			<table id="signInfo_signInfoList_grid" ></table>
		</div>
	</div>
</body>
</html>