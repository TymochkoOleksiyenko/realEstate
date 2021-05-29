package com.realEstate.jpa;

import com.realEstate.entity.StatusOFWishList;
import com.realEstate.entity.WishList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface WishListJPA extends JpaRepository<WishList,Integer> {
    WishList findByUserId(int id);
    List<WishList> findByStatus(StatusOFWishList status);

    @Query("select w from WishList w inner join SelectedForVoting s on s.wishList.id=w.id inner join Vote v on s.id=v.selectedForVoting.id where w.status='WAIT_FOR_RATING' and v.expert.id= :expertId ")
    List<WishList> findByExpertId(int expertId);
}

