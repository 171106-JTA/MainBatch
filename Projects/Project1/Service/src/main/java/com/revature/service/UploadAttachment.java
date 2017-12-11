package com.revature.service;

import java.io.IOException;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.revature.businessobject.FormAttachment;
import com.revature.dao.DAOBusinessObject;
import com.revature.dao.util.ConnectionUtil;

public class UploadAttachment {

	public static void createFormAttachmentsFileUpload(HttpServletRequest request) throws ServletException, IOException {
	    boolean isMultipartContent = ServletFileUpload.isMultipartContent(request);
	    FileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);
	    List<FileItem> fields;
	    List<FileItem> items;
	    Iterator<FileItem> it;
		
		if (isMultipartContent) {
			try {
				fields = upload.parseRequest(request);
				it = fields.iterator();
				
				if ((items = fields.stream().filter((item)->{ return item.getFieldName().equals("formId"); }).collect(Collectors.toList())).size() > 0) {
					uploadFormAttachments(it, Integer.parseInt(items.get(0).getString()));
				} else {
					
				}
			} catch (FileUploadException | NumberFormatException e) {
				e.printStackTrace();
			}
		}
	}
	
	///
	//	PRIVATE METHODS 
	///
	
	private static void uploadFormAttachments(Iterator<FileItem> it, Integer formId) throws FileUploadException {
	    FileItem fileItem;
	    
		while (it.hasNext()) {
			fileItem = it.next();

			if (!fileItem.isFormField()) {
				try (Connection conn = ConnectionUtil.getConnection()) {
					String fieldName = fileItem.getFieldName();
					String str = fileItem.getString();
					byte[] data = fileItem.get();
					
					if (data.length > 0) {
						String name = fileItem.getName();
						FormAttachment attachment = new FormAttachment();
						Blob blob = DAOBusinessObject.createBlob(conn, data);
						String type = fieldName.equals("gradingAttachments") ? "GRADE-ATTACHMENT" : "OTHER-ATTACHMENT";
						
						attachment.setBlobAttachment(blob);
						attachment.setFormId(formId);
						attachment.setAttachmentName(name);
						attachment.setAttachmentType(type);
						
						DAOBusinessObject.insert(conn, attachment);
					}
				} catch (SQLException e) {
					
				}
		}
	}
	}
	
}
