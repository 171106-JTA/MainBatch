package me.daxterix.trms.servlet;

import me.daxterix.trms.model.FilePurpose;
import me.daxterix.trms.model.MimeType;
import me.daxterix.trms.model.RequestFile;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.servlet.http.Part;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class ServletUtils {
    public static String stringsToJsonArrayString(List<String> strings) {
        JsonArrayBuilder arrBuilder = Json.createArrayBuilder();
        for (String s : strings)
            arrBuilder.add(s);
        JsonArray arr = arrBuilder.build();
        return arr.toString();
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
