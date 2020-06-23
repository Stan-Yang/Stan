package com.stan.system.order.entity;

public class OrderInfo {

    private Integer orderid;
    private String ordername;
    private String orderremark;
    private String createdate;
    private String isdelete;

    public OrderInfo(Integer orderid,String ordername,String orderremark,String createdate,String isdelete) {
        this.orderid = orderid;
        this.ordername = ordername;
        this.orderremark = orderremark;
        this.createdate = createdate;
        this.isdelete = isdelete;
    }

    public Integer getOrderid() {
        return orderid;
    }

    public void setOrderid(Integer orderid) {
        this.orderid = orderid;
    }

    public String getOrdername() {
        return ordername;
    }

    public void setOrdername(String ordername) {
        this.ordername = ordername;
    }

    public String getOrderremark() {
        return orderremark;
    }

    public void setOrderremark(String orderremark) {
        this.orderremark = orderremark;
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
}
