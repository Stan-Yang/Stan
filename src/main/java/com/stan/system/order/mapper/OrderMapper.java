package com.stan.system.order.mapper;

import com.stan.system.order.entity.OrderInfo;

import java.util.List;
import java.util.Map;

public interface OrderMapper {

    public List<OrderInfo> findByList(Map param);
    public OrderInfo findById(Integer orderid);
    public int insertOrder(OrderInfo orderInfo);
    public int updateOrder(OrderInfo orderInfo);
    public int deleteOrder(Integer orderid);

}
