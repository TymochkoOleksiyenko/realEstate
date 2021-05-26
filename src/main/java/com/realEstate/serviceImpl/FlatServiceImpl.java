package com.realEstate.serviceImpl;

import com.realEstate.entity.Flat;
import com.realEstate.entity.Infrastructure;
import com.realEstate.jpa.FlatJPA;
import com.realEstate.service.FlatService;
import com.realEstate.service.ImageService;
import com.realEstate.service.InfrastructureService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class FlatServiceImpl implements FlatService {
    private final FlatJPA flatJPA;
    private final ImageService imageService;
    private final InfrastructureService infService;
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
    public List<Flat> findAll() {
        return flatJPA.findAll();
    }

    @Override
    public void deleteByID(int id) {
        flatJPA.deleteById(id);
    }
}
