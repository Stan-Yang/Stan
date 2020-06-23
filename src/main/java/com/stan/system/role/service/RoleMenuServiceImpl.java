package com.stan.system.role.service;

import com.stan.system.role.entity.RoleMenuInfo;
import com.stan.system.role.mapper.RoleMenuMapper;
import com.stan.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoleMenuServiceImpl implements IRoleMenuService {

    @Autowired
    private RoleMenuMapper roleMenuMapper;

    @Transactional
    public int insertRoleMenu(Integer roleid,String menuids){

        //删除角色下的所有权限
        roleMenuMapper.deleteRoleMenu(roleid);

        //重新给角色添加权限
        int rows = 0;
        List<RoleMenuInfo> roleMenuList = new ArrayList<>();
        for(String menuid : menuids.split(",")){
            RoleMenuInfo roleMenuInfo = new RoleMenuInfo();
            roleMenuInfo.setMenuid(StringUtil.stringInt(menuid));
            roleMenuInfo.setRoleid(roleid);
            roleMenuList.add(roleMenuInfo);
            rows = rows + roleMenuMapper.insertRoleMenu(roleMenuInfo);
        }
        return rows;
    }

    @Transactional
    public int deleteRoleMenu(Integer roleid) {
        return roleMenuMapper.deleteRoleMenu(roleid);
    }
}
