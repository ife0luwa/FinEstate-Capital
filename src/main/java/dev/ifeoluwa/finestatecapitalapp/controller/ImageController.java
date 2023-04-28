package dev.ifeoluwa.finestatecapitalapp.controller;

import dev.ifeoluwa.finestatecapitalapp.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author on 25/04/2023
 * @project
 */

@RestController
public class ImageController {

    @Autowired
    private ImageService imageService;


    @GetMapping("image/{imageName}")
    public ResponseEntity<byte[]> getImage(@PathVariable String imageName) throws IOException {
        byte[] imageData = imageService.getImage(imageName);
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(imageData);
    }
}
