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
	boolean result = (boolean)request.getAttribute("result");
%>

	<script>
		<%if(result==true){%>
		<!--작성을 성공했다면 바로 이동해라-->
		location.replace('/boardPostClick.kh?boardNo=<%=boardNo%>');
		<%}else{%>
		<!--작성을 실패했다면 실패 메시지를 띄워주고 이동해라 -->	
		alert('댓글 작성에 실패하였습니다 (지속적인 문제 발생시 관리자에게 문의해주세요)');
		location.replace('/boardPostClick.kh?boardNo=<%=boardNo%>');
		<%}%>
	</script>
	
	
	
	


</body>
</html>