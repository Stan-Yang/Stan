package com.stan.system.menu.service;

import com.stan.system.menu.entity.MenuInfo;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface IMenuService {

    public MenuInfo findById(Integer menuid);

    public List<MenuInfo> findByPid(Integer pid);

    public List<MenuInfo> findByRoleid(Integer roleid);

    public List<MenuInfo> allMenu();

    public List<MenuInfo> findMenuList();

    public int insertMenu(MenuInfo menuInfo);

    public int updateMenu(MenuInfo menuInfo);

    public int deleteMenu(Integer menuid);

}
