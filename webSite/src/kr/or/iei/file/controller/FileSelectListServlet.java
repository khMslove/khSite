package kr.or.iei.file.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.or.iei.file.model.service.FileService;
import kr.or.iei.file.model.vo.FileData;
import kr.or.iei.member.model.vo.Member;

/**
 * Servlet implementation class FileSelectListServlet
 */
@WebServlet("/fileList.kh")
public class FileSelectListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FileSelectListServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		//파일 리스트를 서블릿은 현재 이전 페이지에서 보내주는값은 없고,
		//해당 유저 ID를 가지고 비즈니스 로직 처리를 해야 함 (userId는 session에서 가져옴)
		
		HttpSession session = request.getSession();
		Member m = (Member)session.getAttribute("member");
		String userId = m.getUserId();
		
		
		//비즈니스 로직 처리
		ArrayList<FileData> list = new FileService().selectListFiles(userId);
		
		//결과처리 
		RequestDispatcher view = request.getRequestDispatcher("/views/file/fileList.jsp");
		request.setAttribute("list", list);
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
