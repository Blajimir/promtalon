package ru.promtalon.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * Сущность для подтверждения разных значимых операция, например: Смена пароля, подтверждение почты/телефона и т.д.
 *
 * @author Aleksandr aka Blajimir
 * @since 2017-08-09
 */

@Entity
@Table(name = "accept_operations", uniqueConstraints = @UniqueConstraint(columnNames = {"type", "client_id"}))
@Data
@NoArgsConstructor
public class AcceptOperation implements Serializable {
    public enum OperationType {
        ACCEPT_PHONE,
        ACCEPT_MAIL,
        RESET_PASSWORD
    }

    public enum ContactType {
        PHONE,
        EMAIL
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotNull
    @ManyToOne(optional = false)
    private Client client;
    @NotNull
    private OperationType type;
    @NotNull
    private ContactType contact;
    @NotNull
    private String acceptCode;
    @CreationTimestamp
    private Date createDate;
}
