package com.realEstate.serviceImpl;

import com.realEstate.entity.Feedback;
import com.realEstate.entity.Users;
import com.realEstate.jpa.FeedbackJPA;
import com.realEstate.service.FeedbackService;
import com.realEstate.service.UsersService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class FeedbackServiceImpl implements FeedbackService {
    private final FeedbackJPA feedbackJPA;
    private final UsersService usersService;
    private final EntityManager entityManager;

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
        feedback.setDate(new Date());
        feedback.setUser(user);
        feedback = save(feedback);
        usersService.countAverageRate(expertId);
        return feedback;
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
    @Transactional
    public void deleteByID(int id) {
        Feedback feedback = findById(id);
        if(feedback!=null) {
            Users expert = feedback.getExpert();
            feedbackJPA.deleteById(id);
            List<Feedback> feedbacks = expert.getFeedbacks();
            feedbacks.remove(feedback);
            expert.setFeedbacks(feedbacks);
            usersService.save(expert);
            System.out.println(expert);
            usersService.countAverageRate(expert.getId());
        }
    }
}
