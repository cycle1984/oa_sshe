<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="cycle.oa_sshe.domain.User"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
	User user = (User) session.getAttribute("userSession");
%>

<!DOCTYPE HTML>
<html>
<head>
<base href="<%=basePath%>">
<title>修改个人信息</title>
<!-- 用户修改自己个人信息页面 -->
</head>

<body>

	<script type="text/javascript">
		var user_modifyInfo_submitForm = function($dialog){
			if ($('#ser_modifyInfoUI_form').form('validate')) {
				$('#modifyInfoUI_OKbtn').linkbutton('disable');
				$.post('user_modifyInfo.action',sy.serializeObject($('#user_modifyInfoUI_form')),function(r) {
					if (r.success) {
						$dialog.dialog('close');
						$.messager.show({
							title : '提示',
							msg : r.msg
						});
					} else {
						$.messager.alert('提示', r.msg, 'error',function() {
							$('#modifyInfoUI_OKbtn').linkbutton('enable');
						});
					}
				}, 'json');
			}
		};
	</script>
	<div>
		<form method="post" class="form" id="user_modifyInfoUI_form">
			<fieldset>
				<legend>修改个人资料</legend>
				<table style='margin:0px auto;' class="table">
					<tr>
						<th>验证密码</th>
						<td><input type="password" name="pwd" class="easyui-textbox" data-options="required:true"></td>
					</tr>
					<tr>
						<th>性别</th>
						<td><%if(user.getGender()!=null&&"女".equals(user.getGender())){ %>
							男<input name="gender" value="男" type="radio" >女<input name="gender" value="女" type="radio" checked="checked"><%} else{%>
							男<input name="gender" value="男" type="radio"  checked="checked">女<input name="gender" value="女" type="radio">
						<%} %></td>
					</tr>
					<tr>
						<th>办公室电话</th>
						<td><input type="text" id="user_modifyInfoUI_tel_tel" name="tel" class="easyui-textbox" value="${userSession.tel }" data-options="validType:'tel[$(\'#reg_tel\').val()]'" ></td>
					</tr>
					<tr>
						<th>手机号码</th>
						<td><input type="text" id="user_modifyInfoUI_tel_phone" name="phone" value="${userSession.phone }" class="easyui-textbox" data-options="required:true,prompt:'支持13、15、18开头手机号码',validType:'mobile[$(\'#reg_phone\').val()]'"></td>
					</tr>
					<tr>
						<th>描述</th>
						<td><input type="text" name="description" class="easyui-textbox" data-options="multiline:true,height:55,prompt:'此处可备注信息'"></td>
					</tr>
				</table>
			</fieldset>
		</form>
	</div>
</body>
</html>
