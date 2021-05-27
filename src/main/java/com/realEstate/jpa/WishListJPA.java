package com.realEstate.jpa;

import com.realEstate.entity.WishList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WishListJPA extends JpaRepository<WishList,Integer> {
    WishList findByUserId(int id);
}

