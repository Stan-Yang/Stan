package com.stan.system.role.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.stan.framework.aspectj.lang.annotation.Log;
import com.stan.framework.aspectj.lang.constant.BusinessType;
import com.stan.system.login.SysUser;
import com.stan.system.menu.entity.MenuInfo;
import com.stan.system.menu.service.IMenuService;
import com.stan.system.role.entity.RoleInfo;
import com.stan.system.role.service.IRoleMenuService;
import com.stan.system.role.service.IRoleService;
import com.stan.system.user.entity.UserInfo;
import com.stan.utils.AjaxResult;
import com.stan.utils.StringUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

@Controller
public class RoleController {

    @Autowired
    private IRoleService roleService;

    @Autowired
    private SysUser sysUser;

    @Autowired
    private IRoleMenuService roleMenuService;

    @Autowired
    private IMenuService menuService;

    /**
     *
     * 角色列表数据
     * @param roleInfo 角色信息
     * @param page 分页信息
     *
     */
    @RequiresPermissions(value="role:rolelist:view")
    @GetMapping("/role/rolelist")
    public String rolelist(RoleInfo roleInfo,PageInfo page, ModelMap modelMap){

        int pageNum = page.getPageNum()==0 ? 1 : page.getPageNum();
        int pageSize =  page.getPageSize()==0 ? 10 : page.getPageSize();
        String rolename = StringUtil.stringNull(roleInfo.getRolename());
        String operator = StringUtil.stringNull(roleInfo.getOperator());

        Map<String, Object> map = new HashMap<>();
        map.put("rolename", rolename);
        map.put("operator", operator);
        //使用PageHelper做分页查询
        PageHelper.startPage(pageNum, pageSize);
        List<RoleInfo> rolelist = roleService.findRoleList(map);
        PageInfo<RoleInfo> pageInfo = new PageInfo<RoleInfo>(rolelist);

        //页码
        StringBuffer pagestr = new StringBuffer();
        modelMap.put("pageInfo", pageInfo);
        modelMap.put("roleInfo", roleInfo);

        return "/role/rolelist";
    }

    /**
     *
     * 添加角色
     *
     */
    @RequiresPermissions(value="role:add")
    @GetMapping("/role/add")
    public String roleadd(){
        return "/role/roleadd";
    }

    /**
     *
     * 添加角色保存
     * @param roleInfo 角色信息
     *
     */
    @Log(title = "角色添加",action = BusinessType.INSERT)
    @PostMapping("/role/add")
    @ResponseBody
    public AjaxResult roleadd(RoleInfo roleInfo){

        UserInfo userInfo = sysUser.getUser();
        roleInfo.setCreatedate(StringUtil.stringDate(new Date(),"yyyy-MM-dd"));
        roleInfo.setCreates(userInfo.getUserid());
        roleInfo.setOperator(userInfo.getUsername());
        roleInfo.setUpdatedate(StringUtil.stringDate(new Date(),"yyyy-MM-dd HH:mm:ss"));
        roleInfo.setIsdelete("0");

        int rows = roleService.insertRole(roleInfo);
        return rows > 0 ? AjaxResult.success() : AjaxResult.error();
    }

    /**
     *
     * 修改角色
     * @param roleid 角色id
     *
     * */
    @RequiresPermissions(value="role:edit")
    @GetMapping("/role/edit")
    public String roleedit(Integer roleid,ModelMap modelMap){
        RoleInfo roleInfo = roleService.findById(roleid);
        modelMap.put("roleInfo",roleInfo);
        return "/role/roleedit";
    }

    /**
     *
     * 角色修改保存
     * @param roleInfo 角色信息
     *
     */
    @Log(title = "角色修改",action = BusinessType.UPDATE)
    @PostMapping("/role/edit")
    @ResponseBody
    public AjaxResult roleedit(RoleInfo roleInfo){
        roleInfo.setUpdatedate(StringUtil.stringDate(new Date(),"yyyy-MM-dd HH:mm:ss"));
        int rows = roleService.updateRole(roleInfo);
        return rows > 0 ? AjaxResult.success() : AjaxResult.error();
    }

    /**
     *
     * 角色删除
     * @param roleid 角色id
     *
     */
    @Log(title = "角色删除",action = BusinessType.DELETE)
    @RequiresPermissions(value="role:delete")
    @PostMapping("/role/delete")
    @ResponseBody
    public AjaxResult delete(Integer roleid){
        int rows = roleService.deleteRole(roleid);
        return rows > 0 ? AjaxResult.success() : AjaxResult.error();
    }

    /**
     *
     * 角色权限添加
     * @param roleid 角色id
     *
     */
    @RequiresPermissions(value="role:rolepermission:add")
    @GetMapping("/role/rolepermission")
    public String rolepermission(Integer roleid,ModelMap modelMap){
        RoleInfo roleInfo = roleService.findById(roleid);
        List<MenuInfo> menulist = menuService.findByRoleid(roleid);
        String menuids = "";
        for(MenuInfo m : menulist){
            menuids = menuids.equals("") ? m.getMenuid()+"" : menuids+","+m.getMenuid();
        }
        modelMap.put("roleInfo",roleInfo);
        modelMap.put("menuids",menuids);
        return "/role/permission";
    }

    /**
     *
     * 角色权限添加保存
     * @param roleid 角色id
     *
     */
    @Log(title = "添加角色权限",action = BusinessType.INSERT)
    @PostMapping("/role/rolepermissionadd")
    @ResponseBody
    public AjaxResult rolepermission(Integer roleid,String menuids){
        int rows = roleMenuService.insertRoleMenu(roleid,menuids);
        return rows > 0 ? AjaxResult.success() : AjaxResult.error();
    }

    @GetMapping("/role/roletree")
    public String roletree(String id, ModelMap modelMap){

        return "/role/roletree";
    }

    @PostMapping("/role/roletree")
    @ResponseBody
    public String roletree(String id){
        StringBuffer ztree = new StringBuffer("[");
        if(null == id){
            ztree.append("{id: 1, pId: 0, name: \"角色\",isParent:\"true\", open: true}");
        }else{
            Map map = new HashMap();
            map.put("rolename", null);
            map.put("operator", null);
            List<RoleInfo> rolelist = roleService.findRoleList(map);
            for(RoleInfo r : rolelist){
                ztree.append("{id: "+r.getRoleid()+", pId: "+id+", name: \""+r.getRolename()+"\"},");
            }

        }
        ztree.append("]");
        return ztree.toString();
    }

}
