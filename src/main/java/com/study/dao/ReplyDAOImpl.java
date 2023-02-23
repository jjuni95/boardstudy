package com.study.dao;

import java.util.HashMap;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import com.study.model.CriteriaVO;
import com.study.model.ReplyVO;

@Repository
public class ReplyDAOImpl implements ReplyDAO{
	
	// MyBatis sql 실행 메서드를 제공하는 객체
	@Autowired
	private SqlSessionTemplate template;

	//댓글 조회
	@Override
	public List<ReplyVO> readReply(int boardNo, CriteriaVO cri) throws Exception {
		HashMap<String, Object> map = new HashMap<>();
		map.put("boardNo", boardNo);
		map.put("cri", cri);
		
		return template.selectList("replyMapper.readReply", map);
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
	
	//댓글 일괄삭제
	@Override
	public void allDelete(int boardNo) throws Exception {
		template.update("replyMapper.allDelete", boardNo);
	}
	
	//게시판 총 갯수
	@Override
	public int getTotal(int boardNo) {
		return template.selectOne("replyMapper.getTotal", boardNo);
	}

}
