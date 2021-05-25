package com.realEstate.jpa;

import com.realEstate.entity.Flat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FlatJPA extends JpaRepository<Flat,Integer> {
}
