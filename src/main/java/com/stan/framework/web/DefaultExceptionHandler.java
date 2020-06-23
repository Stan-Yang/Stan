package com.stan.framework.web;

import com.alibaba.druid.support.json.JSONUtils;
import com.stan.framework.aspectj.lang.annotation.Log;
import com.stan.framework.aspectj.lang.constant.BusinessType;
import com.stan.utils.AjaxResult;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@RestControllerAdvice
public class DefaultExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(DefaultExceptionHandler.class);

//    /**
//     * 权限校验失败
//     */
//    @ExceptionHandler(AuthorizationException.class)
//    public AjaxResult handleAuthorizationException(AuthorizationException e)
//    {
//        log.error(e.getMessage());
//        return AjaxResult.error(PermissionUtils.getMsg(e.getMessage()));
//    }


    /**
     * 权限异常
     * @Title: authorizationException
     * @Description: TODO
     * @Date 2018年4月11日 下午2:19:18
     * @author OnlyMate
     * @param request
     * @param response
     * @return
     */
    @ExceptionHandler({ UnauthorizedException.class, AuthorizationException.class })
    public String authorizationException(HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (isAjaxRequest(request)) {
            writeJson(AjaxResult.error("当前登录用户无该权限"), response);
            return null;
        } else {
            response.sendRedirect("/unauth");
            return "";
        }
    }

    /**
     * 输出JSON
     * @Title: writeJson
     * @Description: TODO
     * @Date 2018年4月11日 下午2:18:10
     * @author OnlyMate
     * @param returnJson
     * @param response
     */
    private void writeJson(AjaxResult returnJson, HttpServletResponse response) {
        PrintWriter out = null;
        try {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json; charset=utf-8");
            out = response.getWriter();
            out.write(JSONUtils.toJSONString(returnJson));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }


    /**
     * 是否是Ajax请求
     * @Title: isAjaxRequest
     * @Description: TODO
     * @Date 2018年4月11日 下午2:19:31
     * @author OnlyMate
     * @param request
     * @return
     */
    public static boolean isAjaxRequest(HttpServletRequest request) {
        String requestedWith = request.getHeader("x-requested-with");
        if (requestedWith != null && requestedWith.equalsIgnoreCase("XMLHttpRequest")) {
            return true;
        } else {
            return false;
        }
    }




    /**
     * 请求方式不支持
     */
    @ExceptionHandler({ HttpRequestMethodNotSupportedException.class })
    public AjaxResult handleException(HttpRequestMethodNotSupportedException e)
    {
        log.error(e.getMessage(), e);
        return AjaxResult.error("不支持' " + e.getMethod() + "'请求");
    }

    /**
     * 拦截未知的运行时异常
     */
    @Log(title = "运行时异常", action = BusinessType.UPDATE)
    @ExceptionHandler(RuntimeException.class)
    public AjaxResult notFount(RuntimeException e)
    {
        log.error("运行时异常:", e);
        return AjaxResult.error("运行时异常:" + e.getMessage());
    }

    /**
     * 系统异常
     */
    @Log(title = "服务器错误，请联系管理员", action = BusinessType.UPDATE)
    @ExceptionHandler(Exception.class)
    public AjaxResult handleException(Exception e)
    {
        log.error(e.getMessage(), e);
        return AjaxResult.error("服务器错误，请联系管理员");
    }


}
