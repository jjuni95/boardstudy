package com.study.dao;

import java.util.HashMap;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.study.model.MemberVO;
import com.study.model.NewPwVO;

@Repository
public class MemberDAOImpl implements MemberDAO {

	// MyBatis sql 실행 메서드를 제공하는 객체
	@Autowired
	private SqlSessionTemplate template;

	//회원가입
	@Override
	public void memberJoin(MemberVO member) {
		template.selectOne("MemberMapper.memberJoin", member);
	}

	//아이디 중복 검사
	@Override
	public int idCheck(String memberId) throws Exception {
		int result = template.selectOne("MemberMapper.idCheck", memberId);
		return result;
	}
	
	//이메일 중복 검사
	@Override
	public int emailCheck(String email) throws Exception {
		int result = template.selectOne("MemberMapper.emailCheck", email);
		return result;
	}

	//로그인
	@Override
	public MemberVO memberLogin(MemberVO member) {
		HashMap<String, String> map = new HashMap<>();
		map.put("memberId", member.getMemberId());
		map.put("memberPassword", member.getMemberPassword());
		
		
		MemberVO returnMember = template.selectOne("MemberMapper.memberLogin", map);
		
		return returnMember;
	}

	//REG_DATE가 최신인 MEMBER_NO 가져오기
	@Override
	public String selectMemberNo() {
		String result = template.selectOne("MemberMapper.selectMemberNo");
		return result;
	}

	//비밀번호 일치
	@Override
	public String pwCheck(String memberId) throws Exception {
		String result = template.selectOne("MemberMapper.pwCheck", memberId);
		return result;
	}

	//회원조회
	@Override
	public MemberVO memberCheck(String memberId) {
		MemberVO mVO = template.selectOne("MemberMapper.memberLogin", memberId);
		return mVO;
	}

	//회원정보 수정
	//서비스에서 보낸 파라미터들을 memberUpdate(MemberVO member)에 담음..
	@Override
	public void memberUpdate(MemberVO member) {
		template.update("MemberMapper.memberUpdate", member);
	}

	//회원 탈퇴
	@Override
	public void memberDelete(String memberNo) throws Exception {
		template.update("MemberMapper.memberDelete", memberNo);
	}

	//회원조회
	@Override
	public MemberVO getMember(String memberNo) throws Exception {
		MemberVO mVO = template.selectOne("MemberMapper.getMember", memberNo);
		return mVO;
	}

	//아이디찾는 이력 저장
	@Override
	public void insertId(MemberVO member) throws Exception {
		template.selectOne("MemberMapper.insertId", member);
	}

	//이메일로 아이디, 비밀번호 찾기
	@Override
	public MemberVO findIdByEmail(String email) {
		MemberVO mVO = template.selectOne("MemberMapper.findIdByEmail", email);
		return mVO;
	}

	//비밀번호 찾는 이력 저장
	@Override
	public void insertTempPw(NewPwVO newPwVO) throws Exception {
		template.selectOne("MemberMapper.insertTempPw", newPwVO);
	}

	//임시비밀번호로 업데이트
	@Override
	public MemberVO updatePw(MemberVO member) {
		HashMap<String, String> map = new HashMap<>();
		map.put("memberNo", member.getMemberNo());
		map.put("tempPw", member.getMemberPassword());
		MemberVO returnMember = template.update("MemberMapper.updatePw", map);
		
		return returnMember;
	}

}
