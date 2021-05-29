package com.realEstate.controller.expert;

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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
    public String setRate(RateInformationDTO dto) {
        RateInformationDTO rateInformationDTO = new RateInformationDTO();
        List<RateItemDTO> list=new ArrayList<>();
        dto.setWishListId(10);
        list.add(new RateItemDTO(9,1));
        list.add(new RateItemDTO(10,2));
        list.add(new RateItemDTO(11,3));
        list.add(new RateItemDTO(12,4));
        rateInformationDTO.setRates(list);
        selectedForVotingService.setRate(rateInformationDTO);
        WishList wishList = wishListService.findById(dto.getWishListId());
        System.out.println("Votes count "+wishListService.checkMaxCountOfVotes(wishList));
        System.out.println("WishList "+wishList);
        if(wishList!=null && wishListService.checkMaxCountOfVotes(wishList)>3){
            shulceService.orderByPriority(wishList);
        }
        return "redirect:/expert/getListForRate";
    }

}
