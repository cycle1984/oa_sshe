<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/common.jsp"%>
<!DOCTYPE html >
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>签收信息详情页</title>
</head>
<body>
	<table style="width: 100%;" class="table">
	        <tr>
	            <td style="width: 80px;">标题</td>
	            <td>${document.documentTitle } </td>
	        </tr>
	        <tr>
	            <td style="width: 80px;">发布日期</td>
	            <td><s:date name="document.createdatetime" format="yyyy-MM-dd HH:mm" /></td>
	        </tr>
	        <tr>
	            <td style="width: 80px;">发布单位</td>
	            <!-- <td><s:text name="doucment.publishCyUnit.name"></s:text> </td> -->
	            <td>${publishUnitName }</td>
	        </tr>
	        <tr>
	            <td style="width: 80px;">发布人</td>
	            <td><s:text name="document.publishUserName"></s:text></td>
	        </tr>
	        <tr>
	            <td style="width: 80px;">签收时间</td>
	            <td><s:date name="signDate" format="yyyy-MM-dd HH:mm" /></td>
	        </tr>
	        <tr>
	            <td style="width: 80px;">签收人</td>
	            <td>${signUserName }</td>
	        </tr>
	        <tr>
	            <td style="width: 80px;">签收IP</td>
	            <td>${ip }</td>
	        </tr>
	        <tr>
	            <td style="width: 80px;">备注</td>
	            <td>${doucment.description }</td> 
	        </tr> 
	        <tr>
	            <td style="width: 80px;">附件列表</td>
	            <td><s:iterator value="document.myFiles">
	               <!-- <a href="cyFile_fileDown.action?fileID=<s:property value="fileID" />"></a> --> 
	                <a onclick="fileDown(<s:property value="id" />);" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'ext-icon-arrow_down'"><s:text name="fileName"></s:text></a>
	                <br>
	            </s:iterator> </td>
	        </tr>
	        
	    </table>
</body>
</html>