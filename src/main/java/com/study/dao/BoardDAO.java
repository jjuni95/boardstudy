package com.study.dao;

import java.util.List;

import com.study.model.BoardVO;

public interface BoardDAO {
	
	// 게시물 등록
	public void insertBoard(BoardVO board) throws Exception;

	//작성자 가져오기
	public String selectWriter(String memberNo) throws Exception;
	
	//게시판 목록
	public List<BoardVO> getList() throws Exception;
}