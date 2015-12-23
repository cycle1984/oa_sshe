var userGrid = $('#user_list_grid');
$(function(){
	userGrid.datagrid({
		idField:'id',//指定标识字段
		url:'${pageContext.request.contextPath}/user_listGrid.action',//URL从远程站点请求数据
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
			field : 'loginName',
			title : '登录名',
			width : 100,
			sortable : true
		}, {
			field : 'name',
			title : '姓名',
			width : 100,
			sortable : true
		}, {
			field : 'gender',
			title : '性别',
			width : 35 	,
			align:'center',
			sortable : true
		}, {
			field : 'tel',
			title : '办公室电话',
			width : 100,
			sortable : true
		},  {
			field : 'phone',
			title : '手机号码',
			width : 100,
			sortable : true
		}, {
			field : 'dep',
			title : '部门',
			width : 100,
			sortable : true
		}, {
			field : 'unit',
			title : '所属用户',
			width : 100,
			sortable : true,
			formatter: function(value,row,index){//value：字段值,row：行记录数据,index: 行索引
				if(row.unit){
					return row.unit.name;
				}else {
					return value;
				}
			}
		}, {
			field : 'unit.myGroup',//排序时向后台发送的参数
			title : '所属机构',
			width : 100,
			sortable : true,
			formatter: function(value,row,index){//value：字段值,row：行记录数据,index: 行索引
				if(row.unit){
					if(row.unit.myGroup){
						return row.unit.myGroup.name;
					}
				}else {
					return value;
				}
			}
		}, {
			field : 'createdatetime',
			title : '创建时间',
			width : 150,
			align : 'center',
			sortable : true
		}, {
			field : 'description',
			title : '描述',
			width : 100
		}, {
			field : 'role',
			title : '权限级别',
			width : 100,
			sortable : true,
			formatter: function(value,row,index){
				if (row.role){
					return row.role.name;
				} else {
					return value;
				}
			}
		}]],
		onLoadError:function(){
			 $.messager.alert('信息提示','登录超时请重新登录!','error');
		},
		toolbar:'#user_list_toolbar'//工具面板
	});
});

/**
 * 添加用户
 */
var addFunUser = function(){
	var dialog = sy.modalDialog({//创建一个模式化的dialog
		title:'添加用户',
		width : 640,//dialog宽度
		top:'10%',//dialog离页面顶部的距离
		href:'user_saveUI.action',//从URL读取远程数据并且显示到面板。注意：内容将不会被载入，直到面板打开或扩大，在创建延迟加载面板时是非常有用的
		buttons: [ {
			id:'userSaveUI_OKbtn',
			text : '确定',
			iconCls:'icon-ok',
			handler : function() {
				user_saveUI_submitForm(dialog,userGrid);
			}
		} ]
	});
};

/**
 * 编辑用户
 */
var editFunUser = function(){
	var arr = userGrid.datagrid('getSelections');//返回所有被选中的行，当没有记录被选中的时候将返回一个空数组
	if (arr.length != 1) {
		$.messager.show({
			title : '提示信息',
			msg : '请您选择记录，一次只能选择一条记录进行修改！'
		});
	}else{
		var dialog = sy.modalDialog({
			title:'编辑用户信息',
			width : 640,
			top:'10%',
			href:'user_saveUI.action?id='+arr[0].id,
			buttons : [ {
				id:'userSaveUI_OKbtn',
				text : '确定',
				iconCls:'icon-ok',
				handler : function() {
					user_saveUI_submitForm(dialog,userGrid);//定义在saveUI.jsp
				}
			} ],
			onLoad:function(){
				user_saveUI_editForm(arr[0]);//回显数据
			}
		});
	}
};

/**
 * 批量删除
 */
var deleteFunUser = function(){
	var rows = userGrid.datagrid('getChecked');//在复选框被选中的时候返回所有行
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
					url : 'user_delete.action',
					data : {
						ids : ids
					},
					dataType : 'json',
					success : function(r) {
						userGrid.datagrid('uncheckAll');//取消勾选当前页中的所有行
						userGrid.datagrid('load');
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
 *重置密码
 */
var resetPwdFun = function(){
	var rows = userGrid.datagrid("getChecked");//获取已经选择的用户数据
	var ids = "";//用于传递用户id的字符串
	if(rows.length>0){
		$.messager.confirm('提示信息', '即将重置' + rows.length + '条密码,确认重置？',
		function(r){
			if(r){
				// 将id拼成字符串
				for (var i = 0; i < rows.length; i++) {
					ids += rows[i].id + ',';
				}
				ids = ids.substring(0, ids.length - 1);//去掉字符串最后的字符（逗号）
				$.post('user_initPassword.action',{ids : ids},function(r){
					if(r.success){
						userGrid.datagrid('reload');
						userGrid.datagrid('uncheckAll');
						$.messager.show({
							title : '提示',
							msg : r.msg
						});
					}else{
	    				$.messager.alert('提示', r.msg,'error');
	    			}
				},'json');
			}
		});
	}else {
		$.messager.show({
			title : '提示',
			msg : "请选择要重置的数据"
		});
	}
};