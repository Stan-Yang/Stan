package com.stan.system.dept.mapper;

import com.stan.system.dept.entity.Dept;

import java.util.List;
import java.util.Map;

public interface DeptMapper {

    public Dept findById(Integer deptno);

    public List<Map> findAll(Map param);

    public void insertDept(Dept dept);

    public void deleteDept(Integer deptno);

    public void updateDept(Dept dept);
}
