package com.jh.cavy.gateway.limit;

import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;


@Slf4j
@Component
public class AppRunner implements ApplicationRunner {
    @Override
    public void run(ApplicationArguments args) throws Exception {
        //lock();
        //redis();
        //ratelimit();
        //semaphore();
    }

    public static void ratelimit() throws InterruptedException {
        ///设置线程池最大执行20个线程并发执行任务
        int threadSize = 20;
        //AtomicInteger通过CAS操作能保证统计数量的原子性
        AtomicInteger successCount = new AtomicInteger(0);
        CountDownLatch downLatch = new CountDownLatch(threadSize);
        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(threadSize);
        for (int i = 0; i < threadSize; i++) {
            fixedThreadPool.submit(() -> {
                String str = sendRequest("http://localhost:8011/cavy/ratelimit");
                if ("success".equals(JSONUtil.parseObj(str).getStr("data"))) {
                    successCount.incrementAndGet();
                }
                System.out.println(str);
                downLatch.countDown();
            });
        }
        //等待所有线程都执行完任务
        downLatch.await();
        fixedThreadPool.shutdown();
        System.out.println("总共有" + successCount.get() + "个线程获得到了令牌!");
    }
    public static void lock() throws InterruptedException {
        ///设置线程池最大执行20个线程并发执行任务
        int threadSize = 1000;
        //AtomicInteger通过CAS操作能保证统计数量的原子性
        AtomicInteger successCount = new AtomicInteger(0);
        CountDownLatch downLatch = new CountDownLatch(threadSize);
        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(threadSize);
        for (int i = 0; i < threadSize; i++) {
            fixedThreadPool.submit(() -> {
                String str = sendRequest("http://localhost:8011/cavy/lock");
                if ("success".equals(JSONUtil.parseObj(str).getStr("data"))) {
                    successCount.incrementAndGet();
                }
                System.out.println(str);
                downLatch.countDown();
            });
        }
        //等待所有线程都执行完任务
        downLatch.await();
        fixedThreadPool.shutdown();
        System.out.println("总共有" + successCount.get() + "个线程获得到了令牌!");
    }

    public static void semaphore() throws Exception {
        //设置线程池最大执行20个线程并发执行任务
        int threadSize = 20;
        AtomicInteger successCount = new AtomicInteger(0);
        CountDownLatch downLatch = new CountDownLatch(threadSize);
        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(threadSize);
        for (int i = 0; i < threadSize; i++) {
            fixedThreadPool.submit(() -> {
                String str = sendRequest("http://localhost:8011/cavy/SemaphoreLimit");
                if ("success".equals(JSONUtil.parseObj(str).getStr("data"))) {
                    successCount.incrementAndGet();
                }
                System.out.println(str);
                downLatch.countDown();
            });
        }
        downLatch.await();
        fixedThreadPool.shutdown();
        System.out.println("总共有" + successCount.get() + "个线程获得到了许可证!");
    }

    public static void redis() throws Exception {
        //设置线程池最大执行20个线程并发执行任务
        int threadSize = 50;
        AtomicInteger successCount = new AtomicInteger(0);
        CountDownLatch downLatch = new CountDownLatch(threadSize);
        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(threadSize);
        for (int i = 0; i < threadSize; i++) {
            fixedThreadPool.submit(() -> {
                String str = sendRequest("http://localhost:8011/cavy/redisLimit");
                if ("success".equals(JSONUtil.parseObj(str).getStr("data"))) {
                    successCount.incrementAndGet();
                }
                System.out.println(str);
                downLatch.countDown();
            });
        }
        downLatch.await();
        fixedThreadPool.shutdown();
        System.out.println("总共有" + successCount.get() + "个线程获得到了许可证!");
    }

    public static String sendRequest(String path) {
        String token = "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxMjMiLCJpYXQiOjE2Mjg0MDgxNTQsImFjY291bnQiOiIxMjMiLCJ1c2VybmFtZSI6IjEyMyIsInNjb3BlIjpbXSwiZXhwIjoxNjI4NDIyNTU0fQ.jVBefUHL-wN5M9nUFv0_yE3Y_nHISqmcba6PrOdWGJQIfe0kiYv5As21F7gwR5fog1m6ajIgTChyJwpJ1h5oUA";
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("Authorization", token);
        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
        requestBody.add("roundid", "1");

        HttpEntity<MultiValueMap> requestEntity = new HttpEntity<>(requestBody, requestHeaders);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(path, requestEntity, String.class);
        return responseEntity.getBody();
    }
}
