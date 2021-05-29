package com.realEstate.serviceImpl;

import com.realEstate.entity.Flat;
import com.realEstate.entity.Image;
import com.realEstate.entity.Users;
import com.realEstate.jpa.ImageJPA;
import com.realEstate.service.ImageService;
import com.realEstate.service.UsersService;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
public class ImageServiceImpl implements ImageService {
    private ImageJPA imageJPA;
    private final UsersService usersService;

    public ImageServiceImpl(ImageJPA imageJPA,@Lazy UsersService usersService) {
        this.imageJPA = imageJPA;
        this.usersService = usersService;
    }


    @Override
    public Image save(MultipartFile multipartFile) {
        Image image = null;
        if(multipartFile!=null && multipartFile.getSize()>0){
            try {
                image=new Image();
                image.setImg(multipartFile.getBytes());
                image.setImgName(multipartFile.getOriginalFilename());
                image.setImgType(multipartFile.getContentType());
            }
            catch(Exception e) {
                e.printStackTrace();
            }
        }
        if(image!=null) {
            return imageJPA.save(image);
        }else {
            return null;
        }
    }

    @Override
    public List<Image> save(List<MultipartFile> list, Flat flat) {
        List<Image> images =new ArrayList<>();
        for(MultipartFile file:list){
            Image image = save(file);
            if(image!=null){
                image.setFlat(flat);
                images.add(save(image));
            }
        }
        return images;
    }

    @Override
    public Image save(Image image) {
        return imageJPA.save(image);
    }

    @Override
    public Image findById(int id) {
        return imageJPA.findById(id).orElse(null);
    }

    @Override
    public void deleteById(int id) {
        if(id>0){
            Image image = findById(id);
            if(image!=null) {
                if(image.getUser()!=null){
                    Users user = image.getUser();
                    user.setImage(null);
                    usersService.save(user);
                }
                imageJPA.deleteById(id);
            }
        }
    }
}
