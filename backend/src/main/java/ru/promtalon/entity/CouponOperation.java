package ru.promtalon.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "coupon_operations")
@Data
@NoArgsConstructor
public class CouponOperation implements Serializable {
    public enum OperationType {
        TRANSFER, //Перевод купонов
        PAYMENT, //Оплата
        CONVERT, // Пополнение счета, перевод из валюты в купоны
        WITHDRAW // Вывод средств со счета, перевод из купонов в валюту
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(updatable = false)
    @ManyToOne(fetch = FetchType.EAGER)
    //Отправитель
    private CouponAccount sender;
    @Column(updatable = false)
    @ManyToOne(fetch = FetchType.EAGER)
    //Получатель
    private CouponAccount receiver;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Column(updatable = false)
    @Enumerated(EnumType.STRING)
    private OperationType operationType;
    @Basic(optional = false)
    @Column(insertable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date regTimestamp;
}
