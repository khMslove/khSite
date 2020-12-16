package kr.or.iei.board.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.or.iei.board.model.service.BoardService;
import kr.or.iei.board.model.vo.Board;
import kr.or.iei.board.model.vo.BoardComment;

/**
 * Servlet implementation class BoardOneSelectServlet
 */
@WebServlet("/boardPostClick.kh")
public class BoardOneSelectServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BoardOneSelectServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		//1. 파라미터값 가져오기
		int boardNo = Integer.parseInt(request.getParameter("boardNo"));
		
		//2. 비즈니스로직 처리
		Board board = new BoardService().selectOneBoard(boardNo);
		ArrayList <BoardComment> list = new BoardService().selectCommentBoard(boardNo);
		//Controller에서 Service를 호출하는데 하나의 서비스에서 글의 내용 + 댓글을 가져오도록 구현할것인지
		//Controller에서 2개의 Service 메소드를 호출해서 각 각의 글의 내용 / 댓글 을 따로 가져오도로 구현할 것인지
		
		
		//3. 결과 처리
		
		if(board != null)
		{
			//정상적으로 게시물을 읽어 왔다면!! (해당 게시물 데이터를 가지고 jsp 페이지로 이동)
			RequestDispatcher view = request.getRequestDispatcher("/views/board/postReadSuccess.jsp");
			request.setAttribute("board", board);
			request.setAttribute("commentList", list); //게시물을 정상적으로 읽어왔다면 댓글도 포함해서 페이지를 이동
			view.forward(request, response);
			
			
		}else {
			//정상적으로 게시물을 읽어오지 못하였다면!! (읽어오지 못했다라는 메시지를 출력하는 페이지로 보내라!)
			RequestDispatcher view = request.getRequestDispatcher("/views/board/postReadFail.jsp");
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
