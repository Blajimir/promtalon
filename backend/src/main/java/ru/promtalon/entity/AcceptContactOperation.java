package ru.promtalon.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(name = "accept_contact_operations")
@Data
@NoArgsConstructor
public class AcceptContactOperation implements Serializable {
    enum ContactType {
        PHONE,
        EMAIL
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;
    @NotNull
    @OneToOne(optional = false)
    private Client client;
    private ContactType type;
    private String acceptCode;
}
