package com.study.dao;

import com.study.model.MemberVO;

public interface MemberDAO {
	
	//회원가입
	public void memberJoin(MemberVO member) throws Exception;
	
	//아이디 중복 검사
	public int idCheck(String memberId) throws Exception;
	//비밀번호 검사
	public String pwCheck(String memberId) throws Exception;
	
	//이메일 중복 검사
	public int emailCheck(String email) throws Exception;
	
	//로그인
	public MemberVO memberLogin(MemberVO member);

	//REG_DATE가 최신인 MEMBER_NO 가져오기
	public String selectMemberNo();
}
