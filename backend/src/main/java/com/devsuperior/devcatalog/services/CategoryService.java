package com.devsuperior.devcatalog.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.devsuperior.devcatalog.categories.CategoryRepository;
import com.devsuperior.devcatalog.entities.Category;

@Service
public class CategoryService {

	@Autowired
	CategoryRepository repo;
	
	public List<Category> findAll(){
		return repo.findAll();
	}
	
	
}
