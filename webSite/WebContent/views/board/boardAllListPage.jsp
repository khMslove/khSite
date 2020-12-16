<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="kr.or.iei.board.model.vo.BoardPageData" %>
<%@ page import="kr.or.iei.board.model.vo.Board" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="kr.or.iei.member.model.vo.Member" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<%
	//Servlet에서 페이징 처리된 데이터를 꺼내는 코드
	BoardPageData bpd = (BoardPageData)request.getAttribute("pageData");
	ArrayList<Board> list = bpd.getList();
	String pageNavi = bpd.getPageNavi();
	
	
	
%>
	<center>
		<H1><a href="/boardAllListPage.kh">자유게시판</a></H1>
		<table >
			<tr>
				<th>글번호</th>
				<th>글제목</th>
				<th>글쓴이</th>
				<th>작성일</th>
			</tr>
			<%for(Board board : list){ %>
			<tr>
				<td><%=board.getBoardNo()%></td>
				<td><a href="/boardPostClick.kh?boardNo=<%=board.getBoardNo()%>"><%=board.getSubject()%></a></td>
				<td><%=board.getWriter()%></td>
				<td><%=board.getRegDate()%></td>
			</tr>
			<%} %>
			<tr>
				<td colspan="3" align="center">
					<form action="/boardSearchList.kh" method="get">
						<input type="text" name="keyword"/>
						<input type="submit" value="검색"/>
					</form>
				</td>
<% 
	Member m = (Member)session.getAttribute("member");
	//로그인을 했다면 Member객체가 리턴 되었을 것이고,
	//로그인을 안했다면 null리 리턴되었을 것
%>
		<%if(m!=null){ %>
				<td align="center">
					<form action="/views/board/boardWrite.jsp" method="post">
						<input type="submit" value="글쓰기"/>
					</form>
				</td>
		<%} %>		
				
			</tr>
			<tr>
				<td colspan="4" align="center"><%=pageNavi %></td>
			</tr>
		</table>
			<a href="/index.jsp">메인페이지로 이동</a>
	</center>



</body>
</html>








