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
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.study.component.AES256Util;
import com.study.model.BoardVO;
import com.study.model.Criteria;
import com.study.model.MemberVO;
import com.study.model.PageMakerDTO;
import com.study.service.BoardService;

@Controller
@RequestMapping(value = "/board/*")
public class BoardController {
	
	@Autowired
	private BoardService boardservice;
	
	@Autowired
	private AES256Util aesutil;
	
	//게시판 목록 페이지 이동
	@GetMapping(value = "/list")
	public String boardListGET(HttpServletRequest request, Model model, Criteria cri) throws Exception {
		HttpSession session = request.getSession(); // 세션에서 가져올때 얘 꼭 붙여넣기!!!!
		MemberVO mVo = (MemberVO) session.getAttribute("member"); //"member" =>로그인한 사람
		
		
		if(mVo == null) {
			request.setAttribute("msg", "로그인 상태로만 접근이 가능합니다.");
			request.setAttribute("url", "/member/login");
			return "member/alert"; // alert.jsp로 이동
		}
		
		List<Map<String,Object>> boardList = boardservice.getList(cri);
//		model.addAttribute("list", boardList);
		
		int total = boardservice.getTotal(cri);
		PageMakerDTO pageMake = new PageMakerDTO(cri, total);
		System.out.println("pageMake====> "+ pageMake);
		
		if(pageMake.getCri().getType() != null) {
			System.out.println("pageMake.getCri()====> " + pageMake.getCri());
			System.out.println("pageMake.getCri().getType()====> " + pageMake.getCri().getType());
			
			//작성자로 검색할 경우
			if(pageMake.getCri().getType().equals("W")) {
				String keyword = cri.getKeyword();
				
				//1.작성자 이름을 암호화
				String aesName = aesutil.encrypt(keyword);
				cri.setKeyword(aesName);
				
				//2.암호화한걸로 게시판 목록 조회
				boardList = boardservice.getList(cri);
				
				//3.화면에 원래 작성했던 검색키워드 저장?
				cri.setKeyword(keyword);
				
			}
		}
		
		model.addAttribute("pageMaker", pageMake);
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
		
		System.out.println("mVo.getMemberNo()=====> " + mVo.getMemberNo());
		String decWriter = boardservice.selectWriter(mVo.getMemberNo());
		model.addAttribute("decWriter", decWriter);
		System.out.println("게시판 등록 페이지 이동");
		return "board/enroll";
	}
	
	//게시물 등록
	 @PostMapping("/enroll")
	 public String boardEnrollPOST(BoardVO board,HttpServletRequest request
			 						, MultipartHttpServletRequest mpRequest) throws Exception {
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
		 
		 boardservice.enroll(board, mpRequest);
		 
		 return "redirect:/board/list";
	 }
	 
	 //게시판 상세 조회
	 @GetMapping("/get")
	 public void boardGetPageGET(int boardNo, Model model) throws Exception {
		 model.addAttribute("pageInfo", boardservice.getPage(boardNo));
	 }
	 
}
