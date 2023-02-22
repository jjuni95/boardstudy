package com.study.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Resource(name = "fileUtils")
	private FileUtils fileUtils;

	// 자유갤러리 작성 페이지 이동
	@GetMapping("/gwrite")
	public String boardEnrollGET(GBoardVO gboard, HttpServletRequest request, Model model) throws Exception {
		HttpSession session = request.getSession();
		MemberVO mVo = (MemberVO) session.getAttribute("member");
		model.addAttribute("loginSession", mVo);

		if (mVo == null) {
			request.setAttribute("msg", "자유갤러리작성은 로그인 상태로만 접근이 가능합니다.");
			request.setAttribute("url", "/member/login");
			return "member/alert"; // alert.jsp로 이동
		}
		return "gboard/gwrite";
	}

	// 자유갤러리 작성
	@PostMapping("/gwrite")
	public String GBoardListGET(GBoardVO gboard, HttpServletRequest request, MultipartHttpServletRequest mpRequest,
			HttpServletResponse response) throws Exception {

		HttpSession session = request.getSession();
		MemberVO mVo = (MemberVO) session.getAttribute("member");
		gboard.setMemberNo(mVo.getMemberNo());

		int result = gboardservice.insertGalleryFile(gboard, mpRequest, response);
		String msg = null;
		
		// 파일 등록 성공했을때
		if (result == 0) {
			msg = "등록했습니다.";
		} else if (result == 1) {
			msg = "파일이 5M 이상이여서 등록 실패했습니다.";
		} else if (result == 2) {
			msg = "첨부파일을 등록 안했습니다.";
		} else if (result == 3) {
			msg = "jpg, png, gif만 가능합니다.";
		}
		request.setAttribute("msg", msg);
		request.setAttribute("url", "/gboard/glist");
		return "member/alert"; // alert.jsp로 이동
	}

	// 자유갤러리 목록 조회
	@GetMapping(value = "/glist")
	public String gboardList(HttpServletRequest request, Model model) throws Exception {
		HttpSession session = request.getSession();
		MemberVO mVo = (MemberVO) session.getAttribute("member");

		if (mVo == null) {
			request.setAttribute("msg", "게시판 등록페이지는 로그인 상태로만 접근이 가능합니다.");
			request.setAttribute("url", "/member/login");
			return "member/alert"; // alert.jsp로 이동
		}
		model.addAttribute("loginSession", mVo);
		model.addAttribute("list", gboardservice.selectGelleryList());
		logger.error("Error");
		return "gboard/glist";
	}

	// 자유갤러리 8개 이후 조회
	@ResponseBody
	@GetMapping("/plusList")
	public String plusList(int galleryCnt) throws Exception {
		String result = gson.toJson(gboardservice.listPlusEight(galleryCnt));

		return result;
	}

	// 자유갤러리 삭제
	@ResponseBody
	@PostMapping("/delete")
	public Map<String, String> gboardDelete(int galleryNo) throws Exception {

		gboardservice.delete(galleryNo);
		Map<String, String> result = new HashMap<String, String>();
		result.put("msg", "success");

		return result;

	}

}
