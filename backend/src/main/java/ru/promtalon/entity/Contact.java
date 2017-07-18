package ru.promtalon.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "contacts")
@Data
@NoArgsConstructor
public class Contact  implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String email;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private boolean emailAccept;
    @OneToOne(fetch = FetchType.EAGER)
    private Phone phone;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private boolean phoneAccept;
}
