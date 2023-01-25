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

import com.study.component.AES256Util;
import com.study.model.MemberVO;
import com.study.model.NewPwVO;
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
	public String joinPOST(HttpServletRequest request, MemberVO member) throws Exception {

		// 유효성 검사
		if ((member.getMemberName() == null)
				// || (member.getPhone2().length() != 4 || member.getPhone3().length() != 4)
				|| (member.getMemberPassword() == null) || (member.getMemberId() == null)) {
			request.setAttribute("msg", "회원가입에 실패했습니다.");
			request.setAttribute("url", "/member/login");
			return "member/alert"; // alert.jsp로 이동
		}

		// 회원가입 서비스 실행
		memberservice.memberJoin(member);

		// 여기에 request어쩌고 넣기
		request.setAttribute("msg", "회원가입에 성공했습니다.");
		request.setAttribute("url", "/member/login");
		return "member/alert"; // alert.jsp로 이동

		// return "redirect:/main";

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
	public String loginPOST(HttpServletRequest request, MemberVO member, Model model) throws Exception {

		HttpSession session = request.getSession(); // 세션에서 가져올때 얘 꼭 붙여넣기!!!!
		// 로그인 여부 체크: id랑 pw를 보낸다(memberLogin여기서)
		String flag = memberservice.memberLogin(member);

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

		return "redirect:/member/login";
	}

	// 회원정보수정 페이지 이동
	@RequestMapping(value = "/memberUpdateView", method = RequestMethod.GET)
	public String memberUpdateGET(HttpServletRequest request, Model model) throws Exception {

		HttpSession session = request.getSession(); // 세션에서 가져올때 얘 꼭 붙여넣기!!!!
		MemberVO mVo = (MemberVO) session.getAttribute("member"); // MemberVO로 형변환 시키고 다시 객체를 담아준다

		// 복호화하기
		String decName = aesutil.decrypt(mVo.getMemberName());
		model.addAttribute("decName", decName);

		return "member/memberUpdateView";
	}

	// 회원정보 수정
	@RequestMapping(value = "/memberUpdate", method = RequestMethod.POST)
	public String memberUpdatePOST(HttpServletRequest request, MemberVO member, Model model) throws Exception {

		// HttpSession session = request.getSession(); // 세션에서 가져올때 얘 꼭 붙여넣기!!!!
//		MemberVO mVo = (MemberVO) session.getAttribute("member");
		String flag = memberservice.memberLogin(member);

		// 비밀번호 일치 여부 확인
		if (flag == "fail") { // 수정 실패했을때
			int result = 0;
			model.addAttribute("result", result);
			request.setAttribute("msg", "수정에 실패했습니다.");
			request.setAttribute("url", "/member/memberUpdateView");
			return "member/alert"; // alert.jsp로 이동
		} else { // 수정 성공했을때
			memberservice.memberUpdate(member);
		}
		return "redirect:/main";
	}

	// 회원탈퇴
	@ResponseBody // 값을 넘겨줘야할때 사용
	@RequestMapping(value = "/memberDelete", method = RequestMethod.POST)
	public Map<String, String> memberDelete(HttpServletRequest request, String memberNo) throws Exception {
		memberservice.memberDelete(memberNo);

		// String result = memberservice.memberDelete(memberNo);
		MemberVO member = memberservice.getMember(memberNo);
		Map<String, String> result = new HashMap<String, String>();
		if (member == null) {
			result.put("msg", "success");
			HttpSession session = request.getSession();
			
			session.invalidate();
		}
		
		return result;
	}

	// 아이디찾기 페이지 이동
	@RequestMapping(value = "/findId", method = RequestMethod.GET)
	public String findIdGET() {
		return "/member/findId";
	}

	// 아이디찾기
	@RequestMapping(value = "/findId", method = RequestMethod.POST)
	public String findIdPOST(String email, HttpServletRequest request) throws Exception {
		MemberVO mVo = memberservice.findIdByEmail(email); // 입력한 이메일을 조회

		if (mVo != null) {
			memberservice.insertId(mVo);
			request.setAttribute("msg", "가입하신 아이디를 이메일로 전송했습니다.");
			request.setAttribute("url", "/member/login");
			return "member/alert"; // alert.jsp로 이동
		} else {
			request.setAttribute("msg", "일치한 이메일이 없습니다.");
			request.setAttribute("url", "/member/login");
			return "member/alert"; // alert.jsp로 이동
		}
	}

	// 비밀번호찾기 페이지 이동
	@RequestMapping(value = "/findPw", method = RequestMethod.GET)
	public String findPwGET() {
		return "/member/findPw";
	}
	
	//임시 비밀번호 
	@RequestMapping(value="/findPw", method = RequestMethod.POST)
	public String findPwPost(String email, HttpServletRequest request) throws Exception{
		MemberVO mVo = memberservice.findIdByEmail(email); // 입력한 이메일을 조회
		
		if(mVo != null) {
			memberservice.insertTempPw(mVo);
			request.setAttribute("msg", "가입하신 이메일로 임시 비밀번호를 전송했습니다.");
			request.setAttribute("url", "/member/login");
			return "member/alert"; // alert.jsp로 이동
		}else {
			request.setAttribute("msg", "일치한 이메일이 없습니다.");
			request.setAttribute("url", "/member/login");
			return "member/alert"; // alert.jsp로 이동
		}
	}
}
