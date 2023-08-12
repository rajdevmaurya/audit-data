package com.demo.audit.service;

import java.util.List;
import java.util.concurrent.Future;

import com.demo.audit.entity.Product;

public interface ArchivalService {
	
	Future<Boolean> dataArchivalBatch(List<Product> batch) throws Exception;

}
