package com.stan.system.goods.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.stan.system.goods.entity.GoodsInfo;
import com.stan.system.goods.service.IGoodsService;
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
public class GoodsController {

    @Autowired
    public IGoodsService goodsService;

    @RequiresPermissions("goods:goodsList:view")
    @GetMapping("/goods/goodsList")
    public String goodsList(GoodsInfo goodsInfo, PageInfo page, ModelMap modelMap){

        Map<String, Object> map = new HashMap<>();
        String goodsname = goodsInfo.getGoodsname();
        map.put("goodsname", goodsname);
        List<GoodsInfo> goodslist = goodsService.findByList(map);

//        //使用PageHelper做分页查询
//        int pageNum = page.getPageNum()==0 ? 1 : page.getPageNum();
//        int pageSize =  page.getPageSize()==0 ? 10 : page.getPageSize();
//        PageHelper.startPage(pageNum, pageSize);
//        PageInfo<GoodsInfo> pageInfo = new PageInfo<GoodsInfo>(goodslist);
//        //页码
//        StringBuffer pagestr = new StringBuffer();
//        modelMap.put("pageInfo", pageInfo);
//        modelMap.put("rows", goodslist.size());

        pageHelperUtil(page,modelMap,goodslist);

        modelMap.put("goodsInfo", goodsInfo);
        return "goods/goodsList";
    }

    private <T> void pageHelperUtil(PageInfo page, ModelMap modelMap,List<T> list){
        int pageNum = page.getPageNum()==0 ? 1 : page.getPageNum();
        int pageSize =  page.getPageSize()==0 ? 10 : page.getPageSize();
        PageHelper.startPage(pageNum, pageSize);
        PageInfo<T> pageInfo = new PageInfo<T>(list);
        StringBuffer pagestr = new StringBuffer();
        modelMap.put("pageInfo", pageInfo);
        modelMap.put("rows", list.size());
    }

    @RequiresPermissions("goods:goodsInsert:view")
    @GetMapping("/goods/goodsInsert")
    public String goodsInsert(){
        return "goods/goodsadd";
    }

    @RequiresPermissions("goods:goodsInsert:save")
    @PostMapping("/goods/goodsInsert/save")
    @ResponseBody
    public AjaxResult goodsInsert(GoodsInfo goodsInfo){
        int rows =goodsService.insertGoods(goodsInfo);
        return rows > 0 ? AjaxResult.success() : AjaxResult.error();
    }

    @RequiresPermissions("goods:goodsUpdate:view")
    @GetMapping("/goods/goodsUpdate")
    public String goodsUpate(Integer goodsid,ModelMap modelMap){
        GoodsInfo goodsInfo = goodsService.findById(goodsid);
        modelMap.put("goodsInfo", goodsInfo);
        return "goods/goodsedit";
    }

    @RequiresPermissions("goods:goodsUpdate:save")
    @PostMapping("/goods/goodsUpdate/save")
    @ResponseBody
    public AjaxResult goodsUpdate(GoodsInfo goodsInfo){
        int rows =goodsService.updateGoods(goodsInfo);
        return rows > 0 ? AjaxResult.success() : AjaxResult.error();
    }

    @RequiresPermissions("goods:goodsDelete")
    @PostMapping("/goods/goodsDelete")
    @ResponseBody
    public AjaxResult goodsDelete(Integer goodsid){
        int rows =goodsService.deleteGoods(goodsid);
        return rows > 0 ? AjaxResult.success() : AjaxResult.error();
    }


}
