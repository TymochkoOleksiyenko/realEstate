package com.realEstate.jpa;

import com.realEstate.entity.Flat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;

public interface FlatJPA extends JpaRepository<Flat,Integer> {
    List<Flat> findByCreatedById(int id);
    List<Flat> findTop6ByOrderByIdDesc();

    @Query("select max(f.price) from Flat f ")
    BigDecimal getMaxPrice();
    @Query("select min(f.price) from Flat f ")
    BigDecimal getMinPrice();

    @Query("select max(f.countOfRooms) from Flat f ")
    Integer getMaxCountOfRooms();
    @Query("select min(f.countOfRooms) from Flat f ")
    Integer getMinCountOfRooms();

    @Query("select max(f.floor) from Flat f ")
    Integer getMaxFloor();
    @Query("select min(f.floor) from Flat f ")
    Integer getMinFloor();

    @Query("select max(f.yearOfEndingDevelopment) from Flat f ")
    Integer getMaxYearOfEndingDevelopment();
    @Query("select min(f.yearOfEndingDevelopment) from Flat f ")
    Integer getMinYearOfEndingDevelopment();

    List<Flat> findByPriceBetween(BigDecimal priceMin,BigDecimal priceMax);

    List<Flat> findByPriceBetweenAndYearOfEndingDevelopmentBetweenAndInfrastructureListIdInAndDistrictIdInAndCountOfRoomsBetween
            (BigDecimal priceMin,BigDecimal priceMax, Integer yearMin,Integer yearMax,List<Integer> infs,List<Integer> districtsId, Integer countOfRoomsMin,Integer countOfRoomsMax);

    List<Flat> findByPriceBetweenAndYearOfEndingDevelopmentBetweenAndDistrictIdInAndCountOfRoomsBetween
            (BigDecimal priceMin,BigDecimal priceMax, Integer yearMin,Integer yearMax,List<Integer> districtsId, Integer countOfRoomsMin,Integer countOfRoomsMax);


}
