package kr.or.iei.member.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.or.iei.member.model.service.MemberService;
import kr.or.iei.member.model.vo.Member;

/**
 * Servlet implementation class MemberDeleteServlet
 */
@WebServlet("/memberWithdraw.kh")
public class MemberDeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MemberDeleteServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//기존 탈퇴 (ID값을 가지고 탈퇴)
		//뉴 로직 탈퇴 (입력한 PW값을 가지고 비교한뒤, 맞으면 ID값을 가지고 탈퇴) 
		
		//1. 입력값 가져오기 (PW값)
		String userPw = request.getParameter("userPw");
		
		//입력한 값이 비밀번호가 맞는지 확인하는 방법은 2가지가 있음 (1. session / 2. DB)
		HttpSession session = request.getSession();
		Member m = (Member)session.getAttribute("member");
		
		if(userPw.equals(m.getUserPw())) 
		{
			//사용자가 입력한 값과 실제 session에 저장된 값이 일치한다면! (맞다면)
			//userId값을 추출해서 탈퇴하는 비즈니스 로직 처리
			String userId = m.getUserId();
			
			int result = new MemberService().deleteMember(userId);
			
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html; charset=UTF-8");
			
			PrintWriter out = response.getWriter();
			
			if(result>0)
			{
				out.println("<script>alert('탈퇴처리 완료');</script>");
				session.invalidate(); //탈퇴가 되었다면 세션을 파기해라
				out.println("<script>location.replace('/index.jsp');</script>");
			}else {
				out.println("<script>alert('탈퇴오류 발생(지속적인 문제 발생시 관리자에게 문의)');</script>");
				out.println("<script>location.replace('/index.jsp');</script>");
			}
		}else {
			//사용자가 입력한 값과 실제 session에 저장된 값이 다르다면! (비밀번호가 틀렸다면!)
			
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html; charset=UTF-8");
			
			PrintWriter out = response.getWriter();
			
			out.println("<script>alert('비밀번호가 일치하지 않습니다');</script>");
			out.println("<script>location.replace('/index.jsp');</script>");
			
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
