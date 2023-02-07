package com.study.dao;

import java.util.List;

import com.study.model.CriteriaVO;
import com.study.model.ReplyVO;

public interface ReplyDAO {

	//댓글 조회
	public List<ReplyVO> readReply(int boardNo, CriteriaVO cri) throws Exception;
	
	//댓글 작성
	public int writeReply(ReplyVO reply) throws Exception;
	
	//댓글수정
	public void updateReply(ReplyVO reply) throws Exception;
	
	//댓글삭제
	public void deleteReply(ReplyVO reply) throws Exception;
	
	//선택된 댓글 조회
	public ReplyVO selectReply(int commentNo) throws Exception;
	
	//댓글 일괄삭제
	public void allDelete(int boardNo) throws Exception;
	
	//댓글 총 갯수
	public int getTotal(int boardNo);
	}
