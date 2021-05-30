package com.realEstate.controller;

import com.realEstate.entity.Feedback;
import com.realEstate.entity.Users;
import com.realEstate.enums.Role;
import com.realEstate.service.FeedbackService;
import com.realEstate.service.UsersService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.text.SimpleDateFormat;

@Controller
@AllArgsConstructor
public class AgentController {
    private UsersService usersService;
    private FeedbackService feedbackService;
    @GetMapping("/agents")
    public String getAllAgents(Model model){
        model.addAttribute("listOfAgents",usersService.findByRole(Role.EXPERT));
        return "user/agents";
    }

    @GetMapping("/agent-{id}")
    public String getAgentPage(@PathVariable int id, Model model){
        model.addAttribute("agent",usersService.findById(id));
        model.addAttribute("dateFormat",new SimpleDateFormat("dd-MM-yyyy"));
        model.addAttribute("listOfAgents",usersService.findByRole(Role.EXPERT));
        String mail = SecurityContextHolder.getContext().getAuthentication().getName();
        model.addAttribute("user",usersService.findByMail(mail).orElse(new Users()));
        return "user/agent-detail";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/addAgentFeedBack")
    public String setAgentFeedBack(int expertId, Feedback feedback){
        feedbackService.addFeedBackToExpert(feedback,expertId);
        return "redirect:/agent-"+expertId;
    }

    @GetMapping("/deleteFeedback-{agentId}-{feedId}")
    public String setAgentFeedBack(@PathVariable  int agentId,@PathVariable int feedId){
        feedbackService.deleteByID(feedId);
        return "redirect:/agent-"+agentId;
    }
}
