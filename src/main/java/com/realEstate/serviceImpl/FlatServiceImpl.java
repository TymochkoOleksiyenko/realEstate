package com.realEstate.serviceImpl;

import com.realEstate.entity.District;
import com.realEstate.entity.Flat;
import com.realEstate.entity.Infrastructure;
import com.realEstate.jpa.FlatJPA;
import com.realEstate.service.DistrictService;
import com.realEstate.service.FlatService;
import com.realEstate.service.ImageService;
import com.realEstate.service.InfrastructureService;
import lombok.AllArgsConstructor;
import org.hibernate.mapping.Collection;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@Service
@AllArgsConstructor
public class FlatServiceImpl implements FlatService {
    private final FlatJPA flatJPA;
    private final ImageService imageService;
    private final InfrastructureService infService;
    private final DistrictService districtService;

    @Override
    public Flat save(Flat flat) {
        return flatJPA.save(flat);
    }

    @Override
    public Flat save(List<MultipartFile> fileList, Flat flat,String[] listOfInf) {
        Flat flatDB = save(flat);
        imageService.save(fileList,flatDB);
        if(listOfInf!=null && listOfInf.length!=0){
            List<String> stringList = Arrays.stream(listOfInf).filter(s -> !s.isEmpty()).collect(Collectors.toList());
            for(String str:stringList){
                try {
                    int id = Integer.parseInt(str);
                    Infrastructure infrastructure = infService.findById(id);
                    if(infrastructure!=null){
                        infrastructure.getFlats().add(flatDB);
                        infService.save(infrastructure);
                    }
                }catch (Exception e){
                    continue;
                }
            }
        }
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
    public List<Flat> findFirst6Desc() {
        return flatJPA.findTop6ByOrderByIdDesc();
    }

    @Override
    public List<Flat> findAll() {
        return flatJPA.findAll();
    }

    @Override
    public void deleteByID(int id) {
        Flat flat = findById(id);
        if (flat!=null){
            for(Infrastructure in:flat.getInfrastructureList()) {
                List<Flat> temp = in.getFlats();
                temp.remove(flat);
                in.setFlats(temp);
                infService.save(in);
            }
            flatJPA.deleteById(id);
        }
    }

    @Override
    public List<Flat> getFiltered(BigDecimal priceMin, BigDecimal priceMax, Integer yearMin, Integer yearMax,
                                  Integer[] infs, Integer districtsId, Integer countOfRoomsMin,
                                  Integer countOfRoomsMax, String search) {
        List<Integer> infsIdList;
        List<Integer> districtIdList;
        if(priceMin==null){
            priceMin = flatJPA.getMinPrice();
        }
        if(priceMax==null){
            priceMax = flatJPA.getMaxPrice();
        }
        if(yearMin==null){
            yearMin = flatJPA.getMinYearOfEndingDevelopment();
        }
        if(yearMax==null){
            yearMax = flatJPA.getMaxYearOfEndingDevelopment();
        }
        if(infs==null || infs.length==0){
            infsIdList = null;
        }else{
            infsIdList = Arrays.asList(infs);
        }
        if(districtsId==null || districtsId==0){
            districtIdList = districtService.findAll().stream().flatMap(dis -> Stream.of(dis.getId())).collect(Collectors.toList());
        }else{
            districtIdList = Collections.singletonList(districtsId);
        }
        if(countOfRoomsMin==null){
            countOfRoomsMin = flatJPA.getMinCountOfRooms();
        }
        if(countOfRoomsMax==null){
            countOfRoomsMax = flatJPA.getMaxCountOfRooms();
        }
        System.out.println("priceMin " + priceMin);
        System.out.println("priceMax " + priceMax);
        System.out.println("yearMin " + yearMin);
        System.out.println("yearMax " + yearMax);
        System.out.println("infsIdList " + infsIdList);
        System.out.println("districtIdList " + districtIdList);
        System.out.println("countOfRoomsMin " + countOfRoomsMin);
        System.out.println("countOfRoomsMax " + countOfRoomsMax);
        if(infsIdList==null) {
            return flatJPA.findByPriceBetweenAndYearOfEndingDevelopmentBetweenAndDistrictIdInAndCountOfRoomsBetween
                    (priceMin, priceMax, yearMin, yearMax, districtIdList, countOfRoomsMin, countOfRoomsMax);
        }else {
            return flatJPA.findByPriceBetweenAndYearOfEndingDevelopmentBetweenAndInfrastructureListIdInAndDistrictIdInAndCountOfRoomsBetween
                    (priceMin, priceMax, yearMin, yearMax, infsIdList, districtIdList, countOfRoomsMin, countOfRoomsMax);
        }
    }

    @Override
    public Integer getMaxFloor() {
        return flatJPA.getMaxFloor();
    }

    @Override
    public Integer getMinFloor() {
        return flatJPA.getMinFloor();
    }

    @Override
    public BigDecimal getMaxPrice() {
        return flatJPA.getMaxPrice();
    }

    @Override
    public BigDecimal getMinPrice() {
        return flatJPA.getMinPrice();
    }

    @Override
    public Integer getMaxCountOfRooms() {
        return flatJPA.getMaxCountOfRooms();
    }

    @Override
    public Integer getMinCountOfRooms() {
        return flatJPA.getMinCountOfRooms();
    }

    @Override
    public Integer getMaxYearOfEndingDevelopment() {
        return flatJPA.getMaxYearOfEndingDevelopment();
    }

    @Override
    public Integer getMinYearOfEndingDevelopment() {
        return flatJPA.getMinYearOfEndingDevelopment();
    }
}
