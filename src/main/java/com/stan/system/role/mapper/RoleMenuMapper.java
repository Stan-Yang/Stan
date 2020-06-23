package com.stan.system.role.mapper;

import com.stan.system.role.entity.RoleMenuInfo;

public interface RoleMenuMapper {

    public int insertRoleMenu(RoleMenuInfo roleMenuInfo);

    public int deleteRoleMenu(Integer roleid);

}
