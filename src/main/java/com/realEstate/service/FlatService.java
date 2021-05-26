package com.realEstate.service;

import com.realEstate.entity.Flat;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;

public interface FlatService {
    Flat save(Flat flat);
    Flat save(List<MultipartFile> fileList,Flat flat,String[] listOfInf);
    Flat update(Flat flat);
    Flat update(List<MultipartFile> fileList,Flat flat);
    Flat findById(int id);
    List<Flat> findByCreatedById(int id);
    List<Flat> findFirst6Desc();
    List<Flat> findAll();
    void deleteByID(int id);
    List<Flat> getFiltered(BigDecimal priceMin,BigDecimal priceMax, Integer yearMin,Integer yearMax, Integer[] infs,
                           Integer districtsId, Integer countOfRoomsMin,Integer countOfRoomsMax, String search);


    Integer getMaxFloor();
    Integer getMinFloor();
    BigDecimal getMaxPrice();
    BigDecimal getMinPrice();
    Integer getMaxCountOfRooms();
    Integer getMinCountOfRooms();
    Integer getMaxYearOfEndingDevelopment();
    Integer getMinYearOfEndingDevelopment();
}
