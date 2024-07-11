package com.jh.cavy.manage.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.enums.CellExtraTypeEnum;
import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jh.cavy.common.Result.ResultPage;
import com.jh.cavy.common.mybatisPlus.PageUtil;
import com.jh.cavy.manage.domain.User;
import com.jh.cavy.manage.dto.UserDTO;
import com.jh.cavy.manage.excel.UserExcelListen;
import com.jh.cavy.manage.mapper.UserMapper;
import com.jh.cavy.manage.param.UserParam;
import com.jh.cavy.manage.service.RoleService;
import com.jh.cavy.manage.service.UserService;
import com.jh.cavy.manage.vo.UserInfoVO;
import com.jh.cavy.manage.vo.UserVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Resource
    private RoleService roleService;
    @Resource
    private UserMapper userMapper;

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return userMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(User record) {
        return userMapper.insert(record);
    }

    @Override
    public int insertSelective(User record) {
        return userMapper.insertSelective(record);
    }

    @Override
    public User selectByPrimaryKey(Integer id) {
        return userMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(User record) {
        return userMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(User record) {
        return userMapper.updateByPrimaryKey(record);
    }

    @Override
    public User getByUserName(String userName) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("user_name", userName);
        return userMapper.selectOne(wrapper);
    }

    @Override
    public ResultPage<UserVO> findUserPage(UserParam userParam) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        //Page<User> page = new Page<>(userParam.getPageIndex(),userParam.getPageSize());
        //Page<User> userPage = userMapper.selectPage(page, queryWrapper);
        queryWrapper.like("user_name", userParam.getUserName());
        Page<UserVO> userVOPage = userMapper.findByPage(PageUtil.newPage(userParam), queryWrapper);
        return new ResultPage<>(userVOPage);
    }

    @Override
    public void export(UserParam userParam, HttpServletResponse response) throws IOException {
        try {
            response.setContentType("application/vnd.ms-excel;charset=UTF-8;attachment;");
            response.setCharacterEncoding("utf-8");
            response.setHeader("Content-disposition", "attachment;filename=用户信息.xlsx");
            EasyExcel.write(response.getOutputStream(), UserDTO.class).sheet("模板")
                    .doWrite(BeanUtil.copyToList(userMapper.selectList(Wrappers.query()), UserDTO.class));
            response.getOutputStream().flush();
        } catch (Exception e) {
            log.error(e.getMessage(),e);
        }finally {
            response.getOutputStream().close();
        }

    }

    @Override
    public Map<String,Integer> importUser(MultipartFile multipartFile) {
        Map<String, Integer> map = new HashMap<String, Integer>(){{put("success",0);}};
        try {
            UserExcelListen listen = new UserExcelListen(this);
            EasyExcel.read(multipartFile.getInputStream(), UserDTO.class,listen).sheet("Sheet1").doRead();
            map.put("success", listen.getSuccessNum());
            map.put("total", listen.getTotalNum());
            map.put("fail", listen.getErrorNum());
            return map;
        } catch (Exception e) {
           log.error(e.getMessage(),e);
        }
        return map;
    }

    @Override
    public void addUser(UserParam userParam) {
        if (StringUtils.isBlank(userParam.getStatus())) {
            userParam.setStatus("0");
        }
        User user = BeanUtil.copyProperties(userParam, User.class);
        userMapper.insert(user);
        List<Integer> roleList = userParam.getRoleList();
        if (CollectionUtil.isNotEmpty(roleList)) {
            for (Integer roleId : roleList) {
                userMapper.insertRoleRelate(user.getId(),roleId);
            }
        }
    }

    @Override
    public void deleteUser(List<String> ids) {
        if (CollectionUtil.isNotEmpty(ids)) {
            userMapper.deleteBatchIds(ids);
        }
    }

    @Override
    public UserInfoVO getUser(Integer id) {
        return userMapper.getUser(id);
    }

    @Override
    public void updateUser(UserParam userParam) {
        User byId = userMapper.selectById(userParam.getId());
        BeanUtil.copyProperties(userParam, byId);
        userMapper.updateByPrimaryKey(byId);
        List<Integer> roleList = userParam.getRoleList();
        if (CollectionUtil.isNotEmpty(roleList)) {
            userMapper.deleteRoleReate(byId.getId());
            for (Integer roleId : roleList) {
                userMapper.insertRoleRelate(byId.getId(),roleId);
            }
        }
    }

}

