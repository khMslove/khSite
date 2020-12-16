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
 * Servlet implementation class BoardCommentInsertServlet
 */
@WebServlet("/boardCommentWrite.kh")
public class BoardCommentInsertServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BoardCommentInsertServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//1. 인코딩
		request.setCharacterEncoding("utf-8");
		
		//2. 이전 페이지에서 보내준 데이터를 가져와야 함
		int boardNo = Integer.parseInt(request.getParameter("boardNo"));
		String comment = request.getParameter("comment");
		
		//3. 작성자값(userId)을 가져오기 위해서 session에서 꺼내옴
		HttpSession session  = request.getSession();
		Member m = (Member)session.getAttribute("member");
		String userId = m.getUserId();
		
		//4. 비즈니스 로직 처리
		int result = new BoardService().insertBoardComment(boardNo,comment,userId);
		
		
		//5. 결과 처리
		
		RequestDispatcher view = request.getRequestDispatcher("/views/board/boardCommentWrite.jsp");
		if(result>0)
		{
			// 데이터 입력에 성공했다면?
			// 성공값(true)+boardNo를 보내주도록 하겠습니다.
			request.setAttribute("boardNo", boardNo);
			request.setAttribute("result", true);
		}else {
			// 데이터 입력에 실패했다면?
			// 실패값(false) + boardNo를 보내주도록 하겠습니다.
			
			request.setAttribute("boardNo", boardNo);
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
