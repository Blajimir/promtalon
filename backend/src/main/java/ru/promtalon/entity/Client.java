package ru.promtalon.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
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
    @NotNull
    @Length(min = 3)
    private String firstName;
    @Length(min = 5)
    private String middleName;
    @NotNull
    @Length(min = 1)
    private String lastName;
    @NotNull
    @Valid
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
