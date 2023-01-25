package com.study.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.study.component.AES256Util;
import com.study.dao.BoardDAO;
import com.study.model.BoardVO;

@Service
public class BoardServiceImpl implements BoardService{

	@Autowired
	BoardDAO boardDAO;
	
	@Autowired
	private AES256Util aesutil;
	
	//게시물 등록
	@Override
	public void enroll(BoardVO board) throws Exception {
		boardDAO.insertBoard(board);
	}

	//작성자 가져오기
	@Override
	public String selectWriter(String memberNo) throws Exception {
		String encName = boardDAO.selectWriter(memberNo); //번호를 보내서 암호화된 이름을 가져오고
		//복호화
		String decWriter = aesutil.decrypt(encName);	
		
		return decWriter;
	}

	//게시판 목록
	@Override
	public List<Map<String,Object>> getList() throws Exception {
		List<Map<String,Object>> boardList =  boardDAO.getList();
		for(int i=0; i<boardList.size(); i++) {
			String memberName = boardList.get(i).get("memberName").toString();
			String decMemberName = aesutil.decrypt(memberName);	
			
			//마스킹(이름 80%)
			
			
			
			boardList.get(i).put("memberName", decMemberName);
			
		}
		return boardList;
	}

	//게시판 조회
	@Override
	public BoardVO getPage(int boardNo) throws Exception {
		BoardVO bVo = boardDAO.getPage(boardNo);
		return bVo;
	}
	
	

}
