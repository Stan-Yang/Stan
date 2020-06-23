package com.stan.system.login;

import com.stan.framework.aspectj.lang.annotation.Log;
import com.stan.framework.aspectj.lang.constant.BusinessType;
import com.stan.system.menu.entity.MenuInfo;
import com.stan.system.menu.service.IMenuService;
import com.stan.system.user.entity.UserInfo;
import com.stan.system.user.service.IUserService;
import com.stan.utils.AjaxResult;
import com.stan.utils.MD5Util;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
public  class LoginController {

    @Autowired
    private IUserService userService;

    @Autowired
    private IMenuService menuService;

    @Autowired
    private SysUser sysUser;

    @GetMapping("/login")
    public String login(HttpServletRequest request, HttpServletResponse response)
    {
//        // 如果是Ajax请求，返回Json字符串。
//        if (ServletUtils.isAjaxRequest(request))
//        {
//            return ServletUtils.renderString(response, "{\"code\":\"1\",\"msg\":\"未登录或登录超时。请重新登录\"}");
//        }

        return "login";
    }

    /**
     * 登录认证
     * */
    @Log(title = "用户登录",action = BusinessType.OTHER)
    @PostMapping("/login")
    @ResponseBody
    public AjaxResult login(
            @RequestParam(value = "username", required = true) String userName,
            @RequestParam(value = "password", required = true) String password,
            @RequestParam(value = "rememberMe", required = true, defaultValue = "false") boolean rememberMe)
    {
        password = MD5Util.encodeByMd5(password);
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(userName, password);
        token.setRememberMe(rememberMe);

        try {
            subject.login(token);
        } catch (AuthenticationException e) {
            //e.printStackTrace();
            return AjaxResult.error("您的账号或密码输入错误!");
        }

        return AjaxResult.success("登录成功！");
    }


    /**
     * 跳转到首页
     */
    @GetMapping("/index")
    public String index(ModelMap mmp) {
        List<MenuInfo> menulist = menuService.findByPid(0);
        for(MenuInfo menuInfo : menulist){
            if(menuInfo.getMenutype().equals("M")){
                List<MenuInfo> list = menuService.findByPid(menuInfo.getMenuid());
                menuInfo.setChildren(list);
            }
        }

        //当前登录用户信息
        UserInfo cuser = sysUser.getUser();
        mmp.put("menulist",menulist);
        mmp.put("cuser",cuser);
        return "index";
    }


    /**
     * welcome
     */
    @GetMapping("/html/welcome")
    public String welcome(HttpServletRequest request) {
        return "html/welcome";
    }

    /**
     * 图标
     */
    @GetMapping("/unicode")
    public String unicode() {
        return "html/unicode";
    }

    /**
     * 按钮
     */
    @GetMapping("/buttons")
    public String buttons() {
        return "html/buttons";
    }

    /**
     * 403
     */
    @GetMapping("/unauth")
    public String unauth() {
        return "error/unauth";
    }


    /**
     * 注销登录
     * @return
     */
    @Log(title = "用户退出",action = BusinessType.OTHER)
    @GetMapping("/loginout")
    public String loginOut() {
        System.out.println("------------------------");
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return "login";
    }

}
