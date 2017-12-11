package com.revature.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.revature.businessobject.BusinessObject;
import com.revature.businessobject.FormAttachment;
import com.revature.dao.DAOBusinessObject;
import com.revature.dao.util.ConnectionUtil;

public class DownloadFormAttachment {
	public static void downloadFormAttachment(ServletContext context, HttpServletRequest request, HttpServletResponse response) throws IOException {
		int id = Integer.parseInt(request.getParameter("fileId"));
		FormAttachment attachment = new FormAttachment();
        List<BusinessObject> records;
        Blob blob;
        
        attachment.setId(id);
        
        try (Connection conn = ConnectionUtil.getConnection()) {
        	if ((records = DAOBusinessObject.load(conn, attachment)).size() > 0) {
        	
	        	attachment = (FormAttachment)records.get(0);
	        	blob = attachment.getBlobAttachment();
	        	
	        	InputStream inputStream = blob.getBinaryStream();
	            int fileLength = inputStream.available();
	              
	            System.out.println("fileLength = " + fileLength);
	
	            // sets MIME type for the file download
	            String mimeType = context.getMimeType(attachment.getAttachmentName());
	             
	            if (mimeType == null) {        
	                mimeType = "application/octet-stream";
	            }              
	              
	            // set content properties and header attributes for the response
	            response.setContentType(mimeType);
	       //     response.setContentLength(fileLength);
	            String headerKey = "Content-Disposition";
	            String headerValue = String.format("attachment; filename=\"%s\"", attachment.getAttachmentName());
	            response.setHeader(headerKey, headerValue);
	
	            // writes the file to the client
	            OutputStream outStream = response.getOutputStream();
	              
	            byte[] buffer = new byte[4096];
	            int bytesRead = -1;
	            StringBuffer out = new StringBuffer();
	            
	            while ((bytesRead = inputStream.read(buffer)) != -1) {
	            	outStream.write(buffer, 0, bytesRead);
	            }
	            
	            
	            inputStream.close();
	            outStream.flush();
	            outStream.close();  
	              
        	} 
        }catch (SQLException e) {
    		e.printStackTrace();
    	}
	}
}
