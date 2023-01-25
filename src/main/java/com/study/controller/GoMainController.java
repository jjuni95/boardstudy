package com.study.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.study.model.MemberVO;

@Controller
public class GoMainController {

	//메인 페이지 이동
	@RequestMapping(value = "/main", method = RequestMethod.GET) //url에 적는 main(http://localhost:8080/boardstudy/main)
	public String mainPageGET(HttpServletRequest request, Model model) {
		
		System.out.println("메인 페이지 진입");
		 HttpSession session = request.getSession();
		 MemberVO loginSession = (MemberVO) session.getAttribute("member");
		 if(loginSession == null) {
			 return "/member/login";
		 }
	     model.addAttribute("loginSession", loginSession);
		 return "/main";    // JSP 경로
	}
	
}
