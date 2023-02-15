package com.study.model;

import java.io.Serializable;
import java.util.Date;

public class EhcacheVO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4755441521470666165L;
	
	private String memberName;
	private int boardNo;
	private String title;
	private Date bregDate;
	private String isEnabled;
	private String memberNo;
	
	public String getMemberName() {
		return memberName;
	}
	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}
	public int getBoardNo() {
		return boardNo;
	}
	public void setBoardNo(int boardNo) {
		this.boardNo = boardNo;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Date getBregDate() {
		return bregDate;
	}
	public void setBregDate(Date bregDate) {
		this.bregDate = bregDate;
	}
	public String getIsEnabled() {
		return isEnabled;
	}
	public void setIsEnabled(String isEnabled) {
		this.isEnabled = isEnabled;
	}
	public String getMemberNo() {
		return memberNo;
	}
	public void setMemberNo(String memberNo) {
		this.memberNo = memberNo;
	}
	@Override
	public String toString() {
		return "EhcacheVO [memberName=" + memberName + ", boardNo=" + boardNo + ", title=" + title + ", regDate="
				+ bregDate + ", isEnabled=" + isEnabled + ", memberNo=" + memberNo + "]";
	}

	
}
