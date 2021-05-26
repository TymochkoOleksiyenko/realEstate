package com.realEstate.controller;

import com.realEstate.entity.Image;
import com.realEstate.service.ImageService;
import lombok.AllArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@AllArgsConstructor
public class ImageController {
    private final ImageService imageService;

    @GetMapping("/getImg-{imgId}")
    public ResponseEntity<ByteArrayResource> getImg(@PathVariable int imgId){
        Image doc = imageService.findById(imgId);
        if(doc==null || doc.getImgName()==null) return null;
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(doc.getImgType()))
                .header(HttpHeaders.CONTENT_DISPOSITION,"attachment:filename=\""+doc.getImgName()+"\"")
                .body(new ByteArrayResource(doc.getImg()));
    }

    @ResponseBody
    @PostMapping("/deleteImg-{imgId}")
    public void deleteImg(@PathVariable int imgId){
        imageService.deleteById(imgId);
    }
}
