package com.demo.audit.service;

import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.demo.audit.entity.Product;
import com.demo.audit.repository.ProductRepository;


@Service
public class ArchivalServiceImpl implements ArchivalService{
	
	private final ProductRepository productRepository;
	
	@Autowired
	public ArchivalServiceImpl(ProductRepository productRepository) {
		this.productRepository=productRepository;
	}
	
    @Transactional(propagation =Propagation.NESTED)
    @Async("enrichThreadPoolTaskExecutorBean")
	@Override
	public Future<Boolean> dataArchivalBatch(List<Product> batch) throws Exception {
    	boolean success = false;
    	try {
    		long startTime=System.currentTimeMillis();
    		List<Product> saveList=productRepository.saveAll(batch);
    		if(saveList.size()==batch.size()) {
    			success=true;
    		}
    		System.out.println(""+ TimeUnit.MILLISECONDS.toMillis(System.currentTimeMillis()-startTime));
    	}catch (Exception e) {
    		System.out.println(""+e);
			throw e;
		}
	 
		return AsyncResult.forValue(success);
	}

}
