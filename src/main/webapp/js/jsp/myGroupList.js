var myGroupGrid = $('#myGroup_list_grid');
$(function(){
	myGroupGrid.datagrid({
		idField:'id',//指定标识字段
		url:'${pageContext.request.contextPath}/myGroup_listGrid.action',//URL从远程站点请求数据
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
			checkbox : true
		}, {
			field : 'name',
			title : '名称',
			sortable : true
		},  {
			field : 'ownerUnit',
			title : '所辖单位'
		}, {
			field : 'description',
			title : '描述',
			sortable : true
		}]],
		toolbar:'#myGroup_list_toolbar'//工具面板
	});
});

/**
 * 添加机构
 */
var addFunMyGroup = function(){
	var dialog = $('<div/>').dialog({
		title:'添加机构',
		width : 400,//dialog宽度
		top:'10%',//dialog离页面顶部的距离
		modal: true,
		href:'myGroup_saveUI.action',//从URL读取远程数据并且显示到面板。注意：内容将不会被载入，直到面板打开或扩大，在创建延迟加载面板时是非常有用的
		buttons: [ {
			id:'myGroupSaveUI_OKbtn',
			text : '确定',
			iconCls:'icon-ok',
			handler : function() {
				myGroup_saveUI_submitForm(dialog,myGroupGrid);//定义在saveUI.jsp
			}
		}],
		onLoad:function(){
			$('#myGroup_saveUI_form_name').textbox('textbox').focus();
		},
		onClose:function(){
			$(this).dialog('destroy');
		}
	});
};

/**
 * 编辑机构
 */
var editFunMyGroup = function(){
	var arr = myGroupGrid.datagrid('getSelections');//返回所有被选中的行，当没有记录被选中的时候将返回一个空数组
	if (arr.length != 1) {
		$.messager.show({
			title : '提示信息',
			msg : '请您选择记录，一次只能选择一条记录进行修改！'
		});
	}else{
		var dialog = sy.modalDialog({
			title:'编辑机构信息',
			width : 400,
			top:'10%',
			href:'myGroup_saveUI.action?id='+arr[0].id,
			buttons : [ {
				id:'myGroupSaveUI_OKbtn',
				text : '确定',
				iconCls:'icon-ok',
				handler : function() {
					myGroup_saveUI_submitForm(dialog,myGroupGrid);//定义在saveUI.jsp
				}
			} ],
			onLoad:function(){
				myGroup_saveUI_editForm(arr[0]);//回显数据
			},
			onBeforeClose:function(){//关闭窗口后，取消勾选
				myGroupGrid.datagrid('uncheckAll');
			}
		});
	}
};

/**
 * 批量删除
 */
var deleteFunMyGroup = function(){
	var rows = myGroupGrid.datagrid('getChecked');//在复选框被选中的时候返回所有行
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
					url : 'myGroup_delete.action',
					data : {
						ids : ids
					},
					dataType : 'json',
					success : function(r) {
						myGroupGrid.datagrid('uncheckAll');//取消勾选当前页中的所有行
						myGroupGrid.datagrid('load');
//						$.messager.show({
//							title : '提示',
//							msg : r.msg
//						});
						$.messager.alert('提示', r.msg,'error');
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
}