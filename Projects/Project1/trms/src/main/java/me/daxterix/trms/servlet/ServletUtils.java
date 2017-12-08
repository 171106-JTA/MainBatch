package me.daxterix.trms.servlet;

import me.daxterix.trms.model.*;

import javax.json.*;
import javax.servlet.http.Part;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
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
        for (ReimbursementRequest req: requests)
            arrBuilder.add(requestToJson(req));
        return arrBuilder.build();
    }

    public static JsonObject requestToJson(ReimbursementRequest req) {
        JsonObjectBuilder builder = Json.createObjectBuilder();
        builder.add("id", req.getId());
        builder.add("filerEmail", req.getFiler().getEmail());
        builder.add("status", req.getStatus().getStatus());
        builder.add("cost", req.getEventCost());
        builder.add("funding", req.getFunding());
        builder.add("description", req.getDescription());

        EventGrade grade = req.getGrade();
        builder.add("passed",
                (grade.getPassedFailed() != null && grade.getPassedFailed()) ||
                        (grade.getGradePercent() >= grade.getCutoffPercent())
        );
        builder.add("isUrgent", req.isUrgent());
        builder.add("timeFiled", req.getTimeFiled().toString());
        builder.add("eventStart", req.getEventStart().toString());
        builder.add("eventEnd", req.getEventEnd().toString());

        builder.add("exceedsFunds", req.isExceedsFunds());
        builder.add("eventType", req.getEventType().getType());
        builder.addNull("history");  // empty user to match expected type
        builder.addNull("files");
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
