package com.stan.framework.shiro;

import com.stan.system.login.SysUser;
import com.stan.system.menu.entity.MenuInfo;
import com.stan.system.menu.service.IMenuService;
import com.stan.system.role.entity.RoleInfo;
import com.stan.system.role.service.IRoleService;
import com.stan.system.user.entity.UserInfo;
import com.stan.system.user.service.IUserService;
import com.stan.utils.MD5Util;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ShiroRealm extends AuthorizingRealm {
    private Logger logger =  LoggerFactory.getLogger(this.getClass());

    @Autowired
    private IUserService userService;

    @Autowired
    private IRoleService roleService;

    @Autowired
    private IMenuService menuService;

    @Autowired
    private SysUser sysUser;

    /**
     * 登录认证
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        logger.info("doGetAuthenticationInfo +"  + authenticationToken.toString());

        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        String userName=token.getUsername();
        UserInfo user = userService.findByLoginName(token.getUsername());
        if (user != null) {
            //设置用户session
            Session session = SecurityUtils.getSubject().getSession();
            session.setAttribute(MD5Util.encodeByMd5("login_cuser"), user);

            return new SimpleAuthenticationInfo(userName,user.getPassword(),getName());
        } else {
            return null;
        }
    }

    /**
     * 授权
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        logger.info("doGetAuthorizationInfo+"+principalCollection.toString());
        UserInfo user = userService.findByLoginName((String) principalCollection.getPrimaryPrincipal());

        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        RoleInfo roleInfo = roleService.findById(user.getRoleid());
        // 角色加入AuthorizationInfo认证对象
        Set<String> rolesSet = new HashSet<>();
        rolesSet.add(roleInfo.getRolename());
        info.setRoles(rolesSet);

        // 权限加入AuthorizationInfo认证对象
        List<MenuInfo>  menus = menuService.findByRoleid(user.getRoleid());
        Set<String> permsSet = new HashSet<>();
        for(MenuInfo menuInfo : menus){
            permsSet.add(menuInfo.getPerms());
        }
        info.setStringPermissions(permsSet);
        return info;
    }

    /**
     * 清理缓存权限
     */
    public void clearCachedAuthorizationInfo()
    {
        this.clearCachedAuthorizationInfo(SecurityUtils.getSubject().getPrincipals());
    }

}
