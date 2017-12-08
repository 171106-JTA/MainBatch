package com.revature.servlets;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.revature.beans.Attachment;
import com.revature.util.ConnectionUtil;
/**
 * Servlet implementation class GetFile
 */
public class GetFile extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetFile() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try(Connection conn = ConnectionUtil.getConnection();)
		{	
			int id=Integer.parseInt(request.getParameter("activeID"));

	        Attachment attachment= getAttachmentFromDB(conn, id);
	        if (attachment == null)// No record found.
	        {
	        	response.getWriter().write("No data found");
	        	return;
	        }
	        // file1.zip, file2.zip
	        String fileName = attachment.getName();
	        System.out.println("File Name: " + fileName);
	 
	        // abc.txt => text/plain
	        // abc.zip => application/zip
	        // abc.pdf => application/pdf
	        String contentType = this.getServletContext().getMimeType(fileName);
	        System.out.println("Content Type: " + contentType);
	        response.setHeader("Content-Type", contentType);
	 
	        response.setHeader("Content-Length", String.valueOf(attachment.getData().length()));
	 
	        response.setHeader("Content-Disposition", "inline; filename=\"" + attachment.getName() + "\"");
	 
	        // For big BLOB data.
	        Blob fileData = attachment.getData();
	        InputStream is = fileData.getBinaryStream();
	 
	        byte[] bytes = new byte[1024];
	        int bytesRead;
	 
	        while ((bytesRead = is.read(bytes)) != -1) {
	            // Write image data to Response.
	            response.getOutputStream().write(bytes, 0, bytesRead);
	        }
	        is.close();
	 
	       } catch (Exception e) {
	           throw new ServletException(e);
	       }
	   }
	 
	   private Attachment getAttachmentFromDB(Connection conn, int id) throws SQLException {
	       String sql = "Select file_name, file_data, file_description from app_files where file_id = ?";
	       Attachment file=null;
	       PreparedStatement pstm = conn.prepareStatement(sql);
	       pstm.setInt(1, id);
	       ResultSet rs = pstm.executeQuery();
	       while(rs.next()){
	           String fileName = rs.getString(1);
	           Blob fileData = rs.getBlob(2);
	           String description = rs.getString(3);
	           file=new Attachment(fileName, fileData, description);
	       }
	       return file;
	   }
	   
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
