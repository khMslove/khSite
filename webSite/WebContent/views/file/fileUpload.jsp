<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<%
	//결과값 가져오기
	boolean result = (boolean)request.getAttribute("result");
%>
	<script>
		<% if(result==true){%>
			alert("파일 업로드 완료");
		<%}else{%>
			alert("파일 업로드 실패(지속적인 문제 발생시 관리자에게 문의해주세요)");
		<%}%>
		location.replace("/views/file/fileUpPage.jsp");
	</script>


</body>
</html>




