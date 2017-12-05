package me.daxterix.trms.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="reqstatus")
public class RequestStatus {
    public static final String AWAITING_SUPERVISOR = "Awaiting Direct Supervisor Approval";
    public static final String AWAITING_DEPT_HEAD = "Awaiting Department Head Approval";
    public static final String AWAITING_BENCO = "Awaiting Benefits Coordinator Approval";
    public static final String AWAITING_GRADE = "Awaiting Event Grade and Review";
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
