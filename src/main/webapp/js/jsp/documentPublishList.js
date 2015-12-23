var publishGrid = $('#document_publishList_grid');

$(function(){
	
	publishGrid.datagrid({
		idField:'id',//指定标识字段
		url:'${pageContext.request.contextPath}/document_publishListGrid.action',//URL从远程站点请求数据
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
			width : 60,
			checkbox : true
		},{
			field : 'createdatetime',
			title : '发布时间',
			width : 80,
			sortable : true
		},{
			field : 'level',
			title : '等级',
			width : 50,
			sortable : true
		}, {
			field : 'documentTitle',
			title : '标题',
			width : 200	,
			align:'center',
			sortable : true
		}, {
			field : 'publishUnit.name',
			title : '发布单位',
			width : 100,
			sortable : true
		}, {
			field : 'publishUserName',
			title : '发布人',
			width : 100,
			sortable : true
		}, {
			field : 'signInfos',
			title : '签收情况',
			width : 50,
			sortable : true
		}]],
		onLoadError:function(){
			 
		},
		toolbar:'#document_publishList_toolbar'//工具面板
	});
	
});
/**
 * 发布公文
 */
var addFunDocumentPublish = function(){
	var dialog = sy.modalDialog({
		title:'发布公文',
		width : 640,
		top:'10%',
		href:'${pageContext.request.contextPath}/document_saveUI.action',
		buttons : [ {
			id:'document_saveUI_OKbtn',
			text : '发布',
			iconCls:'icon-ok',
			handler : function() {
				document_saveUI_submit(dialog);//在document/saveUI.jsp页面定义
			}
		} ]
	});
};