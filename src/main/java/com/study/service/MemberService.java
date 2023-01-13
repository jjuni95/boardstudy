package com.study.service;

import com.study.model.MemberVO;

public interface MemberService {

	//회원가입
	public void memberJoin(MemberVO member) throws Exception;

	//아이디 중복 검사
	public int idCheck(String memberId) throws Exception;
	
	//회원정보 체크 
	public MemberVO memberCheck(String memberId) throws Exception;
	
	//이메일 중복 검사
	public int emailCheck(String emailId,String inputEmail,String selectEmail) throws Exception;

	//로그인
	 public String memberLogin(MemberVO member) throws Exception;

	//회원정보 수정
	 public void memberUpdate(MemberVO member) throws Exception;
	 
	 //회원 탈퇴
	 public void memberDelete(String memberNo) throws Exception;
	 
	 //회원 조회
	 public MemberVO getMember(String memberNo) throws Exception;
	 
	 
	 
}
