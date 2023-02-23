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
import com.study.model.CriteriaVO;
import com.study.model.EhcacheVO;
import com.study.model.MemberVO;
import com.study.model.PageMakerVO;
import com.study.model.ReplyVO;
import com.study.service.BoardService;
import com.study.service.MemberService;
import com.study.service.ReplyService;

@Controller
@RequestMapping(value = "/board/*")
public class BoardController {

	@Autowired
	private BoardService boardservice;

	@Autowired
	private MemberService memberservice;

	@Autowired
	private ReplyService replyService;

	@Autowired
	private AES256Util aesutil;

	@Resource(name = "fileUtils")
	private FileUtils fileUtils;

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	// 게시판 목록 페이지 이동
	@GetMapping(value = "/list")
	public String boardListGET(HttpServletRequest request, Model model, CriteriaVO cri) throws Exception {

		HttpSession session = request.getSession(); // 세션에서 가져올때 얘 꼭 붙여넣기!!!!
		MemberVO mVo = (MemberVO) session.getAttribute("member"); // "member" =>로그인한 사람

		if (mVo == null) {
			request.setAttribute("msg", "게시판 목록은 로그인 상태로만 접근이 가능합니다.");
			request.setAttribute("url", "/member/login");
			return "member/alert"; // alert.jsp로 이동
		}
		String keyword = cri.getKeyword();

		if (cri.getType() != null && cri.getType().equals("W")) {
			// 작성자 이름을 암호화
			String aesName = aesutil.encrypt(keyword);
			cri.setKeyword(aesName);
		}

		int total = boardservice.getTotal(cri);
		PageMakerVO pageMake = new PageMakerVO(cri, total);

		// 현재페이지가 전체마지막페이지보다 크면 1페이지로 이동
		int realEnd = (int) (Math.ceil(total * 1.0 / cri.getAmount()));
		if (cri.getPageNum() > realEnd) {
			cri.setPageNum(1);
			pageMake.setStartPage(1);
			pageMake.setEndPage(10);
			pageMake.getCri().setPageNum(1);
		}
		List<EhcacheVO> boardList = boardservice.getList(cri);

		// 화면에 원래 작성했던 검색키워드 저장
		cri.setKeyword(keyword);

		model.addAttribute("loginSession", mVo);
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
			request.setAttribute("msg", "게시판 등록페이지는 로그인 상태로만 접근이 가능합니다.");
			request.setAttribute("url", "/member/login");
			return "member/alert"; // alert.jsp로 이동
		}

		String decWriter = boardservice.selectWriter(mVo.getMemberNo());
		model.addAttribute("decWriter", decWriter);
		model.addAttribute("loginSession", mVo);
		return "board/enroll";
	}

	// 게시물 등록
	@PostMapping("/enroll")
	public String boardEnrollPOST(BoardVO board, HttpServletRequest request, MultipartHttpServletRequest mpRequest,
			HttpServletResponse response, Model model) throws Exception {
		HttpSession session = request.getSession();
		MemberVO mVo = (MemberVO) session.getAttribute("member");

		// model.addAttribute("loginSession", mVo); 로그인세션 이거 갖다쓰기!!

		if (mVo == null) {
			request.setAttribute("msg", "게시판 진짜 등록은 로그인 상태로만 접근이 가능합니다.");
			request.setAttribute("url", "/member/login");
			return "member/alert"; // alert.jsp로 이동
		}
		board.setMemberNo(mVo.getMemberNo()); // 세션에서 회원번호 가져오기

		boardservice.enroll(board, mpRequest, response);

		request.setAttribute("msg", "게시글이 등록되었습니다.");
		request.setAttribute("url", "/board/list");

		return "member/alert"; // alert.jsp로 이동
	}

	// 게시판 상세 조회
	@GetMapping("/get")
	public String boardGetPageGET(int boardNo, Model model, HttpServletRequest request, CriteriaVO cri)
			throws Exception {
		HttpSession session = request.getSession();

		boardservice.getHitByBoardNo(boardNo);
		Map<String, Object> board = boardservice.getPage(boardNo);
		model.addAttribute("pageInfo", board);
		MemberVO mVo = (MemberVO) session.getAttribute("member");
		model.addAttribute("loginSession", mVo);

		model.addAttribute("showModifyBtn", "N");
		if (mVo.getMemberNo().equals(board.get("memberNo"))) {
			model.addAttribute("showModifyBtn", "Y");
		}

		int deleteChk = boardservice.deleteChk(boardNo);
		// 게시글 삭제되면 접근불가
		if (deleteChk == 1) {
			replyService.allDelete(boardNo);
			request.setAttribute("msg", "접근이 불가능합니다. ");
			request.setAttribute("url", "/board/list");

			return "member/alert"; // alert.jsp로 이동
		}

		String decWriter = memberservice.selectWriter(mVo.getMemberNo());
		model.addAttribute("decWriter", decWriter);
		model.addAttribute("memberNo", mVo.getMemberNo());

		// 파일조회
		List<Map<String, Object>> fileList = boardservice.selectFileList(boardNo);
		model.addAttribute("file", fileList);
		model.addAttribute("fileSize", fileList.size());

		// 댓글 페이징처리
		int total = replyService.getTotal(boardNo);
		PageMakerVO pageMake = new PageMakerVO(cri, total);

		// 현재페이지가 전체마지막페이지보다 크면 1페이지로 이동
		int realEnd = (int) (Math.ceil(total * 1.0 / cri.getAmount()));
		if (cri.getPageNum() > realEnd) {
			cri.setPageNum(1);
			pageMake.setStartPage(1);
			pageMake.setEndPage(10);
			pageMake.getCri().setPageNum(1);
		}
		if (total == 0) {
			model.addAttribute("noPaging", "Y");
		}
		// 댓글조회
		List<ReplyVO> replyList = replyService.readReply(boardNo, cri);
		model.addAttribute("pageMaker", pageMake);
		model.addAttribute("replyList", replyList);
		return "board/get";
	}

	// 게시판 수정
	@PostMapping("/modify")
	public String boardModifyPost(BoardVO board, @RequestParam(value = "fileNoDel[]") String[] files,
			@RequestParam(value = "fileNameDel[]") String[] fileNames, MultipartHttpServletRequest mpRequest,
			HttpServletResponse response, String file0Chg, String file1Chg, String file0No, String file1No,
			String jspfile0No, String jspfile1No) throws Exception {

		// File0Chg,File1Chg가 Y이면 File0No,File1No 값체크(null or "") 후 삭제여부 Y 업데이트
		if (file0Chg.equals("Y")) {
			// 첫번째꺼 삭제했을때
			if (file0No == null || file0No.equals("")) {
				if (jspfile0No != null) {
					int fileNo0 = Integer.parseInt(jspfile0No);
					boardservice.updateFile(fileNo0);
				}
			}
		}
		if (file1Chg.equals("Y")) {
			// 두번째꺼 삭제했을때
			if (file1No == null || file1No == "") {
				if (jspfile1No != null) {
					int fileNo1 = Integer.parseInt(jspfile1No);
					boardservice.updateFile(fileNo1);
				}
			}
		}

		// 게시판 등록 할때 처럼 Insert
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
		}

		boardservice.modify(board, files, fileNames, mpRequest);

		return "redirect:/board/list";
	}

	// 게시글 삭제
	@ResponseBody
	@PostMapping("/delete")
	public int boardDeletePOST(int boardNo) {

		boardservice.delete(boardNo);
		int result = boardservice.deleteChk(boardNo);

		return result;
	}

	// 댓글작성
	@PostMapping("/replyWrite")
	public String replyWrite(ReplyVO reply) throws Exception {

		replyService.writeReply(reply);

		return "redirect:/board/get?boardNo=" + reply.getBoardNo();
	}

	// 댓글수정
	@ResponseBody
	@PostMapping("/replyUpdate")
	public Map<String, String> replyUpdate(int commentNo, String content, int boardNo) throws Exception {

		ReplyVO replyVo = new ReplyVO();
		replyVo.setBoardNo(boardNo);
		replyVo.setCommentNo(commentNo);
		replyVo.setContent(content);

		replyService.updateReply(replyVo);
		Map<String, String> result = new HashMap<String, String>();
		result.put("msg", "success");

		return result;
	}

	// 댓글 삭제
	@ResponseBody
	@PostMapping("/replyDelete")
	public Map<String, String> replyDelete(int commentNo, int boardNo) throws Exception {
		ReplyVO replyVo = new ReplyVO();
		replyVo.setBoardNo(boardNo);
		replyVo.setCommentNo(commentNo);
		replyService.deleteReply(replyVo);

		Map<String, String> result = new HashMap<String, String>();
		result.put("msg", "success");

		return result;
	}
}
