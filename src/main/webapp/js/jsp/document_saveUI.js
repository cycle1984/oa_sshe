var uploader;
$(function(){
	uploader = new plupload.Uploader({//上传插件定义
		browse_button : 'pickfiles',//选择文件的按钮,触发文件选择对话框的DOM元素，当点击该元素后便后弹出文件选择对话框。该值可以是DOM元素对象本身，也可以是该DOM元素的id
		container : 'container',//文件上传容器
		//flash_swf_url : sy.contextPath + '/jslib/plupload_1_5_7/plupload/js/plupload.flash.swf',// Flash环境路径设置
		url : 'document_uploadFile.action',//服务器端接收和处理上传文件的脚本地址
		filters:{//可以使用该参数来限制上传文件的类型，大小等，该参数以对象的形式传入
			max_file_size : '10mb',//用来限定上传文件的大小，如果文件体积超过了该值，则不能被选取
			prevent_duplicates : true //不允许选取重复文件
		},
		chunk_size : '10mb',//分块大小，小于这个大小的不分块
		//unique_names : true,//生成唯一文件名
		file_data_name:'file'//指定文件上传时文件域的名称，默认为file
	});
	
	uploader.bind('Init', function(up, params) {//初始化时
		//$('#filelist').html("<div>当前运行环境: " + params.runtime + "</div>");
		$('#filelist').html("");
	});
	uploader.bind('FilesAdded', function(up, files) {//选择文件后
		$.each(files, function(i, file) {
			$('#filelist').append('<div id="' + file.id + '">' + file.name + '(' + plupload.formatSize(file.size) + ')<strong></strong>' + '<span onclick="uploader.removeFile(uploader.getFile($(this).parent().attr(\'id\')));$(this).parent().remove();" style="cursor:pointer;" class="ext-icon-cross" title="删除">&nbsp;&nbsp;&nbsp;&nbsp;</span></div>');
		});
		up.refresh();
	});
	
	uploader.bind('UploadProgress', function(up, file) {//上传进度改变
		var msg;
		if (file.percent == 100) {
			msg = '100';//因为某些大文件上传到服务器需要合并的过程，所以强制客户看到99%，等后台合并完成...
		} else {
			msg = file.percent;
		}
		$('#' + file.id + '>strong').html(msg + '%');
	
		/*
		parent.progressBar({//显示文件上传滚动条
			title : '文件上传中...',
			value : msg
		});
		*/
	});
	
	uploader.init();//进行初始化
  
	uploader.bind('Error', function(up, err) {//出现错误
		$('#filelist').append("<div>错误代码: " + err.code + ", 描述信息: " + err.message + (err.file ? ", 文件名称: " + err.file.name : "") + "</div>");
		up.refresh();
	});


	//初始化收文单位树形菜单
	$('#document_saveUI_unitCombotree').combotree({
		url: '${pageContext.request.contextPath}/unit_getUnitTree.action',
		multiple:true,//是否允许多选
		multiline:true,//定义是否是多行文本框
		checkbox:true,//定义是否在每一个借点之前都显示复选框。
		required: true,//是否为必选
		prompt:'点击下拉列表左侧白色小三角形可展开单位列表',//提示信息
		width:550,
		onClick:function(node){//因为onCheck和onClick捆绑在一起，所以2个需要一样的设置
	    	var nodes = $('#document_saveUI_unitCombotree').combotree('tree').tree('getChecked');//勾选的节点数
	    	var num=0;//收文的单位数
	    	var text = '';//文本框内容
	    	for(var i =0;i<nodes.length;i++){
	    		var isLeaf = $('#document_saveUI_unitCombotree').combotree('tree').tree('isLeaf',nodes[i].target);
				if(isLeaf){
					num++;
					if(i!=nodes.length-1){
						text+=nodes[i].text+",";
					}else{
						text+=nodes[i].text+",      共"+num+"个单位";
					}
					
				}
	    	}
	    	$('#document_saveUI_unitCombotree').combotree('setText',text);//设置combotree文本框内容
	    }, 
	    onCheck:function(node){//因为onCheck和onClick捆绑在一起，所以2个需要一样的设置
	    	var nodes = $('#document_saveUI_unitCombotree').combotree('tree').tree('getChecked');//勾选的节点数
	    	var num=0;//收文的单位数
	    	var text = '';
	    	
	    	for(var i =0;i<nodes.length;i++){
	    		var isLeaf = $('#document_saveUI_unitCombotree').combotree('tree').tree('isLeaf',nodes[i].target);
				if(isLeaf){
					num++;
					if(i!=nodes.length-1){
						text+=nodes[i].text+",";
					}else{
						text+=nodes[i].text+",      共"+num+"个单位";
					}
					
				}
	    	}
	    	$('#document_saveUI_unitCombotree').combotree('setText',text);
	    }
	});
	
});



/**
 * 发布按钮定义 
 */
var document_saveUI_submit =function($dialog){
	if($('#document_saveUI_form').form('validate')){//验证表单
		if (uploader.files.length > 0) {//>0则有附件
			$('#document_saveUI_OKbtn').linkbutton('disable');//发布按钮变灰，防止重复提交
			uploader.start();//开始上传附件
			var fileNewNames="";//上传后的所有附件名称
			uploader.bind('FileUploaded', function(up, file, responseObject) {//每个上传完毕
				fileNewNames+=responseObject.response+',,,';//拼接每个上传附件的文件名称，用,,,分割
			});
			fileNewNames = fileNewNames.substring(0, fileNewNames.length-3);//去掉最后的,,,
			uploader.bind('StateChanged',function(uploader){//当上传队列中所有文件都上传完成后触发
				if(uploader.files.length === (uploader.total.uploaded + uploader.total.failed)){
					
				
				/**
				 * 获得所选单位id(只获得叶子节点)
				 */
				var ids = '';
				var t = $('#document_saveUI_unitCombotree').combotree('tree');//获得树对象
				var check = t.tree('getChecked');
				for (var i = 0; i < check.length; i++) {
					var isLeaf = t.tree('isLeaf',check[i].target);
					if(isLeaf){
						ids += check[i].id + ',';
					}
				}
				ids = ids.substring(0, ids.length - 1);
				var url = 'document_save.action?ids='+ids+'&fileNewNames='+encodeURI(fileNewNames);
				$.post(url,sy.serializeObject($('#document_saveUI_form'))
						//{
//							ids:ids,
//							fileNewNames:fileNewNames,
//							documentTitle:$('#document_saveUI_documentTitle').val(),
//							level:$('#document_saveUI_level').val(),
//							description:$('#document_saveUI_description').val()
							//}
						,function(result){
								if(result.success){
									//$dialog.dialog('close');
									$.messager.show({
										title : '提示',
										msg : result.msg
									});
									$('#document_publishList_grid').datagrid('load');//重新加载发文列表
									$('#home_main_tabs').tabs('select','发文管理');//选择发文菜单
									
								}else {
									$.messager.alert('提示', result.msg, 'error');
									$('#document_saveUI_OKbtn').linkbutton('enable');
								}
								
				}, 'json');
				}
			});
		}else{
		    $.messager.alert('提示','请选择上传的附件！','info');
	    }
	};
};