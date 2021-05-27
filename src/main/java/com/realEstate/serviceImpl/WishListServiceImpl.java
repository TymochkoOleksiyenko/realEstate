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

import java.util.List;

@Service
@AllArgsConstructor
public class WishListServiceImpl implements WishListService {
    private final WishListJPA wishListJPA;
    private final UsersService usersService;
    private final SelectedForVotingService  selectedForVotingService;

    @Override
    public WishList save(WishList wishList) {
        return wishListJPA.save(wishList);
    }

    @Override
    public WishList addNewItem(int flatId) {
        WishList wishList = new WishList();
        String mail = SecurityContextHolder.getContext().getAuthentication().getName();
        Users users = usersService.findByMail(mail).orElse(null);
        wishList.setUser(users);
        wishList.setStatus(StatusOFWishList.NOT_RATED);
        wishList = save(wishList);
        selectedForVotingService.addNewItem(wishList,flatId);
        return wishList;
    }

    @Override
    public WishList findById(int id) {
        return wishListJPA.findById(id).orElse(null);
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
    public void deleteByID(int id) {
        wishListJPA.deleteById(id);
    }
}
