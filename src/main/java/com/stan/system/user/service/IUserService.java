package com.stan.system.user.service;

import com.stan.system.user.entity.UserInfo;

import java.util.List;
import java.util.Map;

public interface IUserService {

    public List<UserInfo> findByList(Map param);

    public UserInfo findById(Integer userid);

    public UserInfo findByLoginName(String loginname);

    public UserInfo findByUsernameAndPassword(Map param);

    public int insertUser(UserInfo userInfo);

    public int deleteUser(Integer userid);

    public int deleteUserByIds(String ids);

    public int updateUser(UserInfo userInfo);

    public int updateUserPassword(UserInfo userInfo);

    public int resetPassword(Integer userid);

}
