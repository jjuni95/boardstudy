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
		
		//핸드폰번호 합치기
		member.setPhone(member.getPhone1() + member.getPhone2() + member.getPhone3());
		
		//이메일 합치기
		String selectEmail = member.getSelecetEmail();
		if(selectEmail == "1") {
			member.setEmail(member.getEmailId() + member.getInputEmail());
		} else 
			member.setEmail(member.getEmailId() + member.getSelecetEmail()); 
		
		memberDAO.memberJoin(member);
	}

	//아이디 중복 검사
	@Override
	public int idCheck(String memberId) throws Exception {

		int result = memberDAO.idCheck(memberId);
		return result;
	}

	//패스워드 체크
//	@Override
//	public int passChk(MemberVO member) throws Exception {
//		int result = memberDAO.passChk(member);
//		return result;
		
//	}
	
	//회원번호(ex.CU + 20221125 + A + 00001)
	
}
