package com.stan.system.login;

import com.stan.system.user.entity.UserInfo;
import com.stan.utils.MD5Util;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Service
public class SysUser {

    public UserInfo getUser(){
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        //已经拿到session,就可以拿到session中保存的用户信息了。
        UserInfo user = (UserInfo) request.getSession().getAttribute(MD5Util.encodeByMd5("login_cuser"));
        return user;
    }


}
