package com.revature.servlets;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.revature.dao.TRMSDao;
import com.revature.util.ConnectionUtil;

@MultipartConfig
public class UploadFile extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UploadFile() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		TRMSDao dao= new TRMSDao();
		try(Connection conn = ConnectionUtil.getConnection();)
		{	
			String gradeCheck= (String)request.getParameter("gradeCheck");
			int id=Integer.parseInt(request.getParameter("activeID"));
			// Part list (multi files).
            for (Part part : request.getParts()) {
                String fileName = extractFileName(part);
                if (fileName != null && fileName.length() > 0) {
                    // File data
                    InputStream is = part.getInputStream();
                    // Write to file
                    this.writeToDB(conn, fileName, is, id);
                }
            }
            System.out.println("GRADECHECK: " + gradeCheck);
            if(gradeCheck.equals("grade"))
            {
	            ArrayList<Integer> fileIds= dao.getFileIds();
	            int max=0;
	            for(int fileID: fileIds)
	            {
	            	if(max<fileID)
	            		max=fileID;
	            }
	            dao.markGrade(id,max);
            }
            // Upload successfully!.
            RequestDispatcher rd = request.getRequestDispatcher("addToApp.html"); 
			rd.include(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Error: " + e.getMessage());
            System.out.println("EROROROROROR");
        }
    }
 
    private String extractFileName(Part part) {
        // form-data; name="file"; filename="C:\file1.zip"
        // form-data; name="file"; filename="C:\Note\file2.zip"
        String contentDisp = part.getHeader("content-disposition");
        String[] items = contentDisp.split(";");
        for (String s : items) {
            if (s.trim().startsWith("filename")) {
                // C:\file1.zip
                // C:\Note\file2.zip
                String clientFileName = s.substring(s.indexOf("=") + 2, s.length() - 1);
                clientFileName = clientFileName.replace("\\", "/");
                int i = clientFileName.lastIndexOf('/');
                // file1.zip
                // file2.zip
                return clientFileName.substring(i + 1);
            }
        }
        return null;
    }
 
    private void writeToDB(Connection conn, String fileName, InputStream is, int id) throws SQLException {
 
        String sql = "Insert into app_files(app_id, file_name, file_data) values (?,?,?) ";
        PreparedStatement pstm = conn.prepareStatement(sql);
        pstm.setInt(1, id);
        pstm.setString(2, fileName);
        pstm.setBlob(3, is);
        pstm.executeUpdate();
    }
 
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
