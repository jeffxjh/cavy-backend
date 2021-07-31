package com.jh.cavyfile.minio.api;

import com.jh.cavyfile.minio.FileHandler;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/file/minio")
public class MinioApi {
    @Resource
    private FileHandler fileHandler;
    @PostMapping("/upload")
    public void upload(MultipartFile file) {
        fileHandler.uploadFile(file);
    }
    @GetMapping("/{id}")
    public void upload(@PathVariable String id, HttpServletResponse response) {
        fileHandler.downLoad(id,response);
    }
}
