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
		
		/**
		*用户注册弹出的窗口
		*/
		var regForm = function(){
			var dialog = sy.modalDialog({//创建一个模式化的dialog
				title:'用户注册',
				width : 400,//dialog宽度
				top:'10%',//dialog离页面顶部的距离
				href:'${pageContext.request.contextPath}/register.jsp',//从URL读取远程数据并且显示到面板。注意：内容将不会被载入，直到面板打开或扩大，在创建延迟加载面板时是非常有用的
				buttons: [ {
					id:'registerBtn',
					text : '提交',
					iconCls:'icon-ok',
					handler : function() {
						registerFun();
					}
				},{
	    			id:'back_login',
	    			text : '返回登陆',
	    			handler : function() {
	    				location.replace('${pageContext.request.contextPath}/index.jsp');
	    			}
	    		} ],
				onLoad:function(){//在加载远程数据时触发,初始化机构和单位的下拉菜单
					/**
		    		 * 机构下拉菜单初始化
		    		 */
		    		$("#register_myGroupCombobox").combobox({
		    			//value:'-==请选择所属系统==-',
		    			//mode:'remote',
		    		    url:"myGroup_findAll.action",
		    		    valueField:"id",
		    		    textField:"name",
		    		    required:true,
		    		    editable:false,
		    		    onSelect:function(){
		    		    	var myGroupId = $("#register_myGroupCombobox").combobox("getValue");
		    		    	//$('#register_unitCombobox').combobox('setValue' , '');
		    		    	$("#register_unitCombobox").combobox('reload','unit_getUnitsByMyGroupId.action?myGroupId='+myGroupId);
		    		    }
		    		});
		    		
		    		/**
		    		 * unit下拉菜单
		    		 */
		    		$("#register_unitCombobox").combobox({
		    		    valueField:"id",
		    		    textField:"name",
		    		    editable:false,
		    		    required:true
		    		});
		    		$('#register_loginName').textbox('textbox').focus();
	    			$('#register_form :input').keyup(function(event) {
	    				if (event.keyCode == 13) {
	    					registerFun();
	    				}
	    			});
				},
				onOpen : function() {
	    			
	    		}
			});
		}
		
		var registerFun = function() {
			if ($('#register_form').form('validate')) {
				$('#registerBtn').linkbutton('disable');
				$('#back_login').linkbutton('disable');
				$.post('${pageContext.request.contextPath}/user_register.action', $('#register_form').serialize(), function(result) {
					if (result.success) {
						$.messager.alert('提示', result.msg, 'info', function() {
							location.replace('${pageContext.request.contextPath}/index.jsp');
						});

					} else {
						$.messager.alert('提示', result.msg, 'error', function() {
							$('register_form :input:eq(1)').focus();
						});
						$('#registerBtn').linkbutton('enable');
						$('#back_login').linkbutton('enable');
					}
				}, 'json');
			}
		};
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