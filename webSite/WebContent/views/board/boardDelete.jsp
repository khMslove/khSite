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
		boolean result = (boolean)request.getAttribute("result");
	%>
	
	<script>
	<%if(result==true){ %>
		alert('해당 글을 삭제 완료 하였습니다.');
		location.replace('/boardAllListPage.kh');
	<%}else{ %>
		alert('해당 글 삭제가 실패하였습니다.');
		history.back(-1);
	<%} %>
	</script>

</body>
</html>









