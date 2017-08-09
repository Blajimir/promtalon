package ru.promtalon.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Embeddable
@Data
@NoArgsConstructor
public class ClientProfileSettings {
    //boolean acceptByPhone;
    boolean acceptByEmail;
    boolean subscription;
}
