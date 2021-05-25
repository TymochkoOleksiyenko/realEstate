package com.realEstate.controller.user;

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
@AllArgsConstructor
@RequestMapping("/register")
public class RegistrationController {
    private final UsersService usersService;
    @GetMapping
    public String get(Model model,String error){
        model.addAttribute("error",error);
        return "user/register";
    }

    @PostMapping
    public String post(Users users, MultipartFile multipartFile){
        users.setRole(Role.USER);
        Users savedUser = usersService.register(users,multipartFile);
        if(savedUser==null){
            return "redirect:/register?error=Користувач з таким е-mail вже існує";
        }
        return "redirect:/login";
    }
}
