package me.daxterix.trms.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name="filepurpose")
public class FilePurpose implements Serializable{

    @Id
    @Column(name="file_purpose", length=50)
    private String purpose;

    public FilePurpose() {}

    public FilePurpose(String purpose) {
        this.purpose = purpose;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String type) {
        this.purpose = type;
    }
}
