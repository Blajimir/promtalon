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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(updatable = false)
    @OneToOne(fetch = FetchType.EAGER)
    private CouponOperation couponOperation;
    @Column(updatable = false)
    @ManyToOne()
    private Promo promo;
}
