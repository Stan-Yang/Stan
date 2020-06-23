package com.stan.system.role.entity;

public class RoleInfo {

    private Integer roleid;
    private String rolename;
    private String remark;
    private Integer creates;
    private String createdate;
    private String updatedate;
    private String operator;
    private String isdelete;

    public RoleInfo() {
    }

    public RoleInfo(Integer roleid, String rolename, String remark, Integer creates, String createdate,String updatedate,String operator, String isdelete) {
        this.roleid = roleid;
        this.rolename = rolename;
        this.remark = remark;
        this.creates = creates;
        this.createdate = createdate;
        this.updatedate = updatedate;
        this.operator = operator;
        this.isdelete = isdelete;
    }

    public Integer getRoleid() {
        return roleid;
    }

    public void setRoleid(Integer roleid) {
        this.roleid = roleid;
    }

    public String getRolename() {
        return rolename;
    }

    public void setRolename(String rolename) {
        this.rolename = rolename;
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

    public String getUpdatedate() {
        return updatedate;
    }

    public void setUpdatedate(String updatedate) {
        this.updatedate = updatedate;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getIsdelete() {
        return isdelete;
    }

    public void setIsdelete(String isdelete) {
        this.isdelete = isdelete;
    }
}
