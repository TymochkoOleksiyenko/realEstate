package com.realEstate.entity;

import lombok.Data;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Data
public class Flat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToMany(mappedBy = "flat",cascade = CascadeType.REMOVE)
    private List<Image> images;
    private String name;
    @Lob
    @Type(type = "org.hibernate.type.TextType")
    private String description;
    private String address;
    private BigDecimal price;
//    @todo lists
    private int countOfRooms;
    @ManyToOne
    private District district;
    private int floor;
    private BigDecimal area;
    private int yearOfEndingDevelopment;
    @ManyToMany(mappedBy = "flats")
    private List<Infrastructure> infrastructureList;

}
