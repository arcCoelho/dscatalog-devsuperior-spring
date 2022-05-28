package com.devsuperior.devcatalog.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.devcatalog.dto.CategoryDTO;
import com.devsuperior.devcatalog.entities.Category;
import com.devsuperior.devcatalog.repositories.CategoryRepository;
import com.devsuperior.devcatalog.services.exceptions.DataBaseException;
import com.devsuperior.devcatalog.services.exceptions.ResourcesNotFoundException;

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
		Category entity = opt.orElseThrow(() -> new ResourcesNotFoundException("Categoria não encontrada") );
		return new CategoryDTO(opt.get());
	}

	@Transactional
	public CategoryDTO insert(CategoryDTO dto) {
		Category category = new Category(dto.getName());
		category = repo.save(category);
		return new CategoryDTO(category);
	}

	@Transactional
	public CategoryDTO update(Long id, CategoryDTO dto) {
		CategoryDTO categoryDto = null;
		try {
			Category category = repo.getOne(id);
			category.setName(dto.getName());
			repo.save(category);
			categoryDto = new CategoryDTO(category);
		} catch (EntityNotFoundException e) {
			throw new ResourcesNotFoundException("O recurso 'Categoria' de id: "+id+" não foi encontrado");
		}
		return categoryDto;
	}
	
	public void delete(Long id) {
		try {
			repo.deleteById(id);
		} catch (EmptyResultDataAccessException en) {
			throw new ResourcesNotFoundException("A categoria informada não existe");
		} catch (DataIntegrityViolationException ev) {
			throw new DataBaseException("A categoria informada não pode ser excluida pois existem produtos anexadas a ela.");
		}
	}

	@Transactional(readOnly = true)
	public Page<CategoryDTO> findAllPaged(PageRequest pageRequest) {
		Page<Category> lista = repo.findAll(pageRequest);
		return lista.map(x -> new CategoryDTO(x));
	}
	
}
