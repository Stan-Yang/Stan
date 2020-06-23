package com.stan.framework.config;

import org.springframework.context.annotation.Configuration;

/**
 * 定时任务配置
 *
 * @author stan
 *
 */
@Configuration
public class ScheduleConfig {

    /**
     * 定时器
     * */
//    @Bean
//    public Scheduler jobDetail() throws SchedulerException{
//        //创建一个jobDetail的实例，将该实例与HelloJob Class绑定
//        JobDetail jobDetail = JobBuilder.newJob(HelloJob.class).withIdentity("myJob").build();
//        //创建一个Trigger触发器的实例，定义该job立即执行，并且每2秒执行一次，一直执行
//        SimpleTrigger trigger = TriggerBuilder.newTrigger().withIdentity("myTrigger").startNow().withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(2).repeatForever()).build();
//        //创建schedule实例
//        StdSchedulerFactory factory = new StdSchedulerFactory();
//        Scheduler scheduler = factory.getScheduler();
//        scheduler.start();
//        scheduler.scheduleJob(jobDetail,trigger);
//        return  scheduler;
//    }

}
