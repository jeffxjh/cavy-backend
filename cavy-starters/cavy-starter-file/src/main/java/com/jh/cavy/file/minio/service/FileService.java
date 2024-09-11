package com.jh.cavy.file.minio.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jh.cavy.file.minio.domain.CavyFile;
import com.jh.cavy.file.minio.vo.FileVO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FileService extends IService<CavyFile> {
    List<FileVO> batchUpload();

    void ocr(MultipartFile file) throws Exception;
}
