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
	int boardNo = (int)request.getAttribute("boardNo");
%>

	<script>
		alert("글 수정 완료");
		location.replace("/boardPostClick.kh?boardNo=<%=boardNo%>");
	</script>



</body>
</html>