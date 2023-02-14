package com.study.dao;

import java.util.List;
import java.util.Map;

public interface GBoardDAO {
	
	//자유갤러리 작성
	public void insertGalleryFile(Map<String, Object> map) throws Exception;
	
	//자유갤러리 목록
	public List<Map<String,Object>> selectGelleryList() throws Exception;

	//자유갤러리 삭제
	public void delete(int galleryNo);
	
	//메인 자유갤러리 6개 가져오기
	public List<Map<String,Object>> sixMain() throws Exception;
}
