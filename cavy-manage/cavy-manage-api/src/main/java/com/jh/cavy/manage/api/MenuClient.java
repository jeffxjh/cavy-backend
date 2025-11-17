package com.jh.cavy.manage.api;

import cn.hutool.core.lang.tree.Tree;
import com.jh.cavy.common.Result.ResultPage;
import com.jh.cavy.manage.dto.MenuDTO;
import com.jh.cavy.manage.param.MenuAO;
import com.jh.cavy.manage.param.MenuAddParam;
import com.jh.cavy.manage.vo.MenuVO;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Tag(name = "菜单相关API")
@RequestMapping("/menu")
@RestController
@FeignClient(name = "cavy-manage", contextId = "menuClient", path = "menu")
public interface MenuClient {

    @GetMapping("/boolean")
    Boolean getBoolean(@RequestParam("booleanValue") String booleanValue);

    @Schema(name = "获取全量菜单列表")
    @GetMapping("/list")
    List<MenuDTO> getMenus();

    @Schema(name = "菜单管理-获取全量菜单树")
    @PostMapping("/listTree")
    List<Tree<Integer>> listMenu(@RequestBody MenuAO menuAO);

    @Schema(name = "根据菜单ID获取菜单对象")
    @GetMapping("/{id}")
    MenuVO getMenu(@PathVariable("id") Long id);

    @Schema(name = "根据菜单ID删除菜单")
    @DeleteMapping("/{id}")
    void delMenu(@PathVariable("id") Long id);

    @Schema(name = "获取当前用户菜单树")
    @GetMapping("/tree")
    List<Tree<Integer>> getMenusTree();

    @Schema(name = "新增菜单")
    @PostMapping("add")
    void add(@Valid @RequestBody MenuAddParam menuAddParam);

    @Schema(name = "修改菜单")
    @PostMapping("update")
    void update(@Valid @RequestBody MenuAddParam menuAddParam);

    @Schema(name = "获取菜单分页列表")
    @PostMapping("/page")
    ResultPage<MenuVO> page(@RequestBody MenuAO menuAO);

}
