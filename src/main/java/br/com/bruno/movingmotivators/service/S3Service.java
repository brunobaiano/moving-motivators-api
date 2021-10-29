package br.com.bruno.movingmotivators.service;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.*;
import com.amazonaws.util.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class S3Service {
    @Autowired
    private AmazonS3Client amazonS3Client;

    @Value("${application.bucket.name}")
    private String bucketName;

    public List<S3ObjectSummary> getFiles() {
        return amazonS3Client.listObjects(bucketName).getObjectSummaries();
    }

    public PutObjectResult saveFile(MultipartFile file) throws IOException {
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(file.getContentType());
        objectMetadata.setContentLength(file.getSize());
        return amazonS3Client.putObject(bucketName,file.getOriginalFilename(),file.getInputStream(),objectMetadata);
    }

    public byte[] downloadCard(String key) {
        try {
            S3Object object = amazonS3Client.getObject(bucketName, key);
            S3ObjectInputStream objectContent = object.getObjectContent();
            return IOUtils.toByteArray(objectContent);
        } catch (AmazonServiceException | IOException e) {
            throw new IllegalStateException("Failed to download the file", e);
        }
    }
}
