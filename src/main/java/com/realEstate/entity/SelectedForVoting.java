package com.realEstate.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class SelectedForVoting {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    private WishList wishList;

    @ManyToOne
    private Flat flat;
    @OneToMany(mappedBy = "selectedForVoting", cascade = CascadeType.REMOVE)
    private List<Vote> voteList;
}
