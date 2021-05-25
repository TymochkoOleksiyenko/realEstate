package com.realEstate.controller.user;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/login")
public class LoginController {
    @GetMapping
    public String get(Model model,String message){
        model.addAttribute("message",message);
        return "user/login";
    }

//    @PostMapping
//    public String post(){
//        return "user/login";
//    }

}
