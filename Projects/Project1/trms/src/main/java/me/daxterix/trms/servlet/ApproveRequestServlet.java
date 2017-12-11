package me.daxterix.trms.servlet;


import me.daxterix.trms.dao.DAOUtils;
import me.daxterix.trms.dao.RequestDAO;
import me.daxterix.trms.dao.exception.DuplicateIdException;
import me.daxterix.trms.dao.exception.NonExistentIdException;
import me.daxterix.trms.model.*;
import me.daxterix.trms.service.RequestService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/employee/requests/approve")
public class ApproveRequestServlet extends HttpServlet {
    private RequestDAO requestDao = DAOUtils.getRequestDAO();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        try {
            String requestIdStr = request.getParameter("requestId");
            String grantAmountStr = request.getParameter("grantAmount");
            Employee approver = (Employee) request.getAttribute("employee");

            ReimbursementRequest theRequest = requestDao.getRequestById(Long.parseLong(requestIdStr));
            if (grantAmountStr == null)
                RequestService.doApproval(response, approver, theRequest, null);
            else
                RequestService.doApproval(response, approver, theRequest, Float.parseFloat(grantAmountStr));

        } catch (NumberFormatException e) {
            response.setStatus(400);
            out.println("Invalid grant amount");
        } catch (NonExistentIdException e) {
            response.setStatus(400);
            out.println("Invalid request id");
        } catch (DuplicateIdException e) {
            e.printStackTrace();    // shouldn't be possible
        }
    }
}
