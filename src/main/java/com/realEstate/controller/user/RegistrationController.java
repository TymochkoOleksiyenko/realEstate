package com.realEstate.controller.user;

import com.realEstate.enums.Role;
import com.realEstate.entity.Users;
import com.realEstate.service.UsersService;
import lombok.AllArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
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
    private JavaMailSender javaMailSender;

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
        sendEmailRequest(users.getMail());
        return "redirect:/login";
    }

    public void sendEmailRequest(String email){
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(email);

        msg.setSubject("Дякуємо, що обрали наш сервіс");
        msg.setText("Переглянути і обрати нерухомість можна за цим посиланням\n https://find-home-diplom.herokuapp.com/allFlats");

        javaMailSender.send(msg);
    }
}
