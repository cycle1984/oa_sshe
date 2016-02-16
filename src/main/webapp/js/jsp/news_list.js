var newsGrid = $("#news_list_grid");
$(function(){
	newsGrid.datagrid({
		idField:'id',//指定标识字段
		url:'${pageContext.request.contextPath}/news_listGrid.action',//URL从远程站点请求数据
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
			field : 'createTime',
			title : '发布时间',
			sortable : true
		}, {
			field : 'title',
			title : '标题',
			sortable : true
		}, {
			field : 'unit.name',
			title : '发布单位'
		}, {
			field : 'name',
			title : '作者',
			sortable : true
		}]],
		toolbar:'#news_list_toolbar'//工具面板
	});
});

var addFunNews = function(){
	var url = "${pageContext.request.contextPath}/news_saveUI.action";
	var dialog = sy.modalDialog({//创建一个模式化的dialog
		title:'发布信息资讯',
		width : 680,//dialog宽度
		height:500,
		top:'10%',//dialog离页面顶部的距离
		content:'<iframe  src="'+url+'" frameborder="0" style="height:100%;width:100%;" "></iframe>',
		//href:'news_saveUI.action',//从URL读取远程数据并且显示到面板。注意：内容将不会被载入，直到面板打开或扩大，在创建延迟加载面板时是非常有用的
		buttons: [ {
			id:'news_saveUI_OKbtn',
			text : '确定',
			iconCls:'icon-ok',
			handler : function() {
				news_saveUI_submitForm(dialog,newsGrid);//定义在saveUI.jsp
			}
		} ],
		onLoad:function(){
			//$('#unit_saveUI_form_name').textbox('textbox').focus();
		},
	});
}