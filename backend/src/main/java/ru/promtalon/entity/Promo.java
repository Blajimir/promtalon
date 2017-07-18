package ru.promtalon.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "promos")
@Data
@NoArgsConstructor
public class Promo implements Serializable{
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String description;
    @ManyToOne(fetch = FetchType.EAGER)
    private Category category;
    @ManyToOne(fetch = FetchType.EAGER)
    private Partner partner;
    private String iconUri;
    private String content;
    private boolean enable;
    private Date startDate;
    private Date endDate;
    private BigDecimal amount;
}
