package ru.promtalon.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Личный счет
 * */

@Entity
@Table(name = "coupon_accounts")
@Data
@NoArgsConstructor
public class CouponAccount implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @OneToOne(fetch = FetchType.EAGER)
    private Client client;
    //Количество купонов
    private BigDecimal amount;
}
