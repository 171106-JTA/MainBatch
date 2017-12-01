package me.daxterix.trms.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="emprank")
public class EmployeeRank {

    public static final String REGULAR = "Regular";
    public static final String SUPERVISOR = "Supervisor";
    public static final String DEPARTMENT_HEAD = "Department Head";
    public static final String BENCO = "Benefits Coordinator";

    @Id
    @Column(name="emp_rank", length=50)
    private String rank;

    public EmployeeRank() {}

    public EmployeeRank(String rank) {
        this.rank = rank;
    }

}
