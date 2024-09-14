package com.jh.cavy.manage.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jh.cavy.manage.domain.Resource;
import com.jh.cavy.manage.service.ResourceService;
import com.jh.cavy.manage.mapper.ResourceMapper;
import org.springframework.stereotype.Service;

/**
* @author jeffx
* @description 针对表【t_sys_resource(资源表)】的数据库操作Service实现
* @createDate 2024-09-15 00:20:06
*/
@Service
public class ResourceServiceImpl extends ServiceImpl<ResourceMapper, Resource>
    implements ResourceService{

}




