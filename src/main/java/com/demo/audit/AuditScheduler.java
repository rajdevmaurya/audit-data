package com.demo.audit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.demo.audit.service.AuditService;

import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;

@Component
public class AuditScheduler {
	
	@Autowired
	private AuditService auditService;


	@Scheduled(cron = "0 */1 * * * *")
	@SchedulerLock(name = "shortRunningTask", lockAtMostFor = "5m", lockAtLeastFor = "1m")
	public void triggerAuditTask() throws InterruptedException {
		
		
		auditService.getAuditService("AUTO_SCHEDULE");
	}
}
