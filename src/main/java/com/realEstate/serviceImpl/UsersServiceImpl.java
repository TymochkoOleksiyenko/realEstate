package com.realEstate.serviceImpl;

import com.realEstate.entity.Feedback;
import com.realEstate.entity.Image;
import com.realEstate.enums.Role;
import com.realEstate.entity.Users;
import com.realEstate.jpa.UsersJPA;
import com.realEstate.service.ImageService;
import com.realEstate.service.UsersService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UsersServiceImpl implements UsersService {
    private final UsersJPA usersJPA;
    private final ImageService imageService;
    private final EntityManager entityManager;

    @Override
    public Users save(Users user) {
        return usersJPA.save(user);
    }

    @Override
    public Users save(Users user, MultipartFile multipartFile) {
        Image image =imageService.save(multipartFile);
        user.setImage(image);
        user = save(user);
        if(image!=null) {
            image.setUser(user);
            imageService.save(image);
        }
        return user;
    }

    @Override
    public Users update(Users user) {
        Users userDB = findById(user.getId());
        if(userDB!=null){
            userDB.setFullName(user.getFullName());
            userDB.setMail(user.getMail());
            userDB.setPhone(user.getPhone());
            userDB.setExperience(user.getExperience());
            userDB.setAbout(user.getAbout());
            if(user.getPassword().length()>3){
                userDB.setPassword(user.getPassword());
            }
            return usersJPA.save(userDB);
        }else {
            return null;
        }
    }

    @Override
    public Users update(Users user, MultipartFile multipartFile) {
        Users userDB = update(user);
        System.out.println(userDB);
        if(multipartFile!=null && multipartFile.getSize()>0) {
            if(userDB.getImage()!=null) {
                imageService.deleteById(userDB.getImage().getId());
            }
            Image image = imageService.save(multipartFile);
            userDB.setImage(image);
            image.setUser(userDB);
            imageService.save(image);
        }
        return save(userDB);
    }

    @Override
    public Users register(Users user, MultipartFile multipartFile) {
        Users userDB = findByMail(user.getMail()).orElse(null);
        if(userDB!=null) {
            System.out.println(userDB);
            return null;
        }else {
            return save(user,multipartFile);
        }
    }
    @Override
    public Users countAverageRate(int id){
        Users user = findById(id);
        BigDecimal bigDecimal = new BigDecimal(0);
        if(user.getFeedbacks().size()>0) {
            for (Feedback feedback : user.getFeedbacks()) {
                bigDecimal = bigDecimal.add(new BigDecimal(feedback.getMark()));
            }
            bigDecimal = bigDecimal.divide(new BigDecimal(user.getFeedbacks().size()));
        }
        user.setAverageRate(bigDecimal);
        return save(user);
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
