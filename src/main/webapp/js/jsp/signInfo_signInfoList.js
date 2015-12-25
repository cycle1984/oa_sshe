$(function(){
	var docId = $('#signInfo_signInfoList_id').val();
	$('#signInfo_signInfoList_grid').datagrid({
		idField : 'id',
		url:'signInfo_signInfoGrid.action?docId='+docId,
		fit : true,
		fitColumns:true,//适应网格的宽度，防止水平滚动
		striped : true,//是否显示斑马线
		rownumbers : true,
		pagination : true,//如果为true，则在DataGrid控件底部显示分页工具栏
		border:false,
		pageSize : 100,
		pageList : [10, 20, 30, 40, 50, 100,  500],
		columns : [[{
			field : 'signUnit',
			title : '收文单位',
			width : 100,
			align : 'center',
			formatter:function(value,row,index){
				if(row.signUnit){
					return row.signUnit.name;
				}else{
					return value;
				}
			},
			sortable : true
		}, {
			field : 'state',
			title : '签收状态',
			width : 100,
			formatter:function(value,row,index){
				if(row.state){
					return "<span>已签收</span>";
				}else{
					return "<span style='color:red'>未签收</span>";
				}
			},
			sortable : true
		}, {
			field : 'signDate',
			title : '签收时间',
			width : 100,
			align : 'center',
			sortable : true
		},{
			field : 'signUserName',
			title : '签收人',
			width : 100,
			sortable : true
		},{
			field : 'ip',
			title : 'IP地址',
			width : 100,
			sortable : true
		}]],
		toolbar : '#',
		onLoadSuccess:function(data){
		}
	});
	
});