package com.realEstate.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class HistoryView {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    private Users user;
    @ManyToOne
    private Flat flat;
}
