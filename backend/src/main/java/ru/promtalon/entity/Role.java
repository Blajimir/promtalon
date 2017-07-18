package ru.promtalon.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "roles")
@Getter
@Setter
@NoArgsConstructor
public class Role implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotNull
    private String name;

    public Role(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null||!(obj instanceof Role)) return false;
        if(this == obj) return true;
        Role other = (Role) obj;
        return this.getName().equals(other.getName());
    }
}
