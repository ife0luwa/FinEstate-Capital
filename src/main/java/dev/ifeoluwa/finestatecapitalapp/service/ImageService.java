package dev.ifeoluwa.finestatecapitalapp.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.util.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author on 25/04/2023
 * @project
 */

@Service
public class ImageService {


    @Autowired
    private AmazonS3 amazonS3;

    public String storeImage(String key, byte[] bytes) throws IOException {
        try (InputStream inputStream = new ByteArrayInputStream(bytes)) {
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(bytes.length);
            metadata.setContentType("image/jpeg");
            amazonS3.putObject("fin-estate-image", key, inputStream, metadata);
            return key;
        }
    }


    public byte[] getImage(String key) throws IOException {
        S3Object s3Object = amazonS3.getObject("fin-estate-image", key);
        try (InputStream objectData = s3Object.getObjectContent()) {
            return IOUtils.toByteArray(objectData);
        }
    }
}
