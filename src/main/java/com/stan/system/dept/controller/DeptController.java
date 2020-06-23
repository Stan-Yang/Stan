package com.stan.system.dept.controller;

import com.stan.system.dept.entity.Dept;
import com.stan.system.dept.service.IDeptService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class DeptController {

    @Autowired
    private IDeptService deptService;

    @RequestMapping(value = "/dept",method = RequestMethod.GET)
    public List<Map> findAll(Integer deptno){
        Map param = new HashMap();
        param.put("pdeptno",deptno);
        List<Map> list = deptService.findAll(param);
        return list;
    }

    @RequestMapping(value = "/dept",method = RequestMethod.POST)
    public Dept insertDept(){
        Dept dept = new Dept(null,"市場部","市場部");
        deptService.insertDept(dept);
        return dept;
    }

    @RequestMapping("/dept/updateDept")
    public Dept updateDept(){
        Dept dept = new Dept(2,"市場部1","市場部1");
        deptService.updateDept(dept);
        return dept;
    }
    @RequiresPermissions(value="dept:deletDept")
    @RequestMapping("/dept/deleteDept")
    public Dept deleteDept(Integer deptno){
        Dept dept = new Dept(2,"市場部1","市場部1");
        return dept;
    }
}
