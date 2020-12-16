package kr.or.iei.board.model.vo;

import java.util.ArrayList;

public class BoardPageData {
	//페이징 처리된 데이터를 저장하는 vo 객체
	private ArrayList<Board> list;
	private String pageNavi;
	
	//메소드 만들어주세요
	
	//getter ,setter 메소드, 매개변수있는 생성자, 없는 생성자(default 생성자)
	
	public BoardPageData() {
		super();
		// TODO Auto-generated constructor stub
	}

	public BoardPageData(ArrayList<Board> list, String pageNavi) {
		super();
		this.list = list;
		this.pageNavi = pageNavi;
	}
	
	public ArrayList<Board> getList() {
		return list;
	}
	public void setList(ArrayList<Board> list) {
		this.list = list;
	}
	public String getPageNavi() {
		return pageNavi;
	}
	public void setPageNavi(String pageNavi) {
		this.pageNavi = pageNavi;
	}
	
	
	
	
}
