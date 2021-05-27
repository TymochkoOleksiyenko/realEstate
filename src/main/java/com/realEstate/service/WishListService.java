package com.realEstate.service;

import com.realEstate.entity.WishList;

import java.util.List;

public interface WishListService {
    WishList save(WishList wishList);
    WishList addNewItem(int flatId);
    WishList findById(int id);
    List<WishList> findAll();
    void clearWishList();
    void deleteByID(int id);
}
