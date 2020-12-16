package kr.or.iei.file.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import kr.or.iei.common.JDBCTemplate;
import kr.or.iei.file.model.vo.FileData;

public class FileDAO {

	public int insertFile(Connection conn, FileData fd) {
		PreparedStatement pstmt = null;
		int result = 0;
		String query = "INSERT INTO FILETBL VALUES(FILE_SEQ.NEXTVAL,?,?,?,?,?,?,'N')";
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, fd.getOriginalFileName());
			pstmt.setString(2, fd.getChangedFileName());
			pstmt.setString(3, fd.getFilePath());
			pstmt.setLong(4,fd.getFileSize());
			pstmt.setString(5, fd.getFileUser());
			pstmt.setTimestamp(6, fd.getUploadTime());
			
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			JDBCTemplate.close(pstmt);
		}
		return result;
		
		
	}

	public ArrayList<FileData> selectListFiles(Connection conn, String userId) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		ArrayList<FileData> list = new ArrayList<FileData>();
		String query = "SELECT * FROM FILETBL WHERE fileUser=? AND DEL_YN='N'";
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, userId);
			rset = pstmt.executeQuery();
			
			while(rset.next())
			{
				FileData fd = new FileData();
				fd.setFileNo(rset.getInt("fileNo"));
				fd.setOriginalFileName(rset.getString("originalFileName"));
				fd.setChangedFileName(rset.getString("changedFileName"));
				fd.setFilePath(rset.getString("filePath"));
				fd.setFileSize(rset.getLong("fileSize"));
				fd.setFileUser(rset.getString("fileUser"));
				fd.setUploadTime(rset.getTimestamp("uploadTime"));
				
				list.add(fd);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			JDBCTemplate.close(rset);
			JDBCTemplate.close(pstmt);
		}
		
		return list;		
		
	}

	public FileData selectOneFileData(Connection conn, int fileNo, String userId) {
			PreparedStatement pstmt = null;
			ResultSet rset = null;
			FileData fd = null;
			
			String query = "SELECT * FROM FILETBL WHERE fileNo=? AND fileUser=? AND DEL_YN='N'";
			
			try {
				pstmt = conn.prepareStatement(query);
				pstmt.setInt(1, fileNo);
				pstmt.setString(2, userId);
				rset = pstmt.executeQuery();
				
				if(rset.next()) 
				{
					fd = new FileData();
					fd.setFileNo(rset.getInt("fileNo"));
					fd.setOriginalFileName(rset.getString("originalFileName"));
					fd.setChangedFileName(rset.getString("changedFileName"));
					fd.setFilePath(rset.getString("filePath"));
					fd.setFileSize(rset.getLong("fileSize"));
					fd.setFileUser(rset.getString("fileUser"));
					fd.setUploadTime(rset.getTimestamp("uploadTime"));
					
				}	
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally {
				JDBCTemplate.close(rset);
				JDBCTemplate.close(pstmt);
			}
			
			return fd;
			
			
		
	}

	public int deleteFileData(Connection conn, int fileNo, String userId) {
		PreparedStatement pstmt = null;
		int result = 0;
		
		String query = "UPDATE FILETBL SET DEL_YN='Y' WHERE fileNo=? AND fileUser=?";
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, fileNo);
			pstmt.setString(2, userId);
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			JDBCTemplate.close(pstmt);
		}
		return result;		
	}

}









