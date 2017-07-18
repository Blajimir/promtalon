package ru.promtalon.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "clients")
@Data
@NoArgsConstructor
public class Client implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String firstName;
    private String middleName;
    private String lastName;
    @OneToOne(fetch = FetchType.EAGER)
    private Contact contact;
    //Личный счет купонов
    private BigDecimal acct;
}
