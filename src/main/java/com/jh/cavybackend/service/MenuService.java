package com.jh.cavybackend.service;

import cn.hutool.core.lang.tree.Tree;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jh.cavybackend.domain.Menu;
import com.jh.cavybackend.vo.MenuTree;

import java.util.List;

public interface MenuService {


    int deleteByPrimaryKey(Integer id);

    int insert(Menu record);

    int insertSelective(Menu record);

    Menu selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Menu record);

    int updateByPrimaryKey(Menu record);

    List<Menu> findAll();

    List<Menu> findMenuByUser();

    IPage<Menu> selectMenuPage(int pageIndex, int pageSize);

    List<Tree<Integer>> findMenusTree();

}



