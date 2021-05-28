package com.realEstate.controller.admin;

import com.realEstate.entity.District;
import com.realEstate.entity.Infrastructure;
import com.realEstate.entity.Users;
import com.realEstate.service.DistrictService;
import com.realEstate.service.InfrastructureService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/admin/infrastructures")
@AllArgsConstructor
public class InfrastructureAdminController {
    private final InfrastructureService infService;

    @GetMapping
    public String get(Model model){
        model.addAttribute("listOfInf",infService.findAll());
        return "admin/infrastructures";
    }

    @PostMapping("/add")
    public String add(Infrastructure infrastructure){
        infService.save(infrastructure);
        return "redirect:/admin/infrastructures";
    }

    @PostMapping("/update")
    public String update(Infrastructure infrastructure){
        infService.update(infrastructure);
        return "redirect:/admin/infrastructures";
    }

    @PostMapping("/delete")
    public String delete(int id){
        infService.deleteByID(id);
        return "redirect:/admin/infrastructures";
    }
}
