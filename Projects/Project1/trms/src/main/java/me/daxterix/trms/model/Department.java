package me.daxterix.trms.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name="department")
public class Department implements Serializable {
    public static final String SLYTHERIN = "Slytherin";
    public static final String GYFFINDOR = "Gryffindor";
    public static final String HUFFLEPUFF = "Hufflepuff";
    public static final String RAVENCLAW = "Ravenclaw";

    @Id
    @Column(name="dept_name", length=50)
    private String name;

    public Department() {}

    public Department(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
