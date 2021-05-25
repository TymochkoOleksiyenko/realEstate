package com.realEstate.service;

import com.realEstate.entity.Image;
import org.springframework.web.multipart.MultipartFile;

public interface ImageService {
    Image save(MultipartFile multipartFile);
    Image save(Image image);
    Image findById(int id);
    void deleteById(int id);
}
