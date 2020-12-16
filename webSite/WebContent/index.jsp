<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="kr.or.iei.member.model.vo.Member" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>MVC2 MS정보교육원</title>
</head>
<body>
<%
	Member m = (Member)session.getAttribute("member");
%>

<script src="https://code.jquery.com/jquery-3.5.1.js" integrity="sha256-QWo7LDvxbWT2tbbQ97B53yJnYU3WhH/C8ycbRAkjPDc=" crossorigin="anonymous"></script>
<script>
	$(function(){
		$('#withdrawBtn').click(function(){
			var result = window.confirm("정말로 탈퇴 하시겠습니까?");
			if(result==true)
			{
				//탈퇴를 하겠다고 하면 form태그가 기본적으로 가지고 있는 submit 이벤트를 발동 시켜라!
				//$('#memberDelForm').submit();
				
				$('#userPw').attr('type','password');
				$('#checkBtn').css('display','inline');
				//form 태그안에 Btn을 만들면 기본적으로 submit 특징을 가지고 있음
			}
			else{
				return false;
			}
		});
		
	});
</script>
<style>
	#withdrawBtn{
		color : blue;
		text-decoration: underline;
		cursor: pointer;
	}
</style>

<%if(m != null){ %>
	<b><a href="/myPageLoad.kh">[<%=m.getUserName()%>]</a></b> 님 환영합니다 <a href="/logout.kh">로그아웃</a> <br>
	<form action="/memberWithdraw.kh" method="post" id="memberDelForm">
		<span id="withdrawBtn">탈퇴하기</span>
		<input type="hidden" name="userPw" id="userPw" placeholder="비밀번호 입력" />
		<button style="display:none;" id="checkBtn">확인</button>
	</form>
	<a href="/myPageLoad.kh">마이페이지</a><br>
	<%if(m.getUserNo()==0 || (100<=m.getUserNo()&&m.getUserNo()<=199)){ %>
	<a href="/memberAllList.kh">회원관리(관리자 전용)</a>
	<%} %>
	<!-- 기존 로직 
	<a href="/memberWithdraw.kh" id="withdrawBtn">탈퇴하기</a> -->
	<br>
	<a href="/views/file/fileUpPage.jsp">파일 업로드 Page</a><br>
	<a href="/fileList.kh">파일 리스트</a><br>

<%}else{ %>
	<H1>Welcome!! JSP&Servlet Web Site!!!</H1>
	<form action="/memberLogin.kh" method="post">
		ID : <input type="text" name="userId" /><br>
		PW : <input type="password" name="userPw" /><br>
		<input type="submit" value="로그인" /> <input type="reset" value="취소"/>
		<a href="/views/member/memberJoin.jsp">회원가입</a>
	</form>
<%} %>
	<br>
	<a href="/boardAllList.kh">자유게시판(페이징 처리 없음)</a><br>
	<a href="/boardAllListPage.kh">자유게시판(페이징 처리 됨)</a><br>
	



	
</body>
</html>