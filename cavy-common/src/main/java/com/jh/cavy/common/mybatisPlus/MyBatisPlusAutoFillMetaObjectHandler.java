package com.jh.cavy.common.mybatisPlus;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.jh.cavy.common.Resquest.RequestHeadHolder;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

@Slf4j
public class MyBatisPlusAutoFillMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        this.strictInsertFill(metaObject, "addUser", String.class,  RequestHeadHolder.getAccount());
        this.strictUpdateFill(metaObject, "addTime", Date.class, new Date());

    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.strictInsertFill(metaObject, "updateUser", String.class,  RequestHeadHolder.getAccount());
        this.strictUpdateFill(metaObject, "updateTime", Date.class, new Date());
    }
}