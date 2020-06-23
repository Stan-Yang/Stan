package com.stan.system.dept.service;

import com.stan.system.dept.entity.Dept;
import com.stan.system.dept.mapper.DeptMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
public class DeptServiceImpl implements IDeptService {

    @Autowired
    private DeptMapper deptMapper;

    public Dept findById(Integer deptno){
        return deptMapper.findById(deptno);
    }

    public List<Map> findAll(Map param){
        return deptMapper.findAll(param);
    }

    @Transactional(rollbackFor =Exception.class )
    public void insertDept(Dept dept){
        deptMapper.insertDept(dept);
    }

    @Transactional(rollbackFor =Exception.class )
    public void deleteDept(Integer deptno){
        deptMapper.deleteDept(deptno);
    }

    @Transactional(rollbackFor =Exception.class )
    public void updateDept(Dept dept){
        deptMapper.updateDept(dept);
    }
}
