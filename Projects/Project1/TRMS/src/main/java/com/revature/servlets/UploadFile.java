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

/**
 * Servlet implementation class UploadFile
 * Add file to the database with an id to link it to a specific application
 */
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
			String description= (String)request.getParameter("description");
			String gradeCheck= (String)request.getParameter("gradeCheck");
			int id=Integer.parseInt(request.getParameter("activeID"));
            for (Part part : request.getParts()) {
                String fileName = extractFileName(part);
                if (fileName != null && fileName.length() > 0) {
                    // File data
                    InputStream is = part.getInputStream();
                    // Write to file
                    this.writeToDB(conn, fileName, is, id, description);
                }
            }
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
        }
    }
	/**
	 * Extract file name from the file information
	 * @param part
	 * @return String - file name
	 */
    private String extractFileName(Part part) {
        String contentDisp = part.getHeader("content-disposition");
        String[] items = contentDisp.split(";");
        for (String s : items) {
            if (s.trim().startsWith("filename")) {
                String clientFileName = s.substring(s.indexOf("=") + 2, s.length() - 1);
                clientFileName = clientFileName.replace("\\", "/");
                int i = clientFileName.lastIndexOf('/');
                return clientFileName.substring(i + 1);
            }
        }
        return null;
    }
    
    /**
     * Add file to the database and mark application as having a grade/presentation input
     * @param conn - connection to database
     * @param fileName - name of the file
     * @param is - data within the file
     * @param id - application id to attach file to
     * @param description - description of what is in the file
     * @throws SQLException
     */
    private void writeToDB(Connection conn, String fileName, InputStream is, int id, String description) throws SQLException {
 
        String sql = "Insert into app_files(app_id, file_name, file_data, file_description) values (?,?,?,?)";
        PreparedStatement pstm = conn.prepareStatement(sql);
        pstm.setInt(1, id);
        pstm.setString(2, fileName);
        pstm.setBlob(3, is);
        pstm.setString(4, description);
        pstm.executeUpdate();
        
        //remove info hold
        sql = "update app_status set info_hold=0 where app_id=?";
        pstm = conn.prepareStatement(sql);
        pstm.setInt(1, id);
        pstm.executeUpdate();
    }
 
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
