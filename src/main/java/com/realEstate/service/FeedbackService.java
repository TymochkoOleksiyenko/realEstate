package com.realEstate.service;

import com.realEstate.entity.Feedback;

import java.util.List;

public interface FeedbackService {
    Feedback save(Feedback feedback);
    Feedback update(Feedback feedback);
    Feedback findById(int id);
    List<Feedback> findAll();
    void deleteByID(int id);
}
