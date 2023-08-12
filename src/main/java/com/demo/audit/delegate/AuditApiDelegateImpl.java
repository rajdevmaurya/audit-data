package com.demo.audit.delegate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.demo.audit.api.AuditApiDelegate;
import com.demo.audit.service.AuditService;

@Service
public class AuditApiDelegateImpl implements AuditApiDelegate {
	@Autowired
	private AuditService productService;

	public ResponseEntity<Void> triggerPricingRequest() {
		try {
			productService.getAuditService("MANUAL_TRIGGER");
		} catch (Exception e) {
		}
		return new ResponseEntity<>(HttpStatus.OK);
	}

}
