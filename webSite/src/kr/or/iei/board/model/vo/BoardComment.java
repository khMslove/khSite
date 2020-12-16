package kr.or.iei.board.model.vo;

import java.sql.Date;

public class BoardComment {
	private int commentNo;
	private int boardNo;
	private String content;
	private String userId;
	private Date regDate;
	private char delYN;
	
	
	public BoardComment() {
		super();
		// TODO Auto-generated constructor stub
	}
	public BoardComment(int commentNo, int boardNo, String content, String userId, Date regDate, char delYN) {
		super();
		this.commentNo = commentNo;
		this.boardNo = boardNo;
		this.content = content;
		this.userId = userId;
		this.regDate = regDate;
		this.delYN = delYN;
	}
	public int getCommentNo() {
		return commentNo;
	}
	public void setCommentNo(int commentNo) {
		this.commentNo = commentNo;
	}
	public int getBoardNo() {
		return boardNo;
	}
	public void setBoardNo(int boardNo) {
		this.boardNo = boardNo;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public Date getRegDate() {
		return regDate;
	}
	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}
	public char getDelYN() {
		return delYN;
	}
	public void setDelYN(char delYN) {
		this.delYN = delYN;
	}
	
	
	
	
	
}
