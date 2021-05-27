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

    @Override
    public WishList findByUserId(int id) {
        return wishListJPA.findByUserId(id);
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
