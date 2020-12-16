package kr.or.iei.member.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import kr.or.iei.common.JDBCTemplate;
import kr.or.iei.member.model.vo.Member;

public class MemberDAO {

	public Member loginMember(Connection conn, String userId, String userPw) {
			PreparedStatement pstmt = null;
			ResultSet rset = null;
			
			Member m = null;
		
			String query = "select * from member where userid=? and userpw=? and END_YN='N'";
			
			try {
				pstmt = conn.prepareStatement(query);
				pstmt.setString(1, userId);
				pstmt.setString(2, userPw);
				rset = pstmt.executeQuery();
				
				if(rset.next()) 
				{
					m = new Member();
					m.setUserNo(rset.getInt("userno"));					//1
					m.setUserId(rset.getString("userid"));				//2
					m.setUserPw(rset.getString("userpw"));				//3
					m.setUserName(rset.getString("userName"));			//4
					m.setAge(rset.getInt("age"));						//5
					m.setEmail(rset.getString("email"));				//6
					m.setPhone(rset.getString("phone"));				//7
					m.setAddress(rset.getString("address"));			//8
					m.setGender(rset.getString("gender").charAt(0));	//9
					m.setHobby(rset.getString("hobby"));				//10
					m.setEnrollDate(rset.getDate("enrolldate"));		//11
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally {
				JDBCTemplate.close(rset);
				JDBCTemplate.close(pstmt);
			}
			return m;
		
	}

	public int insertMember(Connection conn, Member m) {
		PreparedStatement pstmt = null;
		
		String query = "INSERT INTO MEMBER VALUES(MEMBER_SEQ.NEXTVAL,?,?,?,?,?,?,?,?,?,DEFAULT,'N')";
		int result = 0;
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, m.getUserId());
			pstmt.setString(2, m.getUserPw());
			pstmt.setString(3, m.getUserName());
			pstmt.setInt(4, m.getAge());
			pstmt.setString(5, m.getEmail());
			pstmt.setString(6, m.getPhone());
			pstmt.setString(7, m.getAddress());
			pstmt.setString(8, Character.toString(m.getGender()));
			pstmt.setString(9, m.getHobby());
			
			// executeQuery() -> SELECT 구문 일 때 사용
			// executeUpdate() -> INSERT, UPDATE, DELETE 구문 일 때 사용
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			JDBCTemplate.close(pstmt);
		}
		
		return result;
	}

	public int deleteMember(Connection conn, String userId) {
		PreparedStatement pstmt = null;
		
		String query = "UPDATE MEMBER SET END_YN='Y' WHERE userId=?";
		int result = 0;
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, userId);
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			JDBCTemplate.close(pstmt);
		}
		
		return result;
		
	}

	public int updateMember(Connection conn, Member m) {
		
		PreparedStatement pstmt = null;
		
		String query = "UPDATE MEMBER SET "
				+ "userPw =?, "
				+ "age = ?, "
				+ "email = ?, "
				+ "phone =?, "
				+ "address =?, "
				+ "gender =?, "
				+ "hobby =? WHERE userid=?";
		
		int result  = 0; //결과를 저장하는 변수
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, m.getUserPw());
			pstmt.setInt(2, m.getAge());
			pstmt.setString(3, m.getEmail());
			pstmt.setString(4, m.getPhone());
			pstmt.setString(5, m.getAddress());
			pstmt.setString(6, Character.toString(m.getGender()));
			pstmt.setString(7, m.getHobby());
			pstmt.setString(8, m.getUserId());
			result = pstmt.executeUpdate();	
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			JDBCTemplate.close(pstmt);
		}
		
		return result;
		
	}

	public ArrayList<Member> selectMemberAll(Connection conn) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		//회원정보를 담을 객체
		ArrayList<Member> list = new ArrayList<Member>();
		
		String query = "SELECT * FROM MEMBER ";	
		try {
			pstmt = conn.prepareStatement(query);
			rset = pstmt.executeQuery();		
			while(rset.next()) {
				Member m = new Member();
				m.setUserNo(rset.getInt("userno"));					//1
				m.setUserId(rset.getString("userid"));				//2
				m.setUserPw(rset.getString("userpw"));				//3
				m.setUserName(rset.getString("userName"));			//4
				m.setAge(rset.getInt("age"));						//5
				m.setEmail(rset.getString("email"));				//6
				m.setPhone(rset.getString("phone"));				//7
				m.setAddress(rset.getString("address"));			//8
				m.setGender(rset.getString("gender").charAt(0));	//9
				m.setHobby(rset.getString("hobby"));				//10
				m.setEnrollDate(rset.getDate("enrolldate"));		//11
				m.setEndYN(rset.getString("end_YN").charAt(0));		//12 (탈퇴 여부를 표시하기 위한값)
				list.add(m);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			JDBCTemplate.close(rset);
			JDBCTemplate.close(pstmt);
		}
		return list;

	}

	public int memberStateUpdate(Connection conn, int userNo, char endYN) {
		PreparedStatement pstmt = null;
		int result = 0;
		
		String query = "UPDATE MEMBER SET END_YN=? WHERE USERNO=?";
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, Character.toString(endYN));
			pstmt.setInt(2, userNo);
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			JDBCTemplate.close(pstmt);
		}
		return result;	
	}

}





