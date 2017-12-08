package me.daxterix.trms.servlet;

import me.daxterix.trms.dao.DAOUtils;
import me.daxterix.trms.dao.RequestDAO;
import me.daxterix.trms.dao.exception.NonExistentIdException;
import me.daxterix.trms.model.Employee;
import me.daxterix.trms.model.ReimbursementRequest;

import javax.json.JsonArray;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(name = "GetRequestsServlet", urlPatterns = "/employee/requests")
public class GetRequestsServlet extends HttpServlet {
    private RequestDAO requestDao = DAOUtils.getRequestDAO();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String status = request.getParameter("status");
        String email = request.getParameter("email");
        String department = request.getParameter("department");
        String requestId = request.getParameter("requestId");

        // assume this employee exists and has the right security because of the filters
        Employee emp = (Employee) request.getSession().getAttribute("employee");
        if (email != null)
            email = emp.getEmail();     // value of request paramter email is ignored

        PrintWriter out = response.getWriter();
        response.setContentType("text/plan");

        try {
            if (requestId != null) {
                ReimbursementRequest res = requestDao.getRequestById(Long.parseLong(requestId));
                out.println(ServletUtils.requestToJson(res));
                return;
            }
            List<ReimbursementRequest> result;
            result = filterRequests(email, department, status);
            if (result == null) {
                response.setStatus(404);
                out.println("Invalid options");
            }
            JsonArray jArr = ServletUtils.requestsToJsonArray(result);
            response.setContentType("application/json");
            out.print(jArr.toString());
        }
        catch (NumberFormatException e) {
            response.setStatus(400);
            out.println("Invalid request id");
        }
        catch (NonExistentIdException e) {
            response.setStatus(404);
            out.println("Nonexistent request, depratment, or status");
        }
    }

    /**
     * filtering requests based on request department, email, and request status
     *
     * @param email
     * @param department
     * @param status
     * @return
     */
    private List<ReimbursementRequest> filterRequests(String email, String department, String status) throws NonExistentIdException {
        if (email == null && department == null && status == null)    // 1. all requests
            return requestDao.getAllRequests();

        else if (email == null && department == null) {               // 2. all requests by status
            switch (status) {
                case "all":
                    return requestDao.getAllRequests();
                case "pending":
                    return requestDao.getPendingRequests();
                default:
                    return requestDao.getRequestsByStatus(status);
            }
        }
        else if (email == null && status == null)                     // 3, by departments
            return requestDao.getRequestsByDepartment(department);

        else if (department == null && status == null)                // 4. filed requests
            return requestDao.getFiledRequests(email);

        else if (department == null) {                                  // 5. filed by status
            switch (status) {
                case "all":
                    return requestDao.getFiledRequests(email);
                case "pending":
                    return requestDao.getPendingFiledRequests(email);
                default:
                    return requestDao.getFiledRequestsByStatus(email, status);
            }
        }
        else if (email == null)                                        // 6. by departments and status
            return requestDao.getDepartmentRequestsByStatus(department, status);

        return null;
    }

}
