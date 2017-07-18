package ru.promtalon.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "coupon_operations")
@Data
@NoArgsConstructor
public class CouponOperation implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne(fetch = FetchType.EAGER)
    //Отправитель
    private Client sender;
    @ManyToOne(fetch = FetchType.EAGER)
    //Получатель
    private Client receiver;
}
