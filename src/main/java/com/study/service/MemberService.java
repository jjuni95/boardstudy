package com.study.service;

import com.study.model.MemberVO;

public interface MemberService {

	public void memberJoin(MemberVO member) throws Exception;

	//아이디 중복 검사
	public int idCheck(String memberId) throws Exception;
	
	//패스워드 체크
	//public int passChk(MemberVO member) throws Exception;

}
