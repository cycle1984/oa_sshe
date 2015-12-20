var myGroupGrid;
$(function(){
	myGroupGrid = $('#myGroup_list_grid').datagrid({
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
			 $.messager.alert('发生错误','error');
		}
	});
});