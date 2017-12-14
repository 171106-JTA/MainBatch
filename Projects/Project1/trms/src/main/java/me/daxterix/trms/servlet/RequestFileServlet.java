package me.daxterix.trms.servlet;

import me.daxterix.trms.dao.DAOUtils;
import me.daxterix.trms.dao.ObjectDAO;
import me.daxterix.trms.dao.RequestDAO;
import me.daxterix.trms.dao.exception.DuplicateIdException;
import me.daxterix.trms.dao.exception.NonExistentIdException;
import me.daxterix.trms.model.ReimbursementRequest;
import me.daxterix.trms.model.RequestFile;
import me.daxterix.trms.service.RequestService;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;


/**
 * Servlet implementation class RequestFileServlet
 */
@MultipartConfig
@WebServlet("/files")
public class RequestFileServlet extends HttpServlet {
	private ObjectDAO objectDao = DAOUtils.getObjectDAO();
    private RequestDAO requestDao = DAOUtils.getRequestDAO();

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException {
		try {
		    String idStr = request.getParameter("fileId");
		    if(idStr == null) {
		        List<RequestFile> files = objectDao.getAllObjects(RequestFile.class);
		        List<Long> ids = files.stream().map(file -> file.getId()).collect(Collectors.toList());
		        List<String> strIds  = new ArrayList<>(ids.size());
		        for (Long id : ids)
		            strIds.add("" + id);
		        response.setContentType("application/json");
		        response.getWriter().print(ServletUtils.stringsToJsonArrayString(strIds).toString());
            }
            else {
                Long fileId = Long.parseLong(idStr);
                RequestFile reqFile = objectDao.getObject(RequestFile.class, fileId);

                response.setContentType(reqFile.getMimeType().getMimeType());
                response.setHeader("Content-Disposition", "attachment; filename=" + reqFile.getName());

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
        }
        catch (NumberFormatException | NonExistentIdException | IOException e) {
		    e.printStackTrace();
        }
    }

    /**
     * accepts requests to add files to an already created request
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idString = request.getParameter("id");
        String fileName = request.getParameter("eventFileName");
        String mimeType = request.getParameter("eventFileMimeType");
        String filePurpose = request.getParameter("eventFilePurpose");
        PrintWriter out = response.getWriter();
        response.setContentType("application/json");
        try {
            Part filePart = request.getPart("eventFile");
            if (filePart != null) {
                fileName = new File(fileName).getName();
                ReimbursementRequest theRequest = requestDao.getRequestById(Long.parseLong(idString));
                RequestFile requestFile = ServletUtils.readUploadIntoRequestFile(filePart, fileName, mimeType, filePurpose);
                RequestService.addRequestFile(theRequest, requestFile);
                response.getWriter().print(ServletUtils.requestToJson(theRequest));
            }
        } catch (NonExistentIdException | DuplicateIdException e) {
            response.setStatus(400);
            out.print("encountered non existent or duplicate id");

        } catch (NumberFormatException e) {
            response.setStatus(400);
            out.print("invalid request id");
        }
    }

}
