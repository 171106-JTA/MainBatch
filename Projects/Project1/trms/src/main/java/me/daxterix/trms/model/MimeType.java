package me.daxterix.trms.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name="mimetype")
public class MimeType implements Serializable {

    public static final String PNG = "image/png";

    @Id
    @Column(name="mime_type", length=50)
    private String mimeType;


    public MimeType() {}

    public MimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }
}
