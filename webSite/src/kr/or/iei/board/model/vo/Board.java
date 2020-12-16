package kr.or.iei.board.model.vo;

import java.sql.Date;

public class Board {
	private int boardNo;
	private String subject;
	private String content;
	private String writer;
	private Date regDate;
	private char delYN;
	
	
	public Board() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Board(int boardNo, String subject, String content, String writer, Date regDate, char delYN) {
		super();
		this.boardNo = boardNo;
		this.subject = subject;
		this.content = content;
		this.writer = writer;
		this.regDate = regDate;
		this.delYN = delYN;
	}
	//단축키 : alt + shift + s 누르면 나옵니다.
	public int getBoardNo() {
		return boardNo;
	}
	public void setBoardNo(int boardNo) {
		this.boardNo = boardNo;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getWriter() {
		return writer;
	}
	public void setWriter(String writer) {
		this.writer = writer;
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
