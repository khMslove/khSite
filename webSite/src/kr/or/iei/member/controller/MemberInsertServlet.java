package kr.or.iei.member.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.or.iei.member.model.service.MemberService;
import kr.or.iei.member.model.vo.Member;

/**
 * Servlet implementation class MemberInsertServlet
 */
@WebServlet("/memberJoin.kh")
public class MemberInsertServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MemberInsertServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		//1. 한글값이 있을 경우를 위해 인코딩 처리
		request.setCharacterEncoding("utf-8");
		
		//2. 이전 페이지에서 보낸 전송값을 꺼내서 자바에서 저장
		Member m = new Member();
		
		try {
			String userId = request.getParameter("userId");
			String userPw = request.getParameter("userPw");
			String userName = request.getParameter("userName");
			int age = Integer.parseInt(request.getParameter("age"));
			String email = request.getParameter("email");
			String phone = request.getParameter("phone");
			String address = request.getParameter("address");
			char gender = request.getParameter("gender").charAt(0);
			String [] hobbyValues = request.getParameterValues("hobby");
			String hobby = hobbyValues[0];
			
			for(int i=1;i<hobbyValues.length;i++)
			{
				hobby = hobby + ", " + hobbyValues[i];
			}
			
			m.setUserId(userId);			//1
			m.setUserPw(userPw);			//2
			m.setUserName(userName);		//3
			m.setAge(age);					//4
			m.setEmail(email);				//5
			m.setPhone(phone);				//6
			m.setAddress(address);			//7
			m.setGender(gender);			//8
			m.setHobby(hobby);				//9
			
			//3. 비즈니스 로직 처리
			int result = new MemberService().insertMember(m);
			
			if(result>0)
			{
				//회원가입 성공하면 성공 페이지로 이동
				//response.sendRedirect("/views/member/memberJoinSuccess.jsp");
				RequestDispatcher view = request.getRequestDispatcher("/views/member/memberJoinSuccess.jsp");
				view.forward(request, response);
				
				
			}else {
				//회원가입 실패하면 실패 페이지로 이동
				//response.sendRedirect("/views/member/memberJoinFail.jsp");
				RequestDispatcher view = request.getRequestDispatcher("/views/member/memberJoinFail.jsp");
				view.forward(request, response);
			}
			
			
		} catch (Exception e) {
			RequestDispatcher view = request.getRequestDispatcher("/views/member/memberJoinFail.jsp");
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
