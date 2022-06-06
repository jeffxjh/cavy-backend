package com.jh.cavy.file.minio.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jh.cavy.file.minio.domain.File;
import com.jh.cavy.file.minio.vo.FileVO;

import java.util.List;

public interface FileService extends IService<File> {
    List<FileVO> batchUpload();

}
