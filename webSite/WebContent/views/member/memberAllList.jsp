<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="kr.or.iei.member.model.vo.Member"%>
<%@ page import="java.util.ArrayList"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<!-- 서블릿이 이 페이지를 호출하면서 request 객체를 넘겨주었다라고 생각하면 됩니다. -->
	<%
		ArrayList<Member> list = (ArrayList<Member>) request.getAttribute("list");
		int userNo = (int) request.getAttribute("userNo");
	%>

	<%
		if (userNo==0 || (100 <= userNo && userNo <= 199)) {
	%>
	<center>
		<H1>전체 회원 정보</H1>
		<table border="1px">
			<tr>
				<th>회원번호</th>
				<th>회원ID</th>
				<th>회원이름</th>
				<th>나이</th>
				<th>이메일</th>
				<th>전화번호</th>
				<th>주소</th>
				<th>성별</th>
				<th>취미</th>
				<th>가입일</th>
				<th>탈퇴여부</th>
				
				<%if(userNo==0){ %>
				<th>탈퇴상태</th>
				<%} %>
				
			</tr>
			<%for(Member m : list){ %>
			
				<%if(m.getEndYN()=='N'){ %>
					<tr style="background-color: skyblue;">
				<%}else{ %>	
					<tr style="background-color: red;">
				<%} %>
				
				<td><%=m.getUserNo() %></td>
				<td><%=m.getUserId() %></td>
				<td><%=m.getUserName() %></td>
				<td><%=m.getAge() %></td>
				<td><%=m.getEmail() %></td>
				<td><%=m.getPhone() %></td>
				<td><%=m.getAddress() %></td>
				<td>
					<%if(m.getGender()=='M'){ %> 남성 <%}else{ %> 여성 <%} %>
				</td>
				<td><%=m.getHobby() %></td>
				<td><%=m.getEnrollDate() %></td>
				<td>
					<%if(m.getEndYN()=='N'){ %> 사용중 <%}else{ %> 탈퇴 <%} %>
				</td>
				
				<%if(userNo==0){ %>
				<td>
					<form action="/memberStateChanged.kh" method="get">
					<input type="hidden" name="userNo" value="<%=m.getUserNo()%>"/>
					<input type="hidden" name="endYN" value="<%=m.getEndYN()%>"/>
					<input type="submit" value="<%=m.getEndYN()%>" style="width:100%;"/>
					</form>
				</td>
				<%} %>
				
			</tr>
			<%} %>
		</table>
		<a href="/index.jsp">메인페이지로 이동</a><br>
	</center>
	<%
		}
	%>









</body>
</html>
