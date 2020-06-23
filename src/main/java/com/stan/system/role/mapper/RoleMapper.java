package com.stan.system.role.mapper;

import com.stan.system.role.entity.RoleInfo;

import java.util.List;
import java.util.Map;

public interface RoleMapper {

    public RoleInfo findById(Integer roleid);

    public List<RoleInfo> findRoleList(Map map);

    public int insertRole(RoleInfo roleInfo);

    public int updateRole(RoleInfo roleInfo);

    public int deleteRole(Integer roleid);
}
