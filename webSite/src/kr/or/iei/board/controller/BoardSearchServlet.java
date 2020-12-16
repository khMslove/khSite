package kr.or.iei.board.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.or.iei.board.model.service.BoardService;
import kr.or.iei.board.model.vo.BoardPageData;

/**
 * Servlet implementation class BoardSearchServlet
 */
@WebServlet("/boardSearchList.kh")
public class BoardSearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BoardSearchServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//1. 인코딩  (보낸 키워드에 한글이 포함되어 있을 수 있기 때문에)
		request.setCharacterEncoding("utf-8");
		
		//2. 웹상에서 보내온 데이터를 저장
		String keyword = request.getParameter("keyword");
		
		//3. 현재 페이지 처리
		int currentPage;
		if(request.getParameter("currentPage") == null)
		{
			currentPage = 1;
		}else {
			currentPage = Integer.parseInt(request.getParameter("currentPage"));
		}
		
		//4. 비즈니스 로직 처리
		BoardPageData bpd = new BoardService().boardSearchList(keyword, currentPage);
		
		//5. 결과처리 (돌아온 결과를 jsp 페이지로 전달하면서 이동 - requestDispatcher)
		RequestDispatcher view = request.getRequestDispatcher("/views/board/boardSearchList.jsp");
		request.setAttribute("pageData", bpd);
		request.setAttribute("keyword", keyword);
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







