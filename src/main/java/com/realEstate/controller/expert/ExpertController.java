package com.realEstate.controller.expert;

import com.google.gson.Gson;
import com.realEstate.entity.StatusOFWishList;
import com.realEstate.entity.Users;
import com.realEstate.entity.WishList;
import com.realEstate.entity.dto.RateInformationDTO;
import com.realEstate.entity.dto.RateItemDTO;
import com.realEstate.service.SelectedForVotingService;
import com.realEstate.service.UsersService;
import com.realEstate.service.WishListService;
import com.realEstate.serviceImpl.ShulceServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Controller
@RequestMapping("/expert")
@AllArgsConstructor
public class ExpertController {
    private final UsersService usersService;
    private final WishListService wishListService;
    private final SelectedForVotingService selectedForVotingService;
    private final ShulceServiceImpl shulceService;
    private final EntityManager entityManager;

    @PreAuthorize("hasAuthority('EXPERT')")
    @GetMapping("/getListForRate")
    public String getListForRate(Model model) {
        String mail = SecurityContextHolder.getContext().getAuthentication().getName();
        Users user = usersService.findByMail(mail).orElse(null);
        if(user!=null){
            model.addAttribute("listForRate",new HashSet<>(wishListService.findByStatus(StatusOFWishList.WAIT_FOR_RATING)));
        }
        return "expert/list-for-grading";
    }

    @PreAuthorize("hasAuthority('EXPERT')")
    @GetMapping("/getItemForRate-{id}")
    public String getItemForRate(@PathVariable int id, Model model) {
        WishList wishList = wishListService.findById(id);
        model.addAttribute("wishList",wishList);
        model.addAttribute("isVoted",wishListService.isVoted(wishList));
        return "expert/flats-for-grading";
    }

    @PreAuthorize("hasAuthority('EXPERT')")
    @PostMapping("/setRate")
    @ResponseBody
    public void setRate(String dto) {
        RateInformationDTO rateInformationDTO = new Gson().fromJson(dto,RateInformationDTO.class);
        selectedForVotingService.setRate(rateInformationDTO);
        WishList wishList = wishListService.findById(rateInformationDTO.getWishListId());
        if(wishList!=null && wishListService.checkMaxCountOfVotes(wishList)>3){
            shulceService.orderByPriority(wishList);
        }
    }

}
