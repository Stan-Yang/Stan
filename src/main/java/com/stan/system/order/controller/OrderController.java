package com.stan.system.order.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.stan.system.order.entity.OrderInfo;
import com.stan.system.order.service.IOrderService;
import com.stan.utils.AjaxResult;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class OrderController {

    @Autowired
    private IOrderService orderService;

    /**
     * 订单列表分页查询
     *
     */
    @RequiresPermissions(value="order:orderlist:view")
    @GetMapping("/order/orderlist")
    public String postOrderList(OrderInfo order, PageInfo page, ModelMap modelMap){
        int pageNum = page.getPageNum()==0 ? 1 : page.getPageNum();
        int pageSize =  page.getPageSize()==0 ? 10 : page.getPageSize();
        Map<String, Object> map = new HashMap<>();
        String ordername = order.getOrdername();
        map.put("ordername", ordername);
        //使用PageHelper做分页查询
        PageHelper.startPage(pageNum, pageSize);
        List<OrderInfo> orderlist = orderService.findByList(map);
        PageInfo<OrderInfo> pageInfo = new PageInfo<OrderInfo>(orderlist);

        //页码
        StringBuffer pagestr = new StringBuffer();
        modelMap.put("pageInfo", pageInfo);
        modelMap.put("rows", orderlist.size());
        modelMap.put("orderinfo", order);

        return "order/orderlist";
    }

    /**
     * 修改订单信息
     *
     * @param orderid 订单id
     */
    @RequiresPermissions(value="order:update")
    @GetMapping("/order/update")
    public String userUpdate(Integer orderid, ModelMap modelMap) {
        OrderInfo orderInfo = orderService.findById(orderid);
        modelMap.put("orderInfo", orderInfo);
        return "order/orderedit";
    }


    /**
     * 保存订单信息
     *
     * @param orderInfo d订单信息
     */
    @RequiresPermissions(value="order:save")
    @PostMapping("/order/save")
    @ResponseBody
    public AjaxResult userUpdate(OrderInfo orderInfo) {
        int rows = orderService.updateOrder(orderInfo);
        return rows > 0 ? AjaxResult.success() : AjaxResult.error();
    }


    /**
     * 删除订单信息
     *
     * @param orderid d订单Id
     */
    @RequiresPermissions(value="order:delete")
    @PostMapping("/order/delete")
    @ResponseBody
    public AjaxResult userdelete(Integer orderid) {
        int rows = orderService.deleteOrder(orderid);
        return rows > 0 ? AjaxResult.success() : AjaxResult.error();
    }



}
