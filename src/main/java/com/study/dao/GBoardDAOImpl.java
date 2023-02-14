package com.study.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.study.model.GBoardVO;

@Repository
public class GBoardDAOImpl implements GBoardDAO {

	// MyBatis sql 실행 메서드를 제공하는 객체
	@Autowired
	private SqlSessionTemplate template;

	// 자유갤러리 작성
	@Override
	public void insertGalleryFile(Map<String, Object> map) throws Exception {
		template.insert("gboardMapper.insertGelleryFile", map);
	}

	//자유갤러리 목록
	@Override
	public List<Map<String, Object>> selectGelleryList() throws Exception {
		return template.selectList("gboardMapper.selectGelleryList");
	}

	//자유갤러리 삭제
	@Override
	public void delete(int galleryNo) {
		template.selectOne("gboardMapper.delete", galleryNo);
	}

	//메인 자유갤러리 6개 가져오기
	@Override
	public List<Map<String, Object>> sixMain() throws Exception {
		return template.selectList("gboardMapper.sixMain");
	}



}
