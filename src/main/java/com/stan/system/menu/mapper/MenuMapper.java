package com.stan.system.menu.mapper;

import com.stan.system.menu.entity.MenuInfo;

import java.util.List;

public interface MenuMapper {

    public MenuInfo findById(Integer menuid);

    public List<MenuInfo> findByPid(Integer pid);

    public List<MenuInfo> findByRoleid(Integer userid);

    public List<MenuInfo> findMenuList();

    public int insertMenu(MenuInfo menuInfo);

    public int updateMenu(MenuInfo menuInfo);

    public int deleteMenu(Integer menuid);

}
