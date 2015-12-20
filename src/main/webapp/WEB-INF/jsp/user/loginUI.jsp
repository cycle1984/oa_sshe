<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/common.jsp"%>
<%@ page import="cycle.oa_sshe.domain.User"%>
<%
	User user = (User) session.getAttribute("userSession");
	if (user != null) {
		response.sendRedirect(request.getContextPath()
				+ "/home_index.action");
	}
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>登录页面</title>
	<script type="text/javascript">
	var submitForm = function(){
		if ($('#user_loginUI_form').form('validate')) {
			$('#loginBtn').linkbutton('disable');
			$('#regBtn').linkbutton('disable');
			//$('#home_login_loginForm').submit();
			$.post('${pageContext.request.contextPath}/user_login.action',$('#user_loginUI_form').serialize(),function(r) {
				if (r.success) {
					$.messager.show({
						title : '提示',
						msg : r.msg
					});
					location.replace('home_index.action');

				} else {
					/*IE6-9出错
					$.messager.alert('错误提示',r.msg,'error',function(date){
						$('input[name="pwd"]').val('').focus();
						$('#loginBtn').linkbutton('enable');
					});
					 */
					$.messager.alert(
						'错误提示',
						r.msg,
						'error',
						function(){
							$('#user_loginUI_pwd').textbox('clear').textbox('textbox').focus();//密码框获得焦点
							$('#loginBtn').linkbutton('enable');
							$('#regBtn').linkbutton('enable');
						}
					);
					
				}
			}, 'json');
		}
	}
		$(function(){
			$('form :input').keyup(function(event) {
				if (event.keyCode == 13) {
					submitForm();
				}
			});
			$('#user_loginUI_loginName').textbox('textbox').focus();//登录名文本框获得焦点
		})
	</script>
</head>
<body style="width:100%;height: 100%;margin:0px;padding: 0px;overflow: hidden;"><!-- style里的属性解决在火狐浏览器窗口显示不完整的问题 -->
	<div id="win" class="easyui-window" title="系统登陆"  style="width:340px;height:200px;text-align:center;"  data-options="border:false,modal:true,collapsible:false,minimizable:false,maximizable:false,closable:false,draggable:false,resizable:false">   
    	<form id="user_loginUI_form" method="post">
    		<table cellpadding="10">
    			<tr>
    				<td>
    					<input id="user_loginUI_loginName" name=loginName class="easyui-textbox" data-options="iconCls:'icon-man',height:30,prompt:'登录名',required:true,missingMessage:'请输入登录名'" style="width:300px;"> 
    				</td>
    			</tr>
    			<tr>
    				<td>
    					<input id="user_loginUI_pwd" name="pwd" class="easyui-textbox" data-options="iconCls:'icon-lock',height:30,type:'password',prompt:'密码',required:true,missingMessage:'请输入密码'" style="width:300px" > 
    				</td>
    			</tr>
    		</table>
    	</form>
    	<div style="text-align:center;padding:5px">
	    	<a href="javascript:void(0)" id="loginBtn" class="easyui-linkbutton" data-options="iconCls:'icon-ok'" onclick="submitForm()">登录</a>
	    	<a href="javascript:void(0)" id="regBtn" class="easyui-linkbutton" data-options="iconCls:'icon-add'" onclick="regForm()">注册</a>
	    </div>
	</div> 
</body>
</html>