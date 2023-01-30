package com.study.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.study.model.BoardVO;
import com.study.model.Criteria;

public interface BoardService {

	// 게시물 등록
	public void enroll(BoardVO board, MultipartHttpServletRequest mpRequest) throws Exception;
	
	//작성자 가져오기
	public String selectWriter(String memberNo) throws Exception;
	
	//게시판 목록
	public List<Map<String,Object>> getList(Criteria cri) throws Exception;
	
	//게시판 상세조회
	public Map<String,Object> getPage(int boardNo) throws Exception;
	
	//게시판 총 갯수
	public int getTotal(Criteria cri);

	//조회수 업데이트
	public int getHitByBoardNo(int boardNo);
	
	//게시판 수정
	public void modify(BoardVO board);
	
	//게시판 삭제
	public int delete(int boardNo);
	
	//게시글 삭제여부 확인
	public int deleteChk(int boardNo);
	
}
