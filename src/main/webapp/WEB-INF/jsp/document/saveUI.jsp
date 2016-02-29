<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/common.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>发布公文</title>
</head>
<body>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jsp/document_saveUI.js"></script>
	<div>
		<form id="document_saveUI_form" method="post">
			<table>
				<tr>
					<th style="width: 100;font-size: 13px;">接收单位</th>
					<td id="document_saveUI_unit_td" ><input id="document_saveUI_unitCombotree" style="width: 550px;height: 100px;" class="easyui-textbox" data-options="required: true"></input></td>
				</tr>
				<tr>
                    <th style="width: 100;font-size: 13px;">级别</th>
                    <td>
                    <input id="document_saveUI_level" name="level" class="easyui-combobox" data-options="width:153,value:'加急',editable:false,valueField: 'label',textField: 'value',data:[{
                    	label:'特提',
                    	value:'特提'
                    },{
                    	label:'特急',
                    	value:'特急'
                    },{
                    	label:'加急',
                    	value:'加急'
                    },{
                    	label:'平急',
                    	value:'平急'
                    }]"></input> 
                    </td>
                    
                </tr>
                <tr>
                	<th style="font-size: 13px;">发文字号</th><td><input id="document_saveUI_docNum" name="docNum" data-options="required:true,validType:'maxLength[25]'" class="easyui-textbox" value="new date()"></td>
                </tr>
                <tr>
                   <th style="width: 100;font-size: 13px;">公文标题</th>
                   <td colspan="3">
                   <input id="document_saveUI_documentTitle" name="documentTitle" class="easyui-textbox" data-options="required:true,validType:'maxLength[50]'" style="width:100%;"> 
                   </td>
               </tr>
               <tr>
                   <th style="width: 100;font-size: 13px;">备注</th>
                   <td ><input id="document_saveUI_description" class="easyui-textbox" data-options="multiline:true,height:40"  style="width:100%;" name="description"> </td>
               </tr>
               <tr>
                   <th style="width: 100;font-size: 13px;">文件上传</th>
                   <td ><div id="container">
                       <a id="pickfiles" href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search'">选择文件</a><span style="font-size: 13px;">(最大20Mb)</span>
                       <div id="filelist">您的浏览器没有安装Flash插件,或不支持HTML5!</div>
                   </div> </td>
               </tr>
			</table>
		</form>
	</div>
</body>
</html>