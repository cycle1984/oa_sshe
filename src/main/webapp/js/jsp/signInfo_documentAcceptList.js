var refleshtime ;
/**
 * 定时刷新
 */
var rf = function (){
	$('#signInfo_documentAcceptList_grid').datagrid('load');
};
var setreflesh = function(){
	setTimeout(rf,refleshtime);//定时（refleshtime），执行一次rf方法
};

/**
 * 确定签收
 */
var signInfo_documentAcceptList_acceptDialog_submit = function(rowData){
	if($('#signInfo_documentAcceptList_acceptDialog_form').form('validate')){
		$('#signInfo_documentAcceptList_accept_OKbnt').linkbutton('disable');
		$.post('user_searchByLoginNameAndPwd.action',{
			pwd:$('#accept_pwd').val(),
			id:rowData.id
		},function(r){
			if(r.success){
				$('#signInfo_documentAcceptList_grid').datagrid('load');
				$.messager.show({
					title : '提示',
					msg : r.msg
				});
				$('#signInfo_documentAcceptList_acceptDialog').show().dialog('close');
				downDocumentDialog(rowData);
			}else{
				$.messager.alert('错误提示',r.msg,'error',function(){
					$('#accept_pwd').textbox('clear').textbox('textbox').focus();
					$('#signInfo_documentAcceptList_accept_OKbnt').linkbutton('enable');
				});
			}
		},'json');
		
	}
}

/**
 * 公文详情(下载)窗口
 */
var downDocumentDialog = function(row){
	var dialog = sy.modalDialog({
		title:'公文详情',
		href:'signInfo_getByID.action?id='+row.id,
		width:600,
		top:40
	});
}

$(function(){
	
	$('#signInfo_documentAcceptList_grid').datagrid({
		idField:'id',//指定标识字段
		url:'${pageContext.request.contextPath}/signInfo_receiveListGrid.action',//URL从远程站点请求数据
		fit:true,//当设置为true的时候面板大小将自适应父容器
		fitColumns:true,//适应网格的宽度，防止水平滚动
		striped : true,//是否显示斑马线
		rownumbers : true,//显示一个行号列
		pagination : true,//DataGrid控件底部显示分页工具栏
		singleSelect : true,//如果为true，则只允许选择一行
		border:false,//是否显示面板边框
		pageSize : 20,//每页显示记录数
		sortName : 'document.createdatetime',
		sortOrder : 'desc',
		pageList : [10, 20, 30, 40, 50, 100, 500],//在设置分页属性的时候 初始化页面大小选择列表
		rowStyler:function(index,row){
			if(row.document){
				if(row.document.level=="特提"&&row.signUserName!="本单位发布"){
					return 'color:red;';
				}
				if(row.signUserName=="本单位发布"){
					return 'color:green;';
				}
			}
			
		},
		columns:[[{
			field : 'document.createdatetime',
			title : '发布时间',
			width : 80,
			sortable : true,
			formatter:function(value,row,index){
				if(row.document){
					return row.document.createdatetime;
				}else {
					return value;
				}

			}
		},{
			field : 'document.level',
			title : '等级',
			width : 50,
			sortable : true,
			formatter:function(value,row,index){
				if(row.document){
					return row.document.level;
				}else {
					return value;
				}

			}
		}, {
			field : 'document.documentTitle',
			title : '标题',
			width : 200	,
			align:'left',//公文名称左对齐
			halign:'center',//标题居中对齐
			sortable : true,
			formatter:function(value,row,index){
				if(row.document){
					return row.document.documentTitle;
				}else {
					return value;
				}

			}
		}, {
			field : 'document.publishUnit.name',
			title : '发布单位',
			width : 100,
			sortable : true,
			formatter:function(value,row,index){
				if(row.document){
					if(row.document.publishUnit){
						return row.document.publishUnit.name;
					}
				}else {
					return value;
				}

			}
		}, {
			field : 'publishUserName',
			title : '发布人',
			width : 100,
			sortable : true
		},{
			field : 'signUserName',
			title : '签收情况',
			width : 80,
			formatter:function(value,row,index){
				if(row.signUserName){
					return '签收人:'+value;
				}else{
					return '<span style="color:red;">未签收</span>';
				}
			},
			sortable : true
		}]],
		toolbar : '#signInfo_documentAcceptList_toolbar',
		onLoadSuccess:function(data){//数据加载成功后执行的代码,检测是否有未签收的公文，有则弹出提示窗，循环播放提示音
			var num=0;
			for(var i=0;i<data.rows.length;i++){//遍历当前页数据，
				if(!data.rows[i].state){//签收状态，false进入
					num++;
				}
			}
			if(num>0){
				//获取当前网址，如： http://localhost:8083/uimcardprj/share/meun.jsp  
			    var curWwwPath=window.document.location.href;  
			    //获取主机地址之后的目录，如： uimcardprj/share/meun.jsp  
			    var pathName=window.document.location.pathname;  
			    var pos=curWwwPath.indexOf(pathName);  
			    //获取主机地址，如： http://localhost:8083  
			    var localhostPaht=curWwwPath.substring(0,pos);  
			    //获取带"/"的项目名，如：/uimcardprj  
			    var projectName=pathName.substring(0,pathName.substr(1).indexOf('/')+1);  
				$.messager.show({
					title:'提示信息',
					msg:'您有<span style="color:red">'+num+'+'+'</span>份新文件未签收请您及时办理!'
						+'<object height="0" width="0" data="'+localhostPaht+projectName+'/style/Alarm.mp3"></object></div>',
					timeout:5000,
					showType:'slide'
				});
				refleshtime = 1000*60*2;//文件都未签收的情况2分钟刷新一次
				setreflesh();
			}else{//文件都签收的情况
				refleshtime=1000*60*10;//10分钟刷新一次
				setreflesh();
			}
		},
		onClickRow:function(index, row){//点击数据行的时候执行
			if(row.state){
				downDocumentDialog(row);
			}else{
				
				$('#signInfo_documentAcceptList_acceptDialog').show().dialog({
					title:'公文签收',
					width:600,
					height:160,
					modal:true,
					top:40,
					buttons:[{
						text:'确定签收',
						id:'signInfo_documentAcceptList_accept_OKbnt',
						iconCls:'icon-ok',
						handler:function(){
							signInfo_documentAcceptList_acceptDialog_submit(row);
						}
					},{
						text:'关闭',
						handler:function(){
							$('#signInfo_documentAcceptList_acceptDialog').hide().dialog('close');
						}
					}],
					onOpen : function() {
		    			$('form :input').keyup(function(event) {
		    				if (event.keyCode == 13) {//按下键盘上的enter执行
		    					signInfo_documentAcceptList_acceptDialog_submit(row);
		    				}
		    			});
		    		}
				
				});
				$('#accept_pwd').textbox('clear').textbox('textbox').focus();
				$('#cyDocument_cyDocumentReceiveList_receiveDialog_id').val(row.id);//将公文ID赋值到隐藏的id表单
			}
		},
		onLoadError:function(){
			 
		},
	});
	
	$('#signInfo_documentAcceptList_searchForm_unit_td').on('click', function(){
		$('#signInfo_documentAcceptList_searchForm_unit').textbox('clear');
		dialog = sy.modalDialog({
			title:'选择单位查询',
			width : 350,
			top:'10%',
			href:'${pageContext.request.contextPath}/unit_searchByUnit.action',
			buttons : [ {
				id:'document_searchByUnit_OKbtn',
				text : '确定',
				iconCls:'icon-ok',
				handler : function() {
					$('#signInfo_documentAcceptList_searchForm_unit').textbox('setText',$('#unit_searchByUnit_unit').val());
					$('#signInfo_documentAcceptList_searchForm_unit').textbox('setValue',$('#unit_searchByUnit_unit').val());
					dialog.dialog('close');
				}
			} ]
		});
	});
	
});
