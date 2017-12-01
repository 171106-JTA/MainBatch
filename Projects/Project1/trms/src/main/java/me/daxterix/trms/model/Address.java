package me.daxterix.trms.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="address")
public class Address implements Serializable{
    @Id
    @Column(name="addr_id")
    @SequenceGenerator(name="trig_seq_addr", sequenceName="seq_addr", initialValue=1, allocationSize=1)
    @GeneratedValue(strategy=GenerationType.AUTO, generator="trig_seq_addr")
    private long id;

    @Column(name="addr_address", length=50)
    private String address;

    @Column(name="addr_city", length=50)
    private String city;

    @Column(name="addr_state", length=50)
    private String state;

    @Column(name="addr_zip", length=12)
    private String zip;



    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }
}
