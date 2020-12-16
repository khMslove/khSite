package kr.or.iei.file.controller;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import kr.or.iei.file.model.service.FileService;
import kr.or.iei.file.model.vo.FileData;
import kr.or.iei.member.model.vo.Member;

/**
 * Servlet implementation class FileInsertServlet
 */
@WebServlet("/fileUpload.kh")
public class FileInsertServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FileInsertServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 파일 업로드를 구현하려면 cos 라이브러리를 사용
		// cos 라이브러리를 이용해서 MultipartRequest 객체를 만듦
		// 객체를 만들때 필요한 값
		// 1. request 객체
		// 2. 실제 업로드 되는 경로 
		// 3. 최대 파일 사이즈 크기
		// 4. 인코딩 타입
		// 5. 중복 이름 정책 (new DefaultFileRenamePolicy() 객체 사용)
		
		
		// 최상위 디렉토리 (WebContent)로 부터의 파일이 업로드 되는 경로
		String uploadPath = "/resources/file/";
		
		// 현재 프로젝트에 대한 정보를 가지고 있는 객체
		ServletContext context = request.getServletContext(); 
		
		// ServletContext를 이용하여 실제 경로를 가져와야 함
		String realUploadPath = context.getRealPath(uploadPath);
		
		// 파일 사이즈 (숫자는 byte 단위)
		int uploadFileSizeLimit = 10 * 1024 * 1024; //10MB 까지 업로드 가능
		
		// 인코딩 값
		String encType = "UTF-8";
		
		
		// MultipartRequest 객체 생성 (생성하면서 마지막5번째 정책 설정 객체 만들기)
		
		MultipartRequest multi = new MultipartRequest(request,
												realUploadPath,
												uploadFileSizeLimit,
												encType,
												new DefaultFileRenamePolicy());
		 
		//위 코드까지 진행하면 파일 업로드 까지는 문제 없이 처리된 것
		
		//이제는 DB Table안에 저장할 정보를 추출하는 작업
		
		// 파일 이름 가져오기 
		String originalFileName = multi.getFilesystemName("file");
		
		// 업로드 유저 ID값 가져오기 (session에서 가져와야 함)
		HttpSession session = request.getSession();
		Member m = (Member)session.getAttribute("member");
		String fileUser = m.getUserId();  //userId가 업로드 유저 (fileUser)
		
		
		
		// 업로드 시간 만들기
		// 시간 포맷 및 현재 시간값 가져오기
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS"); //포맷 만들기
		long currentTime = Calendar.getInstance().getTimeInMillis(); //시간값 가져오기
		Timestamp uploadTime = Timestamp.valueOf(formatter.format(currentTime));

		// 원본 파일의 이름바꾸는 작업 (바꾸는 파일의 이름은 시간값_kh)
		// 기존에 만들어놓은 currentTime 변수를 통해서 파일 이름 수정하기 (File 객체 활용)
		
		//File 객체는 경로를 통해서 해당 파일을 연결하는 객체
		File file = new File(realUploadPath+"\\"+originalFileName);
		
		//File객체가 가지고 있능 renameTo 메소드를 통해서 파일의 이름을 바꿀 수 있음
		file.renameTo(new File(realUploadPath+"\\"+currentTime+"_kh"));
		String changedFileName = currentTime+"_kh";
		
		
		//File 객체를 통해 파일이름이 변경되면 새롭게 연결하는 파일 객체가 필요함
		File reNameFile = new File(realUploadPath+"\\"+changedFileName);
		String filePath = reNameFile.getPath();
		
		
		// 해당  업로드된 file의 사이즈
		long fileSize = reNameFile.length(); 
		
		
		//여기까지가 DB에 들어갈 값 셋팅 / 아래는 확인
		System.out.println("파일 이름 (원본) : " + originalFileName);
		System.out.println("파일 이름 (변경) : " + changedFileName);
		System.out.println("파일 경로 : " + filePath);
		System.out.println("파일 사이즈 : " + fileSize);
		System.out.println("업로드 유저 : " + fileUser);
		System.out.println("업로드 시간 : " + uploadTime);
		
		//6개의 데이터를 하나하나 보내도 되지만 vo 객체를 이용해서 한번에 Service로 보내도록 함
		FileData fd = new FileData();
		fd.setOriginalFileName(originalFileName);
		fd.setChangedFileName(changedFileName);
		fd.setFilePath(filePath);
		fd.setFileSize(fileSize);
		fd.setFileUser(fileUser);
		fd.setUploadTime(uploadTime);
		
		// 비즈니스로직 처리
		int result = new FileService().insertFile(fd);
		
		RequestDispatcher view = request.getRequestDispatcher("/views/file/fileUpload.jsp");
		
		if(result>0)
		{
			request.setAttribute("result", true);  //성공시 true 값을 전달
		}else
		{
			//비즈니스 로직 처리시 실패했다면 파일도 삭제를 해주어야 함
			reNameFile.delete(); //해당 파일을 삭제
			request.setAttribute("result", false); //실패시 false 값을 전달
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
