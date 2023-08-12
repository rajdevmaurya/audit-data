 
package com.demo.audit.service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.demo.audit.entity.Lock;
import com.demo.audit.repository.LockRepository;

@Service
public class AuditService{
	
 private  HashMap<String, Integer> queueMap=new HashMap<String, Integer>();
	 
	@Autowired
	private LockRepository lockRepository;

	
	public void getAuditService(String msz) throws InterruptedException {
		String lockValue = null;
		List<Lock> s = lockRepository.findAll();
		if (s.size() > 0) {
		LocalDateTime dt=	s.get(0).getCreateDt();
		
		
			
			s.get(0).getCreateDt();
			lockValue = s.size()+"";
		}
		if (queueMap.get("ADD_MANUAL_TRIGGER_IN_QUE") != null && queueMap.get("ADD_MANUAL_TRIGGER_IN_QUE") > 0) {
			System.out.println("Value found in Queue map size"+queueMap.size());
			queueMap.clear();
			msz = "MANUAL_TRIGGER";
		}
		if (msz.equals("MANUAL_TRIGGER")) {
			System.err.println("MANUAL_TRIGGER request coming...");
			if (lockValue == null) {
				System.err.println("MANUAL_TRIGGER trigger started >>" + msz);
				heyBatchRunIt(msz);
				System.err.println("MANUAL_TRIGGER trigger ended >>" + msz);
			} else {
				System.err.println(msz+" job hold it  for re run ");
				//if(queueMap.size()>=1) {
					//return mess one more 
				//}
				
				queueMap.put("ADD_MANUAL_TRIGGER_IN_QUE", 1);
			}

		} else {
			System.out.println("Auto request coming...");
			if (msz.equals("AUTO_SCHEDULE")) {
				if (lockValue == null) {
					System.out.println("AUTO_SCHEDULE trigger started >>>" + msz);
					heyBatchRunIt(msz);
					System.out.println("AUTO_SCHEDULE trigger ended >>>" + msz);
				} else {
					System.out.println("Auto Job skipped");
				}
			}
		}
	}
 

	public void heyBatchRunIt(String msz) throws InterruptedException {
		System.out.println("Batch strated running...");
		lockRepository.deleteAll();
		Lock lock = new Lock();
		lock.setName(msz);
		lock.setCreateDt(LocalDateTime.now());
		lockRepository.save(lock);
		Thread.sleep(30000);
		lockRepository.delete(lock);
	}
}
