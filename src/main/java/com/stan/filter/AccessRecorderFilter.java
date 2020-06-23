package com.stan.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Date;

@Component
public class AccessRecorderFilter implements Filter {
    private Logger logger = LoggerFactory.getLogger(AccessRecorderFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest)servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession(true);
        String ua = request.getHeader("user-agent");
        String ip = request.getRemoteHost();
        String uri =request.getRequestURI();
        Long st = new Date().getTime();
        filterChain.doFilter(servletRequest,servletResponse);
        Long et = new Date().getTime();
        logger.info("uri:{},ip:{},ua:{},time:{}",uri,ip,ua,(et-st));
    }

    @Override
    public void destroy() {

    }
}
