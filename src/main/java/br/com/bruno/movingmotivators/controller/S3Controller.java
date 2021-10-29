package br.com.bruno.movingmotivators.controller;

import br.com.bruno.movingmotivators.service.S3Service;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/s3")
public class S3Controller {

    @Autowired
    S3Service s3Service;

    @GetMapping("/files")
    public List<S3ObjectSummary> getFiles(){
        return s3Service.getFiles();
    }

    @PostMapping("/save-file")
    public PutObjectResult saveFile(@RequestPart(value = "file") MultipartFile file) throws IOException {
        return s3Service.saveFile(file);
    }

    @GetMapping(value = "download/{key}")
    public byte[] downloadCard(@PathVariable String key) {
        return s3Service.downloadCard(key);
    }



}
