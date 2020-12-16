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
		//Servlet에서 보내온 결과값을 가지고 오기
		boolean result = (boolean)request.getAttribute("result");
	%>
	
	<script>
		<%if(result==true){%>
				alert("파일 삭제를 완료하였습니다.");
		<%}else{%>
				alert("파일 삭제를 실패하였습니다 (지속적인 문제 발생시 관리자에게 문의해주세요)");
		<%}%>
		location.replace("/fileList.kh");
	</script>
	

</body>
</html>



