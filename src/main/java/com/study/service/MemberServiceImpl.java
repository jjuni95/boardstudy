package com.study.service;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.study.component.AESUtil;
import com.study.dao.MemberDAO;
import com.study.model.MemberVO;

@Service
public class MemberServiceImpl implements MemberService {

	@Autowired
	MemberDAO memberDAO;

	@Inject
    PasswordEncoder passwordEncoder;
	
	// 회원가입
	@Override
	public void memberJoin(MemberVO member) throws Exception {

		// phone2,3 이 없으면 phone을 null처리
		if ((member.getPhone2() == "" || member.getPhone2() == null)
				|| (member.getPhone3() == "" || member.getPhone3() == null)) {
			member.setPhone(null);
		} else {
			// 핸드폰번호 합치기
			member.setPhone(member.getPhone1() + member.getPhone2() + member.getPhone3());
		}

		// 이메일 합치기
		String selectEmail = member.getSelectEmail();
		System.out.println(member.getSelectEmail());
		if (selectEmail == "1") {
			member.setEmail(member.getEmailId() + member.getInputEmail());
		} else
			member.setEmail(member.getEmailId() + member.getSelectEmail());

		// 회원번호 구하기 ㅠㅠ
		String result = memberDAO.selectMemberNo();
		System.out.println("result= " + result);

		// 오늘 날짜 구하기(yyyyMMdd)
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Calendar c1 = Calendar.getInstance();
		String strToday = sdf.format(c1.getTime());
		System.out.println("Today=" + strToday);

		if (result == null) {
			result = "CU" + strToday + "A" + "00001";
		} else {
			String memberNo1 = result.substring(0, 10); // cu20230111
			String memberNo2 = result.substring(10, 11); // A
			String member3Str = "";
			int memberNo3 = Integer.parseInt(result.substring(11, 16)); // 00004
			System.out.println("memberNo3= " + memberNo3);
			char aString = memberNo2.charAt(0); // string -> char
			memberNo3++;

			// 0공백으로 처리
			System.out.println(String.format("%05d", memberNo3));
			member3Str = String.format("%05d", memberNo3);

			if (memberNo3 > 99999) {
				if (!memberNo2.equals("Z")) {
					aString++;
					memberNo2 = String.valueOf(aString);
					member3Str = "00001";
				} else {
					System.out.println("회원가입 불가");
				}
			}
			result = memberNo1 + memberNo2 + member3Str;
		}
		member.setMemberNo(result);

		//비밀번호 암호화
		String encPassword = passwordEncoder.encode(member.getMemberPassword());
		member.setMemberPassword(encPassword);
	
		//개인정보 암호화
		
		
		System.out.println(member);
		memberDAO.memberJoin(member);
	}

	
	//개인정보 암호화
	@Autowired
	AESUtil aes;
	
	@RequestMapping(method=RequestMethod.POST)
    public String joinHash(@RequestParam("memberName") String memberName
    				, @RequestParam("email") String email
    				, @RequestParam("phone") String phone
    				, @RequestParam("zipcode") String zipcode
    				, @RequestParam("streeAdr") String streeAdr
    				, @RequestParam("detailAdr") String detailAdr
    				,Model model) throws NoSuchAlgorithmException, UnsupportedEncodingException, GeneralSecurityException {
        
        System.out.println("암호화 전 아이디 : " + memberName);
        System.out.println("암호화 전 이메일 : " + email);
        
        memberName = aes.encrypt(memberName);
        email = aes.encrypt(email);
        
        System.out.println("-----------------------------");
        System.out.println("암호화 후 아이디 : " + memberName);
        System.out.println("암호화 후 이메일 : " + email);
        
        System.out.println("-----------------------------");
        System.out.println("복호화 후 아이디 : " + aes.decrypt(memberName));
        System.out.println("복호화 후 이메일 : " + aes.decrypt(email));
        
        model.addAttribute("memberName", memberName);
        model.addAttribute("email", email);
        return "login";
    }
	
	
	// 아이디 중복 검사
	@Override
	public int idCheck(String memberId) throws Exception {

		int result = memberDAO.idCheck(memberId);
		return result;
	}

	// 이메일 중복 검사
	@Override
	public int emailCheck(String emailId, String inputEmail, String selectEmail) throws Exception {

		String email = "";

		// 이메일 합치기
		if (selectEmail == "1") {
			email = emailId + inputEmail;
		} else {
			email = emailId + selectEmail;
		}

		int result = memberDAO.emailCheck(email);
		return result;
	}

	//회원 조회
	@Override
	public MemberVO memberCheck(String memberId) throws Exception {
		MemberVO mVo= memberDAO.memberCheck(memberId); //memberId를 넘겨준다
				return mVo;
	}
	
	
	// 로그인 체크
	@Override
	public String memberLogin(MemberVO member) throws Exception {
		String pw = memberDAO.pwCheck(member.getMemberId());
		String rawPw = member.getMemberPassword(); //인코딩 전 비밀번호
		
		return passwordEncoder.matches(rawPw, pw) ? "pass" : "fail";
	}

}
