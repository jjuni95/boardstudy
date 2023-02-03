package com.study.service;

import com.study.model.MemberVO;
import com.study.model.NewPwVO;

public interface MemberService {

	// 회원가입
	public void memberJoin(MemberVO member) throws Exception;

	// 아이디 중복 검사
	public int idCheck(String memberId) throws Exception;

	// 회원정보 체크
	public MemberVO memberCheck(String memberId) throws Exception;

	// 이메일 중복 검사
	public int emailCheck(String emailId, String inputEmail, String selectEmail) throws Exception;

	// 로그인
	public String memberLogin(MemberVO member) throws Exception;

	// 회원정보 수정
	public void memberUpdate(MemberVO member) throws Exception;

	// 회원 탈퇴
	public void memberDelete(String memberNo) throws Exception;

	// 회원 조회
	public MemberVO getMember(String memberNo) throws Exception;

	// 이메일로 아이디, 비밀번호 찾기
	public MemberVO findIdByEmail(String email) throws Exception;

	// 아이디찾는 이력 저장
	public void insertId(MemberVO member) throws Exception;

	// 비밀번호 찾는 이력 저장
	public void insertTempPw(MemberVO member) throws Exception;
	
	//작성자 가져오기(회원번호로 이름 조회하기)
	public String selectWriter(String memberNo) throws Exception;



}
