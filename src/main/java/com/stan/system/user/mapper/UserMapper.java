package com.stan.system.user.mapper;

import com.stan.system.user.entity.UserInfo;
import org.apache.catalina.User;

import java.util.List;
import java.util.Map;

public interface UserMapper {

    public List<UserInfo> findByList(Map param);

    public UserInfo findById(Integer userid);

    public UserInfo findByLoginName(String loginname);

    public UserInfo findByUsernameAndPassword(Map param);

    public int insertUser(UserInfo userInfo);

    public int deleteUser(Integer userid);

    public int deleteUserByIds(String[] ids);

    public int updateUser(UserInfo userInfo);

    public int updateUserPassword(UserInfo userInfo);

    public int resetPassword(Integer userid);

}
