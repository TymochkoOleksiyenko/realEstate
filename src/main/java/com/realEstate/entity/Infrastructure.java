package com.realEstate.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class Infrastructure {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;

    @ManyToMany
    @JoinTable(
            name = "flat_infrastructure",
            joinColumns = @JoinColumn(name = "flat", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "item",referencedColumnName = "id")
    )
    private List<Flat> flats;
}
