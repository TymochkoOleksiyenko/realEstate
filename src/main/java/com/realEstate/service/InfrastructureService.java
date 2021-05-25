package com.realEstate.service;

import com.realEstate.entity.Flat;
import com.realEstate.entity.Infrastructure;

import java.util.List;

public interface InfrastructureService {
    Infrastructure save(Infrastructure inf);
    Infrastructure update(Infrastructure inf);
    Infrastructure findById(int id);
    List<Infrastructure> findAll();
    void deleteByID(int id);
}
