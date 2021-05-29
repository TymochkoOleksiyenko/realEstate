package com.realEstate.serviceImpl;

import com.realEstate.entity.Vote;
import com.realEstate.jpa.VoteJPA;
import com.realEstate.service.VoteService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class VoteServiceImpl implements VoteService {
    private final VoteJPA voteJpa;

    @Override
    public Vote save(Vote vote) {
        return voteJpa.save(vote);
    }

    @Override
    public Vote update(Vote vote) {
        return voteJpa.save(vote);
    }

    @Override
    public Vote findById(int id) {
        return voteJpa.findById(id).orElse(null);
    }

    @Override
    public List<Vote> findAll() {
        return voteJpa.findAll();
    }

    @Override
    public void deleteByID(int id) {
        voteJpa.deleteById(id);
    }
}
