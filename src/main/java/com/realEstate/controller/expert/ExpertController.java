package com.realEstate.controller.expert;

import com.realEstate.entity.Users;
import com.realEstate.service.UsersService;
import com.realEstate.service.WishListService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashSet;

@Controller
@RequestMapping("/expert")
@AllArgsConstructor
public class ExpertController {
    private final UsersService usersService;
    private final WishListService wishListService;

    @PreAuthorize("hasAuthority('EXPERT')")
    @GetMapping("/getListForRate")
    public String getListForRate(Model model) {
        String mail = SecurityContextHolder.getContext().getAuthentication().getName();
        Users user = usersService.findByMail(mail).orElse(null);
        if(user!=null){
            model.addAttribute("listForRate",new HashSet<>(wishListService.findByExpertId(user.getId())));
            System.out.println(wishListService.findByExpertId(user.getId()));
        }
        return "expert/list-for-grading";
    }

    @PreAuthorize("hasAuthority('EXPERT')")
    @GetMapping("/getItemForRate-{id}")
    public String getItemForRate(@PathVariable int id, Model model) {
        model.addAttribute("wishList",wishListService.findById(id));
        return "";
    }

    @PreAuthorize("hasAuthority('EXPERT')")
    @PostMapping("/setRate")
    public String setRate() {
        return "redirect:/expert/getListForRate";
    }

}
