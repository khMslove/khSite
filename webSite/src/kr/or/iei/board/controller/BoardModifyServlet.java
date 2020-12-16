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
 * Servlet implementation class BoardModifyServlet
 */
@WebServlet("/boardModify.kh")
public class BoardModifyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BoardModifyServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//1. 인코딩 
		request.setCharacterEncoding("utf-8");
		
		//2. 웹페이지에서 보내온 값을 자바코드로 저장
		int boardNo = Integer.parseInt(request.getParameter("boardNo"));
		String content = request.getParameter("content");
		
		//3. 요청자에 대한 userId값을 추출 (session)
		HttpSession session = request.getSession();
		Member m = (Member)session.getAttribute("member");
		String userId = m.getUserId(); //추후 db에서 writer값으로 사용될 예정
		
		//4. 비즈니스 로직 처리
		int result = new BoardService().updateBoard(boardNo,content,userId);
		
		if(result>0)
		{
			RequestDispatcher view = request.getRequestDispatcher("/views/board/boardModifySuccess.jsp");
			request.setAttribute("boardNo", boardNo);
			view.forward(request, response);
		}else {
			RequestDispatcher view = request.getRequestDispatcher("/views/board/boardModifyFail.jsp");
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
