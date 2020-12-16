package kr.or.iei.file.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.or.iei.file.model.service.FileService;
import kr.or.iei.file.model.vo.FileData;
import kr.or.iei.member.model.vo.Member;

/**
 * Servlet implementation class FileDownloadServlet
 */
@WebServlet("/fileDownload.kh")
public class FileDownloadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FileDownloadServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// request.getParameter 이용해서 값을 가져와야 함
		int fileNo = Integer.parseInt(request.getParameter("fileNo"));
		
		// userId값을 가져와야 함
		HttpSession session = request.getSession();
		Member m = (Member)session.getAttribute("member");
		String userId = m.getUserId();
		
		// 위에 2개의 정보를 가져온 이유는? 비즈니스로직을 통하여 해당 파일의 정보를 가져오기 위해서
		// 정보를 가져오는 이유는 해당 파일이 저장된 경로를 알아내서 그 파일을 클라이언트에게 전달하기 위해서
		
		FileData fd = new FileService().selectOneFileData(fileNo,userId);
		
		
		// 위에까지 작업은 실제 다운로드를 구현하기 위한 준비 단계라고 생각할 수 있음
		// 아래 코드가 실제 다운로드를 구현하는 코드
		
		if(fd != null) 
		{
			// 요청한 파일이 정상적으로 존재한다면
			
			// 가져온 파일 정보를 가지고 해당 파일을 물리적으로 접근 해야 함
			File file = new File(fd.getFilePath());
			
			// 웹 브라우저를 통해서 문자열(String)이 아닌 데이터가 전송되려면 Binary 타입으로 처리 해야 함
			response.setContentType("application/octet-stream");
			
			// 파일의 사이즈를 전달 해주어야 함
			response.setContentLengthLong(fd.getFileSize());
			
			// 사용자에게 전달할 파일의 이름을 인코딩 해주어야 함
			// 이때, 파일은 해당 컴퓨터의 OS 포맷에 맞게 인코딩 해주어야 함 
			// windows는 기본적으로 ISO-8859-1
			String fileName = new String(fd.getOriginalFileName().getBytes(),"ISO-8859-1");
			
			// 파일이름을 http header를 통해서 전달
			response.setHeader("Content-Disposition", "attachment;filename="+fileName);
			
		
			
			// 위 코드까지는 파일이름 + 파일을 전송할 수 있는 웹 환경을 셋팅 했다고 보면 됨
			
			// 아래코드부터는 실제 파일이 가지고 있는 데이터들을 보내는 작업
			// 해당되는 파일의 데이터를 가져올 수 있는 통로(InputStream 생성)
			FileInputStream fileIn = new FileInputStream(file);
			
			// 클라리언트에게 데이터를 전달할 통로 생성 (outputStream 생성)
			ServletOutputStream out = response.getOutputStream();
			
			// 4KByte 씩 처리
			byte [] outputByte = new byte[4096];
			
			//inputStream으로 데이터를 읽어다가 output 스트림으로 전송하기
			while(fileIn.read(outputByte,0,4096) != -1)
			{
				out.write(outputByte,0,4096);
			}
			
			fileIn.close();
			out.close();
			
	
			
			
		}else
		{
			// 요청한 파일이 정상적으로 존재하지 않는다면
			RequestDispatcher view = request.getRequestDispatcher("/views/file/fileDownloadFailed.jsp");
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
