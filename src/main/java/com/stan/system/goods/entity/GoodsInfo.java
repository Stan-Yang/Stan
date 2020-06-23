package com.stan.system.goods.entity;

public class GoodsInfo {

    private Integer goodsid;
    private String goodsname;
    private String goodsremark;
    private Float goodsprice;
    private Integer goodsnumber;
    private String createdate;
    private String isdelete;

    public GoodsInfo(Integer goodsid, String goodsname, String goodsremark, Float goodsprice, Integer goodsnumber, String createdate, String isdelete) {
        this.goodsid = goodsid;
        this.goodsname = goodsname;
        this.goodsremark = goodsremark;
        this.goodsprice = goodsprice;
        this.goodsnumber = goodsnumber;
        this.createdate = createdate;
        this.isdelete = isdelete;
    }

    public Integer getGoodsid() {
        return goodsid;
    }

    public void setGoodsid(Integer goodsid) {
        this.goodsid = goodsid;
    }

    public String getGoodsname() {
        return goodsname;
    }

    public void setGoodsname(String goodsname) {
        this.goodsname = goodsname;
    }

    public String getGoodsremark() {
        return goodsremark;
    }

    public void setGoodsremark(String goodsremark) {
        this.goodsremark = goodsremark;
    }

    public Float getGoodsprice() {
        return goodsprice;
    }

    public void setGoodsprice(Float goodsprice) {
        this.goodsprice = goodsprice;
    }

    public Integer getGoodsnumber() {
        return goodsnumber;
    }

    public void setGoodsnumber(Integer goodsnumber) {
        this.goodsnumber = goodsnumber;
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
