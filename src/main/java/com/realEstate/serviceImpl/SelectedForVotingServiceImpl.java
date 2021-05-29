package com.realEstate.serviceImpl;

import com.realEstate.entity.*;
import com.realEstate.entity.dto.RateInformationDTO;
import com.realEstate.entity.dto.RateItemDTO;
import com.realEstate.jpa.SelectedForVotingJPA;
import com.realEstate.service.FlatService;
import com.realEstate.service.SelectedForVotingService;
import com.realEstate.service.UsersService;
import com.realEstate.service.VoteService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class SelectedForVotingServiceImpl implements SelectedForVotingService {
    private final SelectedForVotingJPA selectedJPA;
    private final FlatService flatService;
    private final UsersService usersService;
    private final VoteService voteService;

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
    public void setRate(RateInformationDTO rateInformation){
        String mail = SecurityContextHolder.getContext().getAuthentication().getName();
        Users expert = usersService.findByMail(mail).orElse(null);
        if(expert!=null) {
            for (RateItemDTO item : rateInformation.getRates()) {
                SelectedForVoting selected = findById(item.getSelectedId());
                if (selected != null) {
                    boolean isExpertRated;
                    if(selected.getVoteList()!=null) {
                        isExpertRated= selected.getVoteList().stream().allMatch(vote -> vote.getExpert().getId() != expert.getId());
                    }else
                    {
                        isExpertRated = true;
                    }
                    if(isExpertRated) {
                        Vote vote = new Vote();
                        vote.setSelectedForVoting(selected);
                        vote.setExpert(expert);
                        vote.setVote(item.getVoteRate());
                        voteService.save(vote);
                    }
                }
            }
        }
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
