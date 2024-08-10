package com.jh.cavy.manage.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeNode;
import cn.hutool.core.lang.tree.TreeNodeConfig;
import cn.hutool.core.lang.tree.TreeUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jh.cavy.common.Resquest.RequestHeadHolder;
import com.jh.cavy.common.Result.ResultPage;
import com.jh.cavy.common.mybatisPlus.PageUtil;
import com.jh.cavy.manage.domain.Menu;
import com.jh.cavy.manage.domain.UserMenu;
import com.jh.cavy.manage.mapper.MenuMapper;
import com.jh.cavy.manage.mapper.UserMenuMapper;
import com.jh.cavy.manage.param.MenuAO;
import com.jh.cavy.manage.param.MenuAddParam;
import com.jh.cavy.manage.service.MenuService;
import com.jh.cavy.manage.vo.MenuVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class MenuServiceImpl implements MenuService {

    @Resource
    private MenuMapper menuMapper;
    @Resource
    private UserMenuMapper userMenuMapper;

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
    public List<Menu> findMenuByCurrentUser() {
        LambdaQueryWrapper<UserMenu> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(UserMenu::getUserId, RequestHeadHolder.getUserIdString());
        List<UserMenu> userMenus = userMenuMapper.selectList(queryWrapper);
        return menuMapper.selectList(Wrappers.<Menu>lambdaQuery()
                                             .in(Menu::getId, userMenus.stream().map(UserMenu::getMenuId).collect(Collectors.toSet())));
    }

    @Override
    public IPage<Menu> selectMenuPage(int pageIndex, int pageSize) {
        return menuMapper.selectMenuPage(new Page<>(pageIndex, pageSize, true), 1);
    }

    @Override
    public List<Tree<Integer>> findMenusTree() {
        List<Menu> menuByUser = findMenuByCurrentUser();
        // 构建node列表
        List<TreeNode<Integer>> nodeList = CollUtil.newArrayList();
        for (Menu menu : menuByUser) {
            TreeNode<Integer> integerTreeNode = new TreeNode<>(menu.getId(), menu.getParentId(), menu.getMenuName(), menu.getWeight());
            Map<String, Object> extra = new HashMap<>();
            extra.put("code", menu.getMenuCode());
            extra.put("icon", menu.getIcon());
            extra.put("url", menu.getUrl());
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
            tree.putExtra("url", treeNode.getExtra().get("url"));
        });
        return build;
    }

    @Override
    public void add(MenuAddParam menuAddParam) {

    }

    @Override
    public ResultPage<MenuVO> page(MenuAO menuAO) {
        LambdaQueryWrapper<Menu> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.like(Menu::getMenuName, menuAO.getMenuName());
        queryWrapper.like(Menu::getMenuCode, menuAO.getMenuCode());
        Page<MenuVO> userVOPage = menuMapper.page(PageUtil.newPage(menuAO), queryWrapper);
        return new ResultPage<>(userVOPage);
    }

    @Override
    public List<Tree<Integer>> menusTree(MenuAO menuAO) {
        LambdaQueryWrapper<Menu> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.like(menuAO.getMenuName()!=null,Menu::getMenuName, menuAO.getMenuName());
        queryWrapper.like(menuAO.getMenuCode()!=null,Menu::getMenuCode, menuAO.getMenuCode());
        List<Menu> menuList = menuMapper.selectList(queryWrapper);
        List<MenuVO> menuVOList = BeanUtil.copyToList(menuList, MenuVO.class);
        // 构建node列表
        List<TreeNode<Integer>> nodeList = CollUtil.newArrayList();
        for (MenuVO menu : menuVOList) {
            //List<Menu> childrenMenuList = menuMapper.selectList(Wrappers.<Menu>lambdaQuery().eq(Menu::getMenuId, menu.getParentId()));
            TreeNode<Integer> integerTreeNode = new TreeNode<>(menu.getMenuId(), menu.getParentId(), menu.getMenuName(), menu.getWeight());
            Map<String, Object> extra = new HashMap<>();
            extra.put("id", menu.getId());
            extra.put("code", menu.getMenuCode());
            extra.put("menuId", menu.getMenuId());
            extra.put("sort", menu.getSort());
            extra.put("createTime", menu.getAddTime());
            extra.put("hidden", menu.getHidden());
            extra.put("isDefault", menu.getIsDefault());
            extra.put("icon", menu.getIcon());
            extra.put("url", menu.getUrl());
            integerTreeNode.setExtra(extra);
            nodeList.add(integerTreeNode);
        }
        //配置
        TreeNodeConfig treeNodeConfig = new TreeNodeConfig();
        // 自定义属性名 都要默认值的
        treeNodeConfig.setWeightKey("weight");
        treeNodeConfig.setIdKey("menuId");
        treeNodeConfig.setNameKey("menuName");
        // 最大递归深度
        treeNodeConfig.setDeep(3);
        List<Tree<Integer>> build = TreeUtil.build(nodeList, 0, treeNodeConfig, (treeNode, tree) -> {
            tree.setId(treeNode.getId());
            tree.setParentId(treeNode.getParentId());
            tree.setWeight(treeNode.getWeight());
            tree.setName(treeNode.getName());
            tree.putExtra("code", treeNode.getExtra().get("code"));
            tree.putExtra("menuId", treeNode.getExtra().get("menuId"));
            tree.putExtra("createTime", treeNode.getExtra().get("createTime"));
            tree.putExtra("hidden", treeNode.getExtra().get("hidden"));
            tree.putExtra("isDefault", treeNode.getExtra().get("isDefault"));
            tree.putExtra("sort", treeNode.getExtra().get("sort"));
            tree.putExtra("icon", treeNode.getExtra().get("icon"));
            tree.putExtra("url", treeNode.getExtra().get("url"));
            tree.putExtra("id", treeNode.getExtra().get("id"));
        });
        return build;
    }

    @Override
    public MenuVO getMenu(Long id) {
        Menu menu = menuMapper.selectById(id);
        return BeanUtil.toBean(menu, MenuVO.class);
    }

}




