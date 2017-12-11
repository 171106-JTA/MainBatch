package me.daxterix.trms.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="reqfile")
public class RequestFile implements Serializable {

    @Id
    @Column(name="file_id")
    @SequenceGenerator(name="trig_seq_file", sequenceName="seq_file", initialValue=1, allocationSize=1)
    @GeneratedValue(strategy=GenerationType.AUTO, generator="trig_seq_file")
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="req_id", foreignKey=@ForeignKey(name="fk_file_req"))
    private ReimbursementRequest request;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="file_purpose", foreignKey=@ForeignKey(name="fk_file_purpose"))
    private FilePurpose purpose;

    @Column(name="file_name", length=255)
    private String name;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="mime_type", foreignKey=@ForeignKey(name="fk_file_mime"))
    private MimeType mimeType;

    @Lob
    @Column(name="file_content")
    private byte[] content;

    @OneToOne(mappedBy="file")
    private EventGrade eventGrade;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public ReimbursementRequest getRequest() {
        return request;
    }

    public void setRequest(ReimbursementRequest request) {
        this.request = request;
    }

    public FilePurpose getPurpose() {
        return purpose;
    }

    public void setPurpose(FilePurpose type) {
        this.purpose = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    public MimeType getMimeType() {
        return mimeType;
    }

    public void setMimeType(MimeType mimeType) {
        this.mimeType = mimeType;
    }

    public EventGrade getEventGrade() {
        return eventGrade;
    }

    public void setEventGrade(EventGrade eventGrade) {
        this.eventGrade = eventGrade;
    }
}
