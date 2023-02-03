package com.study.dao;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.study.model.ReplyVO;

@Repository
public class ReplyDAOImpl implements ReplyDAO{
	
	// MyBatis sql 실행 메서드를 제공하는 객체
	@Autowired
	private SqlSessionTemplate template;

	//댓글 조회
	@Override
	public List<ReplyVO> readReply(int boardNo) throws Exception {
		return template.selectList("replyMapper.readReply", boardNo);
	}

	//댓글 작성
	@Override
	public int writeReply(ReplyVO reply) throws Exception {
		return template.insert("replyMapper.writeReply", reply);
	}

	//댓글 수정
	@Override
	public void updateReply(ReplyVO reply) throws Exception {
		template.update("replyMapper.updateReply", reply);
	}

	//댓글 삭제
	@Override
	public void deleteReply(ReplyVO reply) throws Exception {
		template.update("replyMapper.deleteReply", reply);
	}

	//선택된 댓글 조회
	@Override
	public ReplyVO selectReply(int commentNo) throws Exception {
		return template.selectOne("replyMapper.selectReply", commentNo);
	}

}
