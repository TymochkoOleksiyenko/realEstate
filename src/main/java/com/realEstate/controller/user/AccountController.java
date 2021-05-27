package com.realEstate.controller.user;

import com.realEstate.entity.Users;
import com.realEstate.service.UsersService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

@Controller
@AllArgsConstructor
public class AccountController {
    private final UsersService usersService;

    @GetMapping("/account")
    public String account(Model model){
        String mail = SecurityContextHolder.getContext().getAuthentication().getName();
        model.addAttribute("user",usersService.findByMail(mail).orElse(new Users()));
        return "user/edit-user";
    }

    @PostMapping("/account")
    public String account(Users user, MultipartFile multipartFile){
        usersService.update(user, multipartFile);
        if(user.getPassword().isEmpty()) {
            return "redirect:/account";
        }else {
            return "redirect:/login";
        }
    }
}
