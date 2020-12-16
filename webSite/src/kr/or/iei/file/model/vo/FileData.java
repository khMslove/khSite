package kr.or.iei.file.model.vo;

import java.sql.Timestamp;

/*
CREATE TABLE FILETBL
(
    fileNo number Primary Key,
    originalFileName VARCHAR2(100) not null,
    changedFileName VARCHAR2(100) not null,
    filePath VARCHAR2(300) not null,
    fileSize NUMBER not null,
    fileUser VARCHAR2(20),
    uploadTime TimeStamp,
    DEL_YN CHAR(1) CHECK (DEL_YN IN('Y','N')),
    FOREIGN KEY (fileUser) REFERENCES MEMBER(userId)
);
*/
public class FileData {
	private int fileNo;
	private String originalFileName;
	private String changedFileName;
	private String filePath;
	private long fileSize;
	private String fileUser;
	private char delYn;
	private Timestamp uploadTime;
	
		
	public FileData() {
		super();
		// TODO Auto-generated constructor stub
	}
	public FileData(int fileNo, String originalFileName, String changedFileName, String filePath, long fileSize,
			String fileUser, char delYn, Timestamp uploadTime) {
		super();
		this.fileNo = fileNo;
		this.originalFileName = originalFileName;
		this.changedFileName = changedFileName;
		this.filePath = filePath;
		this.fileSize = fileSize;
		this.fileUser = fileUser;
		this.delYn = delYn;
		this.uploadTime = uploadTime;
	}
	public int getFileNo() {
		return fileNo;
	}
	public void setFileNo(int fileNo) {
		this.fileNo = fileNo;
	}
	public String getOriginalFileName() {
		return originalFileName;
	}
	public void setOriginalFileName(String originalFileName) {
		this.originalFileName = originalFileName;
	}
	public String getChangedFileName() {
		return changedFileName;
	}
	public void setChangedFileName(String changedFileName) {
		this.changedFileName = changedFileName;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public long getFileSize() {
		return fileSize;
	}
	public void setFileSize(long fileSize) {
		this.fileSize = fileSize;
	}
	public String getFileUser() {
		return fileUser;
	}
	public void setFileUser(String fileUser) {
		this.fileUser = fileUser;
	}
	public char getDelYn() {
		return delYn;
	}
	public void setDelYn(char delYn) {
		this.delYn = delYn;
	}
	public Timestamp getUploadTime() {
		return uploadTime;
	}
	public void setUploadTime(Timestamp uploadTime) {
		this.uploadTime = uploadTime;
	}
	
	
	
	
	
}



