package kr.or.iei.board.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.or.iei.board.model.service.BoardService;
import kr.or.iei.member.model.vo.Member;

/**
 * Servlet implementation class BoardCommentUpdateServlet
 */
@WebServlet("/boardCommentModify.kh")
public class BoardCommentUpdateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BoardCommentUpdateServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//1. 인코딩
		request.setCharacterEncoding("utf-8");
		
		//2. 이전 페이지에서 보내온값 저장
		int commentNo = Integer.parseInt(request.getParameter("commentNo"));
		String content = request.getParameter("co_content");
		//boardNo값은 서블릿 동작이 끝나면 다시 게시물로 돌아가기 위해 사용하는 값
		int boardNo = Integer.parseInt(request.getParameter("boardNo"));
		
		

		//3. 세션에서 요청자의 ID를 가져오도록 해야 함
		HttpSession session = request.getSession();
		Member m = (Member)session.getAttribute("member");
		String userId = m.getUserId();
		
		//4. 비즈니스 로직 처리
		int result = new BoardService().updateBoardComment(commentNo,content,userId);
		
		//5. 결과처리
		RequestDispatcher view = request.getRequestDispatcher("/views/board/boardCommentUpdate.jsp");
		
		//결과처리하고 돌아가려면 해당 게시물 번호가 있어야 하는데 현재 게시물 번호가 없어서 못돌아갑니다.
		request.setAttribute("boardNo", boardNo);
		
		if(result>0)
		{//댓글이 정상적으로 수정되었다면 true 값
			request.setAttribute("result", true);
		}else
		{//댓글이 정상적으로 수정되지 못하였다면 false 값
			request.setAttribute("result", false);
		}
	
		view.forward(request, response);
		
		
				
		
		
		
		
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
