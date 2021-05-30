package com.realEstate.controller.admin;

import com.realEstate.entity.District;
import com.realEstate.entity.Infrastructure;
import com.realEstate.service.DistrictService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/districts")
@AllArgsConstructor
public class DistrictAdminController {
    private final DistrictService districtService;

    @GetMapping
    public String get(Model model){
        model.addAttribute("listOfDistricts",districtService.findAll());
        return "admin/districts";
    }

    @PostMapping("/add")
    public String add(District district){
        districtService.save(district);
        return "redirect:/admin/districts";
    }

    @PostMapping("/update")
    public String update(District district){
        districtService.update(district);
        return "redirect:/admin/districts";
    }

    @PostMapping("/delete")
    public String delete(int id){
        districtService.deleteByID(id);
        return "redirect:/admin/districts";
    }
}
