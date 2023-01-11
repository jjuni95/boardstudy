package com.study.service;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
	
		
		System.out.println(member);
		memberDAO.memberJoin(member);
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

	// 로그인
	@Override
	public int memberLogin(MemberVO member) throws Exception {
		String pw = memberDAO.pwCheck(member.getMemberId());
		String rawPw = member.getMemberPassword();
		String k;
		if(passwordEncoder.matches(rawPw, pw)) {
			k="cc";
			System.out.println("비밀번호 일치");
//			member.setMemberPassword(pw);
		}else {
			k="k01";
			System.out.println("로그인 불가");
//			member.setMemberPassword(pw);
		}
		
		return k;
	}

}
