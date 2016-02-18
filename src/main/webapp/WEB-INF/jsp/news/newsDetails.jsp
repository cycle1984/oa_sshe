<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>信息详情</title>
</head>
<body >

<div style="width:80%;margin:0px auto;">
	<div style="text-align: center;font-family:'微软雅黑';"><h1><s:text name="title"></s:text></h1></div>
	<div style="text-align: center;font-family:'微软雅黑';">作者:<s:text name="unit.name"></s:text>&nbsp;<s:text name="userName"></s:text>&nbsp;&nbsp;&nbsp;<s:date name="createTime" /> </div>
	<hr style="border:1px dotted  #0000fff;width: 90%"/>
	<div id="news_div_content" style="margin: 0 auto;"><s:text name="content"></s:text></div>
</div>
</body>
</html>