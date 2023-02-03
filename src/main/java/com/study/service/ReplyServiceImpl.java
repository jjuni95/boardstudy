package com.study.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Formatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.study.component.AES256Util;
import com.study.dao.ReplyDAO;
import com.study.model.ReplyVO;

@Service
public class ReplyServiceImpl implements ReplyService{
	
	@Autowired
	ReplyDAO replyDAO;
	
	@Autowired
	private AES256Util aesutil;

	
	//댓글 조회
	@Override
	public List<ReplyVO> readReply(int boardNo) throws Exception{
		
		List<ReplyVO> replyList = replyDAO.readReply(boardNo);
		
		
		for(int i = 0; i< replyList.size(); i++) {
			
			//이름
			String memberName = replyList.get(i).getMemberName().toString();
			String decMemberName = aesutil.decrypt(memberName);	
			
			replyList.get(i).setMemberName(decMemberName);
			
			//등록일(String -> Date)
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date regDateFormat = simpleDateFormat.parse( replyList.get(i).getRegDate());
			String regDate = simpleDateFormat.format(regDateFormat);
			
			replyList.get(i).setRegDate(regDate);
			
		}
		
		return replyList;
	}

	//댓글작성
	@Override
	public void writeReply(ReplyVO reply) throws Exception {
		replyDAO.writeReply(reply);
	}

	//댓글수정
	@Override
	public void updateReply(ReplyVO reply) throws Exception {
		replyDAO.updateReply(reply);
	}

	
	//댓글 삭제
	@Override
	public void deleteReply(ReplyVO reply) throws Exception {
		replyDAO.deleteReply(reply);
	}

	@Override
	public ReplyVO selectReply(int commentNo) throws Exception {
		return replyDAO.selectReply(commentNo);
	}
	
	

}
