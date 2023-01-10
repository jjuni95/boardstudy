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
		
		//회원번호
		String result = memberDAO.selectMemberNo();
		
		System.out.println("result= " + result);
		
//		for(int i = 0; i<result.length(); i++) {
//			System.out.println(result.charAt(10));
//		}
		
		String asdf = result.substring(10,11);    //A
		String aaaa = result.substring(11,16); //00004
		
		System.out.println("asdf= " + asdf);
		System.out.println("aaaa= " + aaaa);
		
		memberDAO.memberJoin(member);
	}

	//아이디 중복 검사
	@Override
	public int idCheck(String memberId) throws Exception {

		int result = memberDAO.idCheck(memberId);
		return result;
	}

	//로그인
	@Override
	public MemberVO memberLogin(MemberVO member) throws Exception {
		return memberDAO.memberLogin(member);
	}

	
	//패스워드 체크
//	@Override
//	public int passChk(MemberVO member) throws Exception {
//		int result = memberDAO.passChk(member);
//		return result;
		
//	}
	
	//회원번호(ex.CU + 20221125 + A + 00001)
	
}
