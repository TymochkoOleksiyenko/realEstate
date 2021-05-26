package com.realEstate.serviceImpl;

import com.realEstate.entity.Image;
import com.realEstate.entity.Role;
import com.realEstate.entity.Users;
import com.realEstate.jpa.UsersJPA;
import com.realEstate.service.ImageService;
import com.realEstate.service.UsersService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UsersServiceImpl implements UsersService {
    private final UsersJPA usersJPA;
    private final ImageService imageService;
    @Override
    public Users save(Users user) {
        return usersJPA.save(user);
    }

    @Override
    public Users save(Users user, MultipartFile multipartFile) {
        Image image =imageService.save(multipartFile);
        user.setImage(image);
        user = save(user);
        image.setUser(user);
        imageService.save(image);
        return user;
    }

    @Override
    public Users update(Users user) {
        Users userDB = findById(user.getId());
        if(userDB!=null){
            userDB.setFullName(user.getFullName());
            userDB.setMail(user.getMail());
            userDB.setPhone(user.getPhone());
            if(user.getPassword().length()>3){
                userDB.setPassword(user.getPassword());
            }
        }
        return usersJPA.save(userDB);
    }

    @Override
    public Users update(Users user, MultipartFile multipartFile) {
        user = update(user);
        if(multipartFile!=null && multipartFile.getSize()>0) {
            if(user.getImage()!=null) {
                imageService.deleteById(user.getImage().getId());
            }
            Image image = imageService.save(multipartFile);
            user.setImage(image);
            image.setUser(user);
            imageService.save(image);
        }
        return save(user);
    }

    @Override
    public Users register(Users user, MultipartFile multipartFile) {
        if(findByMail(user.getMail()).isPresent()) {
            return null;
        }else {
            return save(user,multipartFile);
        }
    }

    @Override
    public Users findById(int id) {
        return usersJPA.findById(id).orElse(null);
    }

    @Override
    public List<Users> findByRole(Role role) {
        return usersJPA.findByRole(role);
    }

    @Override
    public Optional<Users> findByMail(String mail) {
        return usersJPA.findByMail(mail);
    }

    @Override
    public List<Users> findAll() {
        return usersJPA.findAll();
    }

    @Override
    public void deleteByID(int id) {
        usersJPA.deleteById(id);
    }

}
