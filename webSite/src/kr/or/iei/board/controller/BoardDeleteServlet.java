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
 * Servlet implementation class BoardDeleteServlet
 */
@WebServlet("/boardDelete.kh")
public class BoardDeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BoardDeleteServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//1. 페이지에서 보내온 데이터를 자바코드로 저장
		int boardNo = Integer.parseInt(request.getParameter("boardNo"));
		
		//2. session에서 해당 사용자의 ID값을 가져와야 함
		HttpSession session = request.getSession();
		Member m = (Member)session.getAttribute("member");
		String userId = m.getUserId();
		
		
		//데이터 확인 코드
		//System.out.println("보낸 글번호 : " + boardNo);
		//System.out.println("보낸 사용자 : " + userId);
		
		//3. 비즈니스 로직 처리
		int result = new BoardService().deleteBoard(boardNo,userId);
		
		//4. 결과처리
		RequestDispatcher view = request.getRequestDispatcher("/views/board/boardDelete.jsp");
		if(result>0)
		{
			request.setAttribute("result", true);
		}else
		{
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
