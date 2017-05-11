<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Json 测试</title>
<script type="text/javascript" src="js/jquery-3.2.0.min.js"></script>
<script type="text/javascript" src="js/json-2.2.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	testRequestBody();
});
function testRequestBody(){
	$.ajax({
				url:"${pageContext.request.contextPath}/json/jsonTest",
				dataType : "json",
				type :"post",
				contentType:"application/json",
				data:JSON.stringify({ids:1,names:"springmvc 企业应用实战"}),
				async:false,
				success:function(data){
					console.log(data);
					$("#id").html(data.id);
					$("#name").html(data.name);
					$("#author").html(data.author);
				},
				error:function(){
					alert("数据发送失败");
				}
			});
}
</script>
</head>
<body>
编号：<span id="id" ></span><br>
书名: <span id="name"></span><br>
作者：<span id="author"></span><br>
</body>
</html>