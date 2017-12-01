package me.daxterix.trms.model;

import javax.persistence.*;
import java.io.Serializable;

@Table
@Entity(name="empaccount")
public class EmployeeAccount implements Serializable {
    @Id
    @Column(name="email", length=50)
    private String email;

    @Column(name="pass", length=50)
    private String password;

    @OneToOne(mappedBy="account", fetch=FetchType.LAZY, cascade=CascadeType.ALL)
    private Employee employee;

    @OneToOne(mappedBy="account", fetch=FetchType.LAZY, cascade=CascadeType.ALL)
    private EmployeeInfo info;

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public EmployeeInfo getInfo() {
        return info;
    }

    public void setInfo(EmployeeInfo info) {
        this.info = info;
    }
}
