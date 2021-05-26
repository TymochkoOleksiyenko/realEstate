package com.realEstate.entity;

import lombok.Data;

import javax.persistence.*;

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


    @Enumerated(EnumType.STRING)
    private Role role;

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
