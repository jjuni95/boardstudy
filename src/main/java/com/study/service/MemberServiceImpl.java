package com.study.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.study.dao.MemberDAO;
import com.study.model.MemberVO;

@Service
public class MemberServiceImpl implements MemberService{

	@Autowired
	MemberDAO memberDAO;
	
	@Override
	public void memberJoin(MemberVO member) throws Exception{
		
		member.setPhone(member.getPhone1() + member.getPhone2() + member.getPhone3());
		
		//여기에 email 합치기
		
		memberDAO.memberJoin(member);
	}

	//아이디 중복 검사
	@Override
	public int idCheck(MemberVO member) throws Exception {

		int result = memberDAO.idCheck(member);
		return result;
	}

	//패스워드 체크
//	@Override
//	public int passChk(MemberVO member) throws Exception {
//		int result = memberDAO.passChk(member);
//		return result;
		
//	}
}
