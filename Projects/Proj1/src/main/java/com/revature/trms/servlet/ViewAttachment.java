package com.revature.trms.servlet;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.revature.trms.dao.ReimCaseDAO;
import com.revature.trms.model.ReimbursementCase;

/**
 * Servlet implementation class ViewAttachment
 */
public class ViewAttachment extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int caseId = Integer.parseInt(request.getParameter("caseId"));
		ReimCaseDAO rd = new ReimCaseDAO();
		ReimbursementCase reimCase = rd.getCaseByCaseId(caseId);
		response.setContentType("image/png");
		response.setHeader("Content-Disposition",
				"attachment; filename= case" + reimCase.getRequest_date() + "_" + reimCase.getCase_id() + ".png");
		if (reimCase.getAttachment() != null) {
			InputStream is = new ByteArrayInputStream(reimCase.getAttachment());
			OutputStream os = response.getOutputStream();
			// We're going to read and write 1KB at a time
			byte[] buffer = new byte[1024];

			// Reading returns -1 when there's no more data left to read.
			while (is.read(buffer) != -1) {
				os.write(buffer);
			}
			os.flush();
			os.close();
			is.close();
		}

	}

}
