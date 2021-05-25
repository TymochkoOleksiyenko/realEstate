package com.realEstate.jpa;

import com.realEstate.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersJPA  extends JpaRepository<Users,Integer> {
}
