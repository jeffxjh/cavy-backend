package com.jh.cavy.manage.api;

import cn.hutool.core.util.StrUtil;
import com.jh.cavy.cache.service.CacheService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RequestMapping("/business")
@RestController
public class BusinessApi {
    private static final String KEY = "business";
    @Resource(name="${cache.use}")
    private CacheService cacheService;


    @GetMapping("/")
    public Map<String, String> get(@RequestParam String uuid, HttpServletRequest request) throws InterruptedException {
        Map<String, String> result = new HashMap<>(2);
        if (StrUtil.isEmpty(uuid)) {
            uuid = request.getSession().getId();
        }
        result.put("uuid", uuid);
        result.put("count", "100");
        if (cacheService.hasKey(uuid)) {
            log.info("查询执行进度");
            String finish = cacheService.get(uuid).toString();
            if (finish.equals("100")) {
                //这里给前端一个已完成的信号 然后删除key
                cacheService.del(uuid);
            }
            result.put("count", finish);
        } else {
            log.info("开始执行任务");
            Business business = new Business(uuid);
            new Thread(business).start();
        }
        return result;
    }

     class Business implements Runnable {
        private String uuid;

        public Business() {
        }

        public Business(String uuid) {
            this.uuid = uuid;
        }

        @SneakyThrows
        @Override
        public void run() {
            int count = 0;
            log.info("执行任务中。。");
            //模拟业务操作
            while (count <= 100) {
                log.info("任务进度{}。。", count + "");
                Thread.sleep(200);
                //用过期时间限制重复操作
                cacheService.set(uuid, count, 60);
                count++;
            }
            log.info("任务{}完成。。", uuid);
        }
    }
}
