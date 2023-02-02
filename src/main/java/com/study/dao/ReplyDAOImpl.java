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
	public List<ReplyVO> readReply(int boardNo) {
		return template.selectList("replyMapper.readReply", boardNo);
	}

	//댓글 작성
	@Override
	public int writeReply(ReplyVO reply) {
		return template.insert("replyMapper.writeReply", reply);
	}

}
