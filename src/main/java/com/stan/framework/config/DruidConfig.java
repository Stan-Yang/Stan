package com.stan.framework.config;


import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class DruidConfig {

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.druid.master")
    public DataSource druid(){
        DruidDataSource ds = new DruidDataSource();
        return ds;
    }

    @Bean
    public ServletRegistrationBean statViewServlet(){
        //创建StatViewServlet,绑定到/druid/*路劲下
        ServletRegistrationBean bean = new ServletRegistrationBean(new StatViewServlet(),"/druid/*");
        Map<String,String> param = new HashMap<String,String>();
//        param.put("loginUsername","sysadmin");
//        param.put("loginPassword","123456");
        param.put("allow","");
        //param.put("deny","33.31.51.66");//设置不允许这个ip访问
        bean.setInitParameters(param);
        return bean;
    }
}
