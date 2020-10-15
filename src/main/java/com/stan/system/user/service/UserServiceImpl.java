package com.stan.system.user.service;

import com.stan.framework.aspectj.lang.annotation.Log;
import com.stan.framework.aspectj.lang.annotation.ReadOnly;
import com.stan.framework.aspectj.lang.constant.BusinessType;
import com.stan.system.login.SysUser;
import com.stan.system.user.entity.UserInfo;
import com.stan.system.user.mapper.UserMapper;
import com.stan.utils.MD5Util;
import com.stan.utils.StringUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Lazy
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private SysUser sysUser;


    @ReadOnly
    public List<UserInfo> findByList(Map param){
        System.out.println("-------------------");
        return userMapper.findByList(param);
    }

    public UserInfo findById(Integer userid) {
        return userMapper.findById(userid);
    }

    public UserInfo findByLoginName(String loginname){
       return userMapper.findByLoginName(loginname);
    }

    public UserInfo findByUsernameAndPassword(Map param) {
        return userMapper.findByUsernameAndPassword(param);
    }

    //Transactional添加事物
    @Transactional(rollbackFor = Exception.class)
    public int insertUser(UserInfo userInfo) {
        return userMapper.insertUser(userInfo);
    }

    @Transactional(rollbackFor = Exception.class)
    public int deleteUser(Integer userid) {
        return userMapper.deleteUser(userid);
    }

    @Transactional(rollbackFor = Exception.class)
    public int updateUser(UserInfo userInfo) {
        UserInfo user = sysUser.getUser();
        if(user.getUserid().equals(userInfo.getUserid())){
            //设置用户session
            Session session = SecurityUtils.getSubject().getSession();
            session.setAttribute(MD5Util.encodeByMd5("login_cuser"), userInfo);
        }
        return userMapper.updateUser(userInfo);
    }

    @Transactional(rollbackFor = Exception.class)
    public int updateUserPassword(UserInfo userInfo){
        UserInfo user = sysUser.getUser();
        if(user.getUserid().equals(userInfo.getUserid())){
            //设置用户session
            Session session = SecurityUtils.getSubject().getSession();
            session.setAttribute(MD5Util.encodeByMd5("login_cuser"), userInfo);
        }
        return userMapper.updateUserPassword(userInfo);
    }

    @Transactional(rollbackFor = Exception.class)
    public int resetPassword(Integer userid){
        return userMapper.resetPassword(userid);
    }

    @Transactional(rollbackFor = Exception.class)
    public int deleteUserByIds(String userids){
        String[] userIds = userids.split(",");
        int rows = 0;
        T:for(String userid : userIds){
            UserInfo user = userMapper.findById(StringUtil.stringInt(userid));
            if(user.getRoleid()==1){
                rows=-1;
               continue T;
            }
        }

        return rows==-1?-1:userMapper.deleteUserByIds(userIds);
    }
}
