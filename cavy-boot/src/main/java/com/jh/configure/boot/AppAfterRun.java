package com.jh.configure.boot;

import com.jh.cavy.cache.service.CacheService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;


@Slf4j
@Component
@RequiredArgsConstructor
public class AppAfterRun implements CommandLineRunner {
    private final ApplicationContext applicationContext;

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
