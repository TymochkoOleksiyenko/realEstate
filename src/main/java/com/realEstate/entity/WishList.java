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

    @OneToOne
    private Users user;

    @OneToMany(mappedBy = "wishList")
    private List<SelectedForVoting> list;

    @Enumerated(EnumType.STRING)
    private StatusOFWishList status = StatusOFWishList.NOT_RATED;
}
