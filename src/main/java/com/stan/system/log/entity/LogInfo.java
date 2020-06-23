package com.stan.system.log.entity;

import com.stan.system.user.entity.UserInfo;

public class LogInfo {

    private Integer logid;
    private String remark;
    private String operator;
    private Integer operatorid;
    private String IP;
    private String browser;
    private String operationdate;
    private String operationtime;
    private String isdelete;
    private UserInfo userInfo;


    public LogInfo() {
    }

    public LogInfo(Integer logid, String remark, String operator, Integer operatorid, String IP, String browser,String operationdate,String operationtime,String isdelete) {
        this.logid = logid;
        this.remark = remark;
        this.operator = operator;
        this.operatorid = operatorid;
        this.IP = IP;
        this.browser = browser;
        this.operationdate = operationdate;
        this.operationtime = operationtime;
        this.isdelete = isdelete;
    }

    public Integer getLogid() {
        return logid;
    }

    public void setLogid(Integer logid) {
        this.logid = logid;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public Integer getOperatorid() {
        return operatorid;
    }

    public void setOperatorid(Integer operatorid) {
        this.operatorid = operatorid;
    }

    public String getIP() {
        return IP;
    }

    public void setIP(String IP) {
        this.IP = IP;
    }

    public String getBrowser() {
        return browser;
    }

    public void setBrowser(String browser) {
        this.browser = browser;
    }

    public String getOperationdate() {
        return operationdate;
    }

    public void setOperationdate(String operationdate) {
        this.operationdate = operationdate;
    }

    public String getOperationtime() {
        return operationtime;
    }

    public void setOperationtime(String operationtime) {
        this.operationtime = operationtime;
    }

    public String getIsdelete() {
        return isdelete;
    }

    public void setIsdelete(String isdelete) {
        this.isdelete = isdelete;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }
}
