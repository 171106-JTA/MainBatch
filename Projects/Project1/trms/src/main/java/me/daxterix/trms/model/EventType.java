package me.daxterix.trms.model;

import org.hibernate.annotations.Check;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name="eventtype")
public class EventType implements Serializable {

    public static final String UNIVERSITY_COURSE = "University Course";
    public static final String SEMINAR = "Seminars";
    public static final String CERTIFICATION_PREP = "Certification Preparation Class";
    public static final String CERTIFICATION = "Certification";
    public static final String TECH_TRAINING = "Technical Training";
    public static final String OTHER = "Other";

    @Id
    @Column(name="event_type", length=50)
    private String type;

    @Column(name="percent_off", nullable=false)
    @Check(constraints="percent_off >= 0 AND percent_off <= 100")
    private float percentOff;

    public EventType(String type, float percentOff) {
        this.type = type;
        this.percentOff = percentOff;
    }

    public EventType() {}

    public EventType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public float getPercentOff() {
        return percentOff;
    }

    public void setPercentOff(float percentOff) {
        this.percentOff = percentOff;
    }
}