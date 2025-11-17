package com.jh.cavy.manage.api;


import com.jh.cavy.common.Result.ResultPage;
import com.jh.cavy.manage.param.RoleAddParam;
import com.jh.cavy.manage.param.RoleParam;
import com.jh.cavy.manage.vo.RoleVO;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "角色相关API")
@RequestMapping("/role")
@RestController
@FeignClient(name = "cavy-manage", contextId = "roleClient", path = "role")
public interface RoleClient {

    @Schema(name = "根据用户名称获取角色列表")
    @GetMapping("/getRoleByUserName")
    List<RoleVO> getRoleByUserName(@RequestParam("userName") String userName);

    @GetMapping("getById")
    RoleVO getRole(@RequestParam("id") String id);


    @Schema(name = "获取全量角色列表")
    @PostMapping
    List<RoleVO> roleList();

    @Schema(name = "获取角色分页列表")
    @GetMapping()
    ResultPage<RoleVO> list(@ModelAttribute RoleParam roleParam);

    @Schema(name = "新增角色")
    @PostMapping("add")
    void add(@Valid @RequestBody RoleAddParam roleAddParam);

    @Schema(name = "修改角色")
    @PostMapping("update")
    void update(@Valid @RequestBody RoleAddParam roleAddParam);

    @DeleteMapping()
    void delete(@RequestParam("ids") List<String> ids);

}
