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
 * Servlet implementation class BoardCommentDeleteServlet
 */
@WebServlet("/boardCommentDelete.kh")
public class BoardCommentDeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BoardCommentDeleteServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//view 페이지에서 전송된 2개의 값을 가져 와야 함 (commentNo, boardNo)
		int commentNo = Integer.parseInt(request.getParameter("commentNo"));
		int boardNo = Integer.parseInt(request.getParameter("boardNo"));
		
		//session에서 해당 삭제 요청자의 ID값도 가져 와야 함 (userId)
		HttpSession session = request.getSession();
		Member m = (Member)session.getAttribute("member");
		String userId = m.getUserId();
		
		//데이터가 잘 넘어오는지 확인
		//System.out.println("댓글 번호 : " + commentNo);
		//System.out.println("게시물 번호 : " + boardNo);
		//System.out.println("작성자 ID : " + userId);
		
		//비즈니스 로직 처리
		int result = new BoardService().deleteBoardComment(commentNo,userId);
		
		//결과 처리
		if(result>0)
		{
			response.sendRedirect("/boardPostClick.kh?boardNo="+boardNo);
		}else
		{
			RequestDispatcher view = request.getRequestDispatcher("/views/board/boardCommentDeleteFail.jsp");
			request.setAttribute("boardNo", boardNo);
			view.forward(request, response);
		}
		
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
