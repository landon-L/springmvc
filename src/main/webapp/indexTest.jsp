<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<h2>数据绑定测试</h2>
<a href="pathVariableTeset/3">测试pathVariable[${requestScope.pathVariable}]</a><br/>
<a href="requestHeaderTest">测试requestHeader [${requestScope.requestHeader}]</a><br/>
<a href="cookieValueTest">测试cookieValue [${requestScope.cookieValue}]</a><br/>
<a href="json/jsonTest">测试jackson</a>
</body>
</html>
