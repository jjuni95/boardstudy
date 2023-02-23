package com.study.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
	public int insertGalleryFile(GBoardVO gboard, MultipartHttpServletRequest mpRequest, HttpServletResponse response)
			throws Exception {

		List<Map<String, Object>> fileList = fileUtils.parseInsertGBoardFileInfo(gboard, mpRequest);
		System.out.println("fileList===>" + fileList);
		Map<String, Object> map = new HashMap<String, Object>();

		int result = 0;
		int fileCnt = fileList.size();
		String regExp = "^([\\S]+(\\.(?i)(jpg|png|gif))$)";

		if (fileCnt > 0) {
			for (int i = 0; i < fileCnt; i++) {
				System.out.println("fileList ===> " + fileList.get(i));
				long fileSize = (long) fileList.get(i).get("FILE_SIZE");
				long megaByte = 5242880; // 5MB
				String fileName = (String) fileList.get(i).get("ORG_FILE_NAME");

				// 확장자가 다를경우
				if (!fileName.matches(regExp)) {
					result = 3;
					break;
				}
				// 사이즈가 클 경우
				else if (fileSize >= megaByte) {
					result = 1;
					break;
				}
			}
			// 다 통과하면 실행
			if (result == 0) {
				for (int i = 0; i < fileCnt; i++) {
					map.put("memberNo", gboard.getMemberNo());
					map.put("originfileName", fileList.get(i).get("ORG_FILE_NAME"));
					map.put("savedfileName", fileList.get(i).get("STORED_FILE_NAME"));
					map.put("fileSize", fileList.get(i).get("FILE_SIZE"));

					gboardDAO.insertGalleryFile(map);
				}
			}
		} else {
			result = 2; // 2 => 첨부파일을 등록 안했을때
		}
		// throw new RuntimeException("RuntimeException for rollback");
		return result;
	}

	// 자유갤러리 목록
	@Override
	public List<Map<String, Object>> selectGelleryList() throws Exception {
		return gboardDAO.selectGelleryList();
	}

	// 자유갤러리 8개씩 가져오기
	@Override
	public List<Map<String, Object>> listPlusEight(int galleryCnt) throws Exception {
		return gboardDAO.listPlusEight(galleryCnt);
	}

	// 자유갤러리 삭제
	@Override
	public void delete(int galleryNo) {
		gboardDAO.delete(galleryNo);
	}

	// 메인 자유갤러리 6개 가져오기
	@Override
	public List<Map<String, Object>> sixMain() throws Exception {
		return gboardDAO.sixMain();
	}
}
