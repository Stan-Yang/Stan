package com.stan.system.dept.service;

import com.stan.system.dept.entity.Dept;

import java.util.List;
import java.util.Map;

public interface IDeptService {

    public Dept findById(Integer deptno);

    public List<Map> findAll(Map param);

    public void insertDept(Dept dept);

    public void deleteDept(Integer deptno);

    public void updateDept(Dept dept);

}
