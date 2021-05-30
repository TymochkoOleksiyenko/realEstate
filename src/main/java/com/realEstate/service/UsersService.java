package com.realEstate.service;

import com.realEstate.enums.Role;
import com.realEstate.entity.Users;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface UsersService {
    Users save(Users user);
    Users save(Users user, MultipartFile multipartFile);
    Users update(Users user);
    Users update(Users user,MultipartFile multipartFile);
    Users register(Users user,MultipartFile multipartFile);
    Users countAverageRate(int id);
    Users findById(int id);
    List<Users> findByRole(Role role);
    Optional<Users> findByMail(String mail);
    List<Users> findAll();
    void deleteByID(int id);
}
