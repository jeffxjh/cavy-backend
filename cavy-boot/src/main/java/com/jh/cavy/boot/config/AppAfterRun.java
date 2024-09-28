package com.jh.cavy.boot.config;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.jh.cavy.cache.service.CacheService;
import com.jh.cavy.manage.domain.Dict;
import com.jh.cavy.manage.domain.DictItem;
import com.jh.cavy.manage.service.DictItemService;
import com.jh.cavy.manage.service.DictService;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Slf4j
@Component
@RequiredArgsConstructor
public class AppAfterRun implements CommandLineRunner {
    private final ApplicationContext applicationContext;
    private final DictService dictService;
    private final DictItemService dictItemService;
    private final CacheService cacheService;

    @Override
    public void run(String... args) throws Exception {
        String[] names = applicationContext.getBeanDefinitionNames();
        //int index = 1;
        //for (String name : names) {
        //log.trace("{}: {}", index++ , name );
        //}
        log.debug("项目启动 容器注入javaBean:{}个.", names.length);
        //加载字典
        //List<Dict> dictList = dictService.list();
        //dictList.stream().parallel().forEach(dict -> {
        //    List<DictItem> list = dictItemService.list(Wrappers.<DictItem>lambdaQuery().eq(DictItem::getDicId, dict.getId()));
        //    list.stream().parallel().map(  a->new HashMap<>(){{
        //        put(a.getItem(), a.getLabel());
        //    }})
        //    cacheService.sHasKey()
        //})
        //将字典数据缓存


    }
}
