package com.devsuperior.devcatalog.services;

import java.util.Iterator;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.devcatalog.dto.CategoryDTO;
import com.devsuperior.devcatalog.dto.ProductDTO;
import com.devsuperior.devcatalog.entities.Category;
import com.devsuperior.devcatalog.entities.Product;
import com.devsuperior.devcatalog.repositories.CategoryRepository;
import com.devsuperior.devcatalog.repositories.ProductRepository;
import com.devsuperior.devcatalog.services.exceptions.DataBaseException;
import com.devsuperior.devcatalog.services.exceptions.ResourcesNotFoundException;

@Service
public class ProductService {

	@Autowired
	ProductRepository repo;
	
	@Autowired
	CategoryRepository categoryRepository;

	@Transactional(readOnly = true)
	public Page<ProductDTO> findAllPaged(PageRequest pageRequest) {
		Page<Product> lista = repo.findAll(pageRequest);
		return lista.map(x-> new ProductDTO(x, x.getCategories()));
	}

	@Transactional(readOnly = true)
	public ProductDTO findById(Long id) {
		Optional<Product> opt = repo.findById(id);
		Product entity = opt.orElseThrow( () -> new ResourcesNotFoundException("Produto não encontrado") );
		return new ProductDTO(entity, entity.getCategories());
	}

	@Transactional	
	public ProductDTO insert(ProductDTO dto) {
		Product product = new Product();
		copyDtoToEntity(dto, product);
		repo.save(product);
		return new ProductDTO(product);
	}
	
	@Transactional
	public ProductDTO update(Long id, ProductDTO dto) {
		try {
			Product p = repo.getOne(id);
			copyDtoToEntity(dto, p);
			
			repo.save(p);
			dto = new ProductDTO(p);
		} catch (Exception e) {
			throw new ResourcesNotFoundException("O recurso 'Produto' de id: "+id+" não foi encontrado");
		}
		return dto;
	}

	public void delete(Long id) {
		try {
			repo.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new ResourcesNotFoundException("O Produto informado não existe");
		} catch (DataIntegrityViolationException e) {
			throw new DataBaseException("O Produto informado não pode ser excluido pois existem registros anexados a ele");
		}
		
	}

	private void copyDtoToEntity(ProductDTO dto, Product entity) {
		entity.setDate(dto.getDate());
		entity.setDescription(dto.getDescription());
		entity.setImgUrl(dto.getImgUrl());
		entity.setName(dto.getName());
		entity.setPrice(dto.getPrice());
		
		entity.getCategories().clear();
		
		for(CategoryDTO catDto: dto.getCategories()) {
			Long id = 0L;
			try {
				id = catDto.getId();
				Category cat = categoryRepository.getOne( id );
				entity.getCategories().add(cat);
			} catch (Exception e) {
				throw new ResourcesNotFoundException("O recurso 'Categoria' de id: "+id+" não foi encontrado");
			}
			
		}
		
	}

}
