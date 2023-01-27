package com.study.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.study.component.AES256Util;
import com.study.component.FileUtils;
import com.study.dao.BoardDAO;
import com.study.model.BoardVO;
import com.study.model.Criteria;

@Service
public class BoardServiceImpl implements BoardService{

	@Autowired
	BoardDAO boardDAO;
	
	@Autowired
	private AES256Util aesutil;
	
	@Resource(name="fileUtils")
	private FileUtils fileUtils;
	//게시물 등록
	@Override
	public void enroll(BoardVO board, MultipartHttpServletRequest mpRequest) throws Exception {
		boardDAO.insertBoard(board);
		
		List<Map<String,Object>> list = fileUtils.parseInsertFileInfo(board, mpRequest);
		Map<String, Object> map = new HashMap<String, Object>();
		int size = list.size();
		for(int i=0; i<size; i++) {
			
			//첨부파일
			boardDAO.insertFile(map);
			boardDAO.insertFile(map.put("originfileName", originfileName));
			boardDAO.insertFile(map.put("savedfileName", savedfileName));
		}
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
	public List<Map<String,Object>> getList(Criteria cri) throws Exception {
		List<Map<String,Object>> boardList =  boardDAO.getList(cri);
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

	//게시판 총 갯수
	@Override
	public int getTotal(Criteria cri) {
		int boardCnt = boardDAO.getTotal(cri);
		return boardCnt;
	}

	
	

}
