package com.stan.system.log.service;

import com.stan.system.log.entity.LogInfo;

import java.util.List;
import java.util.Map;

public interface ILogService {

    public LogInfo findById(Integer logid);

    public List<LogInfo> findLogList(Map map);

    public int insertLog(LogInfo logInfo);

    public int deleteLog(Integer logid);

    public int deleteLogByIds(String ids);

}
