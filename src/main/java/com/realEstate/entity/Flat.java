package com.realEstate.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.jetbrains.annotations.NotNull;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Getter
@Setter
public class Flat implements Comparable<Flat>{
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

    @ManyToOne
    private Users createdBy;

    @Override
    public String toString() {
        return "Flat{" +
                "id=" + id +
                ", images=" + images.size() +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", address='" + address + '\'' +
                ", price=" + price +
                ", countOfRooms=" + countOfRooms +
                ", district=" + district.getId() +
                ", floor=" + floor +
                ", area=" + area +
                ", yearOfEndingDevelopment=" + yearOfEndingDevelopment +
                ", infrastructureList=" + infrastructureList +
                ", createdBy=" + createdBy +
                '}';
    }

    @Override
    public int compareTo(@NotNull Flat o) {
        return this.getId()-o.getId();
    }
}
