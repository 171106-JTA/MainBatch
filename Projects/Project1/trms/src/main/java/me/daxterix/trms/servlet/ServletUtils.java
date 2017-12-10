package me.daxterix.trms.servlet;

import me.daxterix.trms.model.*;

import javax.json.*;
import javax.servlet.http.Part;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public class ServletUtils {
    public static JsonArray stringsToJsonArrayString(List<String> strings) {
        JsonArrayBuilder arrBuilder = Json.createArrayBuilder();
        for (String s : strings)
            arrBuilder.add(s);
        JsonArray arr = arrBuilder.build();
        return arr;
    }

    public static JsonArray requestsToJsonArray(List<ReimbursementRequest> requests) {
        JsonArrayBuilder arrBuilder = Json.createArrayBuilder();
        for (ReimbursementRequest req : requests)
            arrBuilder.add(requestToJson(req));
        return arrBuilder.build();
    }

    private static JsonObject requestToJson(ReimbursementRequest req) {
        JsonObjectBuilder builder = Json.createObjectBuilder();
        builder.add("id", req.getId());
        builder.add("filerEmail", req.getFiler().getEmail());
        builder.add("status", req.getStatus().getStatus());
        builder.add("cost", req.getEventCost());
        builder.add("funding", req.getFunding());
        builder.add("description", req.getDescription());

        EventGrade grade = req.getGrade();
        if (grade == null)
            builder.addNull("passed");
        else builder.add("passed",
                (grade.getPassedFailed() != null && grade.getPassedFailed()) ||
                        (grade.getGradePercent() >= grade.getCutoffPercent())
        );
        if (req.isUrgent() != null)
            builder.add("isUrgent", req.isUrgent());
        else
            builder.addNull("isUrgent");

        builder.add("timeFiled", req.getTimeFiled().toString());
        builder.add("eventStart", req.getEventStart().toString());
        builder.add("eventEnd", req.getEventEnd().toString());

        builder.add("exceedsFunds", req.isExceedsFunds());
        builder.add("eventType", req.getEventType().getType());
        return builder.build();
    }

    public static JsonObject addressToJson(Address address) {
        JsonObjectBuilder builder = Json.createObjectBuilder();
        builder.add("address", address.getAddress());
        builder.add("city", address.getAddress());
        builder.add("state", address.getAddress());
        builder.add("zip", address.getAddress());
        return builder.build();
    }

    public static JsonObject dateTimeToJsonObject(LocalDateTime date) {
        JsonObjectBuilder builder = Json.createObjectBuilder();
        builder.add("year", date.getYear());
        builder.add("month", date.getMonthValue());
        builder.add("day", date.getDayOfMonth());
        builder.add("hour", date.getHour());
        builder.add("minute", date.getMinute());
        builder.add("second", date.getSecond());
        return builder.build();
    }


    public static JsonObject dateTimeToJsonObject(LocalDate date) {
        LocalDateTime dateTime = LocalDateTime.of(date, LocalTime.of(0, 0, 0));
        return dateTimeToJsonObject(dateTime);
    }

    public static JsonObject employeeToJson(Employee emp) {
        JsonObjectBuilder builder = Json.createObjectBuilder();
        builder.add("email", emp.getEmail());
        EmployeeInfo info = emp.getAccount().getInfo();
        if (info != null) {
            builder.add("address", addressToJson(emp.getAccount().getInfo().getAddress()));
            builder.add("birthday", dateTimeToJsonObject(emp.getAccount().getInfo().getBirthday()));
        }
        else {
            builder.addNull("address");
            builder.addNull("birthday");
        }

        builder.add("rank", emp.getRank().getRank());

        if (emp.getSupervisor() != null)
            builder.add("supervisorEmail", emp.getSupervisor().getEmail());
        else builder.addNull("supervisorEmail");
        builder.add("availableFunds", emp.getAvailableFunds());
        builder.add("firstname", emp.getFirstname());
        builder.add("lastname", emp.getLastname());
        builder.add("department", emp.getDepartment().getName());
        return builder.build();
    }

    /**
     * @param filePart
     * @param fileName
     * @param mimeType
     * @param filePurpose
     * @return
     * @throws IOException
     */
    static RequestFile readUploadIntoRequestFile(Part filePart, String fileName, String mimeType, String filePurpose) throws IOException {
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