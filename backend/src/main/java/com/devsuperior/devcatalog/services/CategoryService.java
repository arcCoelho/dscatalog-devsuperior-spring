package com.devsuperior.devcatalog.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.devcatalog.categories.CategoryRepository;
import com.devsuperior.devcatalog.dto.CategoryDTO;
import com.devsuperior.devcatalog.entities.Category;
import com.devsuperior.devcatalog.services.exceptions.EntityNotFoundException;

@Service
public class CategoryService {

	@Autowired
	CategoryRepository repo;
	
	@Transactional(readOnly = true)
	public List<CategoryDTO> findAll(){
		List<Category> lista = repo.findAll();
		
		return lista.parallelStream().map(x -> new CategoryDTO(x)).collect(Collectors.toList());
	}

	@Transactional(readOnly = true)
	public CategoryDTO findById(Long id) {
		Optional<Category> opt = repo.findById(id);		
		Category entity = opt.orElseThrow(() -> new EntityNotFoundException("Categoria não encontrada") );
		return new CategoryDTO(opt.get());
	}
	
	
}
