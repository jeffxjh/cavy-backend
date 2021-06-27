package com.jh.cavybackend.api;

import cn.hutool.core.lang.tree.Tree;
import com.jh.cavybackend.domain.Menu;
import com.jh.cavybackend.rabbitmq.MqSend;
import com.jh.cavybackend.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@RequestMapping("/menu")
@RestController
public class MenuApi {
    @Autowired
    private MqSend mqSend;
    @Autowired
    private MenuService menuService;

    private static Map<Integer, Menu> productMap;

    static {
        productMap = Stream.of(
                Menu.builder().id(1).menuName("product1").build(),
                Menu.builder().id(2).menuName("product2").build(),
                Menu.builder().id(3).menuName("product3").build()
        ).collect(Collectors.toConcurrentMap(Menu::getId, p -> p));
    }

    @GetMapping("/boolean")
    public Boolean getBoolean(String booleanValue) {
        return "true".equals(booleanValue);
    }

    @GetMapping("/{id}")
    public Menu getProduct(@PathVariable Long id) {
        return productMap.get(id);
    }

    @GetMapping("/list")
    public List<Menu> getMenus() {
        return menuService.findAll();
    }
    @GetMapping("/tree")
    public List<Tree<Integer>> getMenusTree() {
        return menuService.findMenusTree();
    }


    @GetMapping("/rabbit")
    public String rabbit(@RequestParam String exchange,@RequestParam String key,@RequestParam String msg) {
        mqSend.sendMsg(exchange,key,msg);
        return "";
    }
}
