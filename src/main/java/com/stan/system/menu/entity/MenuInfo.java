package com.stan.system.menu.entity;

import java.util.ArrayList;
import java.util.List;

public class MenuInfo {

    private Integer menuid;
    private Integer pid;
    private Integer level;
    private String menuname;
    private String url;
    private String menutype;
    private String perms;
    private String icon;
    private String remark;
    private Integer creates;
    private String createdate;
    private String isdelete;
    private String morder;
    private List<MenuInfo> children = new ArrayList<MenuInfo>();

    public Integer getMenuid() {
        return menuid;
    }

    public void setMenuid(Integer menuid) {
        this.menuid = menuid;
    }

    public Integer getPid() {
        return pid;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public String getMenuname() {
        return menuname;
    }

    public void setMenuname(String menuname) {
        this.menuname = menuname;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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

    public String getIsdelete() {
        return isdelete;
    }

    public void setIsdelete(String isdelete) {
        this.isdelete = isdelete;
    }

    public String getMenutype() {
        return menutype;
    }

    public void setMenutype(String menutype) {
        this.menutype = menutype;
    }

    public String getPerms() {
        return perms;
    }

    public void setPerms(String perms) {
        this.perms = perms;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public List<MenuInfo> getChildren()
    {
        return children;
    }

    public void setChildren(List<MenuInfo> children)
    {
        this.children = children;
    }

    public String getMorder() {
        return morder;
    }

    public void setMorder(String morder) {
        this.morder = morder;
    }
}
