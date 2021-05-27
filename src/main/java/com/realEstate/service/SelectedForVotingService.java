package com.realEstate.service;

import com.realEstate.entity.SelectedForVoting;
import com.realEstate.entity.WishList;

import java.util.List;

public interface SelectedForVotingService {
    SelectedForVoting save(SelectedForVoting selected);
    SelectedForVoting addNewItem(WishList wishList, int flatId);
    SelectedForVoting findById(int id);
    List<SelectedForVoting> findAll();
    void deleteByID(int id);
}
