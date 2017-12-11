package com.project1.services;

import java.io.File;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.project1.classes.Employee;
import com.project1.classes.Form;
import com.project1.dao.FormDao;

public class createForm {
	public static void submitForm(HttpServletRequest request) {
		HttpSession session = request.getSession();
		Employee currEmp = (Employee) session.getAttribute("currEmp");
		//Create a form and set the person to review it to 1( for the supervisor)
		
		double amount = (Double)session.getAttribute("amount");
		String remType = (String) session.getAttribute("RemType");
		Form currForm = new Form(currEmp.getEmployeeID(), amount, remType);
		currForm.setCurrViewer(1);
		currForm.setAlterApproved(0);
		currForm.setAlteredAmount(0);
		//currForm.setDocument((FileInputStream)request.get);
		currForm.setStatus("Currently being reviewed by Direct Supervisor.");
		
		//FIIE
		
		ServletFileUpload sf = new ServletFileUpload(new DiskFileItemFactory());
		try {
			List<FileItem> multifiles = sf.parseRequest(request);
			String newFileName;
			for(FileItem item: multifiles) {
				System.out.println("Line Test!");
				currForm.setDocument(item);
				newFileName = currEmp.getEmployeeID() + remType + item.getName();
				if(newFileName.contains("null")) {
					break;
				}
				currForm.setFileName(newFileName);
				item.write(new File("C:\\Users\\Leng Vang\\Documents\\Project1\\171106_Project1\\src\\main\\webapp\\"+ newFileName));
			}
			System.out.println("file uploaded");
		} catch (FileUploadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//END OF FILE
		
		
		FormDao fdao = new FormDao();
		
		fdao.createForm(currForm);
	}
}
