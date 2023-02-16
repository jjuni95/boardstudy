package com.study.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.google.gson.Gson;
import com.study.component.FileUtils;
import com.study.model.GBoardVO;
import com.study.model.MemberVO;
import com.study.service.GBoardService;

@Controller
@RequestMapping(value = "/gboard/*")
public class GalleryBoardController {

	private Gson gson = new Gson();
	
	@Autowired
	private GBoardService gboardservice;

	@Resource(name = "fileUtils")
	private FileUtils fileUtils;

	//자유갤러리 작성 페이지 이동
	@GetMapping("/gwrite")
	public String boardEnrollGET(GBoardVO gboard, HttpServletRequest request, Model model) throws Exception {
		HttpSession session = request.getSession();
		MemberVO mVo = (MemberVO) session.getAttribute("member");

		if (mVo == null) {
			request.setAttribute("msg", "자유갤러리작성은 로그인 상태로만 접근이 가능합니다.");
			request.setAttribute("url", "/member/login");
			return "member/alert"; // alert.jsp로 이동
		}
		return "gboard/gwrite";
	}
	
	// 자유갤러리 작성
	@PostMapping("/gwrite")
	public String GBoardListGET(GBoardVO gboard
								, HttpServletRequest request
								, MultipartHttpServletRequest mpRequest
								, HttpServletResponse response) throws Exception {

		HttpSession session = request.getSession();
		MemberVO mVo = (MemberVO) session.getAttribute("member");
		System.out.println("mVo.getMemberNo() ===> " + mVo.getMemberNo());
		gboard.setMemberNo(mVo.getMemberNo()); 
		
		gboardservice.insertGalleryFile(gboard, mpRequest, response);

		request.setAttribute("msg", "게시글이 등록되었습니다.");
		request.setAttribute("url", "/gboard/glist");
		return "member/alert"; // alert.jsp로 이동
	}

	//자유갤러리 목록 조회
	@GetMapping(value="/glist")
	public String gboardList(Model model) throws Exception{
		
		model.addAttribute("list", gboardservice.selectGelleryList());
		return "gboard/glist";
	}

	//자유갤러리 8개 이후 조회
	@ResponseBody
	@GetMapping("/plusList")
	public String plusList(int galleryCnt)throws Exception{
		
		//model.addAttribute("plusList", gboardservice.listPlusEight(galleryCnt));
		String result = gson.toJson(gboardservice.selectGelleryList());
		System.out.println("gboardservice.selectGelleryList() ===> " + gboardservice.selectGelleryList());
		
		return result;
	}
	
	//자유갤러리 삭제
	@ResponseBody
	@PostMapping("/delete")
	public Map<String, String> gboardDelete(int galleryNo) throws Exception {
		
		gboardservice.delete(galleryNo);
		Map<String, String> result = new HashMap<String, String>();
		result.put("msg", "success");
		
		return result;
		
	}
	
}
