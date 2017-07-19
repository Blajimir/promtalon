package ru.promtalon.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.List;

@Embeddable
@Data
@NoArgsConstructor
public class Contact  implements Serializable {
    @Pattern(regexp = ".+@.+\\..+")
    private String email;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private boolean emailAccept;
    private String phone;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private boolean phoneAccept;
}
