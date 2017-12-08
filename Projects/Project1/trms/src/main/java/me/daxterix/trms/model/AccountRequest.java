package me.daxterix.trms.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="accountrequest")
public class AccountRequest {
    @Id
    @Column(name="email")
    private String email;

    @MapsId
    @OneToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="email", foreignKey=@ForeignKey(name="fk_accountreq_acc"))
    public EmployeeAccount account;

    private LocalDateTime fileDate;
}
