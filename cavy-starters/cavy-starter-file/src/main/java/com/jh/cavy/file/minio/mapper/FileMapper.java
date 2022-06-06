package com.jh.cavy.file.minio.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jh.cavy.file.minio.domain.File;

public interface FileMapper extends BaseMapper<File> {
    int insert(File record);

    int insertSelective(File record);
}