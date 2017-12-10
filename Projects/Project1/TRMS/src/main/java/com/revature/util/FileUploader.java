package com.revature.util;

import java.io.IOException;
import java.nio.file.Paths;
import java.sql.SQLException;

import javax.servlet.http.Part;

import com.revature.beans.Attachment;
import com.revature.dao.AttachmentDaoImpl;
import com.revature.logging.LoggingService;

public class FileUploader {
	
	
	public static boolean upload(Part filePart, int requestId) {
		String filename = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
		
		System.out.println("file to upload: " + filename);
		
		AttachmentDaoImpl dao = new AttachmentDaoImpl();
		
		Attachment attachment = new Attachment();
		attachment.setFilename(filename);
		attachment.setDirectory("test");
		attachment.setApprovalType("pending");
		attachment.setRequestId(requestId);
		
		try {
			dao.addAttachment(attachment);
			
			//Save file on server.
			filePart.write(filename);
			throw new SQLException();
			//return true;
		} catch (SQLException e) {
			System.out.println("Error saving file info in database.");
			LoggingService.getLogger().warn("Error saving file info in database.", e);
		} catch (IOException e) {
			System.out.println("Error saving file info in database.");
			LoggingService.getLogger().warn("Error saving file on server.", e);
			try {
				//AttachmentDAO.delete(filename);
				throw new SQLException();
			} catch (SQLException e2) {
				LoggingService.getLogger().warn("Error deleting file info from database.", e);
			}
		}
		return false;
	}

}
