package com.jh.cavybackend;

import com.jh.cavybackend.domain.User;
import com.jh.cavybackend.elasticsearch.Book;
import com.jh.cavybackend.elasticsearch.repositories.BookRepository;
import com.jh.cavybackend.redis.CacheService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

@SpringBootTest
class CavyBackendApplicationTests {
    @Autowired
    private CacheService cacheService;
    @Autowired
    private BookRepository bookRepository;
    @Test
    void contextLoads() {
        cacheService.set("key2", "value3");
        cacheService.lSetAll("list", Arrays.asList("hello", "redis"));
        List<Object> list = cacheService.lGet("list", 0, -1);
        User user = new User();
        user.setRealName("realName");
        user.setEmail("aaaaaaaaa@qq.com");
        cacheService.hset("role","test" ,user, 12);
        User hget = (User)cacheService.hget("role", "test");

        System.out.println(cacheService.get("key2"));

    }
    @Test
    public  void esTest(){

        Book book=new Book();
        bookRepository.save(book);

    }
}
