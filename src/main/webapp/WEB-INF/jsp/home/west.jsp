<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>菜单</title>
</head>
<body>
	<div>
		<ul id="home_west_tree">
		</ul>
	</div>
	<script type="text/javascript">
			$('#home_west_tree').tree({
				url : 'myResource_getAllMenu.action',
				parentField : 'pid',
				onClick : function(node) {
					if (node.attributes.url) {
						var url = '${pageContext.request.contextPath}/'
								+ node.attributes.url+".action";
						addTab({
							title : node.text,
							href : url,
							closable : true,
							fit:true
						});
					}

				}
			});
	</script>
</body>
</html>