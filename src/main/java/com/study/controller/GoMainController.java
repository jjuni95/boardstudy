package com.study.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.study.model.MemberVO;
import com.study.service.BoardService;
import com.study.service.GBoardService;

@Controller
public class GoMainController {

	@Autowired
	private BoardService boardservice;
	
	@Autowired
	private GBoardService gboardservice;
	
	//메인 페이지 이동
	@RequestMapping(value = "/main", method = RequestMethod.GET) //url에 적는 main(http://localhost:8080/boardstudy/main)
	public String mainPageGET(HttpServletRequest request, Model model) throws Exception {
		
		System.out.println("메인 페이지 진입");
		 HttpSession session = request.getSession();
		 MemberVO loginSession = (MemberVO) session.getAttribute("member");
		 if(loginSession == null) {
			 return "/member/login";
		 }
	     model.addAttribute("loginSession", loginSession);
	     
	     List<Map<String, Object>> boardList = boardservice.fourMain();
	     
	     model.addAttribute("list", boardList);
	     model.addAttribute("glist", gboardservice.sixMain());
	     
		 return "/main";    // JSP 경로
	}
	
}
