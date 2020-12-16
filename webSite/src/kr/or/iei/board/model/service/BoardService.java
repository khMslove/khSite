package kr.or.iei.board.model.service;

import java.sql.Connection;
import java.util.ArrayList;

import kr.or.iei.board.model.dao.BoardDAO;
import kr.or.iei.board.model.vo.Board;
import kr.or.iei.board.model.vo.BoardComment;
import kr.or.iei.board.model.vo.BoardPageData;
import kr.or.iei.common.JDBCTemplate;

public class BoardService {
	BoardDAO boardDAO = new BoardDAO();

	public ArrayList<Board> selectAllBoardList() {
		Connection conn = JDBCTemplate.getConnection();
		ArrayList<Board> list = boardDAO.selectAllBoardList(conn);
		JDBCTemplate.close(conn);

		return list;
	}

	public BoardPageData selectAllBoardPage(int currentPage) {
		Connection conn = JDBCTemplate.getConnection();
		int recordCountPerPage = 5; // 한 페이지당 몇개의 게시물이 보이게 될 것인지
		ArrayList<Board> list = boardDAO.selectAllBoardPageList(conn, currentPage, recordCountPerPage);

		int naviCountPerPage = 5; // pageNavi값이 몇개씩 보여줄 것인지
		String pageNavi = boardDAO.getPageNavi(conn, currentPage, recordCountPerPage, naviCountPerPage);

		// 리턴은 한번에 하나밖에 할 수 없기 때문에 2개의 데이터를 저장할 vo객체가 필요함
		BoardPageData bpd = new BoardPageData();
		bpd.setList(list);
		bpd.setPageNavi(pageNavi);
		JDBCTemplate.close(conn);
		
		return bpd;
	}

	public BoardPageData boardSearchList(String keyword, int currentPage) {
		Connection conn = JDBCTemplate.getConnection();
		int recordCountPerPage = 5; // 한 페이지당 몇개의 게시물이 보이게 될 것인지
		ArrayList<Board> list = boardDAO.boardSearchList(conn, currentPage, recordCountPerPage, keyword);

		int naviCountPerPage = 5; // pageNavi값이 몇개씩 보여줄 것인지
		String pageNavi = boardDAO.getPageNavi(conn, currentPage, recordCountPerPage, naviCountPerPage, keyword);

		// 리턴은 한번에 하나밖에 할 수 없기 때문에 2개의 데이터를 저장할 vo객체가 필요함
		BoardPageData bpd = new BoardPageData();
		bpd.setList(list);
		bpd.setPageNavi(pageNavi);
		JDBCTemplate.close(conn);
		
		return bpd;

	}

	public Board selectOneBoard(int boardNo) {
		Connection conn = JDBCTemplate.getConnection();
		Board board = boardDAO.selectOneBoard(conn, boardNo);
		JDBCTemplate.close(conn);
		
		return board;
		
	}

	public int insertBoard(String subject, String content, String writer) {
		Connection conn = JDBCTemplate.getConnection();
		int result = boardDAO.insertBoard(conn,subject,content,writer);
		if(result>0)
		{
			JDBCTemplate.commit(conn);
		}else
		{
			JDBCTemplate.rollback(conn);
		}
		JDBCTemplate.close(conn);
		
		return result;
	}

	public int updateBoard(int boardNo, String content, String userId) {
		Connection conn = JDBCTemplate.getConnection();
		int result = boardDAO.updateBoard(conn,boardNo,content,userId);
		if(result>0)
		{
			JDBCTemplate.commit(conn);
		}else {
			JDBCTemplate.rollback(conn);
		}
		JDBCTemplate.close(conn);
		
		return result;		
	}

	public int deleteBoard(int boardNo, String userId) {
		Connection conn = JDBCTemplate.getConnection();
		int result = boardDAO.deleteBoard(conn,boardNo,userId);
		if(result>0)
		{
			JDBCTemplate.commit(conn);
		}else {
			JDBCTemplate.rollback(conn);
		}
		JDBCTemplate.close(conn);
		
		return result;	
	}

	public ArrayList <BoardComment> selectCommentBoard(int boardNo) {
		Connection conn = JDBCTemplate.getConnection();
		ArrayList <BoardComment> list = boardDAO.selectCommentBoard(conn,boardNo);
		JDBCTemplate.close(conn);
		return list;
		
	}

	public int insertBoardComment(int boardNo, String comment, String userId) {
		Connection conn = JDBCTemplate.getConnection();
		int result = boardDAO.insertBoardComment(conn,boardNo,comment,userId);
		if(result>0)
		{
			JDBCTemplate.commit(conn);
		}else {
			JDBCTemplate.rollback(conn);
		}
		JDBCTemplate.close(conn);
		return result;
	}

	public int updateBoardComment(int commentNo, String content, String userId) {
		Connection conn = JDBCTemplate.getConnection();
		int result = boardDAO.updateBoardComment(conn,commentNo,content,userId);
		if(result>0)
		{
			JDBCTemplate.commit(conn);
		}else {
			JDBCTemplate.rollback(conn);
		}
		JDBCTemplate.close(conn);
		return result;
		
		
	}

	public int deleteBoardComment(int commentNo, String userId) {
		Connection conn = JDBCTemplate.getConnection();
		int result = boardDAO.deleteBoardComment(conn,commentNo,userId);
		if(result>0)
		{
			JDBCTemplate.commit(conn);
		}else
		{
			JDBCTemplate.rollback(conn);
		}
		JDBCTemplate.close(conn);
		
		return result;
	}

}










