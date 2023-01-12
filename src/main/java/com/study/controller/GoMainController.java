package com.study.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class GoMainController {

	//로그인 페이지 이동
	@RequestMapping(value = "/main", method = RequestMethod.GET) //url에 적는 main(http://localhost:8080/boardstudy/main)
	public String mainPageGET(Model model) {
		
		System.out.println("메인 페이지 진입");
		
		model.addAttribute("result", 1234);
		return "/main";    // JSP 경로
	}
}
