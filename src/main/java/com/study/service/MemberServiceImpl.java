package com.study.service;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.study.component.AES256Util;
import com.study.dao.MemberDAO;
import com.study.model.MemberVO;

@Service
public class MemberServiceImpl implements MemberService {

	@Autowired
	MemberDAO memberDAO;

	@Autowired
	private AES256Util aesutil;
	
	@Inject
	PasswordEncoder passwordEncoder;

	// 회원가입
	@Override
	public void memberJoin(MemberVO member) throws Exception {
		
		String phone = "";
		// phone2,3 이 없으면 phone을 null처리
		if ((member.getPhone2() == "" || member.getPhone2() == null)
				|| (member.getPhone3() == "" || member.getPhone3() == null)) {
			member.setPhone(null);
		}
		
		// 핸드폰번호 합치기
		phone = member.getPhone1() + member.getPhone2() + member.getPhone3();
		member.setPhone(phone);
		

		// 이메일 합치기
		String selectEmail = member.getSelectEmail();
		System.out.println(member.getSelectEmail());
		if (selectEmail == "1") {
			member.setEmail(member.getEmailId() + member.getInputEmail());
		} else {
			member.setEmail(member.getEmailId() + member.getSelectEmail());
		}
		
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

		// 비밀번호 암호화
		String encPassword = passwordEncoder.encode(member.getMemberPassword());
		member.setMemberPassword(encPassword);

		// 개인정보 암호화
		String encName = aesutil.encrypt(member.getMemberName());		//이름
		String encEmail = aesutil.encrypt(member.getEmail());			//이메일
		String encPhone = aesutil.encrypt(member.getPhone()); 			//핸드폰번호
		String encZipcode = aesutil.encrypt(member.getZipcode());		//우편번호
		String encStreetAdr = aesutil.encrypt(member.getStreeAdr());	//주소
		String encDetailAdr = aesutil.encrypt(member.getDetailAdr());	//상세주소
		
		member.setMemberName(encName);
		member.setEmail(encEmail);
		member.setPhone(encPhone);
		member.setZipcode(encZipcode);
		member.setStreeAdr(encStreetAdr);
		member.setDetailAdr(encDetailAdr);
		System.out.println(member);
		
		//member.setMemeberName(aesutil.encrypt(member.getMemberName())); 이렇게 합쳐도 됨
		
		//개인정보 복호화 확인용
		String decName = aesutil.decrypt(encName);		//이름 복호화
		String decEmail = aesutil.decrypt(encEmail);	//이메일 복호화
		
		//여기서 복호화한 개인정보들을 (decName이런 변수들) 매퍼랑 연결을 시켜야하는건지?
		
//		System.out.println(decName);
		
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

	// 회원 조회
	@Override
	public MemberVO memberCheck(String memberId) throws Exception {
		MemberVO mVo = memberDAO.memberCheck(memberId); // memberId를 넘겨준다
		return mVo;
	}

	// 로그인 체크
	@Override
	public String memberLogin(MemberVO member) throws Exception {
		String pw = memberDAO.pwCheck(member.getMemberId());
		String rawPw = member.getMemberPassword(); // 암호화 전 비밀번호

		return passwordEncoder.matches(rawPw, pw) ? "pass" : "fail";
	}

	//회원정보 수정
	//controller에서 보내는 파라미터들을 memberUpdate(MemberVO member)로 받고
	//받은 member를 DAO로 보내준다!
	@Override
	public void memberUpdate(MemberVO member) throws Exception {
		memberDAO.memberUpdate(member);
	}

	//회원탈퇴
	@Override
	public void memberDelete(String memberNo) throws Exception {
		memberDAO.memberDelete(memberNo);
	}

	//회원조회
	@Override
	public MemberVO getMember(String memberNo) throws Exception {
		MemberVO mVo = memberDAO.getMember(memberNo);
		return mVo;
	}



}
