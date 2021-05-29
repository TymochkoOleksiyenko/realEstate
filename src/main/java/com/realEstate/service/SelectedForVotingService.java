package com.realEstate.service;

import com.realEstate.entity.SelectedForVoting;
import com.realEstate.entity.WishList;
import com.realEstate.entity.dto.RateInformationDTO;

import java.util.List;

public interface SelectedForVotingService {
    SelectedForVoting save(SelectedForVoting selected);
    SelectedForVoting addNewItem(WishList wishList, int flatId);
    void setRate(RateInformationDTO rateInformation);
    SelectedForVoting findById(int id);
    List<SelectedForVoting> findAll();
    void deleteByID(int id);
}
