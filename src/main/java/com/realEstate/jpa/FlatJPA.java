package com.realEstate.jpa;

import com.realEstate.entity.Flat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FlatJPA extends JpaRepository<Flat,Integer> {
    List<Flat> findByCreatedById(int id);
}
