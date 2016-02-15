var contactsGrip = $("#user_contacts_table");
$(function(){
	contactsGrip.datagrid({    
	    url:'${pageContext.request.contextPath}/user_listGrid.action',
	    fit:true,//当设置为true的时候面板大小将自适应父容器
		fitColumns:true,//适应网格的宽度，防止水平滚动
		striped : true,//是否显示斑马线
		rownumbers : true,//显示一个行号列
		pagination : true,//DataGrid控件底部显示分页工具栏
		singleSelect : true,//如果为true，则只允许选择一行
		sortName:'unit.id',
		//sortOrder:'asc',
		border:false,//是否显示面板边框
		pageSize : 20,//每页显示记录数
		pageList : [10, 20, 30, 40, 50, 100, 500],//在设置分页属性的时候 初始化页面大小选择列表
		columns:[[{
			field : 'unit.id',
			title : '所属单位',
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
		}]],
		toolbar:'#user_contacts_toolbar',//工具面板
		onLoadSuccess:function(data){
			var rows = contactsGrip.datagrid("getRows");
			var index=0;
			var rowspan =1;
			
			for(var i=0; i<rows.length; i++){
				if((i+1)<=rows.length-1){
					if(rows[i].unit.id!=rows[i+1].unit.id){//单位不相等，立即合并前面的数据，index下标+1，
						contactsGrip.datagrid('mergeCells',{
							index: index,
							field: 'unit.id',
							rowspan: rowspan
						});
						index++;
						
						rowspan=1;//合并后重置rowspan
						index = contactsGrip.datagrid("getRowIndex",rows[i])+1;//合并后更新index值
					}else{//单位相等，rowspan+1
						rowspan++;
					}
					
					//最后一行的数据没办法再比较一次，强制合并一次
					contactsGrip.datagrid('mergeCells',{
						index: index,
						field: 'unit.id',
						rowspan: rowspan
					});
					
					console.info("i="+index+",r="+rowspan);
//					if(rowspan!=1&&(rowspan!=(rowspan-1))){
//						contactsGrip.datagrid('mergeCells',{
//							index: index,
//							field: 'unit.id',
//							rowspan: rowspan
//						});
//						rowspan=1;//合并后重置rowspan
//						index = contactsGrip.datagrid("getRowIndex",rows[i])+1;//合并后更新index值
//						console.info("aa");
//					}else{
//						
//					}
				}
				
				
			}
		}
	});
});