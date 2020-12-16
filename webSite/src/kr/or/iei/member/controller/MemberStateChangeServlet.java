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
 * Servlet implementation class MemberStateChangeServlet
 */
@WebServlet("/memberStateChanged.kh")
public class MemberStateChangeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MemberStateChangeServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//슈퍼관리자 계정으로 회원의 상태를 변경하려면 총 3가지 정보가 필요한
		
		// 해당 회원의 No값(userNo) , 해당 회원의 현재 상태값(endYN)
		// 그리고 권한을 가지고있는 슈퍼관리자 No값 (session안에 있음)
		
		//1. jsp페이지에서 넘어온값 저장 (userNo, endYN)
		int userNo = Integer.parseInt(request.getParameter("userNo"));
		char endYN = request.getParameter("endYN").charAt(0);
		
		//2. session(현재 로그인 한)에 있는 관리자 No값 가져오기
		HttpSession session = request.getSession();
		int rootNo = ((Member)session.getAttribute("member")).getUserNo();
		
		if(rootNo==0)
		{
			//최고관리자가 맞다면 비즈니스 로직 처리
			
			//현재 상태값을 바꾸어서 DB로 이동하도록 설정
			if(endYN=='N')
			{
				//현재 상태가 N이라면 Y로 바꾸어라
				endYN='Y';
			}else {
				//현재 상태가 Y라면 N으로 바꾸어라
				endYN='N';
			}
			
			//현재 상태를 변경하는 메소드 호출 (해당 유저번호와 상태값 보내주기)
			int result = new MemberService().memberStateUpdate(userNo,endYN); 
			if(result>0)
			{
				//상태변경이 정상적으로 처리 되었다면
				response.sendRedirect("/memberAllList.kh");
				
			}else
			{
				//상태변경이 정상적으로 처리되지 않았다면
				response.setCharacterEncoding("UTF-8");
				response.setContentType("text/html; charset=UTF-8");
				
				PrintWriter out = response.getWriter();
				
				out.println("<script>alert('회원 상태변경 실패 (지속적인 문제 발생시 개발자에게 문의해주세요)');</script>");
				out.println("<script>location.replace('/memberAllList.kh');</script>");
				
				
				
			}
			
			
			
		}else
		{
			//최고관리자가 아니라면 error 페이지로 안내
			response.sendRedirect("/views/common/error/error.jsp");
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
