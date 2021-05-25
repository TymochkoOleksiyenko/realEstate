package com.realEstate.jpa;

import com.realEstate.entity.Role;
import com.realEstate.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UsersJPA  extends JpaRepository<Users,Integer> {
    List<Users> findByRole(Role role);
    Optional<Users> findByMail(String mail);
}
