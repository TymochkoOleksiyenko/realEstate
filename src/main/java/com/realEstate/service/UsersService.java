package com.realEstate.service;

import com.realEstate.entity.Users;

import java.util.List;

public interface UsersService {
    Users save(Users user);
    Users update(Users user);
    Users findById(int id);
    List<Users> findAll();
    void deleteByID(int id);
}
