package com.realEstate.service;

import com.realEstate.entity.Vote;

import java.util.List;

public interface VoteService {
    Vote save(Vote vote);
    Vote update(Vote vote);
    Vote findById(int id);
    List<Vote> findAll();
    void deleteByID(int id);
}
