package com.realEstate.controller.user;

import com.realEstate.entity.Flat;
import com.realEstate.entity.Users;
import com.realEstate.service.DistrictService;
import com.realEstate.service.FlatService;
import com.realEstate.service.InfrastructureService;
import com.realEstate.service.UsersService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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


    @GetMapping("/myFlats-{userId}")
    public String myFlats(@PathVariable int userId, Model model){
        model.addAttribute("listOfFlats",flatService.findByCreatedById(userId));
        return "user/myFlats";
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
        return "redirect:/user/flats/create";
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
        return "redirect:/myFlats-"+user.getId();
    }
}
