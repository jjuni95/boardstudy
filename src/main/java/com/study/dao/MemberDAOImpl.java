package com.study.dao;

import java.util.HashMap;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.study.model.MemberVO;

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



	// 패스워드 체크
//	@Override
//	public int passChk(MemberVO member) throws Exception {
//		int result = template.selectOne("MemberMapper.passChk", member);
//		return result;
//	}
}
