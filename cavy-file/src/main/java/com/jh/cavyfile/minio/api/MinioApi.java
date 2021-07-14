package com.jh.cavyfile.minio.api;

import com.jh.cavyfile.minio.FileHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

@RestController
@RequestMapping("/file/minio")
public class MinioApi {
    @Resource
    private FileHandler fileHandler;
    @GetMapping("/upload")
    public void upload(MultipartFile file) {
        fileHandler.uploadFile(file);
    }
}
