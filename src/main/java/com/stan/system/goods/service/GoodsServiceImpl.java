package com.stan.system.goods.service;

import com.stan.system.goods.entity.GoodsInfo;
import com.stan.system.goods.mapper.GoodsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class GoodsServiceImpl implements IGoodsService {

    @Autowired
    private GoodsMapper goodsMapper;

    @Override
    public List<GoodsInfo> findByList(Map map) {
        return goodsMapper.findByList(map);
    }

    @Override
    public GoodsInfo findById(Integer goodsid) {
        return goodsMapper.findById(goodsid);
    }

    @Override
    public int insertGoods(GoodsInfo goodsInfo) {
        return goodsMapper.insertGoods(goodsInfo);
    }

    @Override
    public int updateGoods(GoodsInfo goodsInfo) {
        return goodsMapper.updateGoods(goodsInfo);
    }

    @Override
    public int deleteGoods(Integer goodsid) {
        return goodsMapper.deleteGoods(goodsid);
    }
}
