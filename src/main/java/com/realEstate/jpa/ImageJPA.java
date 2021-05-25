package com.realEstate.jpa;

import com.realEstate.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageJPA  extends JpaRepository<Image,Integer> {
}
