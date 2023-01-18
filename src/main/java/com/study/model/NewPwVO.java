package com.study.model;

public class NewPwVO {

	//회원번호
	private String memberNo;
	
	//임시비밀번호
	private String newPw;
	
	//현재날짜
	private String regDate;

	public String getMemberNo() {
		return memberNo;
	}

	public void setMemberNo(String memberNo) {
		this.memberNo = memberNo;
	}

	public String getNewPw() {
		return newPw;
	}

	public void setNewPw(String newPw) {
		this.newPw = newPw;
	}

	public String getRegDate() {
		return regDate;
	}

	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}

	@Override
	public String toString() {
		return "newPwVO [memberNo=" + memberNo + ", newPw=" + newPw + ", regDate=" + regDate + "]";
	}
	
	
}
