package com.jh.cavyfile.minio.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jh.cavyfile.minio.domain.File;

public interface FileMapper extends BaseMapper<File> {
    int insert(File record);

    int insertSelective(File record);
}