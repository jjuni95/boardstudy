package com.study.dao;

import java.util.List;
import java.util.Map;

import com.study.model.BoardVO;
import com.study.model.Criteria;

public interface BoardDAO {
	
	// 게시물 등록
	public void insertBoard(BoardVO board) throws Exception;

	//작성자 가져오기
	public String selectWriter(String memberNo) throws Exception;
	
	//게시판 목록
	public List<Map<String,Object>> getList(Criteria cri) throws Exception;
	
	//게시판 조회
	public BoardVO getPage(int boardNo) throws Exception;
	
	//게시판 총 갯수
	public int getTotal(Criteria cri);
	
	//첨부파일 업로드
	public void insertFile(Map<String, Object> map) throws Exception;
	
}
