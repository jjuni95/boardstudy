package com.study.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.study.model.MemberVO;
import com.study.service.MemberService;

@Controller
@RequestMapping(value = "/member")
public class MemberController {

	@Autowired
	private MemberService memberservice;

	// 회원가입 페이지 이동
	@RequestMapping(value = "/join", method = RequestMethod.GET)
	public String joinGET() {

		System.out.println("회원가입 페이지 진입!!");

		return "/member/join";
	}

	// 회원가입
	@RequestMapping(value = "/join", method = RequestMethod.POST)
	public String joinPOST(MemberVO member) throws Exception {
		System.out.println("join 진입");

		// 회원가입 서비스 실행
		memberservice.memberJoin(member);
		System.out.println("join Service 성공?!");
		return "redirect:/main";

	}

	// 로그인 페이지 이동
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String loginGET() {

		System.out.println("로그인 페이지 진입...");

		return "/member/login";
	}

	
	// 아이디 중복 검사
	@ResponseBody	//추가해주지 않는다면 join.jsp로 메서드의 결과가 반환되지 않음
	@RequestMapping(value = "/memberIdChk", method = RequestMethod.POST)
	public int memberIdChkPOST(String memberId) throws Exception{
		
		System.out.println("memberIdChk() 진입");

		int result = memberservice.idCheck(memberId);
		
		System.out.println("결과값 = " + result);
		
		return result;
	} 
	
	
	// 패스워드 체크
//	@ResponseBody
//	@RequestMapping(value = "/passChk", method = RequestMethod.POST)
//	public int passChk(MemberVO member) throws Exception {
//		int result = memberservice.passChk(member);
//		return result;
//	}
}
