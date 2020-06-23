package com.stan.system.log.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.stan.system.log.entity.LogInfo;
import com.stan.system.log.service.ILogService;
import com.stan.utils.AjaxResult;
import com.stan.utils.StringUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class LogController {

    @Autowired
    private ILogService logService;

    /**
     * 日志列表数据
     * @param  log
     * @param  page
     * */
    @RequiresPermissions(value="log:loglist:view")
    @GetMapping("/log/loglist")
    public String log(HttpServletRequest request, LogInfo log, PageInfo page, ModelMap modelMap){
        int pageNum = page.getPageNum()==0 ? 1 : page.getPageNum();
        int pageSize =  page.getPageSize()==0 ? 10 : page.getPageSize();

        String begintime = StringUtil.stringNull(request.getParameter("starttime"));
        String endtime = StringUtil.stringNull(request.getParameter("endtime"));

        Map<String, Object> map = new HashMap<>();
        map.put("remark",log.getRemark());
        map.put("operator",log.getOperator());
        map.put("begintime",begintime);
        map.put("endtime",endtime);

        //使用PageHelper做分页查询
        PageHelper.startPage(pageNum, pageSize);
        List<LogInfo> loglist = logService.findLogList(map);
        PageInfo<LogInfo> pageInfo = new PageInfo<LogInfo>(loglist);

        //页码
        StringBuffer pagestr = new StringBuffer();
        modelMap.put("pageInfo", pageInfo);
        modelMap.put("rows", loglist.size());
        modelMap.put("loginfo", log);
        modelMap.put("starttime", begintime);
        modelMap.put("endtime", endtime);

        return "log/loglist";
    }


    /**
     * 删除日志
     *
     * @param logid 日志id
     */
    @RequiresPermissions(value="log:delete")
    @PostMapping("/log/delete")
    @ResponseBody
    public AjaxResult logDelete(Integer logid) {
        String msg = "操作成功！";
        int rows = logService.deleteLog(logid);
        return rows > 0 ? AjaxResult.success(msg) : AjaxResult.error(msg);
    }

    /**
     * 批量删除日志
     *
     * @param logids 日志id
     */
    @RequiresPermissions(value="log:deletes")
    @PostMapping("/log/deletes")
    @ResponseBody
    public AjaxResult logDeletes(String logids) {
        String msg = "操作成功！";
        int rows = logService.deleteLogByIds(logids);
        return rows > 0 ? AjaxResult.success(msg) : AjaxResult.error(msg);
    }

}
