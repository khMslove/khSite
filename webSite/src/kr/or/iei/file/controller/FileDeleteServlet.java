package kr.or.iei.file.controller;

import java.io.File;
import java.io.IOException;

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
 * Servlet implementation class FileDeleteServlet
 */
@WebServlet("/fileDelete.kh")
public class FileDeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FileDeleteServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// 파일의 번호를 가져와야 함
		int fileNo = Integer.parseInt(request.getParameter("fileNo"));
		
		// 요청한 사용자의 ID를 가져와야 함 - session에서!!
		HttpSession session = request.getSession();
		Member m = (Member)session.getAttribute("member");
		String userId = m.getUserId();
		
		//파일을 추후에 삭제하기 위하여 파일 정보를 가져와야하는데, 중요한것은 가져올때 삭제로직보다는 먼저 진행해야 함
		FileData fd = new FileService().selectOneFileData(fileNo, userId); // O
		
		//비즈니스 로직 처리
		int result = new FileService().deleteFileData(fileNo, userId);
		
		
		// 삭제로직 이후에 진행하게 되면 null값만 돌아오게 됨 (부적합)
		//FileData fd = new FileService().selectOneFileData(fileNo, userId); // X

		//결과 처리 
		RequestDispatcher view = request.getRequestDispatcher("/views/file/fileDelete.jsp");
		
		if(result>0)
		{
			// 파일이 정상적으로 삭제 되었다면
			request.setAttribute("result", true);
			File file = new File(fd.getFilePath());
			file.delete(); //해당 파일을 연결하여 삭제
			
		}else
		{
			// 파일이 정상적으로 삭제 되지 않았다면
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





