package com.stan.system.menu.controller;

import com.stan.framework.aspectj.lang.annotation.Log;
import com.stan.framework.aspectj.lang.constant.BusinessType;
import com.stan.system.menu.entity.MenuInfo;
import com.stan.system.menu.service.IMenuService;
import com.stan.utils.AjaxResult;
import com.stan.utils.StringUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.Date;
import java.util.List;

@Controller
public class MenuController {

    @Autowired
    private IMenuService menuService;

    /**
     * 跳转到添加菜单列表
     */
    @RequiresPermissions(value="menu:menulist:view")
    @RequestMapping("/menu")
    public String menu(ModelMap modelMap) {
        List<MenuInfo> menulist =menuService.allMenu();
        modelMap.put("menulist" ,menulist);
        modelMap.put("count" ,menulist.size());
        return "menu/menulist";
    }

    /**
     * 菜单树
     */
    @PostMapping("/menu/ztreemenu")
    @ResponseBody
    public AjaxResult menuztree(){
        List<MenuInfo> menulist =menuService.allMenu();
        StringBuffer ztree = new StringBuffer("[");

        for(MenuInfo m : menulist){
            if(!ztree.toString().equals("[")){
                ztree.append(",");
            }
            ztree.append("{\"id\": \""+m.getMenuid()+"\", \"pId\": \""+m.getPid()+"\", \"name\": \""+m.getMenuname()+"\"}");
        }
        ztree.append("]");
        return AjaxResult.success(ztree.toString());
    }

    /**
     * 跳转到添加菜单页面
     */
    @RequiresPermissions(value="menu:add")
    @RequestMapping("/menu/add")
    public String menuadd(Integer menuid,ModelMap mmp){
        MenuInfo menuInfo = new MenuInfo();
        if(menuid!=0){
            menuInfo = menuService.findById(menuid);
        }else{
            menuInfo.setMenuid(0);
            menuInfo.setLevel(0);
        }
        mmp.put("menuinfo",menuInfo);
        return "menu/menuadd";
    }

    /**
     * 修改菜单
     * @param  menuid
     */
    @RequiresPermissions(value="menu:edit")
    @RequestMapping("/menu/edit")
    public String edit(Integer menuid,ModelMap mmp)
    {
        MenuInfo menuInfo = menuService.findById(menuid);
        mmp.put("menuinfo",menuInfo);
        return "menu/menuedit";
    }

    /**
     * 添加或者修改菜单
     * @param menuInfo
     */
    @Log(title = "添加或者修改菜单",action = BusinessType.OTHER)
    @RequestMapping("/menu/menuaddoredit")
    @ResponseBody
    public AjaxResult menucreaet(MenuInfo menuInfo){
        int rows = 0;
        if(null == menuInfo.getMenuid()||menuInfo.getMenuid().equals("")){
            String createdate = StringUtil.stringDate(new Date(),"yyyy-MM-dd HH-mm-ss");
            menuInfo.setIsdelete("0");
            menuInfo.setCreates(1);
            menuInfo.setCreatedate(createdate);
            rows = menuService.insertMenu(menuInfo);
        }else{
            rows = menuService.updateMenu(menuInfo);
        }
        return rows > 0 ? AjaxResult.success() : AjaxResult.error();
    }

    /**
     * 删除菜单
     */
    @Log(title = "删除菜单",action = BusinessType.OTHER)
    @RequiresPermissions(value="menu:delete")
    @RequestMapping("/menu/delete/{menuid}")
    @ResponseBody
    public AjaxResult menudelete(@PathVariable("menuid") Integer menuid){

        List<MenuInfo> list = menuService.findByPid(menuid);
        if(list.size()==0){
            int rows = menuService.deleteMenu(menuid);
            return rows > 0 ? AjaxResult.success() : AjaxResult.error();
        }else{
            return AjaxResult.error("存在下级节点，不能删除！");
        }
    }

}
