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
 * Servlet implementation class MemberLoginServlet
 */
@WebServlet(name = "MemberLogin", urlPatterns = { "/memberLogin.kh" })
public class MemberLoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MemberLoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//1. 이전 페이지에서 보내준 값을 Servlet에서 받을 수 있도록 해야 함
		String userId = request.getParameter("userId");
		String userPw = request.getParameter("userPw");
		
		
		//2. 비즈니스 로직 처리
		Member m = new MemberService().loginMember(userId, userPw);
		
		//3. 돌아온 결과에 따라 성공/실패 로직 처리 
		// 성공했으면 m객체 안에 Member 데이터가 있을 것이고, 실패 했으면 m객체 안에 null이 있는 상황
		
		
		//out객체를 통해서 한글을 정상적으로 전송하려면 인코딩 작업이 필요함
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		
		PrintWriter out = response.getWriter();
		
		if(m!=null) {
			//jsp에서는 session객체가 내장객체 이기 때문에 바로 사용이 가능하지만
			//Servlet은 내장객체가 아니기 때문에 만들고 나서 사용해야 함
			HttpSession session = request.getSession();
			session.setAttribute("member", m);
			
			out.println("<script>alert('로그인 성공');</script>");
		
		}else {
			out.println("<script>alert('로그인 실패 ID(또는 PW를 확인해주세요)');</script>");
			
		}
		// 로그인을 성공하거나 혹은 실패하거나 둘다 메인페이지로 이동하는 경우 
		// if문과 상관없이 /index.jsp 페이지로 이동 처리
		//response.sendRedirect("/index.jsp");
		out.println("<script>location.replace('/index.jsp');</script>");
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
