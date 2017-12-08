package me.daxterix.trms.model;

import org.hibernate.annotations.Check;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * models a reimbursement request
 */

@Entity
@Table(name="request")
@Check(constraints="date_filed <= (event_start - 7) AND event_start <= event_end")
public class ReimbursementRequest implements Serializable {

    @Id
    @Column(name="req_id")
    @SequenceGenerator(name="trig_seq_req", sequenceName="seq_req", initialValue=1, allocationSize=1)
    @GeneratedValue(strategy=GenerationType.AUTO, generator="trig_seq_req")
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="filer_email",foreignKey=@ForeignKey(name="fk_req_emp"))
    private Employee filer;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="event_type",foreignKey=@ForeignKey(name="fk_req_type"))
    private EventType eventType;

    @ManyToOne(fetch = FetchType.LAZY, cascade=CascadeType.ALL)
    @JoinColumn(name="addr_id", foreignKey=@ForeignKey(name="fk_req_addr"))
    private Address address;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="req_status", foreignKey=@ForeignKey(name="fk_req_status"))
    private RequestStatus status;

    @Column(name="event_cost", nullable=false)
    private float eventCost;

    @Column(name="funding", nullable=false)
    private float funding;

    @Convert(converter = BooleanToStringConverter.class)
    @Column(name="is_urgent", length=1)
    @Check(constraints="is_urgent = 'Y' OR is_urgent = 'N'")
    private Boolean isUrgent;

    @Convert(converter = BooleanToStringConverter.class)
    @Column(name="exceeds_funds", length=1)
    @Check(constraints="exceeds_funds = 'Y' OR exceeds_funds = 'N'")
    private Boolean exceedsFunds;

    @Column(name="date_filed")
    private LocalDateTime timeFiled;

    @Column(name="event_start")
    private LocalDate eventStart;

    @Column(name="event_end")
    private LocalDate eventEnd;

    @Column(name="description", length=300)
    private String description;

    @OneToOne(mappedBy="request", fetch=FetchType.EAGER, cascade=CascadeType.ALL)
    private EventGrade grade;

    @OneToMany(mappedBy="request", fetch = FetchType.LAZY, cascade=CascadeType.ALL)
    private List<RequestFile> files;

    @OneToMany(mappedBy="request", fetch = FetchType.LAZY, cascade=CascadeType.ALL)
    private List<RequestHistory> history;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Employee getFiler() {
        return filer;
    }

    public void setFiler(Employee filer) {
        this.filer = filer;
    }

    public EventType getEventType() {
        return eventType;
    }

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }

    public float getEventCost() {
        return eventCost;
    }

    public void setEventCost(float eventCost) {
        this.eventCost = eventCost;
    }

    public float getFunding() {
        return funding;
    }

    public void setFunding(float funding) {
        this.funding = funding;
    }

    public Boolean isUrgent() {
        return isUrgent;
    }

    public Boolean isExceedsFunds() {
        return exceedsFunds;
    }

    public void setExceedsFunds(boolean exceedsFunds) {
        this.exceedsFunds = exceedsFunds;
    }

    public void setUrgent(boolean urgent) {
        isUrgent = urgent;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public RequestStatus getStatus() {
        return status;
    }

    public void setStatus(RequestStatus status) {
        this.status = status;
    }

    public LocalDateTime getTimeFiled() {
        return timeFiled;
    }

    public void setTimeFiled(LocalDateTime dateFiled) {
        this.timeFiled = dateFiled;
    }

    public LocalDate getEventStart() {
        return eventStart;
    }

    public void setEventStart(LocalDate eventStart) {
        this.eventStart = eventStart;
    }

    public LocalDate getEventEnd() {
        return eventEnd;
    }

    public void setEventEnd(LocalDate eventEnd) {
        this.eventEnd = eventEnd;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<RequestFile> getFiles() {
        return files;
    }

    public void setFiles(List<RequestFile> files) {
        this.files = files;
    }

    public EventGrade getGrade() {
        return grade;
    }

    public void setGrade(EventGrade grade) {
        this.grade = grade;
    }

    public List<RequestHistory> getHistory() {
        return history;
    }

    public void setHistory(List<RequestHistory> history) {
        this.history = history;
    }
}
