package com.realEstate.jpa;

import com.realEstate.entity.District;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DistrictJPA extends JpaRepository<District,Integer> {
}
