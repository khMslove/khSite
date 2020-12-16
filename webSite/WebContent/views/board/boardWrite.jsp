<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style>
	textarea {
	resize : none;
	font-size : 20px;
}
</style>
</head>
<body>
<!-- jQuery CDN -->
<script src="https://code.jquery.com/jquery-3.5.1.js" integrity="sha256-QWo7LDvxbWT2tbbQ97B53yJnYU3WhH/C8ycbRAkjPDc=" crossorigin="anonymous"></script>
<script>
	$(function(){
		$('#backBtn').click(function(){
			history.back(-1);
			//갱신된 페이지로 호출하려면 location.replace('/boardAllListPage.kh');
		});
	});
</script>

	<center>
		<H1>글 작성</H1>
		<form action="/boardWrite.kh" method="post">
			<textarea rows="1" cols="100" placeholder="제목을 작성하세요" name="subject"></textarea><br>
			<br>
			<textarea rows="20" cols="100" placeholder="내용을 작성하세요" name="content"></textarea><br>
			<br>
			<input type="submit" value="작성완료"/> <button type="button" id="backBtn">목록</button>
			<input type="reset" value="취소"/>
		</form>
	</center>



</body>
</html>





