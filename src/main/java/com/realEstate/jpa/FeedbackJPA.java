package com.realEstate.jpa;

import com.realEstate.entity.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedbackJPA extends JpaRepository<Feedback,Integer> {
}
