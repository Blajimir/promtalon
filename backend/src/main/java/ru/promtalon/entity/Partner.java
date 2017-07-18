package ru.promtalon.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "partners")
@Data
@NoArgsConstructor
public class Partner implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @OneToOne(fetch = FetchType.EAGER)
    private Client client;
    @OneToOne(fetch = FetchType.EAGER)
    private Address address;
    @OneToOne(fetch = FetchType.EAGER)
    private Contact contact;
    private String siteLink;
    private boolean enabled;
}
