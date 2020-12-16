<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="kr.or.iei.board.model.vo.Board"%>
<%@ page import="kr.or.iei.member.model.vo.Member"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="kr.or.iei.board.model.vo.BoardComment"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style>
#post {
	width: 400px;
	height: 500px;
	border: 1px solid black;
	margin: 0 auto;
}

#modifyBtn {
	color: blue;
	text-decoration: underline;
	cursor: pointer;
}

#deleteBtn {
	color: blue;
	text-decoration: underline;
	cursor: pointer;
}

#comment {
	width: 500px;
	margin: 0 auto;
}
</style>
</head>
<body>
	<%
		Board board = (Board) request.getAttribute("board");
	%>

	<div id="post">
		<center>
			<H4>
				글번호 :
				<%=board.getBoardNo()%>
				/ 글쓴이 :
				<%=board.getWriter()%>
				/ 작성일 :
				<%=board.getRegDate()%>
			</H4>
			<hr>
			<h2>
				글제목 :
				<%=board.getSubject()%></h2>
			<br>

			<form action="/boardModify.kh" method="post" id="modifyForm">
				<!-- 글 내용 textarea -->
				<textarea rows="20" cols="50" disabled="true" name="content"
					id="content" style="resize: none;"><%=board.getContent()%></textarea>
				<input type="hidden" name="boardNo" value="<%=board.getBoardNo()%>" />
			</form>
			<%
				Member m = (Member) session.getAttribute("member");
				//정상적으로 로그인을 한 사람이면 Member 객체를 가져올 것이고, 로그인을 안했다면 null을 가져오게 됨
			%>

			<!-- jQuery CDN -->
			<script src="https://code.jquery.com/jquery-3.5.1.js"
				integrity="sha256-QWo7LDvxbWT2tbbQ97B53yJnYU3WhH/C8ycbRAkjPDc="
				crossorigin="anonymous"></script>

			<script>
	$(function(){
		$('#modifyBtn').click(function(){
			var text=$(this).text();
			if(text=="수정"){
				$(this).text("완료");
				$('#content').removeAttr('disabled');
				$('#deleteBtn').text('취소');
				$('#content').focus(); // 작성하는 textArea로 포커스 되도록
			}else if(text=="완료")
			{
				$('#modifyForm').submit();
			}
			
			
		});
		$('#deleteBtn').click(function(){
			var text = $(this).text();
			if(text=='취소')
			{
			
				<%--
				location.href="/boardPostClick.kh?boardNo=<%=board.getBoardNo()%>";
				--%>
				
				location.replace("/boardPostClick.kh?boardNo=<%=board.getBoardNo()%>");
				
			}
			else if(text=='삭제')
			{
				var result = window.confirm("해당 글을 삭제하시겠습니까?");
				if(result==true)
				{
					//삭제하는 서블릿을 호출하면 됨 (boardNo를 가지고)
					location.href="/boardDelete.kh?boardNo=<%=board.getBoardNo()%>";
				}
				
			}
			
		});
		
	});
</script>


			<%
				if (m != null && m.getUserId().equals(board.getWriter())) {
			%>
			<a id="modifyBtn">수정</a>
			<%
				}
			%>
			<a href="/boardAllListPage.kh">목록</a>
			<%
				if (m != null && m.getUserId().equals(board.getWriter())) {
			%>
			<a id="deleteBtn">삭제</a>
			<%
				}
			%>
		</center>
	</div>



	<%
		ArrayList<BoardComment> list = (ArrayList<BoardComment>) request.getAttribute("commentList");
	%>


	<!-- 수정&삭제 버튼을 눌렀을때 동작하는 JS -->

	<script>
	$(function(){
		$('.commentModifyBtn').click(function(){
			var text = $(this).text();
			if(text=='수정')  //눌렀을때 수정이랑 버튼으로 되어 있었다면
			{

			//댓글 번호 가져오기
			var commentNo = $(this).next().attr('value');			
			//댓글 내용 가져오기
			var comment = $('#content_'+commentNo).text();
			$('#content_'+commentNo).html("<input type='text' name='comment' value='"+comment+"' >");
			$(this).text('완료');
			$(this).parent().next().children('button').text('취소');
			}else if(text=='완료'){
				//눌렀을때 완료라는 버튼으로 되어 있다면 Servlet을 호출하는 submit 동작
				//수정된 데이터를 가져와서 input type=hidden / name=co_content에 데이터를 넣고 서브밋 동작
				//var co_content= $('input[name=comment]').val();
				
				//수정된 데이터를 가져와서 hidden 값에 적용시킨 후
				var commentNo = $(this).next().attr('value');	
				var co_content = $('#content_'+commentNo).children().val();
				$('input[name=co_content]').val(co_content);
				
				
				//submit 동작을 시켜주겠다.	
				$(this).parent().submit();
				
				
				
			}
		});
		
		$('.commentDeleteBtn').click(function(){
			var text = $(this).text(); 
			if(text=='삭제')
			{
				//눌렀을때 삭제라는 버튼으로 되어있었다면 삭제라는 로직을 동작
				var result = window.confirm("해당 댓글을 삭제하시겠습니까?");
				if(result==true)
				{
					$(this).parent().submit();
				}
				
				
				
			}else if(text=='취소')
			{
				//눌렀을때 취소라는 버튼으로 되어있었다면! 해당 페이지를 재로드 혹은 수정기능 취소
				location.replace('/boardPostClick.kh?boardNo=<%=board.getBoardNo()%>');
				
			}
			
			
			
		});
		
		
	});
</script>







	<div id="comment">
		<center>

			<%
				if (m != null) {
			%>
			<form action="/boardCommentWrite.kh" method="post">
				comment : <input type="text" size="35" name="comment"
					placeholder="댓글을 작성해보세요" /> <input type="hidden" name="boardNo"
					value="<%=board.getBoardNo()%>" /> <input type="submit" value="작성" /><br>
			</form>
			<%
				} else {
			%>
			comment : 로그인후 댓글 작성을 이용해보세요! <br>
			<%
				}
			%>

			<%
				if (!list.isEmpty()) {
			%>
			<table>
				<tr>
					<th>댓글내용</th>
					<th>작성자</th>
					<th>작성일</th>
				</tr>

				<%
					for (BoardComment bc : list) {
				%>
				<tr>
					<td id="content_<%=bc.getCommentNo()%>"><%=bc.getContent()%></td>
					<td><%=bc.getUserId()%></td>
					<td><%=bc.getRegDate()%></td>

					<%
						if (m != null && m.getUserId().equals(bc.getUserId())) {
					%>
					<td>
						<!-- 수정 폼태그 -->
						<form action="/boardCommentModify.kh" method="post" style="display: inline">
							<button type="button" class="commentModifyBtn">수정</button>
							<input type="hidden" name="commentNo"
								value="<%=bc.getCommentNo()%>" />
							<input type="hidden" name="co_content" />
							<input type="hidden" name="boardNo" value="<%=board.getBoardNo()%>"/>
						</form> 
						<!-- 삭제 폼태그 -->
						<form action="/boardCommentDelete.kh" method="post" style="display: inline">
							<button type="button" class="commentDeleteBtn">삭제</button>
							<input type="hidden" name="commentNo"
								value="<%=bc.getCommentNo()%>" />
							<input type="hidden" name="boardNo" value="<%=board.getBoardNo()%>"/>
						</form>
					</td>
					<%
						}
					%>
				</tr>
				<%
					}
				%>
			</table>
			<%
				} else {
			%>
			<H3>댓글이 없습니다. 댓글을 작성해보세요</H3>
			<%
				}
			%>
		</center>
	</div>

</body>
</html>













