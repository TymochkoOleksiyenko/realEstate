package com.realEstate.controller.admin;

import com.realEstate.service.FlatService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;

@Controller
@AllArgsConstructor
@PreAuthorize("hasAuthority('ROLE_ADMIN')")
public class AdminController {
    private final FlatService flatService;


    @GetMapping("/deleteFlat-{id}")
    public String delete(@PathVariable int id, HttpServletRequest request){
        flatService.deleteByID(id);
        return "redirect:/allFlats";
    }
}
