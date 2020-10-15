package com.stan;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.EnableAspectJAutoProxy;


@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
//自动扫描mapper包
@MapperScan("com.stan.system.*.mapper")
@EnableAspectJAutoProxy(proxyTargetClass = true,  exposeProxy = true)
public class StanApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(StanApplication.class, args);
        System.out.println("-------------------网站后台管理系统启动成功444444-----------------------");
    }

    //重写configure方法，否则在部署到tomcat时，接口将访问不到
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(StanApplication.class);

    }

}
