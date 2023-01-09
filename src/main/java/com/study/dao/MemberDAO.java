package com.study.dao;

import com.study.model.MemberVO;

public interface MemberDAO {
	
	//회원가입
	public void memberJoin(MemberVO member) throws Exception;
	
	//아이디 중복체크
	public int idCheck(MemberVO member) throws Exception;
	
	//패스워드 중복체크
	//public int passChk(MemberVO member) throws Exception;
}
