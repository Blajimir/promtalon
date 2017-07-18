package ru.promtalon.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "addresses")
@Data
@NoArgsConstructor
public class Address  implements Serializable {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String zipCode;
    private String country;
    private String shortAddress;
}
