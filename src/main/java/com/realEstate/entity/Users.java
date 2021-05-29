package com.realEstate.entity;

import lombok.Data;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@Entity
@Data
public class Users {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private int id;

    @OneToOne(cascade = CascadeType.REMOVE)
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

    @OneToMany(mappedBy = "expert",cascade = CascadeType.REMOVE)
    private List<Feedback> feedbacks;

    @OneToMany(mappedBy = "createdBy", cascade = CascadeType.REMOVE)
    private List<Flat> myFlats;

    @OneToOne(cascade = CascadeType.REMOVE)
    private WishList wishList;

    @Override
    public String toString() {
        return "Users{" +
                "id=" + id +
                ", fullName='" + fullName + '\'' +
                ", mail='" + mail + '\'' +
                ", phone='" + phone + '\'' +
                ", password='" + password + '\'' +
                ", wishList='" + wishList + '\'' +
                ", role=" + role +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Users users = (Users) o;
        return id == users.id &&
                Objects.equals(fullName, users.fullName) &&
                Objects.equals(mail, users.mail) &&
                Objects.equals(phone, users.phone) &&
                Objects.equals(password, users.password) &&
                Objects.equals(address, users.address) &&
                Objects.equals(experience, users.experience) &&
                Objects.equals(averageRate, users.averageRate) &&
                Objects.equals(about, users.about) &&
                role == users.role;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, fullName, mail, phone, password, address, experience, averageRate, about, role);
    }
}
