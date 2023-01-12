package com.study.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
		System.out.println("member.getMemberName()= " + member.getMemberName());

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
	@ResponseBody // 추가해주지 않는다면 join.jsp로 메서드의 결과가 반환되지 않음
	@RequestMapping(value = "/memberIdChk", method = RequestMethod.POST)
	public int memberIdChkPOST(String memberId) throws Exception {

		System.out.println("memberIdChk() 진입");

		int result = memberservice.idCheck(memberId);

		System.out.println("결과값 = " + result);

		return result;
	}
	
	
	// 이메일 중복 검사
	@ResponseBody
	@RequestMapping(value = "/memberEmailChk", method = RequestMethod.POST)
	public int memberEmailChkPOST(String emailId,String inputEmail,String selectEmail) throws Exception {

		System.out.println("memberEmailChk() 진입");

		int result = memberservice.emailCheck(emailId,inputEmail,selectEmail);

		System.out.println("결과값 = " + result);

		return result;
	}

	//로그인
	@RequestMapping(value = "login", method = RequestMethod.POST)
	public String loginPOST(HttpServletRequest request, MemberVO member, RedirectAttributes rttr, Model model) throws Exception {

		System.out.println("login 메서드 진입");
		//System.out.println("전달된 데이터 : " + member);

		HttpSession session = request.getSession(); //세션에서 가져올때 얘 꼭 붙여넣기!!!!
		//로그인 여부 체크: id랑 pw를 보낸다(memberLogin여기서)
		String flag = memberservice.memberLogin(member);
		
//		model.addAttribute("result", 0);
//		 return "/member/login";
		 
		//로그인 실패했을 경우
        if(flag == "fail") {                                // 일치하지 않는 아이디, 비밀번호 입력 경우
            
            int result = 0;
            //request.setAttribute("result", result); //"name", value
            model.addAttribute("result", result);
            
            request.setAttribute("msg", "로그인에 실패했습니당.");
        	request.setAttribute("url", "/member/login");
        	return "member/alert"; //alert.jsp로 이동
   //         return "/member/login";
        }
        //로그인 성공했을 경우
        else {
        	//로그인할때 저장하고싶은 값 담아오기
        	MemberVO mVo = memberservice.memberCheck(member.getMemberId()); //id를 넘겨서 mVo에 저장을 한다
        	
        	session.setAttribute("member", mVo);             // 일치하는 아이디, 비밀번호 경우 (로그인 성공) : 세션에 id랑 pw만 들어가있음
        	return "redirect:/main";
        }
	}

	
	

}
