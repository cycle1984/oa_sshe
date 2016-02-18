<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<title>发布</title>
<meta name="renderer" content="webkit">
	
	
</head>
<body >

<%-- 	<script type="text/javascript" src="${pageContext.request.contextPath}/js/xheditor-1.2.2/xheditor-1.2.2.min.js"></script> --%>
<%-- 	<script type="text/javascript" src="${pageContext.request.contextPath}/js/xheditor-1.2.2/xheditor_lang/zh-cn.js"></script> --%>

		<script type="text/javascript" src="${pageContext.request.contextPath}/js/ckeditor/ckeditor.js"></script>
		<form id="news_saveUI_form" method="post">
			<input type="hidden" id="news_saveUI_form_id" name="id">
			<br>
			标题：<input id="news_saveUI_title" name="title" type="text" style="width: 550px">
			<br>
			<br>
			<textarea id="news_saveUI_content" name="content"  rows="12" cols="80" style="width: 80%"></textarea>
		</form>
	<script type="text/javascript">
// 		$(function(){
// 			$('#news_saveUI_content').xheditor({
// 				html5Upload:false,upMultiple:'1',
// 				upLinkUrl:"upload.php",
// 				upLinkExt:"zip,rar,txt",
// 				upImgUrl:"news_uploadImg.action",
// 				upImgExt:"jpg,jpeg,gif,png",
// 				upFlashUrl:"upload.php",
// 				upFlashExt:"swf",
// 				upMediaUrl:"upload.php",
// 				upMediaExt:"avi"});
// 		})
		var ck = CKEDITOR.replace('news_saveUI_content',{
            filebrowserImageUploadUrl:"news_uploadImg.action"  //上传action 
		});
		
		/**点击确定按钮方法定义 */
		var news_saveUI_submitForm = function($dialog,$grid){
			if($('#news_saveUI_title').val()!=""&&ck.getData()!=""){//如果表单验证通过
				$('#news_SaveUI_OKbtn').linkbutton('disable');//确定按钮禁用
				var url=null;
				if($('#news_saveUI_form_id').val().length>0){
					url='news_edit.action';//若隐藏id有值，则是修改，反之是新增
				}else{
					url='news_save.action';
				}
				$('#news_saveUI_content').val(ck.getData());
				$.post(url,$('#news_saveUI_form').serialize(),function(r){
					if(r.success){
						if($('#news_saveUI_form_id').val().length>0){//更新操作情况
							$grid.datagrid('reload');//更新操作后刷新
						}else{//新增情况
							$grid.datagrid('insertRow',{
			   				    index:0,
			   				    row:r.obj
			   				});
						}
						$dialog.dialog('close');
		   				$.messager.show({
							title : '提示',
							msg : r.msg
						});
					}else{//失败情况
						$.messager.alert('提示', r.msg,'error',function(){
		   					$('#news_SaveUI_OKbtn').linkbutton('enable');//开启取消按钮禁用
		   				});
					}
				},'json');
			}
		};
		
		/**
	  	 *编辑机构信息，回显数据 
	  	 */
	  	var news_saveUI_editForm = function($data){
  			$.messager.progress({
  				text : '数据加载中....'
  			});
  			$('#news_saveUI_form').form('load',{//表单内数据赋值
  				id : $data.id,
  				title : $data.title,
  				content:$data.content
  			});
  			var ct = $data.content;
  			ck.setData(ct);
  			$.messager.progress('close');//关闭数据加载提示窗口
		};
	</script>
</body>
</html>