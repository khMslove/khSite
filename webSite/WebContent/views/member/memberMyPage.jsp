<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="kr.or.iei.member.model.vo.Member" %>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<style>
	fieldset {
	width : 300px;
	height : 400px;
	margin: 0 auto;
	}
	legend {
		text-align: center;
	}
</style>

<%
	Member m = (Member)session.getAttribute("member");
%>


		<form action="/memberUpdate.kh" method="post">
		<fieldset>
			<legend>나의정보</legend>
			회원 ID : <%=m.getUserId() %><br>
			비밀번호 : <input type="password" name="userPw" value="<%=m.getUserPw()%>" /><br>
			비밀번호(re) : <input type="password" name="userPw_re" value="<%=m.getUserPw()%>" /><br>
			이름 : <%=m.getUserName()%><br>
			나이 : <input type="text" name="age" size="3" value="<%=m.getAge()%>" /><br>
			이메일 : <input type="email" name="email" value="<%=m.getEmail()%>" /><br>
			휴대폰 : <input type="text" name="phone" value="<%=m.getPhone()%>" /><br>
			주소 : <input type="text" name="address" value="<%=m.getAddress()%>" /><br>
			성별 : 
				<%if(m.getGender()=='M'){ %>
				<input type="radio" name="gender" value="M" checked/>남
				<input type="radio" name="gender" value="F" />여 <br>
				<%}else{ %>
				<input type="radio" name="gender" value="M" />남
				<input type="radio" name="gender" value="F" checked/>여 <br>
				<%} %>
				
				<!-- jQuery CDN -->
			<script src="https://code.jquery.com/jquery-3.5.1.js" integrity="sha256-QWo7LDvxbWT2tbbQ97B53yJnYU3WhH/C8ycbRAkjPDc=" crossorigin="anonymous"></script>
			
			 	<!-- hobby(취미)를 처리하기 위한 javaScript(jQuery) 코드 -->
			<script>
				<% 
					String [] hobbyValues = m.getHobby().split(", "); //ex) 등산, 독서
				%>	
				$(function(){
					var js_hobbyValues = new Array();
					
					//DB(session)에서 java 코드로 가져온 취미값을 javaScript로 옮기는 코드
					<%
						for(int i=0;i<hobbyValues.length;i++){
					%>
						js_hobbyValues.push('<%=hobbyValues[i]%>');
					<%
						}
					%>
					
					
					//가져온 취미값을 javaScript에서 해당 태그를 찾아서 checked 부여하기
					var $boxs = $('input[name=hobby]'); //6개의 checkbox 타입인 input 태그를 가져옴
					
					
					//찾은 6개의 input 태그들을 각각 function 처리를 하여라. (filter 메소드)
					$boxs.filter(function(){
						
						
						//js_hobbyValues에 저장된 취미 만큼 반복문을 동작 시켜라
						for(var i=0;i<js_hobbyValues.length;i++)
						{
							//만약 js_hobbyValues의 i번에 있는 정보(ex.운동)와 현재 input 태그의 value가
							//같다면 checked를 하고, 아니면 checked를 하지 말아라.
							if(js_hobbyValues[i]==$(this).val()){
								$(this).prop('checked',true);
							}
						}
						
					});
					
				});	
					
			
			
			</script>		
				
			취미 : <br>
				<input type="checkbox" name="hobby" value="운동" />운동
				<input type="checkbox" name="hobby" value="등산" />등산
				<input type="checkbox" name="hobby" value="독서" />독서<br>
				<input type="checkbox" name="hobby" value="노래" />노래
				<input type="checkbox" name="hobby" value="댄스" />댄스
				<input type="checkbox" name="hobby" value="기타" />기타<br><br><br><br>
				<input type="submit" value="정보수정"/> <input type="reset" value="취소"/>
				<a href="/index.jsp">메인페이지로 돌아가기</a>
		</fieldset>
	
	</form>	






</body>
</html>