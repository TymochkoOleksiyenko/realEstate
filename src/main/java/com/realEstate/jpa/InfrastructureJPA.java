package com.realEstate.jpa;

import com.realEstate.entity.Infrastructure;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InfrastructureJPA  extends JpaRepository<Infrastructure,Integer> {
}
