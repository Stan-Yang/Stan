package com.stan.system.role.service;

import com.stan.system.role.entity.RoleInfo;
import com.stan.system.role.mapper.RoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
public class RoleServiceImpl implements IRoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public RoleInfo findById(Integer roleid) {
        return roleMapper.findById(roleid);
    }

    @Override
    public List<RoleInfo> findRoleList(Map map) {
        return roleMapper.findRoleList(map);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insertRole(RoleInfo roleInfo) {
        return roleMapper.insertRole(roleInfo);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateRole(RoleInfo roleInfo) {
        return roleMapper.updateRole(roleInfo);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteRole(Integer roleid) {
        return roleMapper.deleteRole(roleid);
    }
}
