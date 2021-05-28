package com.realEstate.entity;

import lombok.Data;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Arrays;
import java.util.Objects;

@Entity
@Data
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Lob
    @Type(type = "org.hibernate.type.ImageType")
    private byte[] img;
    private String imgType;
    private String imgName;

    @ManyToOne
    private Flat flat;

    @OneToOne
    private Users user;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Image image = (Image) o;
        return id == image.id &&
                Arrays.equals(img, image.img) &&
                Objects.equals(imgType, image.imgType) &&
                Objects.equals(imgName, image.imgName);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(id, imgType, imgName);
        result = 31 * result + Arrays.hashCode(img);
        return result;
    }
}
