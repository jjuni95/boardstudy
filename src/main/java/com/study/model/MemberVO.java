package com.study.model;

public class MemberVO {

	// MEMBER_NO 회원번호
	private String memberNo;

	// REG_DATE 현재날짜
	private String regDate;

	// MEMBER_ID 아이디
	private String memberId;

	// MEMBER_NAME 이름
	private String memberName;

	// MEMBER_PASSWORD 비밀번호
	private String memberPassword;

	// EMAIL 이메일
	private String email;			//전체 이메일
	private String emailId;			//이메일 id
	private String inputEmail;		//직접입력 이메일
	private String selectEmail;		//선택된 이메일
	

	// PHONE 핸드폰
	private String phone; //합쳐진 전체 핸드폰
	private String phone1;
	private String phone2;
	private String phone3;

	//ZIPCODE 우편번호
	private String zipcode;

	//STREETADR 기본주소
	private String streeAdr;

	//DETAILADR 상세주소
	private String detailAdr;

	//ISENABLED 삭제여부
	private String isenabled;
	
	//수정날짜
	private String updateDate;

	
	public String getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}

	public String getMemberNo() {
		return memberNo;
	}

	public void setMemberNo(String memberNo) {
		this.memberNo = memberNo;
	}

	public String getRegDate() {
		return regDate;
	}

	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public String getMemberPassword() {
		return memberPassword;
	}

	public void setMemberPassword(String memberPassword) {
		this.memberPassword = memberPassword;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getInputEmail() {
		return inputEmail;
	}

	public void setInputEmail(String inputEmail) {
		this.inputEmail = inputEmail;
	}

	public String getSelectEmail() {
		return selectEmail;
	}

	public void setSelectEmail(String selectEmail) {
		this.selectEmail = selectEmail;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPhone1() {
		return phone1;
	}

	public void setPhone1(String phone1) {
		this.phone1 = phone1;
	}

	public String getPhone2() {
		return phone2;
	}

	public void setPhone2(String phone2) {
		this.phone2 = phone2;
	}

	public String getPhone3() {
		return phone3;
	}

	public void setPhone3(String phone3) {
		this.phone3 = phone3;
	}

	public String getZipcode() {
		return zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	public String getStreeAdr() {
		return streeAdr;
	}

	public void setStreeAdr(String streeAdr) {
		this.streeAdr = streeAdr;
	}

	public String getDetailAdr() {
		return detailAdr;
	}

	public void setDetailAdr(String detailAdr) {
		this.detailAdr = detailAdr;
	}

	public String getIsenabled() {
		return isenabled;
	}

	public void setIsenabled(String isenabled) {
		this.isenabled = isenabled;
	}

	@Override
	public String toString() {
		return "MemberVO [memberNo=" + memberNo + ", regDate=" + regDate + ", memberId=" + memberId + ", memberName="
				+ memberName + ", memberPassword=" + memberPassword + ", email=" + email + ", emailId=" + emailId
				+ ", inputEmail=" + inputEmail + ", selectEmail=" + selectEmail + ", phone=" + phone + ", phone1="
				+ phone1 + ", phone2=" + phone2 + ", phone3=" + phone3 + ", zipcode=" + zipcode + ", streeAdr="
				+ streeAdr + ", detailAdr=" + detailAdr + ", isenabled=" + isenabled + ", updateDate=" + updateDate
				+ "]";
	}






}
