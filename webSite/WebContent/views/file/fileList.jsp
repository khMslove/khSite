<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="kr.or.iei.file.model.vo.FileData" %>
<%@ page import="java.util.ArrayList" %>    

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<%
	//서블릿에서 보낸 list를 꺼내기
	ArrayList<FileData> list = (ArrayList<FileData>)request.getAttribute("list");
%>

<!-- jQuery CDN  -->
<script src="https://code.jquery.com/jquery-3.5.1.js" integrity="sha256-QWo7LDvxbWT2tbbQ97B53yJnYU3WhH/C8ycbRAkjPDc=" crossorigin="anonymous"></script>

<script>
	$(function(){
		$('.deleteForm').submit(function(){
			
			var fileName = $(this).parent().siblings().first().text();
			var result = window.confirm("["+fileName+"] 파일을 삭제하시겠습니까?");
			if(result==true)
			{
				return true;
			}else
			{
				return false;
			}

		});
	});
</script>


	<center>
		<%if(!list.isEmpty()){ %>
			<!-- 리스트가 있을때 -->
			<H1>나의 파일 목록</H1>
			<table border="1px">
				<tr>
					<th>파일이름</th>
					<th>파일용량</th>
					<th>업로드시간</th>
					<th>다운로드</th>
					<th>삭제</th>
				</tr>
				<%for(FileData fd : list){ %>
					<tr>
						<td><%=fd.getOriginalFileName()%></td>
						<td><%=fd.getFileSize() %></td>
						<td><%=fd.getUploadTime() %></td>
						<td>
							<form action="/fileDownload.kh" method="post">
								<input type="hidden" name="fileNo" value="<%=fd.getFileNo()%>"/>
								<input type="submit" value="다운로드"/>
							</form>
						</td>
						<td>
							<form action="/fileDelete.kh" method="post" class="deleteForm">
								<input type="hidden" name="fileNo" value="<%=fd.getFileNo() %>"/>
								<input type="submit" value="삭제"/>
							</form>
						</td>
					</tr>
				<%} %>
			</table>
		<%}else{ %>
			<!-- 리스트가 없을때 -->
			<H1>현재 업로드된 파일이 없습니다.</H1>
			<H3>(업로드 하였는데 파일이 안보이신다면 관리자에게 문의해주세요)</H3>
		<%} %>
		<a href="/index.jsp">메인 페이지로 이동</a>
	</center>




</body>
</html>








