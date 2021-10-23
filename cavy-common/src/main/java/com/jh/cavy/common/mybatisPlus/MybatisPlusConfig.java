package com.jh.cavy.common.mybatisPlus;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.jh.cavy.common.Resquest.RequestHeadHolder;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Date;
import java.util.Objects;

@Configuration
public class MybatisPlusConfig {
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        return interceptor;
    }
    /**
     * 解决了insert/update数据时默认值问题
     * @return
     */
    @Bean
    public MetaObjectHandler metaObjectHandler() {
        return new MetaObjectHandler() {
            @Override
            public void insertFill(MetaObject metaObject) {
                if(Objects.isNull(this.getFieldValByName("addTime", metaObject))) {
                    this.setFieldValByName("addTime", new Date(System.currentTimeMillis()), metaObject);
                }
                if(this.getFieldValByName("addUser", metaObject)==null||StrUtil.isBlank(StrUtil.toString(this.getFieldValByName("addUser", metaObject)))) {
                    this.setFieldValByName("addUser", RequestHeadHolder.getAccount(), metaObject);
                }
            }

            @Override
            public void updateFill(MetaObject metaObject) {
                if(Objects.isNull(this.getFieldValByName("updateTime", metaObject))) {
                    this.setFieldValByName("updateTime", new Date(System.currentTimeMillis()), metaObject);
                }
                if(this.getFieldValByName("updateUser", metaObject)==null||StrUtil.isBlank(StrUtil.toString(this.getFieldValByName("updateUser", metaObject)))) {
                    this.setFieldValByName("updateUser", RequestHeadHolder.getAccount(), metaObject);
                }
            }
        };
    }
}
