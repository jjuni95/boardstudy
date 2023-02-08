package com.study.service;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.study.component.FileUtils;
import com.study.dao.GBoardDAO;
import com.study.model.GBoardVO;

@Service
public class GBoardServiceImpl implements GBoardService {

	@Autowired
	GBoardDAO gboardDAO;

	@Resource(name = "fileUtils")
	private FileUtils fileUtils;

	// 자유갤러리 작성
	@Override
	public void insertGalleryFile(GBoardVO gboard, MultipartHttpServletRequest mpRequest, HttpServletResponse response)
			throws Exception {

		List<Map<String, Object>> fileList = fileUtils.parseInsertGBoardFileInfo(gboard, mpRequest);
		Map<String, Object> map = new HashMap<String, Object>();

		int size = fileList.size();

		for (int i = 0; i < size; i++) {

			long fileSize = (long) fileList.get(i).get("FILE_SIZE");
			long megaByte = 5242880;

			if (fileSize < megaByte) {
				map.put("memberNo", gboard.getMemberNo());
				map.put("originfileName", fileList.get(i).get("ORG_FILE_NAME"));
				map.put("savedfileName", fileList.get(i).get("STORED_FILE_NAME"));
				map.put("fileSize", fileList.get(i).get("FILE_SIZE"));

				gboardDAO.insertGalleryFile(map);
			} else {
				response.setContentType("text/html; charset=UTF-8");

				PrintWriter out = response.getWriter();
				out.println("<script>alert('크기가 큽니다 ㅠ'); location.href='enroll';</script>");
				out.flush();
			}
		}

	}

}
