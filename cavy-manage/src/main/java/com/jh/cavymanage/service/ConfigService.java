package com.jh.cavymanage.service;

import com.jh.cavymanage.domain.Config;

import java.util.List;

public interface ConfigService {


    int deleteByPrimaryKey(Integer id);

    int insert(Config record);

    int insertSelective(Config record);

    Config selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Config record);

    int updateByPrimaryKey(Config record);

    List<Config> getAll();
}

