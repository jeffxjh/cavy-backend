package com.jh.cavy.manage.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jh.cavy.common.Result.ResultPage;
import com.jh.cavy.common.exception.APIException;
import com.jh.cavy.manage.domain.Dict;
import com.jh.cavy.manage.domain.DictItem;
import com.jh.cavy.manage.dto.DictItemDTO;
import com.jh.cavy.manage.mapper.DictItemMapper;
import com.jh.cavy.manage.mapper.DictMapper;
import com.jh.cavy.manage.param.DictAO;
import com.jh.cavy.manage.param.DictItemAO;
import com.jh.cavy.manage.service.DictService;
import com.jh.cavy.manage.vo.DictStoreVO;
import com.jh.cavy.manage.vo.DictVO;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author jeffx
 * @description 针对表【t_sys_dict(数据字典表)】的数据库操作Service实现
 * @createDate 2024-08-11 17:50:14
 */
@Service
public class DictServiceImpl extends ServiceImpl<DictMapper, Dict>
        implements DictService {
    @Resource
    private DictMapper dictMapper;
    @Resource
    private DictItemMapper dictItemMapper;

    @Override
    public ResultPage<DictVO> getDict(String id) {
        return null;
    }

    @Override
    public Page<DictVO> queryDictPage(DictAO dictAO) {
        LambdaQueryWrapper<Dict> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.or(StringUtils.isNotBlank(dictAO.getName()), wrapper -> wrapper.eq(Dict::getCode, dictAO.getName()).or().eq(Dict::getName, dictAO.getName()));
        Page<Dict> page = new Page<>(dictAO.getPageIndex(), dictAO.getPageSize());
        return dictMapper.queryDictPage(page, queryWrapper);
    }

    @Override
    public void addDict(DictAO dictAO) {
        Dict dict = BeanUtil.copyProperties(dictAO, Dict.class);
        dictMapper.insert(dict);
    }

    @Override
    public void delDict(String id) {
        dictMapper.deleteById(id);
        dictItemMapper.delete(new LambdaQueryWrapper<DictItem>().eq(DictItem::getDicId, id));
    }

    @Override
    public void updateDict(DictAO dictAO) {
        LambdaUpdateWrapper<Dict> wrapper = Wrappers.lambdaUpdate(Dict.class).eq(Dict::getId, dictAO.getId());
        wrapper.set(Dict::getName, dictAO.getName());
        wrapper.set(Dict::getCode, dictAO.getCode());
        baseMapper.update(null, wrapper);
    }

    @Override
    public void addDictItem(DictItemAO dictItem) {
        List<DictItemDTO> dictItems = dictItem.getItems();
        for (DictItemDTO item : dictItems) {
            item.setDicId(dictItem.getDicId());
            Long i = dictItemMapper.selectCount(Wrappers.<DictItem>lambdaQuery()
                                                           .eq(DictItem::getItem, item.getItem())
                                                           .eq(DictItem::getLabel, item.getLabel()));
            if (i > 0) {
                throw new APIException("字典项重复");
            }
            dictItemMapper.insert(BeanUtil.copyProperties(item, DictItem.class));
        }
    }

    @Override
    public void delDictItem(String id) {
        dictItemMapper.deleteById(id);
    }

    @Override
    public List<DictStoreVO> store(DictAO dictAO) {
        LambdaQueryWrapper<Dict> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.in(CollectionUtil.isNotEmpty(dictAO.getCodeList()), Dict::getCode, dictAO.getCodeList());
        queryWrapper.or(StringUtils.isNotBlank(dictAO.getName()), wrapper -> wrapper.eq(Dict::getCode, dictAO.getName()).or().eq(Dict::getName, dictAO.getName()));
        return dictMapper.store(queryWrapper);
    }
}




