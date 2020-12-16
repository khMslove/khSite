package kr.or.iei.board.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sun.glass.ui.View;

import kr.or.iei.board.model.service.BoardService;
import kr.or.iei.member.model.vo.Member;

/**
 * Servlet implementation class BoardWriteServlet
 */
@WebServlet("/boardWrite.kh")
public class BoardWriteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BoardWriteServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//1. 인코딩 
		request.setCharacterEncoding("utf-8");
		
		//2. 웹에서 보내온 값을 자바코드로 저장
		String subject = request.getParameter("subject");
		String content = request.getParameter("content");
		
		//3. 작성자를 session에서 꺼내기
		HttpSession session = request.getSession();
		Member m = (Member)session.getAttribute("member");
		String writer = m.getUserId(); 
		
		//4. 비즈니스 로직 처리 (글 제목, 글 내용, 작성자 값을 가지고 Service를 호출)
		int result = new BoardService().insertBoard(subject,content,writer);
		
		if(result>0)
		{
			RequestDispatcher view = request.getRequestDispatcher("/views/board/boardWriteSuccess.jsp");
			view.forward(request, response);
			
		}else {
			RequestDispatcher view = request.getRequestDispatcher("/views/board/boardWriteFail.jsp");
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
