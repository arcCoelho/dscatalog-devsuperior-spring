package com.devsuperior.devcatalog.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.devcatalog.categories.CategoryRepository;
import com.devsuperior.devcatalog.entities.Category;

@Service
public class CategoryService {

	@Autowired
	CategoryRepository repo;
	
	@Transactional(readOnly = true)
	public List<Category> findAll(){
		return repo.findAll();
	}
	
	
}
