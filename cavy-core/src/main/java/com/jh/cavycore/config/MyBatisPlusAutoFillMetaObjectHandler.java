package com.jh.cavycore.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.jh.cavycore.common.Resquest.RequestHeadHolder;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

@Slf4j
@Component
public class MyBatisPlusAutoFillMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        this.strictInsertFill(metaObject, "adduser", String.class,  RequestHeadHolder.getAccount());
        this.strictUpdateFill(metaObject, "addtime", Date.class, new Date());

    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.strictInsertFill(metaObject, "updateuser", String.class,  RequestHeadHolder.getAccount());
        this.strictUpdateFill(metaObject, "updatetime", Date.class, new Date());
    }
}