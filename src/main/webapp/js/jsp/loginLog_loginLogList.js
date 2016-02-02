$(function(){
	$('#loginLog_loginLogList_grid').datagrid({
		idField : 'id',
		url : 'loginLog_list.action',
		fit : true,
		fitColumns:true,//适应网格的宽度，防止水平滚动
		striped : true,
		rownumbers : true,
		pagination : true,
		singleSelect : false,
		border:false,
		sortName : 'logDate',
		sortOrder : 'desc',
		pageSize : 20,
		pageList : [10, 20, 30, 40, 50, 100, 500],
		columns : [[{
					field : 'id',
					title : '主键',
					width : 100,
					checkbox : true
				}, {
					field : 'logDate',
					title : '登陆时间',
					width : 50,
					sortable : true
				}, {
					field : 'userName',
					title : '用户姓名',
					width : 100,
					sortable : true
				}, {
					field : 'unitName',
					title : '单位名称',
					width : 100,
					sortable : true
				}, {
					field : 'ip',
					title : '登陆IP',
					width : 100
				}]],
		        toolbar : '#loginLog_loginLogList_toolbar'
	});
	
});

var delFunCyLog = function(){
	var rows = $('#loginLog_loginLogList_grid').datagrid('getChecked');
	var ids = "";
	if(rows.length>0){
		$.messager.confirm('提示信息', '即将删除' + rows.length + '条数据,确认删除？',
		function(r){
			if(r){
				// 将id拼成字符串
				for (var i = 0; i < rows.length; i++) {
					ids += rows[i].id + ',';
				}
				ids = ids.substring(0, ids.length - 1);
				$.post('loginLog_delete.action',{ids : ids},function(r){
					if(r.success){
						$('#loginLog_loginLogList_grid').datagrid('load');
						$('#loginLog_loginLogList_grid').datagrid('uncheckAll');
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
			msg : "请选择要删除的记录"
		});
	}
};


