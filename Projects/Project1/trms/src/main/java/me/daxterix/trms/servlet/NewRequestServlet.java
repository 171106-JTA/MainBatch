package me.daxterix.trms.servlet;

import me.daxterix.trms.dao.DAOUtils;
import me.daxterix.trms.dao.ObjectDAO;
import me.daxterix.trms.dao.exception.DuplicateIdException;
import me.daxterix.trms.dao.exception.NonExistentIdException;
import me.daxterix.trms.model.*;
import me.daxterix.trms.service.RequestService;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

@MultipartConfig
@WebServlet(name = "NewRequestServlet", urlPatterns="/employee/newRequest")
public class NewRequestServlet extends HttpServlet {
    private static final long serialVersionUID = 1957120628249070345L;
    ObjectDAO objectDao = DAOUtils.getObjectDAO();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String type = request.getParameter("eventType");
        String address = request.getParameter("eventAddress");
        String city = request.getParameter("eventCity");
        String state = request.getParameter("eventState");
        String zip = request.getParameter("eventZip");
        String description = request.getParameter("eventDescription");
        String priceStr = request.getParameter("eventPrice");
        String startDateStr = request.getParameter("eventStartDate");
        String endDateStr = request.getParameter("eventEndDate");

        ReimbursementRequest newRequest = new ReimbursementRequest();
        Employee emp = (Employee)request.getAttribute("employee");    // set by authentication filter
        newRequest.setFiler(emp);
        newRequest.setExceedsFunds(false);
        newRequest.setStatus(new RequestStatus(RequestStatus.AWAITING_SUPERVISOR));

        Address addr = new Address();
        addr.setAddress(address);
        addr.setCity(city);
        addr.setCity(state);
        addr.setCity(zip);
        newRequest.setAddress(addr);
        newRequest.setTimeFiled(LocalDateTime.now());
        newRequest.setDescription(description);

        newRequest.setEventType(new EventType(type));


        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        try {
            newRequest.setEventCost(Float.parseFloat(priceStr));
            newRequest.setEventStart(LocalDate.parse(startDateStr));
            newRequest.setEventEnd(LocalDate.parse(endDateStr));
            if (RequestService.addRequest(newRequest)) {
                out.print("<div>request ok</div>");
                Part filePart = request.getPart("eventFile");
                String fileName = request.getParameter("eventFileName");
                String mimeType = request.getParameter("eventFileMimeType");

                RequestFile requestFile = readUploadIntoRequestFile(filePart, fileName, mimeType, FilePurpose.EVENT_INFO);
                RequestService.addRequestFile(newRequest, requestFile);
                out.print("<div>file ok</div>");
            }
        }
        catch (NumberFormatException | DateTimeParseException e) {
            out.print("<div>invalid date or price</div>");
        }
        catch (NonExistentIdException | DuplicateIdException e) {
            e.printStackTrace();
            out.print("<div>Oops! Duplicate or nonexistent id encountered.</div>");
        }
        out.println("request ok??");
    }


    /**
     *
     * @param filePart
     * @param fileName
     * @param mimeType
     * @param filePurpose
     * @return
     * @throws IOException
     */
    RequestFile readUploadIntoRequestFile(Part filePart, String fileName, String mimeType, String filePurpose) throws IOException {
        if (filePart == null)
            return null;

        RequestFile reqFile = new RequestFile();

        InputStream is = null;
        ByteArrayOutputStream os = null;
        try {
            is = filePart.getInputStream();
            os = new ByteArrayOutputStream();

            // taken from PUBHUB project code
            byte[] buffer = new byte[1024];  // read in 1KB chunks
            while (is.read(buffer) != -1) {
                os.write(buffer);
            }
            reqFile.setContent(os.toByteArray());

            reqFile.setMimeType(new MimeType(mimeType));
            reqFile.setPurpose(new FilePurpose(filePurpose));
            reqFile.setName(fileName);
            return reqFile;

        } catch (IOException e) {
            System.out.println("Could not upload file!");
            e.printStackTrace();
        } finally {
            if (is != null)
                is.close();
            if (os != null)
                os.close();
        }
        return null;
    }
}
