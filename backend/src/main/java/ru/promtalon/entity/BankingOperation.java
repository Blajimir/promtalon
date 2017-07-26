package ru.promtalon.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

@Entity
@Table(name = "banking_operations")
@Data
@NoArgsConstructor
public class BankingOperation implements Serializable {
    public enum OperationStatus {
        CREATE, //Создан но не инициализирован(клиент не начал операцию оплаты, патежный огрегатор еще не оповещен)
        WAIT, //В ожидании подтверждения оплаты
        PAID, //Оплачен
        REFUND //Возврат средств
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;
    @OneToOne(fetch = FetchType.EAGER)
    private CouponOperation couponOperation;
    //количество переведенных средств
    private BigDecimal amountInCurrency;
    //TODO Подумать над изменением курса и связанной с этим отмене неоплаченных ордеров
    //текущий курс
    @Column(updatable = false)
    private int rate;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @NotNull
    @Enumerated(EnumType.STRING)
    private OperationStatus status;
    @JsonIgnore
    @Column(updatable = false)
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name="banking_operations_properties", joinColumns=@JoinColumn(name="banking_operations_id"))
    private Map<String,String> additionalParameters;
    @JsonIgnore
    private String bankClientClass;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String bankClientName;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @CreationTimestamp
    private Date crateTimestamp;
}
