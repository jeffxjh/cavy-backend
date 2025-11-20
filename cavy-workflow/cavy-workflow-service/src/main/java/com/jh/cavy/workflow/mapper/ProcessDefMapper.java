package com.jh.cavy.workflow.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jh.cavy.workflow.api.dto.ProcessDefinitionDTO;
import com.jh.cavy.workflow.api.dto.ProcessDefinitionVO;
import com.jh.cavy.workflow.domain.ProcessDef;
import org.apache.ibatis.annotations.Param;

public interface ProcessDefMapper extends BaseMapper<ProcessDef> {
    Page<ProcessDefinitionVO> queryPageDefinition(Page<ProcessDefinitionDTO> processDefinitionDTOPage, @Param(Constants.WRAPPER) LambdaQueryWrapper<ProcessDef> queryWrapper);
}
