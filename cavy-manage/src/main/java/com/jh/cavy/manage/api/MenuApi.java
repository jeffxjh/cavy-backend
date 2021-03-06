package com.jh.cavy.manage.api;

import cn.hutool.core.lang.tree.Tree;
import com.jh.cavy.common.Result.ResultPage;
import com.jh.cavy.manage.domain.Menu;
import com.jh.cavy.manage.param.MenuAO;
import com.jh.cavy.manage.param.MenuAddParam;
import com.jh.cavy.manage.service.MenuService;
import com.jh.cavy.manage.vo.MenuVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@RequestMapping("/menu")
@RestController
@Api(value = "菜单", tags = {"菜单"})
public class MenuApi {
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

    @ApiOperation("获取用户菜单树")
    @GetMapping("/tree")
    public List<Tree<Integer>> getMenusTree() {
        return menuService.findMenusTree();
    }

    @PostMapping
    public void add(@Valid @RequestBody MenuAddParam menuAddParam) {
        menuService.add(menuAddParam);
    }

    @ApiOperation("获取菜单分页列表")
    @PostMapping("/page")
    public ResultPage<MenuVO> page(@RequestBody MenuAO menuAO) {
      return menuService.page(menuAO);
    }

}
