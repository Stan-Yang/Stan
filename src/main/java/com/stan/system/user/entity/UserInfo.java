package com.stan.system.user.entity;

public class UserInfo {

    private Integer userid;
    private String username;
    private String loginname;
    private String password;
    private Integer roleid;
    private String avatar;
    private String sex;
    private Integer age;
    private String phone;
    private String email;
    private String isjob;
    private String jobtype;
    private String status;
    private String remark;
    private Integer creates;
    private String createdate;
    private String logindate;
    private String loginip;
    private String isdelete;

    public UserInfo() {
    }

    public UserInfo(Integer userid, String username, String loginname, String password, Integer roleid, String avatar, String sex, Integer age, String phone, String email, String isjob, String jobtype, String status, String remark, Integer creates, String createdate, String logindate, String loginip, String isdelete) {
        this.userid = userid;
        this.username = username;
        this.loginname = loginname;
        this.password = password;
        this.roleid = roleid;
        this.avatar = avatar;
        this.sex = sex;
        this.age = age;
        this.phone = phone;
        this.email = email;
        this.isjob = isjob;
        this.jobtype = jobtype;
        this.status = status;
        this.remark = remark;
        this.creates = creates;
        this.createdate = createdate;
        this.logindate = logindate;
        this.loginip = loginip;
        this.isdelete = isdelete;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getLoginname() {
        return loginname;
    }

    public void setLoginname(String loginname) {
        this.loginname = loginname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getRoleid() {
        return roleid;
    }

    public void setRoleid(Integer roleid) {
        this.roleid = roleid;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIsjob() {
        return isjob;
    }

    public void setIsjob(String isjob) {
        this.isjob = isjob;
    }

    public String getJobtype() {
        return jobtype;
    }

    public void setJobtype(String jobtype) {
        this.jobtype = jobtype;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getCreates() {
        return creates;
    }

    public void setCreates(Integer creates) {
        this.creates = creates;
    }

    public String getCreatedate() {
        return createdate;
    }

    public void setCreatedate(String createdate) {
        this.createdate = createdate;
    }

    public String getLogindate() {
        return logindate;
    }

    public void setLogindate(String logindate) {
        this.logindate = logindate;
    }

    public String getLoginip() {
        return loginip;
    }

    public void setLoginip(String loginip) {
        this.loginip = loginip;
    }

    public String getIsdelete() {
        return isdelete;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setIsdelete(String isdelete) {
        this.isdelete = isdelete;
    }


}
