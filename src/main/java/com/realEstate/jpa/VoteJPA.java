package com.realEstate.jpa;

import com.realEstate.entity.Vote;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VoteJPA extends JpaRepository<Vote,Integer> {
}
