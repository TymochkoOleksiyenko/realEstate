package com.realEstate.entity;

import lombok.Data;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Data
public class Users {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private int id;

    @OneToOne
    private Image image;

    private String fullName;
    private String mail;
    private String phone;
    private String password;
    private String address;
    private Integer experience;
    private BigDecimal averageRate;
    @Lob
    @Type(type = "org.hibernate.type.TextType")
    private String about;



    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "expert")
    private List<Feedback> feedbacks;

    @OneToMany(mappedBy = "createdBy")
    private List<Flat> myFlats;

    @Override
    public String toString() {
        return "Users{" +
                "id=" + id +
                ", fullName='" + fullName + '\'' +
                ", mail='" + mail + '\'' +
                ", phone='" + phone + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                '}';
    }
}
