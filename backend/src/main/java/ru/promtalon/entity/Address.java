package ru.promtalon.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
public class Address  implements Serializable {
    private String zipCode;
    private String country;
    private String shortAddress;
}
