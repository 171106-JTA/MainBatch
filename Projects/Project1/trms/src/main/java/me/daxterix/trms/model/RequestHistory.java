package me.daxterix.trms.model;

import org.hibernate.annotations.Check;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name="reqhistory")
@Check(constraints="(approval_file IS NULL) OR " +
                   "(approval_file IS NOT NULL AND approver_email IS NOT NULL)")
public class RequestHistory implements Serializable{

    @Id
    @Column(name="hist_id")
    @SequenceGenerator(name="trig_seq_hist", sequenceName="seq_hist", initialValue=1, allocationSize=1)
    @GeneratedValue(strategy=GenerationType.AUTO, generator="trig_seq_hist")
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="req_id", foreignKey=@ForeignKey(name="fk_hist_req"))
    private ReimbursementRequest request;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="approver_email", foreignKey=@ForeignKey(name="fk_hist_emp"))
    private Employee approver;

    @OneToOne(fetch = FetchType.LAZY)       // should this be a one to many instead? would need a 'join' table
    @JoinColumn(name="approval_file", foreignKey=@ForeignKey(name="fk_hist_file"))
    private RequestFile file;   // null if not approved by file (approval email)

    @ManyToOne(fetch = FetchType.EAGER)      // or should this be one to one
    @JoinColumn(name="post_status", foreignKey=@ForeignKey(name="fk_hist_pre_status"))
    private RequestStatus postStatus;

    @ManyToOne(fetch = FetchType.EAGER)      // or should this be one to one
    @JoinColumn(name="pre_status", foreignKey=@ForeignKey(name="fk_hist_post_status"))
    private RequestStatus preStatus;

    @Column(name="approval_time")
    private LocalDateTime time;

    @Column(name="approval_reason")
    private String reason;

    public RequestHistory(ReimbursementRequest request, Employee approver, RequestFile file, RequestStatus preStatus,
                          RequestStatus postStatus, LocalDateTime time, String reason) {
        this.request = request;
        this.approver = approver;
        this.file = file;
        this.postStatus = postStatus;
        this.preStatus = preStatus;
        this.time = time;
        this.reason = reason;
    }

    public RequestHistory() {
    }

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

    public Employee getApprover() {
        return approver;
    }

    public void setApprover(Employee approver) {
        this.approver = approver;
    }

    public RequestFile getFile() {
        return file;
    }

    public void setFile(RequestFile file) {
        this.file = file;
    }

    public RequestStatus getPostStatus() {
        return postStatus;
    }

    public void setPostStatus(RequestStatus postStatus) {
        this.postStatus = postStatus;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public RequestStatus getPreStatus() {
        return preStatus;
    }

    public void setPreStatus(RequestStatus preStatus) {
        this.preStatus = preStatus;
    }
}
