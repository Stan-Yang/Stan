package com.stan.system.log.service;

import com.stan.system.log.entity.LogInfo;
import com.stan.system.log.mapper.LogMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
public class LogServiceImpl implements ILogService {

    @Autowired
    private LogMapper logMapper;

    @Override
    public LogInfo findById(Integer logid) {
        return logMapper.findById(logid);
    }

    @Override
    public List<LogInfo> findLogList(Map map) {
        return logMapper.findLogList(map);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insertLog(LogInfo logInfo) {
        return logMapper.insertLog(logInfo);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteLog(Integer logid) {
        return logMapper.deleteLog(logid);
    }

    @Transactional(rollbackFor = Exception.class)
    public int deleteLogByIds(String ids){
        String[] logIds = ids.split(",");
        return logMapper.deleteLogByIds(logIds);
    }
}
