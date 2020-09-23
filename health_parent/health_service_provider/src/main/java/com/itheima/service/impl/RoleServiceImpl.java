package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.dao.RoleDao;
import com.itheima.pojo.Role;
import com.itheima.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author LLaamar
 * @date 2020/9/22 20:09
 */
@Service(interfaceClass = RoleService.class)
@Transactional(rollbackFor = {})
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleDao roleDao;

    @Override
    public List<Role> findAll() {
        return roleDao.findAll();
    }

    @Override
    public List<Integer> findRoleIdsByUserId(Integer userId) {
        return roleDao.findRoleIdsByUserId(userId);
    }
}
