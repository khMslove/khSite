<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="kr.or.iei.board.model.vo.Board" %>
<%@ page import="java.util.ArrayList" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<%
	//jsp페이지에서 사용하기 위하여 데이터를 꺼내는 작업
	ArrayList<Board> list = (ArrayList<Board>)request.getAttribute("boardList");

%>
<center>
	<table border="1px">
		<tr>
			<th>글번호</th>
			<th>글제목</th>
			<th>작성자</th>
			<th>작성일</th>
		</tr>
		
		<%for(Board board : list){ %>
		<tr>
			<td><%=board.getBoardNo()%></td>
			<td><%=board.getSubject()%></td>
			<td><%=board.getWriter()%></td>
			<td><%=board.getRegDate()%></td>
		</tr>
		<%} %>
		
	</table>
</center>



</body>
</html>









