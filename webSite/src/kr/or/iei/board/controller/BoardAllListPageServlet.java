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
 * Servlet implementation class BoardAllListPageServlet
 */
@WebServlet("/boardAllListPage.kh")
public class BoardAllListPageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BoardAllListPageServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		//게시판을 처음 접근하게 되면 그게 바로 1page 라는 생각을 가지고 있어야 합니다.
		int currentPage; //현재 페이지값을 가지고 있는 변수
		//이 변수는 페이지가 변경되면 변경된 페이지값을 가지고 있어야 하는 변수
		
		
		//게시판을 처음 접근하면 현재 페이지(currentPage 값)를 보내주지 않기 때문에 자동으로 1page라고 
		//처리하겠다 라는 의미이고, 추후에는 특정 페이지를 이동할때 값을 보내주게 되므로 그 값을 page로 처리 하겠다 라는 의미
		if(request.getParameter("currentPage")==null)
		{
			currentPage = 1;
		}else
		{
			currentPage = Integer.parseInt(request.getParameter("currentPage"));
		}
		
		//페이지를 가져올 수 있는 비즈니스 로직 처리
		BoardPageData bpd = new BoardService().selectAllBoardPage(currentPage);
		
		RequestDispatcher view = request.getRequestDispatcher("/views/board/boardAllListPage.jsp");
		request.setAttribute("pageData", bpd);
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
