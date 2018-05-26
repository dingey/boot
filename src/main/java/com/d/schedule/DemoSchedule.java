package com.d.schedule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Profile({ "test", "prod" })
public class DemoSchedule {
	private Logger logger = LoggerFactory.getLogger(DemoSchedule.class);
	private int minutesCount = 1;
	private int secondsCount = 1;

	// "0 0 * * * *" 表示每小时0分0秒执行一次
	// " */10 * * * * *" 表示每10秒执行一次
	// "0 0 8-10 * * *" 表示每天8，9，10点执行
	// "0 0/30 8-10 * * *" 表示每天8点到10点，每30分钟执行
	// "0 0 9-17 * * MON-FRI" 表示每周一至周五，9点到17点的0分0秒执行
	// "0 0 0 25 12 ?" 表示每年圣诞节（12月25日）0时0分0秒执行
	@Scheduled(cron = "0 0/10 * * * ?")
	public void run() {
		logger.info("每{}分钟执行一次，" + "第{}次执行方法", "10", minutesCount++);
	}

	@Scheduled(cron = "${schedule.every.ten.minutes}")
	public void runSecond() {
		logger.info("每{}秒执行一次，" + "第{}次执行方法", "600", secondsCount++);
	}
}
