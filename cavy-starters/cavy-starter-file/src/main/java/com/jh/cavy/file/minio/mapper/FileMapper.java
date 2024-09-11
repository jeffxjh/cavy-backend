package com.jh.cavy.file.minio.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jh.cavy.file.minio.domain.CavyFile;

public interface FileMapper extends BaseMapper<CavyFile> {
    int insert(CavyFile record);

    int insertSelective(CavyFile record);
}