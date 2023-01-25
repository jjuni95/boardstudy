package com.study.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.study.model.BoardVO;
import com.study.model.MemberVO;
import com.study.service.BoardService;

@Controller
@RequestMapping(value = "/board/*")
public class BoardController {
	
	@Autowired
	private BoardService boardservice;
	
	//게시판 목록 페이지 이동
	@GetMapping(value = "/list")
	public String boardListGET(HttpServletRequest request, Model model) throws Exception {
		HttpSession session = request.getSession(); // 세션에서 가져올때 얘 꼭 붙여넣기!!!!
		MemberVO mVo = (MemberVO) session.getAttribute("member"); //"member" =>로그인한 사람
		
		if(mVo == null) {
			request.setAttribute("msg", "로그인 상태로만 접근이 가능합니다.");
			request.setAttribute("url", "/member/login");
			return "member/alert"; // alert.jsp로 이동
		}
		//
		
		
		//세션이 있을때 ! 
		List<Map<String,Object>> boardList = boardservice.getList();
		model.addAttribute("list", boardList);
		return "board/list";
	}
	
	//게시판 등록 페이지 이동
	@GetMapping(value="/enroll")
	public String boardEnrollGET(HttpServletRequest request, Model model) throws Exception {
		HttpSession session = request.getSession();
		MemberVO mVo = (MemberVO) session.getAttribute("member");
		
		if(mVo == null) {
			request.setAttribute("msg", "로그인 상태로만 접근이 가능합니다.");
			request.setAttribute("url", "/member/login");
			return "member/alert"; // alert.jsp로 이동
		} 
		
		String decWriter = boardservice.selectWriter(mVo.getMemberNo());
		model.addAttribute("decWriter", decWriter);
		
		System.out.println("게시판 등록 페이지 이동");
		return "board/enroll";
	}
	
	//게시물 등록
	 @PostMapping("/enroll")
	 public String boardEnrollPOST(BoardVO board,HttpServletRequest request) throws Exception {
		 HttpSession session = request.getSession();
		 MemberVO mVo = (MemberVO) session.getAttribute("member");
		//  model.addAttribute("loginSession", mVo); 로그인세션 이거 갖다쓰기!!
		 
		 if(mVo == null) {
				request.setAttribute("msg", "로그인 상태로만 접근이 가능합니다.");
				request.setAttribute("url", "/member/login");
				return "member/alert"; // alert.jsp로 이동
			} 
		 System.out.println("mVo = " + mVo);
		 board.setMemberNo(mVo.getMemberNo()); //세션에서 회원번호 가져오기 
		 
		 boardservice.enroll(board);
		 
		 return "redirect:/board/list";
	 }
	 
	 //게시판 조회
	 @GetMapping("/get")
	 public void boardGetPageGET(int boardNo, Model model) throws Exception {
		 model.addAttribute("pageInfo", boardservice.getPage(boardNo));
	 }
	 
}
