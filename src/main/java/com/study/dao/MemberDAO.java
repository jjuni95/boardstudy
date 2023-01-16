package com.study.dao;

import com.study.model.MemberVO;

public interface MemberDAO {
	
	//회원가입
	public void memberJoin(MemberVO member) throws Exception;
	
	//아이디 중복 검사
	public int idCheck(String memberId) throws Exception;
	
	//비밀번호 검사(암호화 체크)
	public String pwCheck(String memberId) throws Exception;
	
	//회원정보 수정할때 비밀번호 검사
//	public int updatePwCheck()
	
	//이메일 중복 검사
	public int emailCheck(String email) throws Exception;
	
	//로그인
	public MemberVO memberLogin(MemberVO member) throws Exception;

	//회원정보 체크 
	public MemberVO memberCheck(String memberId) throws Exception;
	
	//REG_DATE가 최신인 MEMBER_NO 가져오기
	public String selectMemberNo() throws Exception;
	
	//회원정보 수정
	public void memberUpdate(MemberVO member) throws Exception;
	
	//회원 탈퇴
	public void memberDelete(String memberNo) throws Exception;
	
	//회원조회
	public MemberVO getMember(String memberNo) throws Exception;
}
