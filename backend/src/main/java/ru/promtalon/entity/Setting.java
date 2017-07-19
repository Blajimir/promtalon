package ru.promtalon.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Map;
@Entity
@Table(name = "settings")
@Data
@NoArgsConstructor
public class Setting implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotNull
    @Column(unique = true)
    private String name;
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name="setting_properties", joinColumns=@JoinColumn(name="banking_operations_id"))
    Map<String,String> props;
}
