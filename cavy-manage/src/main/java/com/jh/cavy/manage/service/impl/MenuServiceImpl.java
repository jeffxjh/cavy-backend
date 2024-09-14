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
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jh.cavy.common.Resquest.RequestHeadHolder;
import com.jh.cavy.common.Result.ResultPage;
import com.jh.cavy.common.mybatisPlus.PageUtil;
import com.jh.cavy.manage.domain.Answer;
import com.jh.cavy.manage.domain.Menu;
import com.jh.cavy.manage.domain.UserMenu;
import com.jh.cavy.manage.mapper.AnswerMapper;
import com.jh.cavy.manage.mapper.MenuMapper;
import com.jh.cavy.manage.mapper.UserMenuMapper;
import com.jh.cavy.manage.param.MenuAO;
import com.jh.cavy.manage.param.MenuAddParam;
import com.jh.cavy.manage.service.MenuService;
import com.jh.cavy.manage.vo.MenuVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {

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
        Menu menu = BeanUtil.copyProperties(menuAddParam, Menu.class);
        menuMapper.insert(menu);
        menu.setMenuId(menu.getId());
        menuMapper.updateByPrimaryKeySelective(menu);
    }

    @Override
    public ResultPage<MenuVO> page(MenuAO menuAO) {
        LambdaQueryWrapper<Menu> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.like(Menu::getMenuName, menuAO.getMenuName());
        queryWrapper.like(Menu::getMenuCode, menuAO.getMenuCode());
        Page<MenuVO> userVOPage = menuMapper.page(PageUtil.newPage(menuAO), queryWrapper);
        return new ResultPage<>(userVOPage);
    }
    public List<Menu> queryParentIds(Menu menu, List<Menu> taxCompanyList) {
        //递归获取父级ids,不包含自己
        List<Menu> parentIds = new ArrayList<>();
        this.getParentIds(taxCompanyList, menu, parentIds);
        return parentIds;
    }
    /**
     * 递归获取父级ids
     * @param menuList
     * @param curMenu
     * @param parentIds
     */
    private void getParentIds(List<Menu> menuList, Menu curMenu, List<Menu> parentIds) {
        for (Menu menu : menuList) {
            if (curMenu.getParentId()==null||curMenu.getParentId()==0) {
                continue;
            }
            //判断是否有父节点
            if (menu.getMenuId().equals(curMenu.getParentId())) {
                parentIds.add(menu);
                getParentIds(menuList, menu, parentIds);
            }
        }
    }
    @Override
    public List<Tree<Integer>> menusTree(MenuAO menuAO) {
        Boolean disabled = menuAO.getDisabled();
        LambdaQueryWrapper<Menu> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(menuAO.getStatus()!=null,Menu::getStatus, menuAO.getStatus());
        queryWrapper.or(StringUtils.isNotBlank(menuAO.getMenuName()), wrapper -> wrapper.eq(Menu::getMenuName, menuAO.getMenuName()).or().eq(Menu::getMenuCode, menuAO.getMenuName()));
        List<Menu> menuList = menuMapper.selectList(queryWrapper);
        List<Menu> resultList = new ArrayList<>();
        List<Menu> allMenuList = menuMapper.selectList(Wrappers.<Menu>lambdaQuery());
        for (Menu menu : menuList) {
            resultList.addAll(queryParentIds(menu, allMenuList));
        }
        menuList.addAll(resultList);
        List<MenuVO> menuVOList = BeanUtil.copyToList(new HashSet<>(menuList), MenuVO.class);
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
            extra.put("menuType", menu.getMenuType());
            extra.put("createTime", menu.getAddTime());
            extra.put("hidden", menu.getHidden());
            extra.put("isDefault", menu.getIsDefault());
            extra.put("icon", menu.getIcon());
            extra.put("url", menu.getUrl());
            extra.put("label", menu.getMenuName());
            extra.put("disabled", disabled);
            extra.put("children", menu.getChildren());
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
            tree.putExtra("menuType", treeNode.getExtra().get("menuType"));
            tree.putExtra("createTime", treeNode.getExtra().get("createTime"));
            tree.putExtra("hidden", treeNode.getExtra().get("hidden"));
            tree.putExtra("isDefault", treeNode.getExtra().get("isDefault"));
            tree.putExtra("sort", treeNode.getExtra().get("sort"));
            tree.putExtra("icon", treeNode.getExtra().get("icon"));
            tree.putExtra("url", treeNode.getExtra().get("url"));
            tree.putExtra("label", treeNode.getExtra().get("label"));
            tree.putExtra("disabled", treeNode.getExtra().get("disabled"));
            tree.putExtra("children", treeNode.getExtra().get("children"));
            tree.putExtra("id", treeNode.getExtra().get("id"));
        });
        return build;
    }

    @Override
    public MenuVO getMenu(Long id) {
        Menu menu = menuMapper.selectById(id);
        MenuVO bean = BeanUtil.toBean(menu, MenuVO.class);
        Menu parentMenu = menuMapper.selectById(menu.getParentId());
        if (parentMenu != null) {
            bean.setParentName(parentMenu.getMenuName());
            String parentUrl = parentMenu.getUrl();
            String url = menu.getUrl();
            bean.setParentUrl(parentUrl);
            bean.setCurUrl(url.replaceFirst(parentUrl, "").substring(1));
        }else {
            bean.setParentName("根节点");
        }
        return bean;
    }

    @Override
    public void update(MenuAddParam menuAddParam) {
        Menu menu = BeanUtil.copyProperties(menuAddParam, Menu.class);
        menuMapper.update(menu,Wrappers.<Menu>lambdaUpdate().eq(Menu::getMenuId, menuAddParam.getMenuId()));
    }

    @Override
    public void delMenu(Long id) {
        Menu menu = menuMapper.selectById(id);
        menuMapper.deleteById(id);
        //todo 删除子节点
    }

}




