package com.realEstate.serviceImpl;

import com.realEstate.entity.Flat;
import com.realEstate.entity.Image;
import com.realEstate.jpa.FlatJPA;
import com.realEstate.service.FlatService;
import com.realEstate.service.ImageService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@AllArgsConstructor
public class FlatServiceImpl implements FlatService {
    private final FlatJPA flatJPA;
    private final ImageService imageService;

    @Override
    public Flat save(Flat flat) {
        return flatJPA.save(flat);
    }

    @Override
    public Flat save(List<MultipartFile> fileList, Flat flat) {
        Flat flatDB = save(flat);
        imageService.save(fileList,flat);
        return flatDB;
    }

    @Override
    public Flat update(Flat flat) {
        return save(flat);
    }

    @Override
    public Flat update(List<MultipartFile> fileList, Flat flat) {
        imageService.save(fileList,flat);
        return update(flat);
    }

    @Override
    public Flat findById(int id) {
        return flatJPA.findById(id).orElse(null);
    }

    @Override
    public List<Flat> findByCreatedById(int id) {
        return flatJPA.findByCreatedById(id);
    }

    @Override
    public List<Flat> findAll() {
        return flatJPA.findAll();
    }

    @Override
    public void deleteByID(int id) {
        flatJPA.deleteById(id);
    }
}
