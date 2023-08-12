package com.demo.audit.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.demo.audit.entity.Product;
import com.demo.audit.repository.ProductRepository;

public class DataArchivalServiceImpl implements DataArchivalService{
	
	@Autowired
	private ArchivalService archivalSerice;
	
	@Autowired
	private ProductRepository  productRepository;

	@Override
	public boolean processArchivalReqest() throws Exception {
		boolean success=false;
		List <Product> fetchArchivals =null;
		int chunksSize=1000;
		Integer totalRowCounts=productRepository.totalRowsCount();
		if(totalRowCounts<=0) {
			return true;
		}
		List<Integer> totalBatchSize= getBatchSize(totalRowCounts,chunksSize);
		for(Integer batchSize : totalBatchSize) {
			//fetchArchival=productRepository.findAll(batchSize);
			success=updateStatusToArchive(fetchArchivals);
		}
		
		return success;
	}
	
	
	private boolean updateStatusToArchive(List <Product> fetchArchivals) throws Exception {
		return processArchivalBatch(fetchArchivals);
		
		
	}
	@Transactional
	public boolean processArchivalBatch(List <Product> archivas) throws Exception {
		boolean success = false;
		try {
			int batchSize=100;
			List<Future<Boolean>> saveFuteures=new ArrayList<>();
			for(int i=0;i<archivas.size(); i=i+batchSize) {
				
				if(i+batchSize > archivas.size()) {
					saveFuteures.add(archivalSerice.dataArchivalBatch(archivas.subList(i, i+batchSize)));
					break;
				}
				saveFuteures.add(archivalSerice.dataArchivalBatch(archivas.subList(i, i+batchSize)));
			}
			if(!saveFuteures.isEmpty()) {
				success=saveFuteures.get(0).get();
			}
			
			for (Future<Boolean> f : saveFuteures) {
				success = success && f.get();
				if(!success) {
					break;
				}
			}
		
		return success;
	}catch (Exception e) {
		throw e;
	}
}
	
	private List<Integer> getBatchSize(Integer totalRowCounts,int batchSize){
		List<Integer> batchCount= new ArrayList<>();
		for (int i=0; i<totalRowCounts; i=i+batchSize) {
			if(i+batchSize>totalRowCounts) {
				batchCount.add(batchSize);
				break;
			}
			batchCount.add(batchSize);
		}
		return batchCount;
	}

}
