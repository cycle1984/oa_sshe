var publishGrid = $('#document_historyPublishList_grid');

$(function(){
	
	publishGrid.datagrid({
		idField:'id',//指定标识字段
		url:'${pageContext.request.contextPath}/document_historyPublishGrid.action',//URL从远程站点请求数据
		fit:true,//当设置为true的时候面板大小将自适应父容器
		fitColumns:true,//适应网格的宽度，防止水平滚动
		autoRowHeight:true,//定义设置行的高度，根据该行的内容。设置为false可以提高负载性能。
		nowrap:false,//如果为true，则在同一行中显示数据。设置为true可以提高加载性能。
		striped : true,//是否显示斑马线
		rownumbers : true,//显示一个行号列
		pagination : true,//DataGrid控件底部显示分页工具栏
		singleSelect : false,//如果为true，则只允许选择一行
		border:false,//是否显示面板边框
		pageSize : 20,//每页显示记录数
		pageList : [10, 20, 30, 40, 50, 100, 500],//在设置分页属性的时候 初始化页面大小选择列表
		rowStyler:function(index,row){
			if(row.level=="特提"){
				return 'color:red;';
			}
		},
		sortName : 'createdatetime',
		sortOrder : 'desc',
		columns:[[{
			field : 'id',
			title : '主键',
			width : 60,
			hidden:true
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
			halign:'center',
			sortable : true
		}, {
			field : 'publishUnit',
			title : '发布单位',
			width : 100,
			formatter: function(value,row,index){
				if(value){return value.name;}
			},
			sortable : true
		}, {
			field : 'publishUserName',
			title : '发布人',
			width : 100,
			sortable : true
		}, {
			field : 'signInfoString',
			title : '签收情况',
			width : 100,
			sortable : true
		}]],
		onClickRow:function(index, row){
			viewSignInfos(row.id,index);
		},
		onLoadError:function(){
			 
		},
		toolbar:'#document_historyPublishList_toolbar'//工具面板
	});
	$('#document_publishList_searchForm_unit_td').on('click', function(){
		dialog = sy.modalDialog({
			title:'选择单位查询',
			width : 640,
			top:'10%',
			href:'${pageContext.request.contextPath}/unit_searchByUnit.action',
			buttons : [ {
				id:'document_saveUI_OKbtn',
				text : '确定',
				iconCls:'icon-ok',
				handler : function() {
					document_saveUI_submit(dialog);//在document/saveUI.jsp页面定义
				}
			} ]
		});
	});
	
});

/**
 * 查看签收信息列表
 */
var viewSignInfos = function(docId,index){
	var dialog = sy.modalDialog({
		title:'文件签收情况表',
		href:'signInfo_toViewInfoJsp.action?docId='+docId,
		width:700,
		height:'70%',
		border:true,
		onClose:function(){
			publishGrid.datagrid('unselectRow',index);
			$(this).dialog('destroy');
		}
	});
};

