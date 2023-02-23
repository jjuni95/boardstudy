package com.study.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import com.study.model.BoardVO;
import com.study.model.CriteriaVO;
import com.study.model.EhcacheVO;

@Repository
public class BoardDAOImpl implements BoardDAO {

	// MyBatis sql 실행 메서드를 제공하는 객체
	@Autowired
	private SqlSessionTemplate template;

	// 게시물 등록
	@Override
	public int insertBoard(BoardVO board) throws Exception {
		return template.selectOne("boardMapper.insertBoard", board);
	}

	//작성자 가져오기
	@Override
	public String selectWriter(String memberNo) throws Exception { //memberNo보내서 이름을 가져옴
		String result = template.selectOne("boardMapper.selectWriter", memberNo);
		return result;
	}

	//게시판 목록
	@Override
	@Cacheable("getList")
	public List<EhcacheVO> getList(String keyword, int pageNum, int amount,
			String type, String[] typeArr) throws Exception {
		HashMap<String, Object> map = new HashMap<>();
		map.put("keyword", keyword);
		map.put("pageNum", pageNum);
		map.put("amount", amount);
		map.put("type", type);
		map.put("typeArr", typeArr);
		
		return template.selectList("boardMapper.getList", map);
	}

	//게시판 상세 조회
	@Override
	public Map<String,Object> getPage(int boardNo) throws Exception {
		Map<String,Object> map = template.selectOne("boardMapper.getPage", boardNo);
		return map;
	}
	
	//게시판 총 갯수
	@Override
	public int getTotal(CriteriaVO cri) {
		return template.selectOne("boardMapper.getTotal", cri);
	}

	//첨부파일 업로드
	@Override
	public void insertFile(Map<String, Object> map) throws Exception {
		template.insert("boardMapper.insertFile", map);
	}

	//조회수 업데이트
	@Override
	public int getHitByBoardNo(int boardNo) {
		return template.update("boardMapper.updateHit", boardNo);
	}

	//게시판 수정
	@Override
	public void modify(BoardVO board) {
		template.update("boardMapper.modify",  board);
	}

	//게시판 삭제
	@Override
	public int delete(int boardNo) {
		return template.update("boardMapper.delete", boardNo);
	}

	//게시판 삭제여부 확인
	@Override
	public int deleteChk(int boardNo) {
		return template.selectOne("boardMapper.deleteChk", boardNo);
	}

	//첨부파일 조회
	@Override
	public List<Map<String, Object>> selectFileList(int boardNo) {
		return template.selectList("boardMapper.selectFileList", boardNo);
	}

	//첨부파일 수정(삭제)
	@Override
	public void updateFile(Map<String, Object> map) {
		template.update("boardMapper.updateFile", map);
	}

	//메인 게시판 4개 가져오기
	@Override
	public List<Map<String,Object>> fourMain() throws Exception {
		return template.selectList("boardMapper.fourMain");
	}


}
