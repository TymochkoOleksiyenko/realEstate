package com.realEstate.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class WishList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private boolean graded;

    @ManyToOne
    private Users user;

    @OneToMany(mappedBy = "wishList")
    private List<SelectedForVoting> list;

}
