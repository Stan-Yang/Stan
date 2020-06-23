package com.stan.system.goods.mapper;

import com.stan.system.goods.entity.GoodsInfo;

import java.util.List;
import java.util.Map;

public interface GoodsMapper {

    public List<GoodsInfo> findByList(Map map);

    public GoodsInfo findById(Integer goodsid);

    public int insertGoods(GoodsInfo goodsInfo);

    public int updateGoods(GoodsInfo goodsInfo);

    public int deleteGoods(Integer goodsid);

}
