package com.realEstate.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
public class Feedback {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    private Users user;
    @ManyToOne
    private Users expert;
    private int mark;
    private String body;
    private Date date;

    @Override
    public String toString() {
        return "Feedback{" +
                "id=" + id +
                ", user=" + user.getId() +
                ", expert=" + expert.getId() +
                ", mark=" + mark +
                ", body='" + body + '\'' +
                ", date=" + date +
                '}';
    }
}
