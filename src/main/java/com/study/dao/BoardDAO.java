package com.study.dao;

import java.util.List;
import java.util.Map;

import com.study.model.BoardVO;
import com.study.model.CriteriaVO;

public interface BoardDAO {
	
	// 게시물 등록
	public int insertBoard(BoardVO board) throws Exception;

	//작성자 가져오기
	public String selectWriter(String memberNo) throws Exception;
	
	//게시판 목록
	public List<Map<String,Object>> getList(CriteriaVO cri) throws Exception;
	
	//게시판 상세조회
	public Map<String,Object> getPage(int boardNo) throws Exception;
	
	//게시판 총 갯수
	public int getTotal(CriteriaVO cri);
	
	//첨부파일 업로드
	public void insertFile(Map<String, Object> map) throws Exception;
	
	//조회수 업데이트
	public int getHitByBoardNo(int boardNo);
	
	//게시판 수정
	public void modify(BoardVO board);
	
	//게시판 삭제
	public int delete(int boardNo);
	
	//게시판 삭제여부 확인
	public int deleteChk(int boardNo);
	
	//첨부파일 조회
	public List<Map<String, Object>> selectFileList(int boardNo);
	
	//첨부파일 수정(삭제)
	public void updateFile(Map<String, Object> map);
}
