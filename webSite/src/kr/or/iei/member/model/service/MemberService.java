package kr.or.iei.member.model.service;

import java.sql.Connection;
import java.util.ArrayList;

import kr.or.iei.common.JDBCTemplate;
import kr.or.iei.member.model.dao.MemberDAO;
import kr.or.iei.member.model.vo.Member;

public class MemberService {
	//Service의 역할
	// Connection 객체 생성 + DAO 연결
	
	MemberDAO mDAO = new MemberDAO();
	public Member loginMember(String userId, String userPw) {
		Connection conn = JDBCTemplate.getConnection();
		Member m = mDAO.loginMember(conn,userId,userPw);
		JDBCTemplate.close(conn);
		return m;
	}
	public int insertMember(Member m) {
		Connection conn = JDBCTemplate.getConnection();
		int result = mDAO.insertMember(conn,m);
		if(result>0)
		{
			JDBCTemplate.commit(conn);
		}else {
			JDBCTemplate.rollback(conn);
		}
		JDBCTemplate.close(conn);
		
		return result;
	}
	public int deleteMember(String userId) {
		Connection conn = JDBCTemplate.getConnection();
		int result = mDAO.deleteMember(conn,userId);	
		if(result>0)
		{
			JDBCTemplate.commit(conn);
		}else {
			JDBCTemplate.rollback(conn);
		}
		JDBCTemplate.close(conn);
		
		return result;
		
	}
	
	public int updateMember(Member m) {
		Connection conn = JDBCTemplate.getConnection();
		int result = mDAO.updateMember(conn,m);
		if(result>0)
		{
			JDBCTemplate.commit(conn);
		}else {
			JDBCTemplate.rollback(conn);
		}
		JDBCTemplate.close(conn);
		return result;
		
	}
	public ArrayList<Member> selectMemberAll() {
		Connection conn = JDBCTemplate.getConnection();
		ArrayList<Member> list = mDAO.selectMemberAll(conn);
		JDBCTemplate.close(conn);
		return list;
	}
	public int memberStateUpdate(int userNo, char endYN) {
		Connection conn = JDBCTemplate.getConnection();
		int result = mDAO.memberStateUpdate(conn,userNo,endYN);
		if(result>0)
		{
			JDBCTemplate.commit(conn);
		}else {
			JDBCTemplate.rollback(conn);
		}
		JDBCTemplate.close(conn);
		
		return result;
	}

}





