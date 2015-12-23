var roleGrid = $('#role_list_grid');
$(function(){
	roleGrid.datagrid({
		idField:'id',//指定标识字段
		url:'${pageContext.request.contextPath}/role_listGrid.action',//URL从远程站点请求数据
		fit:true,//当设置为true的时候面板大小将自适应父容器
		fitColumns:true,//适应网格的宽度，防止水平滚动
		striped : true,//是否显示斑马线
		rownumbers : true,//显示一个行号列
		pagination : true,//DataGrid控件底部显示分页工具栏
		singleSelect : false,//如果为true，则只允许选择一行
		border:false,//是否显示面板边框
		pageSize : 20,//每页显示记录数
		pageList : [10, 20, 30, 40, 50, 100, 500],//在设置分页属性的时候 初始化页面大小选择列表
		columns:[[{
			field : 'id',
			title : '主键',
			width : 100,
			checkbox : true
		}, {
			field : 'name',
			title : '名称',
			width : 100,
			sortable : true
		}, {
			field : 'description',
			title : '描述',
			width : 100,
			sortable : true
		}]],
		onLoadError:function(){
			 $.messager.alert('信息提示','登录超时请重新登录!','error');
		},
		toolbar:'#role_list_toolbar'//工具面板
	});
});

/**
 * 添加角色
 */
var addFunRole = function(){
	var dialog = sy.modalDialog({//创建一个模式化的dialog
		title:'添加角色',
		width : 400,//dialog宽度
		top:'10%',//dialog离页面顶部的距离
		href:'role_saveUI.action',//从URL读取远程数据并且显示到面板。注意：内容将不会被载入，直到面板打开或扩大，在创建延迟加载面板时是非常有用的
		buttons: [ {
			id:'roleSaveUI_OKbtn',
			text : '确定',
			iconCls:'icon-ok',
			handler : function() {
				role_saveUI_submitForm(dialog,roleGrid);//定义在saveUI.jsp
			}
		} ]
	});
};

/**
 * 编辑角色
 */
var editFunRole = function(){
	var arr = roleGrid.datagrid('getSelections');//返回所有被选中的行，当没有记录被选中的时候将返回一个空数组
	if (arr.length != 1) {
		$.messager.show({
			title : '提示信息',
			msg : '请您选择记录，一次只能选择一条记录进行修改！'
		});
	}else{
		var dialog = sy.modalDialog({
			title:'编辑角色信息',
			width : 400,
			top:'10%',
			href:'role_saveUI.action?id='+arr[0].id,
			buttons : [ {
				id:'roleSaveUI_OKbtn',
				text : '确定',
				iconCls:'icon-ok',
				handler : function() {
					role_saveUI_submitForm(dialog,roleGrid);//定义在saveUI.jsp
				}
			} ],
			onLoad:function(){
				role_saveUI_editForm(arr[0]);//回显数据
			}
		});
	}
};

/**
 * 批量删除
 */
var deleteFunRole = function(){
	var rows = roleGrid.datagrid('getChecked');//在复选框被选中的时候返回所有行
	var ids ="";
	if (rows.length > 0) {
		$.messager.confirm('提示信息', '即将删除' + rows.length + '条数据,确认删除？',function(r) {
			if(r){//点击确认进入
				// 将id拼成字符串
				for (var i = 0; i < rows.length; i++) {
					ids += rows[i].id + ',';
				}
				ids = ids.substring(0, ids.length - 1);
				$.ajax({
					url : 'role_delete.action',
					data : {
						ids : ids
					},
					dataType : 'json',
					success : function(r) {
						roleGrid.datagrid('uncheckAll');//取消勾选当前页中的所有行
						roleGrid.datagrid('load');
						$.messager.show({
							title : '提示',
							msg : r.msg
						});
					}
				});
			}
		});
	}else{
		$.messager.show({
			title : '提示',
			msg : "请选择要删除的数据"
		});
	}
};

/**
 * 设置权限
 */
var setFunMyResource = function(){
	var arr = roleGrid.datagrid('getChecked');//获取已经勾选的数据行
	if(arr.length!=1){//判断是否只选择了一行
		$.messager.show({
			title : '提示信息',
			msg : '一次只能选择一条记录设置权限！'
		});
	}else{
		var roleId = arr[0].id;
		console.info('rows:'+roleId);
		$('#role_setMyResource_tree').tree({
			url : 'role_setMyResourceUI.action',//准备回显到前台没有勾选之前的权限树
			parentField : 'pid',//父节点id
			checkbox:true,//显示节点前面的复选框
			//cascadeCheck:false
			onLoadSuccess : function(node, data) {//树加载成功后把拥有的权限勾选
				$.post('role_getRoleMyResource.action', {
					roleId : roleId
				}, function(result) {
					if (result) {
						for (var i = 0; i < result.length; i++) {//遍历所有拥有的权限资源
							var node = $('#role_setMyResource_tree').tree('find', result[i].id);//查找指定节点并返回节点对象
							if (node) {
								var isLeaf = $('#role_setMyResource_tree').tree('isLeaf', node.target);//判断指定的节点是否是叶子节点
								if (isLeaf) {
									$('#role_setMyResource_tree').tree('check', node.target);//如果是叶子节点，勾选
								}
							}
						}
					}
					parent.$.messager.progress('close');
				}, 'json');
			}
		});
		
		$('#role_setMyResource_dialog').dialog({
			title:'正在为权限组【'+arr[0].name+'】设置权限',
			modal : true,
			width:500,
			height:500,
			buttons : [{
				text : '确定',
				handler : function() {
					//var checkeds =$('#role_setMyResource_tree').tree('getChecked');
					var checkeds =$('#role_setMyResource_tree').tree('getChecked',[ 'checked', 'indeterminate' ]);//获取已选择和未确定的节点
					
					var ids = "";
					for (var i = 0; i < checkeds.length; i++) {
						console.info(checkeds[i].text);
						ids += checkeds[i].id + ',';
					}
					ids = ids.substring(0, ids.length - 1);
					$.post('${pageContext.request.contextPath}/role_setMyResource.action',{ids : ids,roleId:roleId},function(r){
						if(r.success){
							$('#role_setMyResource_dialog').dialog('close');
							roleGrid.datagrid('load');
							roleGrid.datagrid('unselectAll');
							$.messager.show({
								title : '提示',
								msg : r.msg
							});
						}else{
		    				$.messager.alert('提示', r.msg,'error');
		    			}
					},'json');
				}
			}]
		});
	}
};