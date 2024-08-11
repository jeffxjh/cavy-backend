package com.jh.cavy.manage.service;

import cn.hutool.core.lang.tree.Tree;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jh.cavy.common.Result.ResultPage;
import com.jh.cavy.manage.domain.Menu;
import com.jh.cavy.manage.param.MenuAO;
import com.jh.cavy.manage.param.MenuAddParam;
import com.jh.cavy.manage.vo.MenuVO;

import javax.validation.Valid;
import java.util.List;

public interface MenuService {


    int deleteByPrimaryKey(Integer id);

    int insert(Menu record);

    int insertSelective(Menu record);

    Menu selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Menu record);

    int updateByPrimaryKey(Menu record);

    List<Menu> findAll();

    List<Menu> findMenuByCurrentUser();

    IPage<Menu> selectMenuPage(int pageIndex, int pageSize);

    List<Tree<Integer>> findMenusTree();

    void add(MenuAddParam menuAddParam);

    ResultPage<MenuVO> page(MenuAO menuAO);

    List<Tree<Integer>> menusTree(MenuAO menuAO);

    MenuVO getMenu(Long id);

    void update(@Valid MenuAddParam menuAddParam);

    void delMenu(Long id);
}




