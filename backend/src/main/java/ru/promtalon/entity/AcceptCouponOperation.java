package ru.promtalon.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(name = "accept_coupon_operations")
@Data
@NoArgsConstructor
public class AcceptCouponOperation implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;
    @NotNull
    @OneToOne
    private CouponOperation couponOperation;
    @NotNull
    @Column(unique = true)
    private String acceptCode;
}
