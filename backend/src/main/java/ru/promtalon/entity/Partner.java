package ru.promtalon.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "partners")
@Data
@NoArgsConstructor
public class Partner implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    @OneToOne(fetch = FetchType.EAGER)
    private Client client;
    private Address address;
    private Contact contact;
    private String siteLink;
    private boolean enabled;
    @CreationTimestamp
    private Date regDate;
}
