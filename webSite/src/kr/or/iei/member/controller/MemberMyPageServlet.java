package kr.or.iei.member.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.or.iei.member.model.service.MemberService;
import kr.or.iei.member.model.vo.Member;

/**
 * Servlet implementation class MemberMyPageServlet
 */
@WebServlet("/myPageLoad.kh")
public class MemberMyPageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MemberMyPageServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// 해당 Servlet의 로직은 
		// 나의 정보를 가져와서 jsp 페이지를 통해서 보여주는 역할
		// 나의 정보는 어디서 가져오느냐? 1. session / 2. DB
		// DB에서 처리한다하더라도 DB에서 ID값 + PW값 추출해서 가져온 DB데이터로 session을 갱신
		
		HttpSession session = request.getSession();
		Member m = (Member)session.getAttribute("member");
		String userId = m.getUserId();
		String userPw = m.getUserPw();
		
		//갱신하기 위한 비즈니스 로직 처리
		m = new MemberService().loginMember(userId, userPw);
		session.setAttribute("member", m); //session 갱신
		
		//response.sendRedirect();
		//request.requestDispatcher();
	
		response.sendRedirect("/views/member/memberMyPage.jsp");
		
		
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
