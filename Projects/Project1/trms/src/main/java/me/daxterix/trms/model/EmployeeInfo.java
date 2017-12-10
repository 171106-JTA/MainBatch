package me.daxterix.trms.model;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name="empinfo")
public class EmployeeInfo implements Serializable {

    @Id
    @Column(name="email", length=50)
    private String email;

    @MapsId // primary key (email) is also a foreign key
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="email", foreignKey=@ForeignKey(name="fk_info_acc"), updatable=false)
    private EmployeeAccount account;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="addr_id", foreignKey=@ForeignKey(name="fk_info_addr"))
    private Address address;

    @Column(name="birthday")
    private LocalDate birthday;

    @Column(name="phone", length=12)
    private String phone;


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public EmployeeAccount getAccount() {
        return account;
    }

    public void setAccount(EmployeeAccount account) {
        this.account = account;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
