package kr.or.iei.member.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.or.iei.member.model.service.MemberService;
import kr.or.iei.member.model.vo.Member;

/**
 * Servlet implementation class MemberAllSelectServlet
 */
@WebServlet("/memberAllList.kh")
public class MemberAllSelectServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MemberAllSelectServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			//작업하기 전에 !! 보안상 관리자가 요청한게 맞는지 재체크를 하도록 하겠습니다.
			HttpSession session = request.getSession();
			Member m = (Member)session.getAttribute("member");
			
			// 비로그인자에 대해서 처리하는 방법은 2가지가 있음 (현재 코드는 2개 모두 처리 해준 코드)
			// 1. Exception 처리
			// 2. m!=null 아닌 경우만 처리하도록
			if(m!=null && 0==m.getUserNo() || (100 <= m.getUserNo() && m.getUserNo() <= 199))
			{
				//모든 회원을 가져오는 비즈니스 로직 처리를 하도록 하겠습니다. 
				ArrayList<Member> list = new MemberService().selectMemberAll();
				
				if(!(list.isEmpty()))
				{
					// 리스트가 비워져 있지 않다면
					// 데이터가 있는 상태에서 jsp 페이지를 호출해야 됨
					// (예전에는 본인의 정보이기 때문에 session을 사용 했음)
					// 데이터를 보내주면서 jsp 페이지를 호출해야 할때 사용하는 객체가 바로바로바로!!
					// RequestDispatcher
					
					RequestDispatcher view = request.getRequestDispatcher("/views/member/memberAllList.jsp");
					request.setAttribute("list",list);
					request.setAttribute("userNo", m.getUserNo());
					view.forward(request, response);
					
					
				}else {
					//리스트가 비워져 있다면
					response.setCharacterEncoding("UTF-8");
					response.setContentType("text/html; charset=UTF-8");
					
					PrintWriter out = response.getWriter();
					
					out.println("<script>alert('회원정보 읽어오기 실패');</script>");
					out.println("<script>location.replace('/index.jsp');</script>");
					
				}
				
				
				
				
			}else {
				//즉, 관리자가 아닌 유저가 요청했을 경우
				response.sendRedirect("/views/common/error/error.jsp");
			}
		} catch (Exception e) {
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
