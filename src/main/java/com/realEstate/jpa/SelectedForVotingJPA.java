package com.realEstate.jpa;

import com.realEstate.entity.SelectedForVoting;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SelectedForVotingJPA extends JpaRepository<SelectedForVoting,Integer> {
}
