<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page import= "com.opensymphony.xwork2.ActionContext"  %> 
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>新增用户</title>
</head>
<body>
	<%
	System.out.println("dsadsa="+ActionContext.getContext().getValueStack().findString("id"));
	%>

	<script type="text/javascript">
	
		$(function(){
			/**
			 * 机构下拉菜单初始化
			 * myGroup
			 */
			$("#user_saveUI__myGroupCombobox").combobox(
					{
						//value:'-==请选择所属系统==-',
						//mode:'remote',
						url : "myGroup_findAll.action",
						valueField : "id",
						textField : "name",
						required : false,
						editable : false,
						onSelect : function(record) {
							$('#user_saveUI_unitCombobox').combobox('setValue', '');
						}
					});

			/**
			 * unit下拉菜单
			 */
			$("#user_saveUI_unitCombobox").combobox({
				valueField : "id",
				textField : "name",
				editable : false,
				required : true,
				onShowPanel : function() {//当下拉面板显示的时候触发
					var myGroupId = $("#user_saveUI__myGroupCombobox").combobox("getValue");
					$("#user_saveUI_unitCombobox").combobox('reload','unit_getUnitsByMyGroupId.action?myGroupId='+myGroupId);
				}
			});

			/**
			 * role下来菜单初始化
			 */
			$('#user_saveUI_roleCombobox').combobox({
				url : 'role_findAll.action',
				valueField : 'id',
				textField : 'name',
				editable : false,
				required : true
			});
		});
	
		/**
		 * 定义确定按钮js
		 */
		var user_saveUI_submitForm = function($dialog,$grid){
			if($('#user_saveUI_form').form('validate')){//如果表单验证通过
				$('#userSaveUI_OKbtn').linkbutton('disable');//确定按钮禁用
				var url=null;
				if($('#user_saveUI_form_id').val().length>0){
					url='user_edit.action';//若隐藏id有值，则是修改，反之是新增
				}else{
					url='user_save.action';
				}
				$.post(url,$('#user_saveUI_form').serialize(),function(r){
					if(r.success){
						if($('#user_saveUI_form_id').val().length>0){//更新操作情况
							$grid.datagrid('reload');//更新操作后刷新
						}else{//新增情况
							$grid.datagrid('insertRow',{
			   				    index:0,
			   				    row:r.obj
			   				});
						}
						$dialog.dialog('close');
		   				$.messager.show({
							title : '提示',
							msg : r.msg
						});
					}else{//失败情况
						$.messager.alert('提示', r.msg,'error',function(){
		   					$('#userSaveUI_OKbtn').linkbutton('enable');//开启取消按钮禁用
		   				});
					}
				},'json');
			}
		};
		
		/**
	  	 *编辑用户信息，回显数据 
	  	 */
	  	var user_saveUI_editForm = function($data){
  			$.messager.progress({
  				text : '数据加载中....'
  			});
  			var unitId="";//要传给后台单位的主键值
  			var roleId="";//要传给后台权限、角色的主键值
  			var unitName="";//在前端下拉菜单显示的单位名称，
  			if ($data.unit != null) {
				unitId = $data.unit.id;
				unitName = $data.unit.name;
			}
			if ($data.role != null) {
				roleId = $data.role.id;
			}
  			$('#user_saveUI_form').form('load',{//表单内数据赋值
  				id : $data.id,
				loginName : $data.loginName,
				name : $data.name,
				gender : $data.gender,
				tel:$data.tel,
				phone : $data.phone,
				dep:$data.dep,
				description : $data.description,
				unitId : unitId,//设置传到后台unitId的值
				roleId : roleId//设置传到后台roleId的值
  			});
  			$('#user_saveUI_unitCombobox').combobox('setText',unitName);//设置回显的text文本
  			$.messager.progress('close');//关闭数据加载提示窗口
		};
	</script>
	
	<div style="text-align: center;vertical-align: middle;">
		<fieldset>
			<legend>用户基本信息</legend>
			<form id="user_saveUI_form" method="post">
				<input name="id" id="user_saveUI_form_id" type="hidden"><!-- 隐藏用户的ID主键  -->
				<table style="margin:0px auto;">
					<tr>
						<td>登录名</td>
						<td>
							<%
								String id = ActionContext.getContext().getValueStack().findString("id");//获得值栈中id的值，用于判断需要显示登录名是否需要查询（新增时可查询用户名是否可用）
							%>
							<s:if test="id==null"><input id="user_saveUI_loginName" name="loginName" type="text" class="easyui-textbox" data-options="required:true,delay:1000,validType:['username[$(\'#user_saveUI_loginName\').val()]','remote[\'${pageContext.request.contextPath}/user_searchByLoginName.action\',\'loginName\']']" ></s:if>
							<s:else><input id="user_saveUI_loginName" name="loginName" type="text" class="easyui-textbox" data-options="required:true,delay:1000,validType:['username[$(\'#user_saveUI_loginName\').val()]']"  readonly="readonly"></s:else>
							
						</td>
					</tr>
					<tr>
						<td>姓名</td>
						<td><input name="name" class="easyui-textbox" data-options="required:true"></td>
					</tr>
					<tr>
						<td>性别</td>
						<td>男<input name="gender" value="男" type="radio" checked="checked">女<input name="gender" value="女" type="radio"></td>
					</tr>
					<tr>
						<td>办公室电话</td>
						<td><input name="tel"  class="easyui-textbox" data-options="required:true"></td>
					</tr>
					<tr>
						<td>手机号码</td>
						<td><input type="text" id="user_saveUI_form_phone" name="phone" class="easyui-textbox" data-options="required:true,prompt:'支持13、15、18开头手机号码',validType:'mobile[$(\'#user_saveUI_form_phone\').val()]'"></td>
					</tr>
					<tr>
						<td>所属机构</td>
						<td><select id="user_saveUI__myGroupCombobox" name="myGroupId" style="width: 155px"></select></td>
					</tr>
					<tr>
						<td>所属单位</td>
						<td><select id="user_saveUI_unitCombobox" name="unitId" style="width: 155px"></select></td>
					</tr>
					<tr>
						<td>部门</td>
						<td><input name="dep" type="text" class="easyui-textbox" ></td>
					</tr>
					<tr>
						<td>权限级别</td>
						<td><select id="user_saveUI_roleCombobox" name="roleId" style="width: 155px"></select></td>
					</tr>
					<tr>
						<td>描述</td>
						<td><input name="description" class="easyui-textbox" data-options="multiline:true,height:50"></td>
					</tr>
				</table>
			</form>
		</fieldset>
	</div>
</body>
</html>