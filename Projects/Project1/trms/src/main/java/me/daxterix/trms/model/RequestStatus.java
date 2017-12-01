package me.daxterix.trms.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="reqstatus")
public class RequestStatus {
    public static final String AWAITING_SUPERVISOR = "Pending Direct Supervisor Approval";
    public static final String APPROVED_SUPERVISOR = "Approved by Direct Supervisor";
    public static final String APPROVED_DEPT_HEAD = "Approved by Department Head";
    public static final String APPROVED_BENCO = "Approved by Benefits Coordinator";
    public static final String PENDING_GRADE= "Pending Grade Review";   // should this be broken up?
    public static final String GRANTED = "Granted";
    public static final String DENIED = "Denied";

    @Id
    @Column(name="req_status")
    private String status;


    public RequestStatus(String status) {
        this.status = status;
    }

    public RequestStatus() {
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RequestStatus)) return false;

        RequestStatus that = (RequestStatus) o;

        return status != null ? status.equals(that.status) : that.status == null;
    }

    @Override
    public int hashCode() {
        return status != null ? status.hashCode() : 0;
    }
}
