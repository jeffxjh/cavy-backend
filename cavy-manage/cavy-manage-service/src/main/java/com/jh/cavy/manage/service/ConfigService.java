package com.jh.cavy.manage.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.jh.cavy.manage.domain.Config;

import java.util.List;

public interface ConfigService  extends IService<Config> {


    int deleteByPrimaryKey(Integer id);

    int insert(Config record);

    int insertSelective(Config record);

    Config selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Config record);

    int updateByPrimaryKey(Config record);

    List<Config> getAll();
}


