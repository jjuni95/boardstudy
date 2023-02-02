package com.study.controller;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.study.component.AES256Util;
import com.study.component.FileUtils;
import com.study.model.BoardVO;
import com.study.model.Criteria;
import com.study.model.MemberVO;
import com.study.model.PageMakerDTO;
import com.study.model.ReplyVO;
import com.study.service.BoardService;
import com.study.service.ReplyService;

@Controller
@RequestMapping(value = "/board/*")
public class BoardController {

	@Autowired
	private BoardService boardservice;
	
	@Autowired
	private ReplyService replyService;

	@Autowired
	private AES256Util aesutil;

	@Resource(name = "fileUtils")
	private FileUtils fileUtils;

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	// 게시판 목록 페이지 이동
	@GetMapping(value = "/list")
	public String boardListGET(HttpServletRequest request, Model model, Criteria cri) throws Exception {
		HttpSession session = request.getSession(); // 세션에서 가져올때 얘 꼭 붙여넣기!!!!
		MemberVO mVo = (MemberVO) session.getAttribute("member"); // "member" =>로그인한 사람

		if (mVo == null) {
			request.setAttribute("msg", "로그인 상태로만 접근이 가능합니다.");
			request.setAttribute("url", "/member/login");
			return "member/alert"; // alert.jsp로 이동
		}

		List<Map<String, Object>> boardList = boardservice.getList(cri);
//		model.addAttribute("list", boardList);

		int total = boardservice.getTotal(cri);
		PageMakerDTO pageMake = new PageMakerDTO(cri, total);
		System.out.println("pageMake====> " + pageMake);

		if (pageMake.getCri().getType() != null) {
			System.out.println("pageMake.getCri()====> " + pageMake.getCri());
			System.out.println("pageMake.getCri().getType()====> " + pageMake.getCri().getType());

			// 작성자로 검색할 경우
			if (pageMake.getCri().getType().equals("W")) {
				String keyword = cri.getKeyword();

				// 1.작성자 이름을 암호화
				String aesName = aesutil.encrypt(keyword);
				cri.setKeyword(aesName);

				// 2.암호화한걸로 게시판 목록 조회
				boardList = boardservice.getList(cri);

				// 3.화면에 원래 작성했던 검색키워드 저장
				cri.setKeyword(keyword);

			}
		}

		model.addAttribute("pageMaker", pageMake);
		model.addAttribute("list", boardList);
		return "board/list";
	}

	// 게시판 등록 페이지 이동
	@GetMapping(value = "/enroll")
	public String boardEnrollGET(HttpServletRequest request, Model model) throws Exception {
		HttpSession session = request.getSession();
		MemberVO mVo = (MemberVO) session.getAttribute("member");

		if (mVo == null) {
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

	// 게시물 등록
	@PostMapping("/enroll")
	public String boardEnrollPOST(BoardVO board, HttpServletRequest request, MultipartHttpServletRequest mpRequest,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		MemberVO mVo = (MemberVO) session.getAttribute("member");
		// model.addAttribute("loginSession", mVo); 로그인세션 이거 갖다쓰기!!

		if (mVo == null) {
			request.setAttribute("msg", "로그인 상태로만 접근이 가능합니다.");
			request.setAttribute("url", "/member/login");
			return "member/alert"; // alert.jsp로 이동
		}
		System.out.println("mVo = " + mVo);
		board.setMemberNo(mVo.getMemberNo()); // 세션에서 회원번호 가져오기

		boardservice.enroll(board, mpRequest, response);

		request.setAttribute("msg", "게시글이 등록되었습니다.");
		request.setAttribute("url", "/board/list");
		return "member/alert"; // alert.jsp로 이동
//		return "redirect:/board/list";
	}

	// 게시판 상세 조회
	@GetMapping("/get")
	public String boardGetPageGET(int boardNo, Model model, HttpServletRequest request) throws Exception {
		HttpSession session = request.getSession();
		boardservice.getHitByBoardNo(boardNo);
		Map<String, Object> board = boardservice.getPage(boardNo);
		model.addAttribute("pageInfo", board);
		MemberVO mVo = (MemberVO) session.getAttribute("member");

		System.out.println("mVo.getMemberNo() ===> " + mVo.getMemberNo());
		System.out.println("board.get(\"memberNo\") ===> " + board.get("memberNo"));
		System.out.println("board ===> " + board);

		model.addAttribute("showModifyBtn", "N");
		if (mVo.getMemberNo().equals(board.get("memberNo"))) {
			model.addAttribute("showModifyBtn", "Y");
		}

		int deleteChk = boardservice.deleteChk(boardNo);
		// 작성자가 아니면 못들어가게하기
		if (!mVo.getMemberNo().equals(board.get("memberNo")) && deleteChk == 1) {
			request.setAttribute("msg", "작성한 본인만 접근이 가능합니다. ");
			request.setAttribute("url", "/board/list");
			return "member/alert"; // alert.jsp로 이동
		}

		List<Map<String, Object>> fileList = boardservice.selectFileList(boardNo);
		model.addAttribute("file", fileList);
		model.addAttribute("fileSize", fileList.size());


		//댓글조회
		List<ReplyVO> replyList = replyService.readReply(boardNo);
		model.addAttribute("replyList", replyList);
		System.out.println("replyList ===> "+ replyList);
		
		
		return "board/get";
	}

	// 게시판 수정
	@PostMapping("/modify")
	public String boardModifyPost(BoardVO board, @RequestParam(value = "fileNoDel[]") String[] files,
			@RequestParam(value = "fileNameDel[]") String[] fileNames, MultipartHttpServletRequest mpRequest,
			HttpServletResponse response) throws Exception {

		List<Map<String, Object>> fileList = fileUtils.parseInsertFileInfo(board, mpRequest);
		Map<String, Object> map = new HashMap<String, Object>();
		int size = fileList.size();
		// 파일을 추가하면 파일 인서트
		if (size > 0) {
			for (int i = 0; i < size; i++) {
				long fileSize = (long) fileList.get(i).get("FILE_SIZE");
				long megaByte = 5242880;

				if (fileSize < megaByte) {
					map.put("boardNo", board.getBoardNo());
					map.put("originfileName", fileList.get(i).get("ORG_FILE_NAME"));
					map.put("savedfileName", fileList.get(i).get("STORED_FILE_NAME"));
					map.put("fileSize", fileList.get(i).get("FILE_SIZE"));

					boardservice.insertFile(map);
				} else {
					response.setContentType("text/html; charset=UTF-8");

					PrintWriter out = response.getWriter();
					out.println("<script>alert('크기가 큽니다 ㅠ'); location.href='enroll';</script>");
					out.flush();
				}
			}
		// 파일이 있는상태에서의 수정 => 업데이트
		} else {
			boardservice.modify(board, files, fileNames, mpRequest);
		}

		return "redirect:/board/list";
//		return "redirect:/board/get?boardNo=" + board.getBoardNo();
	}

	// 게시글 삭제
	@ResponseBody
	@PostMapping("/delete")
	public int boardDeletePOST(int boardNo) {

		boardservice.delete(boardNo);
		int result = boardservice.deleteChk(boardNo);

		return result;
	}
	
	//댓글작성
	
}
