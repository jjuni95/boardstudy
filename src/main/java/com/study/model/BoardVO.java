package com.study.model;

public class BoardVO {

	
	//게시판번호
	private int boardNo;
	//회원번호
	private String memberNo;
	//제목
	private String title;
	//내용
	private String content;
	//현재날짜
	private String regDate;
	//조회수
	private int hit;
	//삭제여부
	private String isenabled;
	//수정날짜
	private String updateDate;
	public int getBoardNo() {
		return boardNo;
	}
	public void setBoardNo(int boardNo) {
		this.boardNo = boardNo;
	}
	public String getMemberNo() {
		return memberNo;
	}
	public void setMemberNo(String memberNo) {
		this.memberNo = memberNo;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getRegDate() {
		return regDate;
	}
	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}
	public int getHit() {
		return hit;
	}
	public void setHit(int hit) {
		this.hit = hit;
	}
	public String getIsenabled() {
		return isenabled;
	}
	public void setIsenabled(String isenabled) {
		this.isenabled = isenabled;
	}
	public String getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}
	@Override
	public String toString() {
		return "BoardVO [boardNo=" + boardNo + ", memberNo=" + memberNo + ", title=" + title + ", content=" + content
				+ ", regDate=" + regDate + ", hit=" + hit + ", isenabled=" + isenabled + ", updateDate=" + updateDate
				+ "]";
	}

	
}
