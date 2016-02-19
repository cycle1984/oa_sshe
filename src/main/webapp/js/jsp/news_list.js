var newsGrid = $("#news_list_grid");
$(function(){
	newsGrid.datagrid({
		idField:'id',//指定标识字段
		url:'${pageContext.request.contextPath}/news_grid.action',//URL从远程站点请求数据
		fit:true,//当设置为true的时候面板大小将自适应父容器
		fitColumns:true,//适应网格的宽度，防止水平滚动
		striped : true,//是否显示斑马线
		rownumbers : true,//显示一个行号列
		pagination : true,//DataGrid控件底部显示分页工具栏
		singleSelect : false,//如果为true，则只允许选择一行
		border:false,//是否显示面板边框
		pageSize : 20,//每页显示记录数
		pageList : [10, 20, 30, 40, 50, 100, 500],//在设置分页属性的时候 初始化页面大小选择列表
		sortName : 'createTime',
		sortOrder : 'desc',
		columns:[[{
			field : 'id',
			title : '主键',
			hidden:true
		}, {
			field : 'createTime',
			title : '发布时间',
			sortable : true
		}, {
			field : 'unit.name',
			title : '发布单位',
			formatter: function(value,row,index){//value：字段值,row：行记录数据,index: 行索引
				if(row.unit){
					return row.unit.name;
				}else {
					return value;
				}
			}
		}, {
			field : 'userName',
			title : '作者',
			sortable : true
		}, {
			field : 'title',
			title : '标题',
			sortable : true
		}, {
			field : 'content',
			title : '内容',
			hidden:true
		}]],
		toolbar:'#news_list_toolbar',//工具面板
		onClickRow:function(index, row){
			var dialog = sy.modalDialog({//创建一个模式化的dialog
				title:row.title,
				width : 900,//dialog宽度
				height:500,
				maximizable:true,
				top:'10%',//dialog离页面顶部的距离
				//content:'<iframe name="news_saveUI_frame"  src="'+url+'" frameborder="0" style="height:100%;width:100%;" "></iframe>',
				href:'news_newsDetails.action?id='+row.id//从URL读取远程数据并且显示到面板。注意：内容将不会被载入，直到面板打开或扩大，在创建延迟加载面板时是非常有用的
			});
		}
	});
});

