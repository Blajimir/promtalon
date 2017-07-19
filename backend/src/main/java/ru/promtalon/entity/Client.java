package ru.promtalon.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Класс-сущность предоставляющая данные о клиенте
 * */

@Entity
@Table(name = "clients")
@Data
@NoArgsConstructor
public class Client implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(updatable = false)
    @OneToOne(fetch = FetchType.EAGER)
    private User user;
    private String firstName;
    private String middleName;
    private String lastName;
    private Contact contact;
    @Basic(optional = false)
    @Column(insertable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date regDate;
    //TODO Подумать, стоит или нет добавлять это поле сюда (скорее всего нет)
    /*@ReadOnlyProperty
    @OneToOne(fetch = FetchType.EAGER)
    //Личный счет купонов
    private BigDecimal acct;*/
}
