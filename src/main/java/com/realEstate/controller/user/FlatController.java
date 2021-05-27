package com.realEstate.controller.user;

import com.realEstate.entity.Flat;
import com.realEstate.entity.Users;
import com.realEstate.service.*;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;

@Controller
@RequestMapping("/user/flats")
@AllArgsConstructor
public class FlatController {
    private final FlatService flatService;
    private final UsersService usersService;
    private final DistrictService districtService;
    private final InfrastructureService infService;
    private final WishListService wishListService;
    private final SelectedForVotingService selectedForVotingService;


    @GetMapping("/myFlats")
    public String myFlats( Model model){
        String mail = SecurityContextHolder.getContext().getAuthentication().getName();
        int userId = usersService.findByMail(mail).orElse(new Users()).getId();
        model.addAttribute("listOfFlats",flatService.findByCreatedById(userId));
        return "user/myFlats";
    }

    @GetMapping("/myWishList")
    public String myWishList( Model model){
        String mail = SecurityContextHolder.getContext().getAuthentication().getName();
        Users user = usersService.findByMail(mail).orElse(null);
        if(user!=null) {
            model.addAttribute("wishList", user.getWishList());
        }
        return "user/wishlist";
    }

    @GetMapping("/clearWishList")
    public String clearWishList(){
        wishListService.clearWishList();
        return "redirect:/user/flats/myWishList";
    }

    @ResponseBody
    @PostMapping("/addWishItem-{id}")
    public void addWishItem(@PathVariable int id){
        wishListService.addNewItem(id);
    }

    @GetMapping("/deleteWishItem-{id}")
    public String deleteWishItem(@PathVariable int id){
        selectedForVotingService.deleteByID(id);
        return "redirect:/user/flats/myWishList";
    }


    @GetMapping("/create")
    public String get(Model model){
        model.addAttribute("listOfDistricts",districtService.findAll());
        model.addAttribute("listOfInfrastructure",infService.findAll());
        return "user/addFlat";
    }

    @PostMapping("/create")
    public String create(Flat flat, MultipartFile[] files,String[] listOfInf){
        String mail = SecurityContextHolder.getContext().getAuthentication().getName();
        usersService.findByMail(mail).ifPresent(flat::setCreatedBy);
        if(files!=null) {
            flatService.save(Arrays.asList(files), flat,listOfInf);
        }else {
            flatService.save(flat);
        }
        return "redirect:/user/flats/myFlats";
    }

    @GetMapping("/edit-{id}")
    public String edit(@PathVariable int id, Model model){
        model.addAttribute("flat",flatService.findById(id));
        return "user/editFlat";
    }

    @PostMapping("/edit")
    public String edit(Flat flat, MultipartFile[] files){
        flatService.update(Arrays.asList(files),flat);
        return "redirect:/user/flats";
    }

    @GetMapping("/delete-{id}")
    public String delete(@PathVariable int id){
        String mail = SecurityContextHolder.getContext().getAuthentication().getName();
        Users user = usersService.findByMail(mail).orElse(new Users());
        flatService.deleteByID(id);
        return "redirect:/user/flats/myFlats";
    }
}
