<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="cycle.oa_sshe.domain.User,cycle.oa_sshe.utils.IpUtil"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
	User user = (User) session.getAttribute("userSession");
%>

<!DOCTYPE HTML>
<html>
<head>
<base href="<%=basePath%>">

<title>用户信息</title>

</head>

<body>
	<div>
		<fieldset>
			<legend>用户信息</legend>
			<table class="table">
				<tr>
					<th>登录名</th>
					<!-- <td><%=user.getLoginName() %></td> -->
					<td>${userSession.loginName }</td>
					<th>姓名</th>
					<td>${userSession.name }</td>
				</tr>
				<tr>
					<th>性别</th>
					<td>${userSession.gender }</td>
					<th>办公室电话</th>
					<td>${userSession.tel }</td>
				</tr>
				<tr>
					<th>手机号码</th>
					<td colspan="3">${userSession.phone }</td>
				</tr>
				<tr>
					<th>注册时间</th>
					<td>${userSession.getCreatedatetime() } </td>
					<th>最后修改时间</th>
					<td>${userSession.getUpdatedatetime() } </td>
				</tr>
				<tr>
					<th>当前IP</th>
					<td colspan="3"><%=IpUtil.getIpAddr(request) %></td>
				</tr>
			</table>
		</fieldset>
	</div>
</body>
</html>
