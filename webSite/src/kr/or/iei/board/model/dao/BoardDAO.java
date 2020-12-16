package kr.or.iei.board.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import kr.or.iei.board.model.vo.Board;
import kr.or.iei.board.model.vo.BoardComment;
import kr.or.iei.common.JDBCTemplate;

public class BoardDAO {

	public ArrayList<Board> selectAllBoardList(Connection conn) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		ArrayList<Board> list = new ArrayList<Board>();

		String query = "SELECT * FROM BOARD WHERE DEL_YN='N'";

		try {
			pstmt = conn.prepareStatement(query);
			rset = pstmt.executeQuery();

			while (rset.next()) {
				Board board = new Board();
				board.setBoardNo(rset.getInt("boardNo"));
				board.setSubject(rset.getString("subject"));
				board.setContent(rset.getString("content"));
				board.setWriter(rset.getString("writer"));
				board.setRegDate(rset.getDate("regDate"));

				list.add(board);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(rset);
			JDBCTemplate.close(pstmt);
		}
		return list;
	}

	public ArrayList<Board> selectAllBoardPageList(Connection conn, int currentPage, int recordCountPerPage) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;

		ArrayList<Board> list = new ArrayList<Board>();

		// 여기서 필요한 로직이 바로 TOP-N 분석
		// 5개씩 가져오려면 BoardNo를 바탕으로 내림차순을 한 뒤 가장 상위 5개를 가져와야 하기 때문에
		// TOP-N 분석 형태가 필요

		/*
		 * SQL 구문 예시 -- 1page 라면 1~5 까지의 Row_Num에 해당되는 데이터를 보여주어라 SELECT * FROM (SELECT
		 * ROW_NUMBER() OVER(order by BOARDNO DESC) AS Row_Num ,BOARD.* FROM BOARD WHERE
		 * DEL_YN='N') WHERE Row_Num between 1 and 5;
		 * 
		 * -- 2page 라면 6~10 까지의 Row_Num에 해당되는 데이터를 보여주어라 SELECT * FROM (SELECT
		 * ROW_NUMBER() OVER(order by BOARDNO DESC) AS Row_Num ,BOARD.* FROM BOARD WHERE
		 * DEL_YN='N') WHERE Row_Num between 6 and 10;
		 * 
		 */
		// start와 end값을 구하는 공식
		// start = 현재페이지 * 보여줄 페이지 개수 - (보여줄 페이지 개수 -1)
		// end = 현재페이지 * 보여줄 페이지 개수
		// ex) 1page 라면? (recordCountPerPage = 5일때)
		// start = 1 * 5 - 4 (최종결과 : 1)
		// end = 1 * 5 (최종결과 : 5)

		// ex) 2page 라면? (recordCountPerPage = 5일때)
		// start = 2 * 5 - 4 (최종결과 : 6)
		// end = 2 * 5 (최종결과 : 10)

		// ex) 1page 라면? (recordCountPerPage = 10일때)
		// start = 1 * 10 - 9 (최종결과 : 1)
		// end = 1 * 10 (최종결과 : 10)

		int start = currentPage * recordCountPerPage - (recordCountPerPage - 1);
		int end = currentPage * recordCountPerPage;

		String query = "SELECT * FROM (SELECT ROW_NUMBER() OVER(order by BOARDNO DESC) " + "AS Row_Num ,BOARD.* "
				+ "FROM BOARD WHERE DEL_YN='N') WHERE Row_Num between ? and ?";

		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, start);
			pstmt.setInt(2, end);

			rset = pstmt.executeQuery();

			while (rset.next()) {
				Board board = new Board();
				board.setBoardNo(rset.getInt("boardNo"));
				board.setSubject(rset.getString("subject"));
				board.setContent(rset.getString("content"));
				board.setWriter(rset.getString("writer"));
				board.setRegDate(rset.getDate("regDate"));

				list.add(board);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(rset);
			JDBCTemplate.close(pstmt);
		}

		return list;

	}

	public String getPageNavi(Connection conn, int currentPage, int recordCountPerPage, int naviCountPerPage) {
		// 현재 변수를 재확인
		// currentPage : 현재 페이지를 가지고 있는 변수
		// recordCountPerPage : 한 페이지당 보여질 게시물의 개수
		// naviCountPerPage : pageNavi가 몇개씩 보여질 것인지에 대한 개수

		int postTotalCount = postTotalCount(conn); // 전체 게시물의 개수를 구하기 위한 메소드

		// 생성될 페이지 개수를 구하기
		// ex) postTotalCount에 108이라는 값이 있다면? 22개의 page가 생성 되면 됨
		// 108 / 5 -> 몫이 페이지가 되는데, 이때 나머지가 있으면 +1 없으면 +0
		// ex) 105 / 5 -> 몫: 21, 나머지 : 0 => 몫 + 0 => 21Page
		// ex) 108 / 5 -> 몫: 21, 나머지 : 3 => 몫 + 1 => 22Page

		int pageTotalCount; // 전체 페이지를 저장하는 변수
		if (postTotalCount % recordCountPerPage > 0) {
			pageTotalCount = postTotalCount / recordCountPerPage + 1;
			// ex) pageTotalCount = 108 / 5 + 1 -> 22 Page

		} else {
			pageTotalCount = postTotalCount / recordCountPerPage + 0;
			// ex) pageTotalCount = 105 / 5 + 0 -> 21 Page
		}

		// 자! 이제는 현재 페이지를 중심으로 pageNavi를 계산 해야 함 (ex. 총 record는 108개 / 총 페이지는 22page)
		// 만약 내가 현재 다1page에 있면 pageNavi는 어떻게 될까? (1~5 페이지를 가진 pageNavi)
		// 만약 내가 현재 3page에 있다면 pageNavi는 어떻게 될까? (1~5 페이지를 가진 pageNavi)
		// 만약 내가 현재 6page에 있다면 pageNavi는 어떻게 될까? (6~10 페이지를 가진 pageNavi)
		// 만약 내가 현재 12page에 있다면 pageNavi는 어떻게 될까? (11~15 페이지를 가진 pageNavi)
		// 만약 내가 현재 21page에 있다면 pageNavi는 어떻게 될까? (21~22 페이지를 가진 pageNavi)

		// 위 개념들을 바탕으로 현재 페이지를 중점으로 startNavi 페이지 번호와 endNavi 페이지 번호를 구할것임

		// startNavi = ((현재페이지-1)/보여질 navi개수) * 보여질navi개수 +1;
		int startNavi = ((currentPage - 1) / naviCountPerPage) * naviCountPerPage + 1;

		// 위에 공식으로 계산을 해보겠습니다.
		// ex) 1페이지 일 경우 (결과 : startNavi는 1이 나와야 함)
		// ((1-1)/5)*5+1; -> 1
		// ex) 3페이지 일 경우 (결과 : startNavi는 1이 나와야 함)
		// ((3-1)/5)*5+1; -> 1
		// ex) 6페이지 일 경우 (결과 : startNavi는 6이 나와야 함)
		// ((6-1)/5)*5+1; -> 6
		// ex) 12페이지 일 경우 (결과 : startNavi는 11이 나와야 함)
		// ((12-1)/5)*5+1; -> 11

		// endNavi = 시작navi번호 + 보여질 navi개수 -1;
		int endNavi = startNavi + naviCountPerPage - 1;

		// 위에 공식으로 계산을 해보겠습니다.
		// ex) 1 페이지 일 경우 (결과 : endNavi는 5가 나와야 함)
		// 1 + 5 -1 -> 5
		// ex) 3 페이지 일 경우 (결과 : endNavi는 5가 나와야 함)
		// 1 + 5 - 1 -> 5
		// ex) 12 페이지 일 경우 (결과 : endNavi는 15가 나와야 함)
		// 11 + 5 - 1 -> 15

		// 문제점 ex) 21페이지 일 경우 (결과 : endNavi는 22가 나와야 함)
		// 21 + 5 - 1 -> 25
		// 즉, 총 페이지수가 22이므로 22를 넘어가지 않도록 해야 함
		if (endNavi > pageTotalCount) {
			endNavi = pageTotalCount;
		}

		// 여기까지 계산되었으면 공식 계산은 끝났음!
		// 이제 pageNavi의 모양을 구성해야 함

		StringBuilder sb = new StringBuilder();

		// 만약 첫번째 pageNavi가 아니라면 ' < ' 모양을 추가해라 (첫번째 pageNavi이면 추가하지 말아라)
		// ex)
		// 1 2 3 4 5 (추가하면 안됨)
		// < 6 7 8 9 10 (추가해야 함)
		if (startNavi != 1) {
			sb.append("<a href='/boardAllListPage.kh?currentPage=" + (startNavi - 1) + "'><</a> ");
		}

		for (int i = startNavi; i <= endNavi; i++) {
			if (i == currentPage) {
				sb.append("<a href='/boardAllListPage.kh?currentPage=" + i + "'><b>" + i + "</b></a> ");
			} else {
				sb.append("<a href='/boardAllListPage.kh?currentPage=" + i + "'>" + i + "</a> ");
			}
		}

		// 만약 마지막 pageNavi가 아니라면 ' > ' 모양을 추가해라 (마지막 pageNavi이면 추가하지 말아라)
		if (endNavi != pageTotalCount) {
			sb.append("<a href='/boardAllListPage.kh?currentPage=" + (endNavi + 1) + "'>></a> ");
		}

		// System.out.println(sb);

		return sb.toString();

	}

	public int postTotalCount(Connection conn) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		int postTotalCount = 0;

		String query = "SELECT COUNT(*) as totalCount " + "FROM BOARD " + "WHERE DEL_YN='N'";

		try {
			pstmt = conn.prepareStatement(query);
			rset = pstmt.executeQuery();
			rset.next();
			postTotalCount = rset.getInt("totalCount");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(rset);
			JDBCTemplate.close(pstmt);
		}
		return postTotalCount;
	}

	public ArrayList<Board> boardSearchList(Connection conn, int currentPage, int recordCountPerPage, String keyword) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;

		ArrayList<Board> list = new ArrayList<Board>();

		// 여기서 필요한 로직이 바로 TOP-N 분석
		// 5개씩 가져오려면 BoardNo를 바탕으로 내림차순을 한 뒤 가장 상위 5개를 가져와야 하기 때문에
		// TOP-N 분석 형태가 필요

		/*
		 * SQL 구문 예시 -- 1page 라면 1~5 까지의 Row_Num에 해당되는 데이터를 보여주어라 SELECT * FROM (SELECT
		 * ROW_NUMBER() OVER(order by BOARDNO DESC) AS Row_Num ,BOARD.* FROM BOARD WHERE
		 * DEL_YN='N') WHERE Row_Num between 1 and 5;
		 * 
		 * -- 2page 라면 6~10 까지의 Row_Num에 해당되는 데이터를 보여주어라 SELECT * FROM (SELECT
		 * ROW_NUMBER() OVER(order by BOARDNO DESC) AS Row_Num ,BOARD.* FROM BOARD WHERE
		 * DEL_YN='N') WHERE Row_Num between 6 and 10;
		 * 
		 */
		// start와 end값을 구하는 공식
		// start = 현재페이지 * 보여줄 페이지 개수 - (보여줄 페이지 개수 -1)
		// end = 현재페이지 * 보여줄 페이지 개수
		// ex) 1page 라면? (recordCountPerPage = 5일때)
		// start = 1 * 5 - 4 (최종결과 : 1)
		// end = 1 * 5 (최종결과 : 5)

		// ex) 2page 라면? (recordCountPerPage = 5일때)
		// start = 2 * 5 - 4 (최종결과 : 6)
		// end = 2 * 5 (최종결과 : 10)

		// ex) 1page 라면? (recordCountPerPage = 10일때)
		// start = 1 * 10 - 9 (최종결과 : 1)
		// end = 1 * 10 (최종결과 : 10)

		int start = currentPage * recordCountPerPage - (recordCountPerPage - 1);
		int end = currentPage * recordCountPerPage;

		String query = "SELECT * FROM (SELECT ROW_NUMBER() OVER(order by BOARDNO DESC) " + "AS Row_Num ,BOARD.* "
				+ "FROM BOARD WHERE SUBJECT like ? AND DEL_YN='N') WHERE Row_Num between ? and ?";

		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, "%" + keyword + "%");
			pstmt.setInt(2, start);
			pstmt.setInt(3, end);

			rset = pstmt.executeQuery();

			while (rset.next()) {
				Board board = new Board();
				board.setBoardNo(rset.getInt("boardNo"));
				board.setSubject(rset.getString("subject"));
				board.setContent(rset.getString("content"));
				board.setWriter(rset.getString("writer"));
				board.setRegDate(rset.getDate("regDate"));

				list.add(board);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(rset);
			JDBCTemplate.close(pstmt);
		}

		return list;
	}

	public String getPageNavi(Connection conn, int currentPage, int recordCountPerPage, int naviCountPerPage,
			String keyword) {
		//키워드를 바탕으로 검색된 pageNavi를 만드는 메소드
		
		// 현재 변수를 재확인
		// currentPage : 현재 페이지를 가지고 있는 변수
		// recordCountPerPage : 한 페이지당 보여질 게시물의 개수
		// naviCountPerPage : pageNavi가 몇개씩 보여질 것인지에 대한 개수

		int postTotalCount = postSearchTotalCount(conn,keyword); // 전체 게시물의 개수를 구하기 위한 메소드

		// 생성될 페이지 개수를 구하기
		// ex) postTotalCount에 108이라는 값이 있다면? 22개의 page가 생성 되면 됨
		// 108 / 5 -> 몫이 페이지가 되는데, 이때 나머지가 있으면 +1 없으면 +0
		// ex) 105 / 5 -> 몫: 21, 나머지 : 0 => 몫 + 0 => 21Page
		// ex) 108 / 5 -> 몫: 21, 나머지 : 3 => 몫 + 1 => 22Page

		int pageTotalCount; // 전체 페이지를 저장하는 변수
		if (postTotalCount % recordCountPerPage > 0) {
			pageTotalCount = postTotalCount / recordCountPerPage + 1;
			// ex) pageTotalCount = 108 / 5 + 1 -> 22 Page

		} else {
			pageTotalCount = postTotalCount / recordCountPerPage + 0;
			// ex) pageTotalCount = 105 / 5 + 0 -> 21 Page
		}

		// 자! 이제는 현재 페이지를 중심으로 pageNavi를 계산 해야 함 (ex. 총 record는 108개 / 총 페이지는 22page)
		// 만약 내가 현재 다1page에 있면 pageNavi는 어떻게 될까? (1~5 페이지를 가진 pageNavi)
		// 만약 내가 현재 3page에 있다면 pageNavi는 어떻게 될까? (1~5 페이지를 가진 pageNavi)
		// 만약 내가 현재 6page에 있다면 pageNavi는 어떻게 될까? (6~10 페이지를 가진 pageNavi)
		// 만약 내가 현재 12page에 있다면 pageNavi는 어떻게 될까? (11~15 페이지를 가진 pageNavi)
		// 만약 내가 현재 21page에 있다면 pageNavi는 어떻게 될까? (21~22 페이지를 가진 pageNavi)

		// 위 개념들을 바탕으로 현재 페이지를 중점으로 startNavi 페이지 번호와 endNavi 페이지 번호를 구할것임

		// startNavi = ((현재페이지-1)/보여질 navi개수) * 보여질navi개수 +1;
		int startNavi = ((currentPage - 1) / naviCountPerPage) * naviCountPerPage + 1;

		// 위에 공식으로 계산을 해보겠습니다.
		// ex) 1페이지 일 경우 (결과 : startNavi는 1이 나와야 함)
		// ((1-1)/5)*5+1; -> 1
		// ex) 3페이지 일 경우 (결과 : startNavi는 1이 나와야 함)
		// ((3-1)/5)*5+1; -> 1
		// ex) 6페이지 일 경우 (결과 : startNavi는 6이 나와야 함)
		// ((6-1)/5)*5+1; -> 6
		// ex) 12페이지 일 경우 (결과 : startNavi는 11이 나와야 함)
		// ((12-1)/5)*5+1; -> 11

		// endNavi = 시작navi번호 + 보여질 navi개수 -1;
		int endNavi = startNavi + naviCountPerPage - 1;

		// 위에 공식으로 계산을 해보겠습니다.
		// ex) 1 페이지 일 경우 (결과 : endNavi는 5가 나와야 함)
		// 1 + 5 -1 -> 5
		// ex) 3 페이지 일 경우 (결과 : endNavi는 5가 나와야 함)
		// 1 + 5 - 1 -> 5
		// ex) 12 페이지 일 경우 (결과 : endNavi는 15가 나와야 함)
		// 11 + 5 - 1 -> 15

		// 문제점 ex) 21페이지 일 경우 (결과 : endNavi는 22가 나와야 함)
		// 21 + 5 - 1 -> 25
		// 즉, 총 페이지수가 22이므로 22를 넘어가지 않도록 해야 함
		if (endNavi > pageTotalCount) {
			endNavi = pageTotalCount;
		}

		// 여기까지 계산되었으면 공식 계산은 끝났음!
		// 이제 pageNavi의 모양을 구성해야 함

		StringBuilder sb = new StringBuilder();

		// 만약 첫번째 pageNavi가 아니라면 ' < ' 모양을 추가해라 (첫번째 pageNavi이면 추가하지 말아라)
		// ex)
		// 1 2 3 4 5 (추가하면 안됨)
		// < 6 7 8 9 10 (추가해야 함)
		if (startNavi != 1) {
			sb.append("<a href='/boardSearchList.kh?keyword="+keyword+"&currentPage=" + (startNavi - 1) + "'><</a> ");
		}

		for (int i = startNavi; i <= endNavi; i++) {
			if (i == currentPage) {
				sb.append("<a href='/boardSearchList.kh?keyword="+keyword+"&currentPage=" + i + "'><b>" + i + "</b></a> ");
			} else {
				sb.append("<a href='/boardSearchList.kh?keyword="+keyword+"&currentPage=" + i + "'>" + i + "</a> ");
			}
		}

		// 만약 마지막 pageNavi가 아니라면 ' > ' 모양을 추가해라 (마지막 pageNavi이면 추가하지 말아라)
		if (endNavi != pageTotalCount) {
			sb.append("<a href='/boardSearchList.kh?keyword="+keyword+"&currentPage=" + (endNavi + 1) + "'>></a> ");
		}

		// System.out.println(sb);

		return sb.toString();
	}

	private int postSearchTotalCount(Connection conn, String keyword) {
		//키워드를 통해 검색된 게시물의 총 개수를 구하는 메소드
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		int postTotalCount = 0;

		String query = "SELECT COUNT(*) as totalCount " + "FROM BOARD " 
						+ "WHERE SUBJECT like ? AND DEL_YN='N'";

		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, "%"+keyword+"%");
			rset = pstmt.executeQuery();
			rset.next();
			postTotalCount = rset.getInt("totalCount");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(rset);
			JDBCTemplate.close(pstmt);
		}
		return postTotalCount;
	}

	public Board selectOneBoard(Connection conn, int boardNo) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		Board board = null;
		
		String query = "SELECT * FROM BOARD WHERE boardNo=? AND DEL_YN='N'";
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, boardNo);
			rset = pstmt.executeQuery();
			
			if(rset.next()) 
			{
				board = new Board();
				board.setBoardNo(rset.getInt("boardNo"));
				board.setSubject(rset.getString("subject"));
				board.setContent(rset.getString("content"));
				board.setWriter(rset.getString("writer"));
				board.setRegDate(rset.getDate("regDate"));
			}	
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			JDBCTemplate.close(rset);
			JDBCTemplate.close(pstmt);
		}
		return board;
		
	}

	public int insertBoard(Connection conn, String subject, String content, String writer) {
		PreparedStatement pstmt = null;
		int result = 0;
		String query = "INSERT INTO BOARD VALUES(BOARD_SEQ.NEXTVAL,?,?,?,SYSDATE,'N')";
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, subject);
			pstmt.setString(2, content);
			pstmt.setString(3, writer);
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			JDBCTemplate.close(pstmt);
		}
		
		return result;
	}

	public int updateBoard(Connection conn, int boardNo, String content, String userId) {
			PreparedStatement pstmt = null;
			int result = 0;
			String query = "UPDATE BOARD SET CONTENT=? WHERE BOARDNO=? AND WRITER=?";
			
			try {
				pstmt = conn.prepareStatement(query);
				
				pstmt.setString(1, content);
				pstmt.setInt(2, boardNo);
				pstmt.setString(3, userId);
				result = pstmt.executeUpdate(); 
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally {
				JDBCTemplate.close(pstmt);
			}
			return result;
			
		
	}

	public int deleteBoard(Connection conn, int boardNo, String userId) {
		PreparedStatement pstmt = null;
		int result = 0;
		String query = "UPDATE BOARD SET DEL_YN='Y' WHERE BoardNo=? AND WRITER=?";
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, boardNo);
			pstmt.setString(2, userId);
			result = pstmt.executeUpdate();			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			JDBCTemplate.close(pstmt);
		}
		return result;		
		
	}

	public ArrayList <BoardComment> selectCommentBoard(Connection conn, int boardNo) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		//여러개의 댓글을 저장할 수 있는 list
		ArrayList <BoardComment> list = new ArrayList<BoardComment>();
		
		String query = "SELECT * FROM BoardComment WHERE DEL_YN='N' AND boardNo=? ORDER BY commentNo DESC";
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, boardNo);
			rset = pstmt.executeQuery();
			
			while(rset.next())
			{
				BoardComment bc = new BoardComment();
				bc.setCommentNo(rset.getInt("commentNo"));
				bc.setBoardNo(rset.getInt("boardNo"));
				bc.setContent(rset.getString("content"));
				bc.setUserId(rset.getString("userId"));
				bc.setRegDate(rset.getDate("regDate"));
				
				list.add(bc);
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

	public int insertBoardComment(Connection conn, int boardNo, String comment, String userId) {
		PreparedStatement pstmt = null;
		int result = 0;
		String query = "INSERT INTO BoardComment VALUES(BC_SEQ.NEXTVAL,?,?,?,SYSDATE,'N')";
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, boardNo);
			pstmt.setString(2, comment);
			pstmt.setString(3, userId);
			result = pstmt.executeUpdate();	
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			JDBCTemplate.close(pstmt);
		}
		return result;
		
	}

	public int updateBoardComment(Connection conn, int commentNo, String content, String userId) {
		PreparedStatement pstmt = null;
		int result = 0;
		String query = "UPDATE BoardComment SET content=? WHERE commentNo=? AND userId=?";
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, content);
			pstmt.setInt(2, commentNo);
			pstmt.setString(3, userId);
			result = pstmt.executeUpdate();			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			JDBCTemplate.close(pstmt);
		}
		
		return result;
		
	}

	public int deleteBoardComment(Connection conn, int commentNo, String userId) {
		PreparedStatement pstmt = null;
		int result = 0;
		
		String query = "UPDATE BoardComment SET DEL_YN='Y' WHERE CommentNo=? AND userId=?";
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, commentNo);
			pstmt.setString(2, userId);
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








