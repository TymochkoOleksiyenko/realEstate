package com.realEstate.serviceImpl;

import com.realEstate.entity.Feedback;
import com.realEstate.entity.Users;
import com.realEstate.jpa.FeedbackJPA;
import com.realEstate.service.FeedbackService;
import com.realEstate.service.UsersService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class FeedbackServiceImpl implements FeedbackService {
    private final FeedbackJPA feedbackJPA;
    private final UsersService usersService;

    @Override
    public Feedback save(Feedback feedback) {
        return feedbackJPA.save(feedback);
    }

    @Override
    public Feedback addFeedBackToExpert(Feedback feedback, int expertId) {
        Users expert = usersService.findById(expertId);
        String mail = SecurityContextHolder.getContext().getAuthentication().getName();
        Users user = usersService.findByMail(mail).orElse(null);
        feedback.setExpert(expert);
        feedback.setUser(user);
        return save(feedback);
    }

    @Override
    public Feedback update(Feedback feedback) {
        return null;
    }

    @Override
    public Feedback findById(int id) {
        return feedbackJPA.findById(id).orElse(null);
    }

    @Override
    public List<Feedback> findAll() {
        return feedbackJPA.findAll();
    }

    @Override
    public void deleteByID(int id) {
        feedbackJPA.deleteById(id);
    }
}
