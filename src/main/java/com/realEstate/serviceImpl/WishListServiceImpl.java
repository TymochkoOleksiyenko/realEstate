package com.realEstate.serviceImpl;

import com.realEstate.entity.SelectedForVoting;
import com.realEstate.entity.StatusOFWishList;
import com.realEstate.entity.Users;
import com.realEstate.entity.WishList;
import com.realEstate.jpa.WishListJPA;
import com.realEstate.service.SelectedForVotingService;
import com.realEstate.service.UsersService;
import com.realEstate.service.WishListService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

@Service
@AllArgsConstructor
public class WishListServiceImpl implements WishListService {
    private final WishListJPA wishListJPA;
    private final UsersService usersService;
    private final SelectedForVotingService  selectedForVotingService;
    private final EntityManager entityManager;

    @Override
    public WishList save(WishList wishList) {
        return wishListJPA.save(wishList);
    }

    @Override
    public WishList addNewItem(int flatId) {
        WishList wishList;
        String mail = SecurityContextHolder.getContext().getAuthentication().getName();
        Users users = usersService.findByMail(mail).orElse(null);
        if(users.getWishList()==null){
            wishList = new WishList();
        }else {
            wishList = users.getWishList();
        }
        wishList.setUser(users);
        wishList.setStatus(StatusOFWishList.NOT_RATED);
        wishList = save(wishList);
        selectedForVotingService.addNewItem(wishList,flatId);
        users.setWishList(wishList);
        usersService.save(users);
        return wishList;
    }

    public boolean isVoted(WishList wishList){
        String mail = SecurityContextHolder.getContext().getAuthentication().getName();
        Users expert = usersService.findByMail(mail).orElse(new Users());
        for(SelectedForVoting selected: wishList.getList()){
            boolean temp = selected.getVoteList().stream().anyMatch(vote -> vote.getExpert().getId()==expert.getId());
            if(temp){
                return true;
            }
        }
        return false;
    }

    @Override
    public WishList findByUserId(int id) {
        return wishListJPA.findByUserId(id);
    }

    @Override
    public WishList findById(int id) {
        return wishListJPA.findById(id).orElse(null);
    }

    @Override
    public List<WishList> findByExpertId(int expertId) {
        return wishListJPA.findByExpertId(expertId);
    }

    @Override
    public List<WishList> findByStatus(StatusOFWishList status) {
        return wishListJPA.findByStatus(status);
    }

    @Override
    public List<WishList> findAll() {
        return wishListJPA.findAll();
    }

    @Override
    public void clearWishList() {
        String mail = SecurityContextHolder.getContext().getAuthentication().getName();
        Users user = usersService.findByMail(mail).orElse(null);
        if(user!=null){
            List<SelectedForVoting> list = user.getWishList().getList();
            for(SelectedForVoting selected:list){
                selectedForVotingService.deleteByID(selected.getId());
            }
        }
    }

    @Override
    public void sendForRate() {
        String mail = SecurityContextHolder.getContext().getAuthentication().getName();
        Users user = usersService.findByMail(mail).orElse(null);
        if(user!=null){
            WishList list = user.getWishList();
            if(list!=null && list.getList().size()>1 && list.getStatus().equals(StatusOFWishList.NOT_RATED)){
                list.setStatus(StatusOFWishList.WAIT_FOR_RATING);
                save(list);
            }
        }
    }

    @Override
    @Transactional
    public int checkMaxCountOfVotes(WishList wishList){
        int count = 0;
        if(wishList!=null) {
            for (SelectedForVoting selected : wishList.getList()) {
                entityManager.refresh(selected);
                count = Math.max(count, selected.getVoteList().size());
            }
        }
        return count;
    }

    @Override
    public void deleteByID(int id) {
        WishList wishList = findById(id);
        if(wishList!=null) {
            Users user = wishList.getUser();
            user.setWishList(null);
            usersService.save(user);
            wishListJPA.deleteById(id);
        }
    }
}
