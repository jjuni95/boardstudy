package com.study.model;

/*
 *  GALLERY_NO INT NOT NULL IDENTITY (1, 1) PRIMARY KEY,
	MEMBER_NO CHAR(16) NOT NULL FOREIGN KEY REFERENCES Cu_MEMBER(MEMBER_NO),
	ORIGINFILE_NAME VARCHAR(260),
	SAVEDFILE_NAME VARCHAR(36),
	FILE_SIZE INT,
	REG_DATE DATETIME DEFAULT GETDATE(),
	ISENABLED CHAR(1) DEFAULT 'N',
	DELETE_DATE DATETIME DEFAULT GETDATE()

*/
public class GBoardVO {
	//갤러리 첨부파일 번호
	public int galleryNo;
	//회원번호
	public String memberNo;
	//원본 파일명
	public String originfileName;
	//저장된 파일명
	public String savedfileName;
	//파일크기
	public int fileSize;
	//업로드된 날짜
	public String regDate;
	//삭제여부
	public String isenabled;
	//삭제날짜
	public String deleteDate;
	public int getGalleryNo() {
		return galleryNo;
	}
	public void setGalleryNo(int galleryNo) {
		this.galleryNo = galleryNo;
	}
	public String getMemberNo() {
		return memberNo;
	}
	public void setMemberNo(String memberNo) {
		this.memberNo = memberNo;
	}
	public String getOriginfileName() {
		return originfileName;
	}
	public void setOriginfileName(String originfileName) {
		this.originfileName = originfileName;
	}
	public String getSavedfileName() {
		return savedfileName;
	}
	public void setSavedfileName(String savedfileName) {
		this.savedfileName = savedfileName;
	}
	public int getFileSize() {
		return fileSize;
	}
	public void setFileSize(int fileSize) {
		this.fileSize = fileSize;
	}
	public String getRegDate() {
		return regDate;
	}
	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}
	public String getIsenabled() {
		return isenabled;
	}
	public void setIsenabled(String isenabled) {
		this.isenabled = isenabled;
	}
	public String getDeleteDate() {
		return deleteDate;
	}
	public void setDeleteDate(String deleteDate) {
		this.deleteDate = deleteDate;
	}
	@Override
	public String toString() {
		return "GBoardVO [galleryNo=" + galleryNo + ", memberNo=" + memberNo + ", originfileName=" + originfileName
				+ ", savedfileName=" + savedfileName + ", fileSize=" + fileSize + ", regDate=" + regDate
				+ ", isenabled=" + isenabled + ", deleteDate=" + deleteDate + "]";
	}
	
	
	
}
