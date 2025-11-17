package com.jh.cavy.manage.client;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.tree.Tree;
import com.jh.cavy.common.Result.ResultPage;
import com.jh.cavy.manage.api.MenuClient;
import com.jh.cavy.manage.domain.Menu;
import com.jh.cavy.manage.dto.MenuDTO;
import com.jh.cavy.manage.param.MenuAO;
import com.jh.cavy.manage.param.MenuAddParam;
import com.jh.cavy.manage.service.MenuService;
import com.jh.cavy.manage.vo.MenuVO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Service
public record MenuClientImpl(MenuService menuService) implements MenuClient {
    private static Map<Integer, Menu> productMap;

    static {
        productMap = Stream.of(
                Menu.builder().id(1).menuName("product1").build(),
                Menu.builder().id(2).menuName("product2").build(),
                Menu.builder().id(3).menuName("product3").build()
        ).collect(Collectors.toConcurrentMap(Menu::getId, p -> p));
    }
    @Override
    public Boolean getBoolean(String booleanValue)  {
        return "true".equals(booleanValue);
    }

    @Override
    public List<MenuDTO> getMenus() {
        return BeanUtil.copyToList(menuService.findAll(), MenuDTO.class);
    }

    @Override
    public List<Tree<Integer>> listMenu(MenuAO menuAO){
        return menuService.menusTree(menuAO);
    }

    @Override
    public MenuVO getMenu(Long id) {
        return menuService.getMenu(id);
    }

    @Override
    public void delMenu(Long id)  {
        menuService.delMenu(id);
    }

    @Override
    public List<Tree<Integer>> getMenusTree() {
        return menuService.findMenusTree();
    }

    @Override
    public void add(MenuAddParam menuAddParam)  {
        menuService.add(menuAddParam);
    }

    @Override
    public void update(MenuAddParam menuAddParam) {
        menuService.update(menuAddParam);
    }

    @Override
    public ResultPage<MenuVO> page(MenuAO menuAO) {
        return menuService.page(menuAO);
    }
}
