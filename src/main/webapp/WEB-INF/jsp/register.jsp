<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>欢迎注册</title>
</head>
<body>
<h3>注册页面</h3>
<form action="register" method="post">
	<table>
		<tr>
			<td><label>登陆名:</label>
			<td><input type="text" id="name" name="name">
		</tr>
		<tr>
			<td><label>生日:</label>
			<td><input type="text" id="birthday" name="birthday">
		</tr>
		<tr>
			<td><label>Lover:</label>
			<td><input type="text" id="lover" name="lover">
		</tr>
		<tr>
			<td><input type="submit" id="submit" value="登陆"></td>
		</tr>
	</table>
</form>
</body>
</html>