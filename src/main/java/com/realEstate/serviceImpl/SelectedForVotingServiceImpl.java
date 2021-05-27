package com.realEstate.serviceImpl;

import com.realEstate.entity.Flat;
import com.realEstate.entity.SelectedForVoting;
import com.realEstate.entity.WishList;
import com.realEstate.jpa.SelectedForVotingJPA;
import com.realEstate.service.FlatService;
import com.realEstate.service.SelectedForVotingService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class SelectedForVotingServiceImpl implements SelectedForVotingService {
    private final SelectedForVotingJPA selectedJPA;
    private final FlatService flatService;

    @Override
    public SelectedForVoting save(SelectedForVoting selected) {
        return selectedJPA.save(selected);
    }

    @Override
    public SelectedForVoting addNewItem(WishList wishList, int flatId){
        long count = 0;
        if(wishList.getList()!=null){
            count = wishList.getList().stream().filter(selected -> selected.getFlat().getId()==flatId).count();
        }
        if(count==0) {
            SelectedForVoting selected = new SelectedForVoting();
            selected.setWishList(wishList);
            Flat flat = flatService.findById(flatId);
            if (flat != null) {
                selected.setFlat(flat);
                return save(selected);
            }
        }
        return null;
    }

    @Override
    public SelectedForVoting findById(int id) {
        return selectedJPA.findById(id).orElse(null);
    }

    @Override
    public List<SelectedForVoting> findAll() {
        return selectedJPA.findAll();
    }

    @Override
    public void deleteByID(int id) {
        selectedJPA.deleteById(id);
    }
}
