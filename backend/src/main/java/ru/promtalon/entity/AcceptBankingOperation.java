package ru.promtalon.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "accept_banking_operations")
@Data
@NoArgsConstructor
public class AcceptBankingOperation implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;
    @NotNull
    @OneToOne
    private BankingOperation bankingOperation;
    @NotNull
    @Column(unique = true)
    private String acceptCode;
    @CreationTimestamp
    private Date crateTimestamp;

}
