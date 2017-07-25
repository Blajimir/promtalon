package ru.promtalon.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

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
        PAYMENT, //Оплата промо
        CONVERT, // Пополнение счета, перевод из валюты в купоны
        WITHDRAW // Вывод средств со счета, перевод из купонов в валюту
    }

    public enum OperationStatus {
        COMPLETED, //Завершенная операция
        FREEZE, //Операция в ожидании
        CANCELED //Отмененная операция (возврат средств отправителю)
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne(fetch = FetchType.EAGER)
    //Отправитель
    private CouponAccount sender;
    @ManyToOne(fetch = FetchType.EAGER)
    //Получатель
    private CouponAccount receiver;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Column(updatable = false)
    @Enumerated(EnumType.STRING)
    private OperationType type;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Enumerated(EnumType.STRING)
    private OperationStatus status;
    @UpdateTimestamp
    private Date regTimestamp;
}
