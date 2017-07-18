package ru.promtalon.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "categories")
@Data
@NoArgsConstructor
public class Category implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String description;
    private String iconUri;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinTable(name = "categoty_parens_childs",
            joinColumns = @JoinColumn(name = "child_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "parent_id", referencedColumnName = "id")
    )
    private Category parent;
    @OneToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "categoty_parens_childs",
            joinColumns = @JoinColumn(name = "parent_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "child_id", referencedColumnName = "id")
    )
    private List<Category> childs;
}
