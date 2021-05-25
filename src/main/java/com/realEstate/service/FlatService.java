package com.realEstate.service;

import com.realEstate.entity.Flat;

import java.util.List;

public interface FlatService {
    Flat save(Flat flat);
    Flat update(Flat flat);
    Flat findById(int id);
    List<Flat> findAll();
    void deleteByID(int id);
}
