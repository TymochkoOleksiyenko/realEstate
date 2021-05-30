package com.realEstate.entity;

import com.realEstate.enums.Heating;
import com.realEstate.enums.Material;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

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

    @Enumerated(EnumType.STRING)
    private Heating heatingType;

    @Enumerated(EnumType.STRING)
    private Material materialType;

    private int floor;
    private BigDecimal area;
    private int yearOfEndingDevelopment;
    @ManyToMany(mappedBy = "flats")
    private List<Infrastructure> infrastructureList;

    @ManyToOne
    private Users createdBy;

    @OneToMany(mappedBy = "flat",cascade = CascadeType.REMOVE)
    private List<SelectedForVoting> selectedList;

    @Override
    public String toString() {
        return "Flat{" +
                "id=" + id +
                ", images=" + images +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", address='" + address + '\'' +
                ", price=" + price +
                ", countOfRooms=" + countOfRooms +
                ", district=" + district +
                ", heatingType=" + heatingType +
                ", materialType=" + materialType +
                ", floor=" + floor +
                ", area=" + area +
                ", yearOfEndingDevelopment=" + yearOfEndingDevelopment +
                ", infrastructureList=" + infrastructureList +
                ", createdBy=" + createdBy +
                '}';
    }

    @Override
    public int compareTo( Flat o) {
        return this.getId()-o.getId();
    }
}
