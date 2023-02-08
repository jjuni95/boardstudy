package com.study.service;

import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.study.model.GBoardVO;

public interface GBoardService {

	// 자유갤러리 작성
	public void insertGalleryFile(GBoardVO gboard, MultipartHttpServletRequest mpRequest, HttpServletResponse response) throws Exception;

}
