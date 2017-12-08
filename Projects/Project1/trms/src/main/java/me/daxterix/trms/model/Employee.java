package me.daxterix.trms.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name="employee")
public class Employee implements Serializable {
    private static final long INITIAL_FUNDS = 1000;

    @Id
    @Column(name="email", length=50)
    private String email;

    @MapsId     // primary key (email) is also a foreign key
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="email", foreignKey=@ForeignKey(name="fk_emp_acc"), updatable=false)
    private EmployeeAccount account;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="dept_name", foreignKey=@ForeignKey(name="fk_emp_dept"))
    private Department department;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="supe_email", foreignKey=@ForeignKey(name="fk_emp_supe"))
    private Employee supervisor;

    @OneToMany(mappedBy="supervisor")
    private List<Employee> supervisees;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="emp_rank", foreignKey=@ForeignKey(name="fk_emp_rank"))
    private EmployeeRank rank;

    @Column(name="last_name", length=50)
    private String firstname;

    @Column(name="first_name", length=50)
    private String lastname;

    @Column(name="funds", nullable = false)
    private float availableFunds = INITIAL_FUNDS;

    @OneToMany(fetch=FetchType.LAZY, mappedBy="filer")
    private List<ReimbursementRequest> requests;

    @OneToMany(fetch=FetchType.LAZY, mappedBy="approver", cascade=CascadeType.ALL)
    private List<RequestHistory> history;

    public EmployeeAccount getAccount() {
        return account;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAccount(EmployeeAccount account) {
        this.account = account;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public float getAvailableFunds() {
        return availableFunds;
    }

    public void setAvailableFunds(float availableFunds) {
        this.availableFunds = availableFunds;
    }

    public EmployeeRank getRank() {
        return rank;
    }

    public void setRank(EmployeeRank rank) {
        this.rank = rank;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Employee getSupervisor() {
        return supervisor;
    }

    public void setSupervisor(Employee supervisor) {
        this.supervisor = supervisor;
    }

    public List<Employee> getSupervisees() {
        return supervisees;
    }

    public void setSupervisees(List<Employee> supervisees) {
        this.supervisees = supervisees;
    }

    public List<ReimbursementRequest> getRequests() {
        return requests;
    }

    public void setRequests(List<ReimbursementRequest> requests) {
        this.requests = requests;
    }

    public List<RequestHistory> getHistory() {
        return history;
    }

    public void setHistory(List<RequestHistory> history) {
        this.history = history;
    }
}
