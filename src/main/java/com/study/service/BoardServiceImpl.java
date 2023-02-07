package com.study.service;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.study.component.AES256Util;
import com.study.component.FileUtils;
import com.study.dao.BoardDAO;
import com.study.model.BoardVO;
import com.study.model.CriteriaVO;

@Transactional
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
	public void enroll(BoardVO board
					, MultipartHttpServletRequest mpRequest
					, HttpServletResponse response) throws Exception {
		
		List<Map<String,Object>> fileList = fileUtils.parseInsertFileInfo(board, mpRequest);
		Map<String, Object> map = new HashMap<String, Object>();
		int size = fileList.size();
		if(size > 0) {
		//if(size = 0) {
			int SboardNo = boardDAO.insertBoard(board);
			for(int i=0; i<size; i++) {
				
				System.out.println("fileList.get(i).get(\"FILE_SIZE\")===> " + fileList.get(i).get("FILE_SIZE"));

				long fileSize = (long) fileList.get(i).get("FILE_SIZE");
				long megaByte = 5242880;
				
				if(fileSize < megaByte) {
					
					map.put("boardNo", SboardNo);
					map.put("originfileName", fileList.get(i).get("ORG_FILE_NAME"));
					map.put("savedfileName", fileList.get(i).get("STORED_FILE_NAME"));
					map.put("fileSize", fileList.get(i).get("FILE_SIZE"));
					
					boardDAO.insertFile(map);
				}else {
					response.setContentType("text/html; charset=UTF-8");
					 
					PrintWriter out = response.getWriter();
					out.println("<script>alert('크기가 큽니다 ㅠ'); location.href='enroll';</script>");
					out.flush();
				}
			}
		}else {
			boardDAO.insertBoard(board);
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
	public List<Map<String,Object>> getList(CriteriaVO cri) throws Exception {
		List<Map<String,Object>> boardList =  boardDAO.getList(cri);
		for(int i=0; i<boardList.size(); i++) {
			String memberName = boardList.get(i).get("memberName").toString();
			String decMemberName = aesutil.decrypt(memberName);	
			

			boardList.get(i).put("memberName", decMemberName);
			
		}
		return boardList;
	}

	//게시판 상세조회
	@Override
	public Map<String,Object> getPage(int boardNo) throws Exception {
		Map<String,Object> map = boardDAO.getPage(boardNo);
		String decWriter = aesutil.decrypt(map.get("memberName").toString());
		map.put("memberName", decWriter);
		return map;
	}

	//게시판 총 갯수
	@Override
	public int getTotal(CriteriaVO cri) {
		int boardCnt = boardDAO.getTotal(cri);
		return boardCnt;
	}

	//조회수 업데이트
	@Override
	public int getHitByBoardNo(int boardNo) {
		int bVO = boardDAO.getHitByBoardNo(boardNo);
		return bVO;
	}

	//게시판 수정
	@Override
	public void modify(BoardVO board
					, String[] files
					, String[] fileNames
					, MultipartHttpServletRequest mpRequest) throws Exception {
		boardDAO.modify(board);
		
		List<Map<String, Object>> list = fileUtils.parseUpdateFileInfo(board, files, fileNames, mpRequest);
		Map<String, Object> tempMap = null;
		int size = list.size();
		for(int i = 0; i<size; i++) {
			tempMap = list.get(i);
			if(tempMap.get("IS_NEW").equals("Y")) {
				boardDAO.insertFile(tempMap);
			}else {
				boardDAO.updateFile(tempMap);
			}
		}
	}

	//게시판 삭제
	@Override
	public int delete(int boardNo) {
		int bVO = boardDAO.delete(boardNo);
		return bVO;
	}

	//게시판 삭제여부 확인
	@Override
	public int deleteChk(int boardNo) {
		int bVO = boardDAO.deleteChk(boardNo);
		return bVO;
	}

	
	//첨부파일 조회
	@Override
	public List<Map<String, Object>> selectFileList(int boardNo) {
		return boardDAO.selectFileList(boardNo);
	}
	
	//첨부파일 업로드
	@Override
	public void insertFile(Map<String, Object> map) throws Exception {
		boardDAO.insertFile(map);
		
	}

	
}
