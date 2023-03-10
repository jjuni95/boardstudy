package com.study.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.study.model.BoardVO;
import com.study.model.CriteriaVO;
import com.study.model.EhcacheVO;

public interface BoardService {

	// 게시물 등록
	public void enroll(BoardVO board, MultipartHttpServletRequest mpRequest, HttpServletResponse response) throws Exception;
	
	//작성자 가져오기
	public String selectWriter(String memberNo) throws Exception;
	
	//게시판 목록
	public List<EhcacheVO> getList(CriteriaVO cri) throws Exception;
	
	//게시판 상세조회
	public Map<String,Object> getPage(int boardNo) throws Exception;
	
	//게시판 총 갯수
	public int getTotal(CriteriaVO cri);

	//조회수 업데이트
	public int getHitByBoardNo(int boardNo);
	
	//게시판 수정
	public void modify(BoardVO board
					, String[] files
					, String[] fileNames
					, MultipartHttpServletRequest mpRequest) throws Exception;
	
	//게시판 삭제
	public int delete(int boardNo);
	
	//게시글 삭제여부 확인
	public int deleteChk(int boardNo);

	//첨부파일 조회
	public List<Map<String, Object>> selectFileList(int boardNo);
	
	//첨부파일 업로드
	public void insertFile(Map<String, Object> map) throws Exception;
	
	//첨부파일 삭제
	public void updateFile(int fileNo) throws Exception;
	
	//메인에 있는 4개 게시글 보여주기
	public List<Map<String,Object>> fourMain() throws Exception;
	
}
