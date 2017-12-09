package me.daxterix.trms.servlet;

import me.daxterix.trms.dao.DAOUtils;
import me.daxterix.trms.dao.RequestDAO;
import me.daxterix.trms.dao.exception.NonExistentIdException;
import me.daxterix.trms.model.Employee;
import me.daxterix.trms.model.EmployeeRank;
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

@WebServlet("/employee/requests")
public class RequestsServlet extends HttpServlet {
    private RequestDAO requestDao = DAOUtils.getRequestDAO();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String status = request.getParameter("status");
        String wantsFiled = request.getParameter("wantsFiledRequests");
        boolean wantsFiledReqs = (wantsFiled != null);
        String department = request.getParameter("department");

        // assume this employee exists because of the filter
        Employee emp = (Employee) request.getAttribute("employee");

        PrintWriter out = response.getWriter();
        response.setContentType("text/plan");

        try {
            if (hasAccessToRequests(emp, wantsFiledReqs, department)) {
                List<ReimbursementRequest> result = filterRequests(emp, wantsFiledReqs, department, status);
                if (result == null) {
                    response.setStatus(404);
                    out.println("Invalid options");
                } else {
                    JsonArray jArr = ServletUtils.requestsToJsonArray(result);
                    response.setContentType("application/json");
                    out.print(jArr.toString());
                }
            } else {
                out.print("Access denied");
            }
        } catch (NonExistentIdException e) {
            response.setStatus(404);
            out.println("Nonexistent depratment, or status");
        }
    }

    boolean hasAccessToRequests(Employee emp, boolean wantsFiledReqs, String department) {
        String rank = emp.getRank().getRank();
        if (wantsFiledReqs) {
            return true;
        } else if (department != null)
            return (rank.equals(EmployeeRank.DEPARTMENT_HEAD) || rank.equals(EmployeeRank.BENCO));

        else
            return rank.equals(EmployeeRank.BENCO);
    }

    /**
     * filtering requests based on request department, email, and request status
     *
     * @param emp
     * @param wantFiledReqs
     * @param department
     * @param status
     * @return
     * @throws NonExistentIdException
     */
    private List<ReimbursementRequest> filterRequests(Employee emp, boolean wantFiledReqs, String department, String status) throws NonExistentIdException {
        if (!wantFiledReqs && department == null && status == null)    // 1. all requests
            return requestDao.getAllRequests();

        else if (!wantFiledReqs && department == null) {               // 2. all requests by status
            switch (status) {
                case "all":
                    return requestDao.getAllRequests();
                case "pending":
                    return requestDao.getPendingRequests();
                default:
                    return requestDao.getRequestsByStatus(status);
            }
        } else if (!wantFiledReqs && status == null)                  // 3. by departments
            return requestDao.getRequestsByDepartment(department);

        else if (department == null && status == null)                // 4. filed requests
            return requestDao.getFiledRequests(emp.getEmail());

        else if (department == null) {                                // 5. filed by status
            switch (status) {
                case "all":
                    return requestDao.getFiledRequests(emp.getEmail());
                case "pending":
                    return requestDao.getPendingFiledRequests(emp.getEmail());
                default:
                    return requestDao.getFiledRequestsByStatus(emp.getEmail(), status);
            }
        } else if (!wantFiledReqs)                                        // 6. by departments and status
            return requestDao.getDepartmentRequestsByStatus(department, status);

        return null;
    }

}