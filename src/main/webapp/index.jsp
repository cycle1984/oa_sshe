<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="cycle.oa_sshe.domain.User"%>
<!DOCTYPE HTML>
<html>
<head>
	<title>公文传输系统</title>
	<meta name="renderer" content="webkit">
</head>
<body>
	<%
		User user = (User) session.getAttribute("userSession");
		if (user != null) {
			response.sendRedirect(request.getContextPath() + "/home_index.action");
		} else {
			response.sendRedirect(request.getContextPath() + "/user_loginUI.action");
		}
	%>
</body>
</html>