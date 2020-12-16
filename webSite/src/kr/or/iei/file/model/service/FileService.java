package kr.or.iei.file.model.service;

import java.sql.Connection;
import java.util.ArrayList;

import kr.or.iei.common.JDBCTemplate;
import kr.or.iei.file.model.dao.FileDAO;
import kr.or.iei.file.model.vo.FileData;

public class FileService {
		FileDAO fDAO = new FileDAO();
	
	public int insertFile(FileData fd) {
		Connection conn = JDBCTemplate.getConnection();
		int result = fDAO.insertFile(conn,fd);
		if(result>0)
		{
			JDBCTemplate.commit(conn);
		}else
		{
			JDBCTemplate.rollback(conn);
		}
		JDBCTemplate.close(conn);
		return result;
		
	}

	public ArrayList<FileData> selectListFiles(String userId) {
		Connection conn = JDBCTemplate.getConnection();
		ArrayList<FileData> list = fDAO.selectListFiles(conn,userId);
		JDBCTemplate.close(conn);
		return list;
		
	}

	public FileData selectOneFileData(int fileNo, String userId) {
		Connection conn = JDBCTemplate.getConnection();
		FileData fd = fDAO.selectOneFileData(conn,fileNo,userId);
		JDBCTemplate.close(conn);
		return fd;
	}

	public int deleteFileData(int fileNo, String userId) {
		Connection conn = JDBCTemplate.getConnection();
		int result = fDAO.deleteFileData(conn,fileNo,userId);
		if(result>0)
		{
			JDBCTemplate.commit(conn);
		}else
		{
			JDBCTemplate.rollback(conn);
		}
		JDBCTemplate.close(conn);
		return result;
	}

}








