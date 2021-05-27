package com.realEstate.controller.admin;

import com.realEstate.entity.Role;
import com.realEstate.entity.Users;
import com.realEstate.service.UsersService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/admin/experts")
@AllArgsConstructor
public class ExpertAdminController {
    private final UsersService usersService;

    @GetMapping
    public String get(Model model){
        model.addAttribute("listOfExperts",usersService.findByRole(Role.EXPERT));
        return "admin/agents";
    }

    @PostMapping("/add")
    public String add(Users expert, MultipartFile multipartFile){
        expert.setRole(Role.EXPERT);
        usersService.save(expert,multipartFile);
        return "redirect:/admin/experts";
    }

    @PostMapping("/update")
    public String update(Users expert,  MultipartFile multipartFile){
        usersService.update(expert,multipartFile);
        return "redirect:/admin/experts";
    }

    @PostMapping("/delete")
    public String delete(int id){
        usersService.deleteByID(id);
        return "redirect:/admin/experts";
    }
}
