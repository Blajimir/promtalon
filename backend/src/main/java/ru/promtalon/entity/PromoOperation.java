package ru.promtalon.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "promo_operations")
@Data
@NoArgsConstructor
public class PromoOperation implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;
    @OneToOne(fetch = FetchType.EAGER)
    private CouponOperation couponOperation;
    @ManyToOne()
    private Promo promo;
}
