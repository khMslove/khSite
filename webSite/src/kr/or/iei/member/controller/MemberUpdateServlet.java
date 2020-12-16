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
 * Servlet implementation class MemberUpdateServlet
 */
@WebServlet("/memberUpdate.kh")
public class MemberUpdateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public MemberUpdateServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// 1. 보내온 값에 대해 인코딩 처리
		request.setCharacterEncoding("utf-8");

		// 2. View Page에서 보낸 데이터를 자바 변수에 저장
		// 회원 정보 수정 로직을 진행하기 위하여 이전페이지에서 보내온 정보를 저장
		// 누구의 정보를 바꿀지에 대한 회원 구분 데이터가 필요함 - 유니크 속성을 가진 데이터여야 함 (userNo 혹은 userId)

		// Member m= (Member)session.getAttribute("member");
		// String userId = m.getUserId();
		HttpSession session = request.getSession();
		String userId = ((Member)session.getAttribute("member")).getUserId();

		String userPw = request.getParameter("userPw");
		int age = Integer.parseInt(request.getParameter("age"));
		String email = request.getParameter("email");
		String phone = request.getParameter("phone");
		String address = request.getParameter("address");
		char gender = request.getParameter("gender").charAt(0);
		String[] hobbyValues = request.getParameterValues("hobby");
		String hobby = hobbyValues[0];

		for (int i = 1; i < hobbyValues.length; i++) {
			hobby = hobby + ", " + hobbyValues[i];
		}

		Member m = new Member();
		m.setUserId(userId); // 1
		m.setUserPw(userPw); // 2
		m.setAge(age); // 3
		m.setEmail(email); // 4
		m.setPhone(phone); // 5
		m.setAddress(address); // 6
		m.setGender(gender); // 7
		m.setHobby(hobby); // 8
		
		
		//3. 비즈니스 로직 처리
		int result = new MemberService().updateMember(m);
		
		if(result>0)
		{
			response.sendRedirect("/myPageLoad.kh");
		}else {
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html; charset=UTF-8");
			
			PrintWriter out = response.getWriter();
			out.println("<script>alert('회원정보 변경 실패(지속적인 문제발생시 관리자에게 문의해주세요');</script>");
			out.println("<script>location.replace('/myPageLoad.kh');</script>");
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
