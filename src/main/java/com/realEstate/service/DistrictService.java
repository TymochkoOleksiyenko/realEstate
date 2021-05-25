package com.realEstate.service;

import com.realEstate.entity.District;

import java.util.List;

public interface DistrictService {
    District save(District district);
    District findById(int id);
    List<District> findAll();
    void deleteByID(int id);
}
