package com.realEstate.service;

import com.realEstate.entity.Flat;
import com.realEstate.entity.Image;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ImageService {
    Image save(MultipartFile multipartFile);
    List<Image> save(List<MultipartFile> list, Flat flat);
    Image save(Image image);
    Image findById(int id);
    void deleteById(int id);
}
