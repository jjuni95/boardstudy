package com.study.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.study.component.AES256Util;
import com.study.model.MemberVO;
import com.study.service.MemberService;

@Controller
@RequestMapping(value = "/member")
public class MemberController {

	@Autowired
	private MemberService memberservice;
	
	@Autowired
	private AES256Util aesutil;

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
	public int memberEmailChkPOST(String emailId, String inputEmail, String selectEmail) throws Exception {

		System.out.println("memberEmailChk() 진입");

		int result = memberservice.emailCheck(emailId, inputEmail, selectEmail);

		System.out.println("결과값 = " + result);

		return result;
	}

	// 로그인
	@RequestMapping(value = "login", method = RequestMethod.POST)
	public String loginPOST(HttpServletRequest request, MemberVO member, Model model)
			throws Exception {

		System.out.println("login 메서드 진입");
		// System.out.println("전달된 데이터 : " + member);

		HttpSession session = request.getSession(); // 세션에서 가져올때 얘 꼭 붙여넣기!!!!
		// 로그인 여부 체크: id랑 pw를 보낸다(memberLogin여기서)
		String flag = memberservice.memberLogin(member);

//		model.addAttribute("result", 0);
//		 return "/member/login";

		// 로그인 실패했을 경우
		if (flag == "fail") { // 일치하지 않는 아이디, 비밀번호 입력 경우

			int result = 0;
			// request.setAttribute("result", result); //"name", value
			model.addAttribute("result", result);

			request.setAttribute("msg", "로그인에 실패했습니다.");
			request.setAttribute("url", "/member/login");
			return "member/alert"; // alert.jsp로 이동
			// return "/member/login";
		}
		// 로그인 성공했을 경우
		else {
			// 로그인할때 저장하고싶은 값 담아오기
			MemberVO mVo = memberservice.memberCheck(member.getMemberId()); // id를 넘겨서 mVo에 저장을 한다

			session.setAttribute("member", mVo); // 일치하는 아이디, 비밀번호 경우 (로그인 성공) : 세션에 id랑, 이름이랑 memberNo 들어가있음
			return "redirect:/main";
		}
	}

	// 로그아웃
	@RequestMapping(value = "logout", method = RequestMethod.GET)
	public String logoutMainGET(HttpServletRequest request) throws Exception {
		System.out.println("logoutMainGET 메서드 진입");

		HttpSession session = request.getSession();

		session.invalidate();

		return "redirect:/main";
	}
	
	//회원정보수정 페이지 이동
	@RequestMapping(value ="/memberUpdateView", method = RequestMethod.GET)
	public String memberUpdateGET(HttpServletRequest request, Model model) throws Exception {
		
		HttpSession session = request.getSession(); // 세션에서 가져올때 얘 꼭 붙여넣기!!!!
		MemberVO mVo = (MemberVO) session.getAttribute("member"); //MemberVO로 형변환 시키고 다시 객체를 담아준다
		//System.out.println("mVo= " + mVo);
		
		String decName = aesutil.decrypt(mVo.getMemberName());
		model.addAttribute("decName" , decName);
		
		//String pw = aesutil.decrypt(mVo.getMemberPassword());
		System.out.println("mVo.getMemberPassword()= "+ mVo.getMemberPassword() );
		//model.addAttribute("pw" , pw);
		
		return "member/memberUpdateView";
	}
	
	//회원정보 수정
	@RequestMapping(value = "/memberUpdate", method = RequestMethod.POST)
	public String memberUpdatePOST(HttpServletRequest request, MemberVO member, Model model) throws Exception{
		
		//HttpSession session = request.getSession(); // 세션에서 가져올때 얘 꼭 붙여넣기!!!!
//		MemberVO mVo = (MemberVO) session.getAttribute("member");
		String flag = memberservice.memberLogin(member);
		
		if(flag == "fail") {	//수정 실패했을때
			int result = 0;
			model.addAttribute("result", result);
			request.setAttribute("msg", "수정에 실패했습니다.");
			request.setAttribute("url", "/member/memberUpdateView");
			return "member/alert"; // alert.jsp로 이동
		} else {			//수정 성공했을때
			System.out.println("asdf");
			memberservice.memberUpdate(member);
		}
		
		
		
		return "redirect:/main";
	}
	
	//회원탈퇴
	@ResponseBody //값을 넘겨줘야할때 사용
	@RequestMapping(value = "/memberDelete", method = RequestMethod.POST)
	public Map<String,String> memberDelete(String memberNo) throws Exception{
		memberservice.memberDelete(memberNo);
		
		//String result = memberservice.memberDelete(memberNo);
		MemberVO member = memberservice.getMember(memberNo);
		Map<String,String> result = new HashMap<String, String>();
		if(member == null) {
			result.put("msg", "success");
		}
					
		return result;
	}
	

}
