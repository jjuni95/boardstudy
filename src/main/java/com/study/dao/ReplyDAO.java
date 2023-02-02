package com.study.dao;

import java.util.List;

import com.study.model.ReplyVO;

public interface ReplyDAO {

	//댓글 조회
	public List<ReplyVO> readReply(int boardNo);
	
	//댓글 작성
	public int writeReply(ReplyVO reply);
}
