package com.realEstate.serviceImpl;

import com.realEstate.entity.Infrastructure;
import com.realEstate.jpa.InfrastructureJPA;
import com.realEstate.service.InfrastructureService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class InfrastructureServiceImpl implements InfrastructureService {
    private final InfrastructureJPA infJpa;

    @Override
    public Infrastructure save(Infrastructure inf) {
        return infJpa.save(inf);
    }

    @Override
    public Infrastructure update(Infrastructure inf) {
        return infJpa.save(inf);
    }

    @Override
    public Infrastructure findById(int id) {
        return infJpa.findById(id).orElse(null);
    }

    @Override
    public List<Infrastructure> findAll() {
        return infJpa.findAll();
    }

    @Override
    public void deleteByID(int id) {
        infJpa.deleteById(id);
    }
}
