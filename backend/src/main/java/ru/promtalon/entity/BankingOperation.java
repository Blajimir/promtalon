package ru.promtalon.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
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
        PAID, //Оплачен
        WAIT, //В ожидании
        REFUND //Возврат средств
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(updatable = false)
    @OneToOne(fetch = FetchType.EAGER)
    private CouponOperation couponOperation;
    //количество переведенных средств
    private BigDecimal amountInCurrency;
    //текущий курс
    @Column(updatable = false)
    private int rate;
    @JsonIgnore
    @Column(updatable = false)
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name="banking_operations_properties", joinColumns=@JoinColumn(name="banking_operations_id"))
    private Map<String,String> additionalParameters;
    @JsonIgnore
    private String bankClientClass;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String bankClientName;
    @Basic(optional = false)
    @Column(insertable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date crateTimestamp;
}
