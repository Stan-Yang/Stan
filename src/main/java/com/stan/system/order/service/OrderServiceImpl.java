package com.stan.system.order.service;

import com.stan.system.order.entity.OrderInfo;
import com.stan.system.order.mapper.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
public class OrderServiceImpl implements IOrderService {

    @Autowired
    private OrderMapper orderMapper;


    @Override
    public List<OrderInfo> findByList(Map param) {
        return orderMapper.findByList(param);
    }

    @Override
    public OrderInfo findById(Integer orderid) {
        return orderMapper.findById(orderid);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insertOrder(OrderInfo orderInfo) {
        return orderMapper.insertOrder(orderInfo);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateOrder(OrderInfo orderInfo) {
        return orderMapper.updateOrder(orderInfo);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteOrder(Integer orderid) {
        return orderMapper.deleteOrder(orderid);
    }
}
