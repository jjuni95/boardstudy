package com.study.dao;

import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.study.model.GBoardVO;

public interface GBoardDAO {
	
	//자유갤러리 작성
	public void insertGalleryFile(Map<String, Object> map) throws Exception;

}
