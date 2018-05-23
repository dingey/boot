package com.d.config;

import java.util.concurrent.Executors;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

@Configuration
public class ScheduleConfig implements SchedulingConfigurer {
	Logger logger = LoggerFactory.getLogger(SchedulingConfigurer.class);
	int size = 1;// Runtime.getRuntime().availableProcessors();

	@PostConstruct
	public void initMethod() {
		logger.info("定时任务线程池初始化成功，大小{}。", size);
	}

	@Override
	public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
		taskRegistrar.setScheduler(Executors.newScheduledThreadPool(size));
	}
}
