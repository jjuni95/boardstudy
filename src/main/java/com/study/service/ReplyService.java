package com.study.service;

import java.util.List;
import java.util.Map;

import com.study.model.ReplyVO;

public interface ReplyService {

	//댓글조회
	public List<ReplyVO> readReply(int boardNo) throws Exception;
	
	//댓글 작성
	public void writeReply(ReplyVO reply);
}
