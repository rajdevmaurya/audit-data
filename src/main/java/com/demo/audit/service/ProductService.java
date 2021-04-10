package com.demo.audit.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.demo.audit.dto.ProductRequest;
import com.demo.audit.entity.Product;
import com.demo.audit.repository.ProductRepository;

@Service
public class ProductService {
	

	@Autowired
	private ProductRepository repository;

	public String saveProduct(ProductRequest<Product> request) {
		Product product = request.getProduct();
		product.setCreatedBy(request.getCreatedBy());
		repository.save(product);
		return "Product saved successfully";

	}

	public String updateProduct(Long id, double price, ProductRequest<Product> request) {
		Product product = repository.findById(id).get();
		if (product != null) {
			product.setPrice(price);
			product.setModifiedBy(request.getModifiedBy());
			repository.saveAndFlush(product);
		} else {
			throw new RuntimeException("Product not found with id : " + id);
		}
		return "Product Updated successfully ";
	}



}
