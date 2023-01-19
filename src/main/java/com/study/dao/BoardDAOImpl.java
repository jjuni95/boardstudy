package com.study.dao;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.study.model.BoardVO;

@Repository
public class BoardDAOImpl implements BoardDAO {

	// MyBatis sql 실행 메서드를 제공하는 객체
	@Autowired
	private SqlSessionTemplate template;

	// 게시물 등록
	@Override
	public void insertBoard(BoardVO board) throws Exception {
		template.selectOne("boardMapper.insertBoard", board);
	}

	//작성자 가져오기
	@Override
	public String selectWriter(String memberNo) throws Exception { //memberNo보내서 이름을 가져옴
		String result = template.selectOne("boardMapper.selectWriter", memberNo);
		return result;
	}

	//게시판 목록
	@Override
	public List<BoardVO> getList() throws Exception {
		return template.selectList("boardMapper.getList", getList());
	}

}
