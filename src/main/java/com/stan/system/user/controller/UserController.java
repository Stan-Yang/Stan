package com.stan.system.user.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.stan.framework.aspectj.lang.annotation.Log;
import com.stan.framework.aspectj.lang.constant.BusinessType;
import com.stan.framework.config.StanConfig;
import com.stan.system.login.SysUser;
import com.stan.system.user.entity.UserInfo;
import com.stan.system.user.service.IUserService;
import com.stan.utils.AjaxResult;
import com.stan.utils.MD5Util;
import com.stan.utils.StringUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class UserController {

    @Autowired
    private IUserService userService;

    @Autowired
    private SysUser sysUser;

    @RequestMapping("/page")
    public String page() {
        return "page";
    }

    /**
     * 用户列表分页查询
     *
     */
    @RequiresPermissions(value="user:userlist:view")
    @RequestMapping("/user/userlist")
    public String userList(UserInfo user,PageInfo page,ModelMap modelMap) {
        int pageNum = page.getPageNum()==0 ? 1 : page.getPageNum();
        int pageSize =  page.getPageSize()==0 ? 10 : page.getPageSize();
        String username = StringUtil.stringNull(user.getUsername());

        Map<String, Object> map = new HashMap<>();
        map.put("username", username);
        //使用PageHelper做分页查询
        PageHelper.startPage(pageNum, pageSize);
        List<UserInfo> userlist = userService.findByList(map);
        PageInfo<UserInfo> pageInfo = new PageInfo<UserInfo>(userlist);

        //页码
        StringBuffer pagestr = new StringBuffer();
        modelMap.put("pageInfo", pageInfo);
        modelMap.put("rows", userlist.size());
        modelMap.put("userinfo", user);

        return "user/userlist";
    }


    /**
     * 修改用户启用、停用状态
     *
     * @param userid 用户信息
     * @param status 状态（0：启用，1停用）
     */
    @Log(title = "修改用户启用、停用状态",action = BusinessType.UPDATE)
    @RequiresPermissions(value="user:status")
    @RequestMapping("/user/status")
    @ResponseBody
    public AjaxResult updateStatus(Integer userid, String status) {
        UserInfo user = userService.findById(userid);
        user.setStatus(status);
        int rows = userService.updateUser(user);
        return rows > 0 ? AjaxResult.success() : AjaxResult.error();
    }

    /**
     * 添加用户信息
     */
    @RequiresPermissions(value="user:add")
    @GetMapping("/user/add")
    public String userAddPage() {
        return "user/useradd";
    }

    /**
     * 添加保存用户信息
     *
     * @param userinfo 用户信息
     */
    @Log(title = "添加保存用户信息",action = BusinessType.INSERT)
    @PostMapping("/user/insert")
    @ResponseBody
    public AjaxResult insertUser(HttpServletRequest request, UserInfo userinfo) {

        Map map = new HashMap();
        map.put("username", userinfo.getUsername());
        List list = userService.findByList(map);
        if (list.size() > 1) {
            return AjaxResult.error("用户名已存在！");
        } else {
            String status = userinfo.getStatus();
            if (status.equals("启用")) {
                userinfo.setStatus("0");
            } else {
                userinfo.setStatus("1");
            }
            userinfo.setPassword(MD5Util.encodeByMd5(userinfo.getPassword()));
            String createdate = StringUtil.stringDate(new Date(), "yyyy-MM-dd HH-mm-ss");
            userinfo.setIsdelete("0");
            userinfo.setCreates(sysUser.getUser().getUserid());
            userinfo.setCreatedate(createdate);
            userinfo.setLoginip(request.getRemoteHost());
            userinfo.setLogindate(createdate);
            int rows = userService.insertUser(userinfo);
            return rows > 0 ? AjaxResult.success() : AjaxResult.error();
        }
    }

    /**
     * 修改用户信息
     *
     * @param userid 用户id
     */
    @RequiresPermissions(value="user:update")
    @GetMapping("/user/update")
    public String userUpdate(Integer userid, ModelMap modelMap) {
        UserInfo userinfo = userService.findById(userid);
        modelMap.put("userinfo", userinfo);
        return "user/useredit";
    }

    /**
     * 修改保存用户信息
     *
     * @param userinfo 用户信息
     */
    @Log(title = "修改保存用户信息",action = BusinessType.UPDATE)
    @PostMapping("/user/update")
    @ResponseBody
    public AjaxResult userUpdate(UserInfo userinfo) {
        String status = StringUtil.stringNull(userinfo.getStatus());
        if (status.equals("on")) {
            userinfo.setStatus("0");
        } else {
            userinfo.setStatus("1");
        }
        int rows = userService.updateUser(userinfo);
        return rows > 0 ? AjaxResult.success() : AjaxResult.error();
    }

    /**
     * 修改用户密码
     *
     * @param userid 用户id
     */
    @RequiresPermissions(value="user:updatePass:edit")
    @GetMapping("/user/updatePass")
    public String userUpdatePass(Integer userid, ModelMap modelMap) {
        UserInfo userinfo = userService.findById(userid);
        modelMap.put("userinfo", userinfo);
        return "user/password";
    }

    /**
     * 修改保存用户密码
     *
     * @param userinfo 用户信息
     */
    @Log(title = "修改保存用户密码",action = BusinessType.UPDATE)
    @PostMapping("/user/updatePass")
    @ResponseBody
    public AjaxResult userUpdatePass(UserInfo userinfo) {

        Map map = new HashMap();
        map.put("username", userinfo.getUsername());
        List list = userService.findByList(map);
        if (list.size() > 1) {
            return AjaxResult.error("用户名已存在！");
        } else {
            userinfo.setPassword(MD5Util.encodeByMd5(userinfo.getPassword()));
            int rows = userService.updateUserPassword(userinfo);
            return rows > 0 ? AjaxResult.success() : AjaxResult.error();
        }
    }

    /**
     * 重置密码 重置后密码为：123456
     *
     * @param userid 用户id
     */
    @Log(title = "重置密码",action = BusinessType.UPDATE)
    @RequiresPermissions(value="user:resetPassword")
    @PostMapping("/user/resetPassword")
    @ResponseBody
    public AjaxResult resetPassword(Integer userid) {
        int rows = userService.resetPassword(userid);
        return rows > 0 ? AjaxResult.success() : AjaxResult.error();
    }

    /**
     * 修改用户头像
     *
     * @param userid 用户id
     */
    @GetMapping("/user/avatar")
    public String userAvatar(Integer userid, ModelMap modelMap) {
        UserInfo userinfo = userService.findById(userid);
        modelMap.put("user", userinfo);
        return "user/avatar";
    }

    /**
     * 保存头像
     */
    @Log(title = "保存头像", action = BusinessType.UPDATE)
    @PostMapping("/user/updateAvatar")
    @ResponseBody
    public AjaxResult updateAvatar(Integer userId, @RequestParam("avatarfile") MultipartFile file)
    {
        UserInfo user = userService.findById(userId);

        //图片类型
        String sufix = file.getContentType().substring(file.getContentType().lastIndexOf("/")+1);
        //文件名
        String filename = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date())+"."+sufix;

        user.setAvatar(filename);

        userService.updateUser(user);

        try {

            File filemkdir = new File("D:\\IdeaProjects\\Stan\\src\\main\\resources\\static\\images\\profile");
            if(!filemkdir.exists()){
                filemkdir.mkdirs();
            }
            FileCopyUtils.copy(file.getInputStream(),new FileOutputStream(filemkdir.getPath()+"\\" +filename));

            FileCopyUtils.copy(file.getInputStream(),new FileOutputStream(StanConfig.getProfile() +filename));
        } catch (IOException e) {
            e.printStackTrace();
            return AjaxResult.error();
        }
        return AjaxResult.success("修改成功！,"+(StanConfig.getProfile() +filename).replaceAll("D:",""));
    }

    /**
     * 删除用户信息
     *
     * @param userid 用户id
     */
    @Log(title = "删除用户信息",action = BusinessType.DELETE)
    @RequiresPermissions(value="user:delete")
    @PostMapping("/user/delete")
    @ResponseBody
    public AjaxResult userDelete(Integer userid) {
        String msg = "操作成功！";
        int rows = 0;
        UserInfo user = userService.findById(userid);
        if (user.getRoleid() == 1) {
            msg = user.getUsername() + "是系统管理员，不能删除！";
        } else {
            rows = userService.deleteUser(userid);
        }
        return rows > 0 ? AjaxResult.success(msg) : AjaxResult.error(msg);
    }

    /**
     * 批量删除用户信息
     *
     * @param userids 用户id
     */
    @Log(title = "批量删除用户信息",action = BusinessType.DELETE)
    @RequiresPermissions(value="user:deletes")
    @PostMapping("/user/deletes")
    @ResponseBody
    public AjaxResult userDelete(String userids) {
        int rows = userService.deleteUserByIds(userids);
        String msg = "操作成功！";
        if (rows == -1) {
            msg = "删除的用户中存在系统管理员，不能删除！";
        }
        return rows > 0 ? AjaxResult.success(msg) : AjaxResult.error(msg);
    }


}
