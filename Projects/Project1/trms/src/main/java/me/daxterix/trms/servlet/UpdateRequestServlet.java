
package me.daxterix.trms.servlet;

import me.daxterix.trms.dao.DAOUtils;
import me.daxterix.trms.dao.RequestDAO;
import me.daxterix.trms.dao.exception.DuplicateIdException;
import me.daxterix.trms.dao.exception.NonExistentIdException;
import me.daxterix.trms.model.*;
import me.daxterix.trms.service.RequestService;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;

@MultipartConfig
@WebServlet(name="UpdateRequest", urlPatterns="/employee/updateRequest")
public class UpdateRequestServlet extends HttpServlet {

    private RequestDAO requestDao = DAOUtils.getRequestDAO();

    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idString = request.getParameter("id");

        String address = request.getParameter("eventAddress");
        String city = request.getParameter("eventCity");
        String state = request.getParameter("eventState");
        String zip = request.getParameter("eventZip");
        String description = request.getParameter("eventDescription");

        String action = request.getParameter("action");
        String denialReason = request.getParameter("denialReason");
        boolean isDenial = action != null && action.equals("deny");
        boolean isApproval = action != null && action.equals("approve");
        String approvalAmount = request.getParameter("approvalAmount");

        Employee emp = (Employee) request.getAttribute("employee");    // set by authentication filter

        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        try {
            ReimbursementRequest theRequest = requestDao.getRequestById(Long.parseLong(idString));

            if (isDenial) {
                RequestService.doDenial(response, emp, theRequest, denialReason);
            }
            else if (isApproval) {
                RequestService.doApproval(response, emp, theRequest,
                        approvalAmount != null ? Float.parseFloat(approvalAmount) : null);
            }
            else {
                theRequest.getAddress().setAddress(address);
                theRequest.getAddress().setCity(city);
                theRequest.getAddress().setState(state);
                theRequest.getAddress().setZip(zip);
                theRequest.setDescription(description);
                requestDao.update(theRequest);
            }

        } catch (NonExistentIdException | DuplicateIdException e) {
            e.printStackTrace();
            out.print("Oops! Duplicate or nonexistent id encountered.");
        }
        out.print("error adding new request");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idString = request.getParameter("id");
        String fileName = request.getParameter("eventFileName");
        String mimeType = request.getParameter("eventFileMimeType");
        String filePurpose = request.getParameter("eventFilePurpose");
        PrintWriter out = response.getWriter();
        try {
            Part filePart = request.getPart("eventFile");
            if (filePart != null) {
                ReimbursementRequest theRequest = requestDao.getRequestById(Long.parseLong(idString));
                RequestFile requestFile = ServletUtils.readUploadIntoRequestFile(filePart, fileName, mimeType, filePurpose);
                RequestService.addRequestFile(theRequest, requestFile);
                response.getWriter().print(ServletUtils.requestToJson(theRequest));
            }
        } catch (NonExistentIdException | DuplicateIdException e){
            out.print("encountered non existent or duplicate id");

        } catch (NumberFormatException e) {
            out.print("invalid request id");
        }

    }

}
