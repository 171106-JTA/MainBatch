package me.daxterix.trms.model;

import org.hibernate.annotations.Check;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="grade")
@Check(constraints="(pass_fail IS NULL AND cutoff_percent IS NOT NULL AND grade_percent IS NOT NULL) OR " +
                   "(pass_fail IS NOT NULL AND cutoff_percent IS NULL AND grade_percent IS NULL)")
public class EventGrade implements Serializable {
    @Id
    @Column(name="req_id")
    private long requestId;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="req_id", foreignKey=@ForeignKey(name="fk_file_req"), updatable=false)
    private ReimbursementRequest request;

    @OneToOne(fetch = FetchType.LAZY, cascade=CascadeType.ALL)
    @JoinColumn(name="file_id", foreignKey=@ForeignKey(name="fk_grade_file"))
    private RequestFile file;       // should this be a one to many instead? would need a 'join' table

    @Convert(converter = BooleanToStringConverter.class)
    @Column(name="pass_fail", length=1)
    @Check(constraints="pass_fail = 'Y' OR pass_fail = 'N'")
    private Boolean passedFailed;   // if not null then the grading system is pass/fail

    @Column(name="grade_percent")
    @Check(constraints="grade_percent <= 100 AND grade_percent >= 0")
    private Float gradePercent;

    @Column(name="cutoff_percent")
    @Check(constraints="cutoff_percent<= 100 AND cutoff_percent >= 0")
    private Float cutoffPercent;


    public long getRequestId() {
        return requestId;
    }

    public void setRequestId(long requestId) {
        this.requestId = requestId;
    }

    public RequestFile getFile() {
        return file;
    }

    public void setFile(RequestFile file) {
        this.file = file;
    }

    public ReimbursementRequest getRequest() {
        return request;
    }

    public void setRequest(ReimbursementRequest request) {
        this.request = request;
    }

    public Boolean getPassedFailed() {
        return passedFailed;
    }

    public void setPassedFailed(Boolean passedFailed) {
        this.passedFailed = passedFailed;
    }

    public Float getGradePercent() {
        return gradePercent;
    }

    public void setGradePercent(Float gradePercent) {
        this.gradePercent = gradePercent;
    }

    public Float getCutoffPercent() {
        return cutoffPercent;
    }

    public void setCutoffPercent(Float cutoffPercent) {
        this.cutoffPercent = cutoffPercent;
    }
}
