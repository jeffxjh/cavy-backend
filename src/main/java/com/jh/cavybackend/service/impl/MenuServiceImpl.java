package com.jh.cavybackend.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeNode;
import cn.hutool.core.lang.tree.TreeNodeConfig;
import cn.hutool.core.lang.tree.TreeUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jh.cavybackend.domain.Menu;
import com.jh.cavybackend.mapper.MenuMapper;
import com.jh.cavybackend.service.MenuService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MenuServiceImpl implements MenuService {

    @Resource
    private MenuMapper menuMapper;

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return menuMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(Menu record) {
        return menuMapper.insert(record);
    }

    @Override
    public int insertSelective(Menu record) {
        return menuMapper.insertSelective(record);
    }

    @Override
    public Menu selectByPrimaryKey(Integer id) {
        return menuMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(Menu record) {
        return menuMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(Menu record) {
        return menuMapper.updateByPrimaryKey(record);
    }

    @Override
    public List<Menu> findAll() {
        QueryWrapper<Menu> q = new QueryWrapper<>();
        //q.select(Menu.class, a -> a.getColumn().equals("menu_name")||a.getColumn().equals("sort"));
        return menuMapper.selectList(q);
    }

    @Override
    public List<Menu> findMenuByUser() {
        QueryWrapper<Menu> q = new QueryWrapper<>();
        return menuMapper.selectList(q);
    }

    @Override
    public IPage<Menu> selectMenuPage(int pageIndex, int pageSize) {
        return menuMapper.selectMenuPage(new Page<>(pageIndex, pageSize, true), 1);
    }

    @Override
    public List<Tree<Integer>> findMenusTree() {
        List<Menu> menuByUser = findMenuByUser();
        // 构建node列表
        List<TreeNode<Integer>> nodeList = CollUtil.newArrayList();
        for (Menu menu : menuByUser) {
            TreeNode<Integer> integerTreeNode = new TreeNode<>(menu.getId(), menu.getParentId(), menu.getMenuName(), menu.getWeight());
            Map<String, Object> extra = new HashMap<>();
            extra.put("code", menu.getMenuCode());
            extra.put("icon", menu.getIcon());
            integerTreeNode.setExtra(extra);
            nodeList.add(integerTreeNode);
        }
        //配置
        TreeNodeConfig treeNodeConfig = new TreeNodeConfig();
        // 自定义属性名 都要默认值的
        treeNodeConfig.setWeightKey("weight");
        treeNodeConfig.setIdKey("id");
        treeNodeConfig.setNameKey("menuName");
        // 最大递归深度
        treeNodeConfig.setDeep(3);
        List<Tree<Integer>> build = TreeUtil.build(nodeList, 0, treeNodeConfig, (treeNode, tree) -> {
            tree.setId(treeNode.getId());
            tree.setParentId(treeNode.getParentId());
            tree.setWeight(treeNode.getWeight());
            tree.setName(treeNode.getName());
            tree.putExtra("code", treeNode.getExtra().get("code"));
            tree.putExtra("icon", treeNode.getExtra().get("icon"));
        });
        return build;
    }

}



