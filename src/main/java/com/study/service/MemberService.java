package com.study.service;

import com.study.model.MemberVO;

public interface MemberService {

	//회원가입
	public void memberJoin(MemberVO member) throws Exception;

	//아이디 중복 검사
	public int idCheck(String memberId) throws Exception;
	
	//이메일 중복 검사
	public int emailCheck(String emailId,String inputEmail,String selectEmail) throws Exception;

	//로그인
	 public String memberLogin(MemberVO member) throws Exception;

	
	 
	 
}
