package com.jh.cavy.file.minio.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jh.cavy.file.minio.domain.File;
import com.jh.cavy.file.minio.mapper.FileMapper;
import com.jh.cavy.file.minio.service.FileService;
import com.jh.cavy.file.minio.vo.FileVO;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class FileServiceImpl extends ServiceImpl<FileMapper, File> implements FileService {
    @Resource
    private FileMapper fileMapper;

    @Override
    public List<FileVO> batchUpload() {
        ArrayList<File> objects = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            File file = new File();
            file.setWebUrl("" + i);
            file.setFileCode(i + "");
            file.setFileName(i + "");
            file.setFilePath(i + "");
            objects.add(file);
        }
        this.saveBatch(objects);
        File byId = this.getById(16);
        byId.setFilePath("1");
        this.saveOrUpdate(byId);
        return BeanUtil.copyToList(objects, FileVO.class);
    }
}
