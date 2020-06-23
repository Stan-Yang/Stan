package com.stan.system.menu.service;

import com.stan.system.menu.entity.MenuInfo;
import com.stan.system.menu.mapper.MenuMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MenuServiceImpl implements IMenuService {

    @Autowired
    private MenuMapper menuMapper;

    @Override
    public MenuInfo findById(Integer menuid){
        return menuMapper.findById(menuid);
    }

    @Override
    public List<MenuInfo> findByPid(Integer pid) {
        return menuMapper.findByPid(pid);
    }

    public List<MenuInfo> findByRoleid(Integer roleid){
        return menuMapper.findByRoleid(roleid);
    }


    /**
     * 查询所有菜单
     */
    public List<MenuInfo> allMenu(){
        List<MenuInfo> menulist =new ArrayList<MenuInfo>();
        menuList(0,menulist);
        return menulist;
    }

    /**
     * 递归查询所有除了菜单类型为按钮的所有菜单数据
     * @param pid
     * @param menulist
     */
    public void menuList(Integer pid,List<MenuInfo> menulist){
        List<MenuInfo> tempList = menuMapper.findByPid(pid);
        for(MenuInfo menuInfo : tempList){
            List<MenuInfo> childrenList = menuMapper.findByPid(menuInfo.getMenuid());
            menuInfo.setChildren(childrenList);
            menulist.add(menuInfo);

            if(!menuInfo.getMenutype().equals("B")){
                menuList(menuInfo.getMenuid(),menulist);
            }
        }
    }


    @Override
    public List<MenuInfo> findMenuList() {
        return menuMapper.findMenuList();
    }

    @Override
    public int insertMenu(MenuInfo menuInfo) {
        return menuMapper.insertMenu(menuInfo);
    }

    @Override
    public int updateMenu(MenuInfo menuInfo) {
        return menuMapper.updateMenu(menuInfo);
    }

    @Override
    public int deleteMenu(Integer menuid) {
        return menuMapper.deleteMenu(menuid);
    }
}
