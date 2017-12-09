package me.daxterix.trms.servlet;

import me.daxterix.trms.dao.DAOUtils;
import me.daxterix.trms.dao.ObjectDAO;
import me.daxterix.trms.dao.exception.NonExistentIdException;
import me.daxterix.trms.model.RequestFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Servlet implementation class RequestFileServlet
 */
@WebServlet("/employee/requests/file")
public class RequestFileServlet extends HttpServlet {
	ObjectDAO objectDao = DAOUtils.getObjectDAO();

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException {
		try {
            Long fileId = Long.parseLong(request.getParameter("fileId"));
            RequestFile reqFile = objectDao.getObject(RequestFile.class, fileId);


            response.setContentType(reqFile.getMimeType().getMimeType());
            response.setHeader("Content-Disposition", "attachment; filename=" + reqFile.getName() + ".pdf");

            InputStream is = new ByteArrayInputStream(reqFile.getContent());
            OutputStream os = response.getOutputStream();
            byte[] buffer = new byte[1024];

            while (is.read(buffer) != -1) {
                os.write(buffer);
            }
            os.flush();
            os.close();
            is.close();
        }
        catch (NumberFormatException e) {

        }
        catch (NonExistentIdException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
        }
	}

	protected void doPost() {

    }

	void hasAccessToFile() {

    }

}
