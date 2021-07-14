package com.jh.cavymanage.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jh.cavymanage.domain.Config;
import com.jh.cavymanage.mapper.ConfigMapper;
import com.jh.cavymanage.service.ConfigService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ConfigServiceImpl implements ConfigService {

    @Resource
    private ConfigMapper configMapper;

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return configMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(Config record) {
        return configMapper.insert(record);
    }

    @Override
    public int insertSelective(Config record) {
        return configMapper.insertSelective(record);
    }

    @Override
    public Config selectByPrimaryKey(Integer id) {
        return configMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(Config record) {
        return configMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(Config record) {
        return configMapper.updateByPrimaryKey(record);
    }

    @Override
    public List<Config> getAll() {
        return configMapper.selectList(new QueryWrapper<>());
    }

}

