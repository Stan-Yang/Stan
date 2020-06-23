package com.stan.framework.aspectj;

import com.stan.framework.aspectj.lang.annotation.Log;
import com.stan.system.log.entity.LogInfo;
import com.stan.system.log.service.ILogService;
import com.stan.system.login.SysUser;
import com.stan.system.user.entity.UserInfo;
import com.stan.utils.IpUtils;
import com.stan.utils.StringUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Date;

@Aspect
@Component
@EnableAsync
public class LogAspect {

    private static final Logger log = LoggerFactory.getLogger(LogAspect.class);

    @Autowired
    private ILogService logService;

    @Autowired
    private SysUser sysUser;

    // 配置织入点
    @Pointcut("@annotation(com.stan.framework.aspectj.lang.annotation.Log)")
    public void logPointCut()
    {
    }

    /**
     * 返回之后通知 用于拦截操作
     *
     * @param joinPoint 切点
     */
    @AfterReturning(pointcut = "logPointCut()")
    public void doBefore(JoinPoint joinPoint)
    {
        handleLog(joinPoint, null);
    }

    /**
     * 拦截异常操作
     *
     * @param joinPoint
     * @param e
     */
    @AfterThrowing(value = "logPointCut()", throwing = "e")
    public void doAfter(JoinPoint joinPoint, Exception e)
    {
        handleLog(joinPoint, e);
    }

    @Async
    protected void handleLog(final JoinPoint joinPoint, final Exception e)
    {

        try {
            Log controllerLog = getAnnotationLog(joinPoint);
            if (controllerLog == null) {
                return;
            }
            System.out.println(joinPoint.getSignature().getName());

            //当前登录用户信息
            UserInfo user = sysUser.getUser();
            LogInfo logInfo = new LogInfo();
            logInfo.setRemark(controllerLog.title());
            logInfo.setOperator(user.getUsername());
            logInfo.setOperatorid(user.getUserid());
            logInfo.setOperationdate(StringUtil.stringDate(new Date(),"yyyy-MM-dd"));
            logInfo.setOperationtime(StringUtil.stringDate(new Date(),"HH:mm:ss"));
            logInfo.setIP(IpUtils.getIpAddr());
            logInfo.setBrowser(IpUtils.getBrowser());
            logInfo.setIsdelete("0");

            //添加日志记录
            logService.insertLog(logInfo);

            log.info("日志-----------------------"+user.getUsername()+":"+controllerLog.title());
        }catch (Exception exp)
        {
            // 记录本地异常日志
            log.error("==前置通知异常==");
            log.error("异常信息:{}", exp.getMessage());
            exp.printStackTrace();
        }
    }

    /**
     * 是否存在注解，如果存在就获取
     */
    private Log getAnnotationLog(JoinPoint joinPoint) throws Exception
    {
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();

        if (method != null)
        {
            return method.getAnnotation(Log.class);
        }
        return null;
    }

}
