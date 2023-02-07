package com.study.service;

import java.util.List;
import java.util.Map;

import com.study.model.CriteriaVO;
import com.study.model.ReplyVO;

public interface ReplyService {

	//댓글조회
	public List<ReplyVO> readReply(int boardNo, CriteriaVO cri) throws Exception;
	
	//댓글 작성
	public void writeReply(ReplyVO reply)throws Exception;
	
	//댓글수정
	public void updateReply(ReplyVO reply)throws Exception;
	
	//댓글 삭제
	public void deleteReply(ReplyVO reply)throws Exception;
	
	//선택된 댓글 조회
	public ReplyVO selectReply(int commentNo)throws Exception;
	
	//댓글 일괄삭제
	public void allDelete(int boardNo) throws Exception;
	
	//댓글 총 갯수
	public int getTotal(int boardNo);
}
