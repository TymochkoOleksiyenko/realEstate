package com.realEstate.service;

import com.realEstate.entity.WishList;

import java.util.List;

public interface WishListService {
    WishList save(WishList wishList);
    WishList addNewItem(int flatId);
    WishList findByUserId(int id);
    WishList findById(int id);
    List<WishList> findByExpertId(int expertId);
    List<WishList> findAll();
    void clearWishList();
    void sendForRate();
    void deleteByID(int id);
}
