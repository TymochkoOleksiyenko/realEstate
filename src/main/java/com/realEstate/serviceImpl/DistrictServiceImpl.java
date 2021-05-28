package com.realEstate.serviceImpl;

import com.realEstate.entity.District;
import com.realEstate.entity.Infrastructure;
import com.realEstate.jpa.DistrictJPA;
import com.realEstate.service.DistrictService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class DistrictServiceImpl implements DistrictService {
    private final DistrictJPA districtJPA;
    @Override
    public District save(District district) {
        return districtJPA.save(district);
    }

    @Override
    public District update(District district) {
        return districtJPA.save(district);
    }

    @Override
    public District findById(int id) {
        return districtJPA.findById(id).orElse(null);
    }

    @Override
    public List<District> findAll() {
        return districtJPA.findAll();
    }

    @Override
    public void deleteByID(int id) {
        districtJPA.deleteById(id);
    }
}
