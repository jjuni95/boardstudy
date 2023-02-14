package com.study.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.study.model.GBoardVO;

public interface GBoardService {

	// 자유갤러리 작성
	public void insertGalleryFile(GBoardVO gboard, MultipartHttpServletRequest mpRequest, HttpServletResponse response) throws Exception;
	
	//자유갤러리 목록
	public List<Map<String,Object>> selectGelleryList() throws Exception;
	
	//자유갤러리 삭제
	public void delete(int galleryNo);

	//메인 자유갤러리 6개 가져오기
	public List<Map<String,Object>> sixMain() throws Exception;
}
