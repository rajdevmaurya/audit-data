package com.demo.audit.service;
/*
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.demo.audit.entity.Lock;
import com.demo.audit.repository.LockRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AuditService {
	
 private  HashMap<String, Integer> map=new HashMap<String, Integer>();
	 
	@Autowired
	private LockRepository lockRepository;

	
	public String getAuditService(String msz) throws InterruptedException {
		String lockValue = null;
		List<Lock> s = lockRepository.findAll();
		if (s.size() > 0) {
			lockValue = s.size() + "";
		}
		log.info("Value in map" + map);
		if (map.get("SKIP") != null && map.get("SKIP") > 0) {
			log.info("Value found in Queue");
			log.info("Map size" + map.size());
			map.clear();
			msz = "MANUAL_TRIGGER";
		}
		if (msz.equals("MANUAL_TRIGGER")) {
			log.error("MANUAL_TRIGGER request coming===========>");
			if (lockValue == null) {
				log.error("MANUAL_TRIGGER calling.===================>>" + msz);
				Lock lock = new Lock();
				lock.setName("MANUAL_TRIGGER");
				lock.setCreateDt(LocalDateTime.now());
				lockRepository.save(lock);
				heyBatchRunIt();
				lockRepository.delete(lock);
				log.error("MANUAL_TRIGGER calling.....ended===================>>" + msz);
			} else {
				log.error("Job skip for " + msz);
				map.put("SKIP", 1);
			}

		} else {
			log.info("Auto request coming===========>");
			if (msz.equals("AUTO_SCHEDULE")) {
				if (lockValue == null) {
					log.info("AUTO_SCHEDULE calling..............>>" + msz);
					Lock ll = new Lock();
					ll.setName("AUTO_SCHEDULE");
					ll.setCreateDt(LocalDateTime.now());
					lockRepository.save(ll);
					heyBatchRunIt();
					lockRepository.delete(ll);
					log.info("AUTO_SCHEDULE calling.....ended.........>>" + msz);
				} else {
					log.info(" Job skip for " + msz);
				}
			}
		}
		return "Hi Product";
	}
 

	public void heyBatchRunIt() throws InterruptedException {
		log.info("Batch running");
		Thread.sleep(30000);
		
		
	}
}
*/